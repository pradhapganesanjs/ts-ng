package com.pg.springb.front.service;

import java.io.File;
import java.io.FileFilter;
//import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pg.springb.front.util.FrontierConstants;
import com.pg.springb.front.util.XLFileProcessing;
import com.citi.reghub.core.cache.client.CacheClient;
import com.citi.reghub.core.frontier.client.FrontierClient;
import com.citi.reghub.core.frontier.client.FrontierClientConfig;
import com.pg.springb.front.documents.ClientConfigDocument;
import com.pg.springb.front.documents.FileProcessLogDocument;
import com.pg.springb.front.documents.ReportFieldsDocument;
import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportScheduleDocument;
import com.pg.springb.front.documents.ResendConfigDocument;
import com.pg.springb.front.documents.UserDocument;

@Service
public class FileUploadService {

	@Autowired
	DBFacadeService dbFacadeService;
	
	@Autowired
    private CacheClient cacheClient;
	
	@Value("${frontier.upload.path}")
	private String uploadPath;
	
	private FrontierClient frontierClient;
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);
	
	@PostConstruct
	private void initialize() {
		/*
		try {
			FileUtils.forceDelete(new File("/home/reghub/reports/frontier/client_config"));
			FileUtils.forceDelete(new File("/home/reghub/reports/frontier/report_config"));
			FileUtils.forceDelete(new File("/home/reghub/reports/frontier/outbound"));
		}catch(Exception ex) {
			log.error("Error in folder deletion:"+ex.getMessage());
		}*/
        FrontierClientConfig frontierConfig = new FrontierClientConfig(); 
        frontierConfig.put(FrontierClientConfig.FRONTIER_URL_KEY, "/frontier"); 

        frontierConfig.put(FrontierClientConfig.REST_CLIENT, null); 
        frontierConfig.put(FrontierClientConfig.CACHE_CLIENT, cacheClient); 
        frontierConfig.put(FrontierClientConfig.STREAM_CODE, "m2cr"); 
        frontierConfig.put(FrontierClientConfig.FLOW_CODE, "all"); 
        frontierClient = new FrontierClient(frontierConfig);
	}  

	public void addClientConfig() {

		String destPath=uploadPath+FrontierConstants.UPLOAD_FOLDERS[0];
		File[] files = getFilesExcludingSubFolder(destPath, FrontierConstants.UPLOADED_FILE_EXTENSION);
		
		if (files == null || files.length == 0)
			return;
		log.info("<<CLIENT_CONFIG>> folder:"+destPath+", No of Files:"+files.length);
		
		for (File file : files) {
			String status="";
			try {
				List<ClientConfigDocument> xlClientConfigList = new XLFileProcessing().parseClientConfigXL(file);
				if (xlClientConfigList == null || xlClientConfigList.size() <= 0){
					moveFile(file,destPath+FrontierConstants.SUB_FOLDERS[1]);
					updateFileProcessLog(file.getName(),destPath+FrontierConstants.SUB_FOLDERS[1],FrontierConstants.UPLOAD_STATUS[2]);
					continue;}
				else{
				List<ClientConfigDocument> dbClientConfigList = dbFacadeService.getClientConfigDocumentList();
				log.info("<<CLIENT_CONFIG>> xlClientConfigList size:"+xlClientConfigList.size()+" dbClientConfigList:"+dbClientConfigList.size()+" FileName:"+file.getName());
				
				for (ClientConfigDocument xldoc : xlClientConfigList) {
					for (ClientConfigDocument repoDoc : dbClientConfigList) {
						if (xldoc.getClientId().equalsIgnoreCase(repoDoc.getClientId()) && 
								xldoc.getGfcId().equalsIgnoreCase(repoDoc.getGfcId())) {
							xldoc.setId(repoDoc.getId());
							break;}
					}
					xldoc.setLastUpdatedTs(LocalDateTime.now());
				}
				log.info("<<CLIENT_CONFIG>> xlClientConfigList before update:"+xlClientConfigList);
				xlClientConfigList = dbFacadeService.addUpdateClientConfigDocument(xlClientConfigList);
				log.info("<<CLIENT_CONFIG>> xlClientConfigList after update:"+xlClientConfigList);
				xlClientConfigList = dbFacadeService.findClientConfigByStatus("D");
				log.info("<<CLIENT_CONFIG>> clientconfiglist where active status=D:"+xlClientConfigList);
				long val=dbFacadeService.deleteClientConfigbyStatus("D");
				log.info("<<CLIENT_CONFIG>> total no of records deleted="+val);
				destPath=uploadPath+FrontierConstants.UPLOAD_FOLDERS[0]+FrontierConstants.SUB_FOLDERS[0];
				status=FrontierConstants.UPLOAD_STATUS[1];//processed
				frontierClient.evictObject("m2cr_client_reporting_factory");
				}
			}catch (Exception e){
				destPath=uploadPath+FrontierConstants.UPLOAD_FOLDERS[0]+FrontierConstants.SUB_FOLDERS[1];
				status=FrontierConstants.UPLOAD_STATUS[2];//process_failed
				log.error("<<CLIENT_CONFIG>> error:"+e.getMessage());
			}
			moveFile(file,destPath);
			updateFileProcessLog(file.getName(),destPath,status);
		}
	}

	public void addReportConfig() {
		
		String destPath=uploadPath+FrontierConstants.UPLOAD_FOLDERS[1];
		File[] files = getFilesExcludingSubFolder(destPath, FrontierConstants.UPLOADED_FILE_EXTENSION);
		if (files == null || files.length == 0)
			return;
		log.info("<<REPORT_CONFIG>> folder:"+destPath+", No of Files:"+files.length);
		for (File file : files) {
			String status="";
			try {
				List<ReportScheduleDocument> xlReportConfigList = new XLFileProcessing().parseClientReportSchedulesXL(file);
				log.info("<<REPORT_CONFIG>> after file read:"+xlReportConfigList);
				
				if (xlReportConfigList == null || xlReportConfigList.size() <= 0){
					moveFile(file,destPath+FrontierConstants.SUB_FOLDERS[1]);
					updateFileProcessLog(file.getName(),destPath+FrontierConstants.SUB_FOLDERS[1],FrontierConstants.UPLOAD_STATUS[2]);
					continue;}
				List<ReportScheduleDocument> dbReportConfigList = dbFacadeService.getReportScheduleDocumentList();
				log.info("<<REPORT_CONFIG>> xlReportConfigList size:"+xlReportConfigList.size()+" dbReportConfigList size:"+dbReportConfigList.size()+" FileName:"+file.getName());
				
				for (ReportScheduleDocument xlDoc : xlReportConfigList) {
					for (ReportScheduleDocument repoDoc : dbReportConfigList){
						if (xlDoc.getClientId().equalsIgnoreCase(repoDoc.getClientId())) {
							xlDoc.setId(repoDoc.getId());
							break;}
					}
					xlDoc.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_READY);
					xlDoc.setDailyJobStatus(FrontierConstants.CLIENT_JOB_READY);
					xlDoc.setLastUpdatedTs(LocalDateTime.now());
					if(xlDoc.getHashKey()==null || xlDoc.getHashKey().length()<=0)xlDoc.setHashKey(xlDoc.getClientId());
					if(xlDoc.getMtls()==null || xlDoc.getMtls().length()<=0)xlDoc.setMtls("N");
				}
				log.info("<<REPORT_CONFIG>> xlReportConfigList before update:"+xlReportConfigList);
				xlReportConfigList = dbFacadeService.addUpdateReportScheduleDocumentList(xlReportConfigList);
				log.info("<<REPORT_CONFIG>> xlReportConfigList after update:"+xlReportConfigList);
				destPath=uploadPath+FrontierConstants.UPLOAD_FOLDERS[1]+FrontierConstants.SUB_FOLDERS[0];
				status=FrontierConstants.UPLOAD_STATUS[1];//processed
			} catch (Exception e) {
				destPath=uploadPath+FrontierConstants.UPLOAD_FOLDERS[1]+FrontierConstants.SUB_FOLDERS[1];
				status=FrontierConstants.UPLOAD_STATUS[2];//process_failed
				log.error("<<REPORT_CONFIG>> addReportConfigError:"+e.getMessage());
			}
			moveFile(file,destPath);
			updateFileProcessLog(file.getName(),destPath,status);
		}
	}
	
	public String addResendConfig(String fileName,String destPath){
		File file =new File(destPath+fileName);
		String status=FrontierConstants.UPLOAD_STATUS[2];
		String moveToPath=destPath+FrontierConstants.SUB_FOLDERS[1]; //error
		try{
			List<ResendConfigDocument> resendConfigList=new XLFileProcessing().parseResendConfigXL(file);
			if(resendConfigList==null || resendConfigList.size()<=0)
				return status;

			for(ResendConfigDocument resendConfig:resendConfigList){
				List<ReportScheduleDocument> dbRptConfigList = dbFacadeService.getReportConfigByClientId(resendConfig.getClientId());
				log.info("<<RESEND_CONFIG>> dbRptConfigList:"+dbRptConfigList);
				if(dbRptConfigList==null || dbRptConfigList.size()<=0)
					return status;
				ReportScheduleDocument rptConfig=dbRptConfigList.get(0);
				String rptDate=resendConfig.getReportDate();
				String rptTime=resendConfig.getReportTime();
				String frmDate=resendConfig.getFromDate();
				String frmTime=resendConfig.getFromTime();
				
				if("DAILY".equalsIgnoreCase(resendConfig.getReportType())){
					if(rptDate==null || rptDate.length()<=0 || !isValidDate(rptDate, "MM/dd/yyyy"))
						return status;
					if(rptTime!=null && rptTime.length()>0 && !isValidTime(rptTime, "HHmm"))
						return status;
					resendConfig.setEmailTo(rptConfig.getDailyEmailTo());
					resendConfig.setEmailCc(rptConfig.getDailyEmailCc());
					resendConfig.setEmailBcc(rptConfig.getDailyEmailBcc());
					if(rptTime==null || rptTime.length()<=0)
					resendConfig.setReportTime(rptConfig.getDailyTime());
				}else if("INTERVAL".equalsIgnoreCase(resendConfig.getReportType())){
					if(rptDate==null || rptDate.length()<=0 || !isValidDate(rptDate, "MM/dd/yyyy"))
						return status;
					if(rptTime==null || rptTime.length()<=0 || !isValidTime(rptTime, "HHmm"))
						return status;
					if(frmDate==null || frmDate.length()<=0 || !isValidDate(frmDate, "MM/dd/yyyy"))
						return status;
					if(frmTime==null || frmTime.length()<=0 || !isValidTime(frmTime, "HHmm"))
						return status;
					resendConfig.setEmailTo(rptConfig.getIntervalEmailTo());
					resendConfig.setEmailCc(rptConfig.getIntervalEmailCc());
					resendConfig.setEmailBcc(rptConfig.getIntervalEmailBcc());
				}else
					return status;
				resendConfig.setStatus("OPEN");
				resendConfig.setLastUpdatedTs(LocalDateTime.now());
				resendConfig.setOrganization(rptConfig.getOrganization());
				log.info("<<RESEND_CONFIG>> prepared resendConfig:"+resendConfig);
			}
			resendConfigList=dbFacadeService.saveResendConfig(resendConfigList);
			log.info("<<RESEND_CONFIG>> after save:"+resendConfigList);
			status=FrontierConstants.UPLOAD_STATUS[1];
			moveToPath=destPath+FrontierConstants.SUB_FOLDERS[0];
	}catch(Exception e){
		status=FrontierConstants.UPLOAD_STATUS[2];//process_failed
		log.error("<<RESEND_CONFIG>> addResendConfig error:"+e.getMessage());
	}finally{moveFile(file,moveToPath);}
		return status;
	}
	
	public String addReportFields(String upldFileName, String upldPath, LocalDateTime upldTs, String hostName){
		String destPath=upldPath+FrontierConstants.SUB_FOLDERS[1];
		String status=FrontierConstants.UPLOAD_STATUS[2];
		
		File file =new File(upldPath+upldFileName);
		try{
			List<ReportFieldsEntityMapping> xlRptFieldsList=new XLFileProcessing().parseReportingFieldsXL(file, FrontierConstants.REPORT_FIELD_HEADER);
			if(xlRptFieldsList == null || xlRptFieldsList.size()<=0)
				return status;
			ReportFieldsDocument rptFieldsDoc=new ReportFieldsDocument();
			rptFieldsDoc.setFields(xlRptFieldsList);
			
			List<ReportFieldsDocument> rptFieldDocList=dbFacadeService.findAllReportFields();
			if(rptFieldDocList!=null && rptFieldDocList.size()>0){
				dbFacadeService.deleteReportFields(rptFieldDocList);
				log.info("old ReportFieldsDocument deleted:");
			}
			dbFacadeService.saveReportFields(rptFieldsDoc);
			status=FrontierConstants.UPLOAD_STATUS[1];
			destPath=upldPath+FrontierConstants.SUB_FOLDERS[0];
			log.info("new ReportFieldsDocument added:"+rptFieldsDoc);
		}catch(Exception ex){
			log.error("addReportFields error:"+ex.getMessage());
		}finally{
			moveFile(file,destPath);
			addFileProcessLog(FrontierConstants.UPLOAD_DOC_TYPE[3],upldFileName, upldPath, destPath, upldTs, status,hostName);
			}
		return status;
	}
	
	public String addUsers(String upldFileName, String upldPath, LocalDateTime upldTs, String hostName){
		String destPath=upldPath+FrontierConstants.SUB_FOLDERS[1];
		String status=FrontierConstants.UPLOAD_STATUS[2];
		File file =new File(upldPath+upldFileName);
		try{
			List<UserDocument> usersList=new XLFileProcessing().parseUserListXL(file);
			if(usersList.size()<=0)return status;
			List<UserDocument> dbUserList = dbFacadeService.findAllUsers();
			for(UserDocument xlUser:usersList){
				for(UserDocument dbUser:dbUserList){
					if(xlUser.getUserId().equalsIgnoreCase(dbUser.getUserId())){
						xlUser.setId(dbUser.getId());
						break;
					}	
				}	
			}
			usersList=dbFacadeService.saveUsers(usersList);
			log.info("usersList after dbUpdate:"+usersList);
			status=FrontierConstants.UPLOAD_STATUS[1];
			destPath=upldPath+FrontierConstants.SUB_FOLDERS[0];
		}catch (Exception ex) {
			log.error("addUsers error:"+ex.getMessage());
		}finally{
			moveFile(file,destPath);
			addFileProcessLog(FrontierConstants.UPLOAD_DOC_TYPE[4],upldFileName, upldPath, destPath, upldTs, status,hostName);
		}
		return status;
	}
	
	private String getReqestorIdFromUpldFileName(String upldFileName){
		return upldFileName.split(FrontierConstants.FILE_NAME_SEPARATOR)[1];
	}
	
	private boolean isValidDate(String date, String dateFormat){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		try{
			LocalDate.parse(date, formatter);
			return true;
		}catch(Exception e){}
		return false;
	}
	
	private boolean isValidTime(String time, String timeFormat){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
		try{
			LocalTime.parse(time, formatter);
			return true;
			}catch(Exception e){}
		return false;
	}
	
	private File[] getFilesExcludingSubFolder(String folderPath, String extension){
		return new File(folderPath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toLowerCase().endsWith(extension) && pathname.isFile();
			}
		});
	}

	private boolean moveFile(File file, String destFolder) {
		try{
			String newFileName = file.getName();
			File destDir = new File(destFolder);
			Path sourcePath = file.toPath();
			Path destPath = destDir.toPath();
			Files.move(sourcePath, destPath.resolve(newFileName));
			return true;
			} catch (Exception e) {
			log.error("moveFile error:"+e.getMessage());
			}
		return false;
	}
	
	private boolean addFileProcessLog(String docType, String upldFileName, String upldFolder, String desFolder, LocalDateTime upldTs, String upldStatus, String hostName){
		FileProcessLogDocument fileProcessLog=new FileProcessLogDocument();
		try{
			fileProcessLog.setDocType(docType);
			fileProcessLog.setFileName(upldFileName);
			fileProcessLog.setUploadedPath(upldFolder);
			fileProcessLog.setUploadedBy(getReqestorIdFromUpldFileName(upldFileName));
			fileProcessLog.setUploadedTs(upldTs);
			fileProcessLog.setStatus(upldStatus);
			fileProcessLog.setHostName(hostName);
			fileProcessLog.setProcessedFilePath(desFolder);
			fileProcessLog.setProcessedTs(LocalDateTime.now());
			fileProcessLog=dbFacadeService.saveFileProcessLog(fileProcessLog);
			log.info("addFileProcessLog fileProcessLog:"+fileProcessLog);
			return true;
			}catch(Exception e){
			log.error("addFileProcessLog error:"+e.getMessage());
			}
		return false;
	}
	
	private boolean updateFileProcessLog(String fileName, String filePath, String status){
		try{
			List<FileProcessLogDocument> fileProcessLogList=dbFacadeService.findAllFileProcessLogByFileName(fileName);
			if(fileProcessLogList!=null && fileProcessLogList.size()>0){
				FileProcessLogDocument fileProcessLog=fileProcessLogList.get(0);
				fileProcessLog.setStatus(status);
				fileProcessLog.setProcessedFilePath(filePath);
				fileProcessLog.setProcessedTs(LocalDateTime.now());
				dbFacadeService.saveFileProcessLog(fileProcessLog);
				return true;
				}
			}catch(Exception e){
			log.error("updateFileProcessLog error:"+e.getMessage());
			}
			return false;
	}
}

