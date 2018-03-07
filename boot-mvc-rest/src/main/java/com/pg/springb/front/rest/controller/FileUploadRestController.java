package com.pg.springb.front.rest.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pg.springb.front.util.FrontierConstants;
import com.pg.springb.front.documents.FileProcessLogDocument;
import com.pg.springb.front.rest.helper.ApiCommonResponse;
import com.pg.springb.front.rest.helper.Error;
import com.pg.springb.front.service.DBFacadeService;
import com.pg.springb.front.service.FileUploadService;

@Controller
@RequestMapping("/api")
public class FileUploadRestController {
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadRestController.class);

	@Value("${frontier.upload.path}")
	private String uploadPath;
	
	@Autowired
	DBFacadeService dbFacadeService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@PostMapping("/upload")
	public @ResponseBody ApiCommonResponse<String>  fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("filetype") String filetype, @RequestParam("userid") String userId){

		if(StringUtils.isBlank(userId))
			return new ApiCommonResponse<String>(null,Error.NUM.set("-100"),Error.MSG.set("Please login"));
				
		if (file.isEmpty())
			return new ApiCommonResponse<String>(null,Error.NUM.set("-100"),Error.MSG.set("Please select a file and try again!"));
		
		if(!(filetype.equalsIgnoreCase("clientconfig") || filetype.equalsIgnoreCase("reportconfig"))) 
			return new ApiCommonResponse<String>(null,Error.NUM.set("-100"),Error.MSG.set("Please select Upload Type and try again!"));

		
		if(!(filetype.equalsIgnoreCase("clientconfig") || filetype.equalsIgnoreCase("reportconfig") || filetype.equalsIgnoreCase("resendconfig"))) 
			return new ApiCommonResponse<String>(null,Error.NUM.set("-100"),Error.MSG.set("Please select Upload Type and try again!"));
		
		String uploadFolder= "";
		String docType="";
		String errMessage ="file uploaded and record saved sucessfully!";
		
		if (filetype.equalsIgnoreCase("clientconfig")) {
			uploadFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[0];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[0];
		}else if(filetype.equalsIgnoreCase("reportconfig")){ 
			uploadFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[1];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[1];
		}else if(filetype.equalsIgnoreCase("resendconfig")){
			uploadFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[2];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[2];
		}
		
		LocalDateTime upldTs=LocalDateTime.now();
		String newFileName = generateFileName(upldTs, userId, file.getOriginalFilename());
		try{
			if(!saveFile(file, uploadFolder, newFileName))
				return new ApiCommonResponse<String>(null,Error.NUM.set("-100"),Error.MSG.set("Uploaded Failed! Please contact administrator."));
			
			FileProcessLogDocument fileProcessLog=new FileProcessLogDocument();
			fileProcessLog.setDocType(docType);
			fileProcessLog.setFileName(newFileName);
			fileProcessLog.setUploadedBy(userId);
			fileProcessLog.setUploadedPath(uploadFolder);
			fileProcessLog.setUploadedTs(upldTs);
			fileProcessLog.setStatus(FrontierConstants.UPLOAD_STATUS[0]);
			fileProcessLog.setHostName(getHostName());
			
			if(FrontierConstants.UPLOAD_DOC_TYPE[2].equalsIgnoreCase(docType)){
				String status=fileUploadService.addResendConfig(newFileName, uploadFolder);
				fileProcessLog.setStatus(status);
				fileProcessLog.setProcessedFilePath(uploadFolder);
				fileProcessLog.setProcessedTs(LocalDateTime.now());
				if(FrontierConstants.UPLOAD_STATUS[2].equals(status))
					errMessage=status+"! Invalid Data in the xl / reportConfig not found with given clientId";
			}
			fileProcessLog=dbFacadeService.saveFileProcessLog(fileProcessLog);
			log.info("file uploaded and record saved:"+fileProcessLog);
		}catch(Exception ex){
			log.info("fileUpload error:"+ex.getMessage());
			return new ApiCommonResponse<String>(null,Error.NUM.set("-100"),Error.MSG.set("processing exception Please contact administrator."));
		}
		return new ApiCommonResponse<String>(null,Error.NUM.set("100"),Error.MSG.set(errMessage));
	}
	
	@PostMapping("/saveInitialconfigData")
	public @ResponseBody ApiCommonResponse<String> saveInitialconfigData(@RequestParam("file") MultipartFile file,@RequestParam("filetype") String docType, @RequestParam("userid") String userId){

		if(StringUtils.isBlank(userId))
			return new ApiCommonResponse<String>(null,Error.NUM.set("-100"),Error.MSG.set("Please login"));
		
		if(!"reportingFields".equalsIgnoreCase(docType) && !"usersList".equalsIgnoreCase(docType))
			return new ApiCommonResponse<String>(null,Error.NUM.set("-100"),Error.MSG.set("Please select Upload Type and try again!"));

		String uploader=userId;
		String errMessage="file uploaded and data processed sucessfully";
		LocalDateTime upldTs=LocalDateTime.now();
		String newFileName = generateFileName(upldTs, uploader, file.getOriginalFilename());
		String upldFolder= "";
		
		if ("reportingFields".equalsIgnoreCase(docType)) {
			upldFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[3];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[3];
		}else if ("usersList".equalsIgnoreCase(docType)) {
			upldFolder = uploadPath+FrontierConstants.UPLOAD_FOLDERS[4];
			docType=FrontierConstants.UPLOAD_DOC_TYPE[4];
		}
		if(saveFile(file, upldFolder, newFileName)){
			String upldStatus="";
			if(FrontierConstants.UPLOAD_DOC_TYPE[3].equals(docType)){
					upldStatus=fileUploadService.addReportFields(newFileName, upldFolder, upldTs, getHostName());
					if(!upldStatus.equals(FrontierConstants.UPLOAD_STATUS[1]))
						errMessage=file.getOriginalFilename()+" processing failed due to incorrect header/data for reporting field";
				}else if(FrontierConstants.UPLOAD_DOC_TYPE[4].equals(docType)){
					upldStatus=fileUploadService.addUsers(newFileName, upldFolder, upldTs, getHostName());
					if(!upldStatus.equals(FrontierConstants.UPLOAD_STATUS[1]))
						errMessage=file.getOriginalFilename()+" processing failed due to incorrect header/data for users";
				}
		}else{
			errMessage=file.getOriginalFilename()+" could not be saved, please contact administrator!";
			}
		return new ApiCommonResponse<String>(null,Error.NUM.set("100"),Error.MSG.set(errMessage));
	}
	
	private boolean saveFile(MultipartFile file, String uploadFolder, String fileName){
		try{
			byte[] bytes = file.getBytes();
			Path path = Paths.get(uploadFolder+fileName);
			Files.write(path, bytes);
			return true;
			}catch(IOException e){
				log.error("File Upload Failed, "+e.getMessage());
				}
		return false;
	}
	
	private String getHostName(){
		try{
			return InetAddress.getLocalHost().getHostName();
		}catch(Exception e){
			log.error("getHostName error:"+e.getMessage());
		}
		return "";
	}
	
	private String generateFileName(LocalDateTime upldTs, String upldBy, String origFileName){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		return upldTs.format(formatter)+"-"+upldBy+"-"+origFileName.replace(" ","_");
	}
}
