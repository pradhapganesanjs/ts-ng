package com.pg.springb.front.util;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pg.springb.front.documents.ActiveHostDocument;
import com.pg.springb.front.documents.ClientConfigDocument;
import com.pg.springb.front.documents.EmailSentLogDocument;
import com.pg.springb.front.documents.ReportFieldsDocument;
import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportScheduleDocument;
import com.pg.springb.front.documents.ReportingTransDocument;
import com.pg.springb.front.documents.ResendConfigDocument;
import com.pg.springb.front.email.DeliveryReportEmailThread;
import com.pg.springb.front.email.FrontierReportEmailThread;
import com.pg.springb.front.service.DBFacadeService;

@Service
public class FrontierServiceUtil {
	
	private static final Logger log = LoggerFactory.getLogger(FrontierServiceUtil.class);

	@Autowired
	Environment env;
	
	@Autowired
	DBFacadeService dbFacadeService;

	@Value("${frontier.export.path}")
	private String outboundPath;
	
	@Value("${frontier.active.profile}")
	private String frontierActiveProfile;
	
	public void triggerIntervalEmail() {
		if(!amIActiveHost())
			return;
		List<ReportScheduleDocument> reportConfigs = dbFacadeService.findAllReportConfigByIntervalReady();
		if(reportConfigs==null || reportConfigs.size()<=0)
			return;
		List<ReportFieldsDocument> reportFields = dbFacadeService.findAllReportFields();
		if(reportFields==null || reportFields.size()<=0 )
			return;
		List<ReportFieldsEntityMapping> reportHeader = reportFields.get(0).getFields();
		if(reportHeader==null || reportHeader.size()<=0)
			return;
		log.info("<<INTERVAL>> reportConfigs: "+reportConfigs.size());
		LocalDateTime currTs=LocalDateTime.now(Clock.systemUTC());
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (ReportScheduleDocument reportConfig : reportConfigs) {
			boolean isStatusMadeRunning=false;
			try {
				String clientId = reportConfig.getClientId();
				
				if(reportConfig.getIntervalMinute()==null)
					continue;
				int intrvlMint=Integer.parseInt(reportConfig.getIntervalMinute());
				int currentMint=currTs.getMinute();
				if(!FrontierUtil.isTimeForInterval(intrvlMint, currentMint))
					continue;
				List<ReportingTransDocument> transactions = dbFacadeService.getIntervalTransactionsAll(clientId);
				if(transactions==null || transactions.size()<=0)
					continue;
				List<ClientConfigDocument> clientGfcs=dbFacadeService.getActiveClientsGFCID(clientId);
				if(clientGfcs==null || clientGfcs.size()<=0)
					continue;
				transactions=getValidTransactionWithGFCID(transactions,clientGfcs);
				if(transactions==null || transactions.size()<=0)
					continue;
				log.info("<<INTERVAL>> creating report for clientId:"+clientId+", with transactions:"+transactions.size());
				reportConfig.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_RUNNING);
				dbFacadeService.addUpdateReportConfigDocument(reportConfig);
				isStatusMadeRunning=true;
				
				EmailSentLogDocument emailBo=new EmailSentLogDocument();
				emailBo.setClientId(clientId);
				emailBo.setReportType(FrontierConstants.CLIENT_REPORT_TYPE[0]);
				emailBo.setEmailTo(reportConfig.getIntervalEmailTo());
				emailBo.setEmailCc(reportConfig.getIntervalEmailCc());
				emailBo.setEmailBcc(reportConfig.getIntervalEmailBcc());
				String fileName 	= FrontierUtil.generateIntervalReportFileName(clientId, currTs);
				String emailMessage	= FrontierUtil.generateEmailMessage(reportConfig.getOrganization());
				String emailSubject = FrontierUtil.generateEmailSubject(clientId, FrontierConstants.CLIENT_REPORT_TYPE[0],currTs);
				if(this.frontierActiveProfile.equalsIgnoreCase("uat") || this.frontierActiveProfile.equalsIgnoreCase("qat"))
					emailSubject=frontierActiveProfile.toUpperCase()+"-"+emailSubject;
				emailBo.setAttachment(fileName);
				emailBo.setSubject(emailSubject);
				emailBo.setHostName(FrontierUtil.getHostName());
				
				FrontierReportEmailThread thread=new FrontierReportEmailThread(fileName, FrontierConstants.REPORT_FILE_EXTENSION, 
						outboundPath+FrontierConstants.REPORT_FOLDERS[0], emailMessage, reportHeader, 
						transactions, reportConfig, null, emailBo, dbFacadeService);
				executor.submit(thread);
			}catch(Exception e){
				log.error("<<INTERVAL>> error:"+e.getMessage());
				try{
					if(isStatusMadeRunning){
						reportConfig.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_READY);
						dbFacadeService.addUpdateReportConfigDocument(reportConfig);
					}
				}catch(Exception ex){log.error("<<INTERVAL>> dbUpdate error:"+e.getMessage());}
			}
		}
		executor.shutdown();
	}
	
	
	public void triggerIntervalEmail_1() {

		if(!amIActiveHost())
			return;
	
		log.info("<<INTERVAL>> - I am active host");
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<ReportScheduleDocument> scheduleDocumentList = dbFacadeService.getIntervalRptCfgList();

		for (ReportScheduleDocument scheduleDocument : scheduleDocumentList) {	
			try {
			
			log.info("<<INTERVAL>> - Started Processing for Client:"+scheduleDocument.getClientId());	
			
			if (scheduleDocument.getIntervalJobStatus() != null
					&& !scheduleDocument.getIntervalJobStatus().trim().equals("")
					&& !FrontierConstants.CLIENT_JOB_READY.equalsIgnoreCase(scheduleDocument.getIntervalJobStatus())) {
				log.info("<<INTERVAL>> - Client not READY:"+scheduleDocument.getClientId());
				continue;
			}
			log.info("<<INTERVAL>> - Client:"+scheduleDocument.getClientId() +", Interval Minute:"+scheduleDocument.getIntervalMinute() +":minutes");
//			 if(!isRightTimeForInterval(scheduleDocument.getClientId(),scheduleDocument.getIntervalMinute(),scheduleDocument.getIntervalEmailSentTS())) {
//				 log.info("<<INTERVAL>> - Not Right time for Client:"+scheduleDocument.getClientId());				 
//				 continue;
//			 }
			
			scheduleDocument.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_RUNNING);
			dbFacadeService.addUpdateReportConfigDocument(scheduleDocument);
			
//			List<ReportingFieldsDocument> reportingFieldsList = dbFacadeService.getReportingFieldsList();
			List<ReportFieldsDocument> rptFieldsList = dbFacadeService.findAllReportFields();
			log.info("<<INTERVAL>> rptFieldsList: "+rptFieldsList);
			if(rptFieldsList==null || rptFieldsList.size()<=0 || rptFieldsList.get(0).getFields()==null || 
					rptFieldsList.get(0).getFields().size()<=0)
				return;
			List<ReportFieldsEntityMapping> rptFieldList = rptFieldsList.get(0).getFields();
//			String docId = scheduleDocument.getId();
			String clientId = scheduleDocument.getClientId();

			String fileName = generateIntervalCSVFileName(clientId, FrontierConstants.REPORT_FILE_EXTENSION);

			List<ReportingTransDocument> rptTransactionsList = dbFacadeService
					.getIntervalTransactionsAll( clientId);
			
			//for valid gfcId in clientConfig
			log.info(rptTransactionsList.size()+"<-size: <<INTERVAL>> - Txn before, clientId:"+clientId);
			List<ClientConfigDocument> validClientCfgList=dbFacadeService.getActiveClientsGFCID(clientId);
			rptTransactionsList=getValidTransactionWithGFCID(rptTransactionsList,validClientCfgList);
			log.info(rptTransactionsList.size()+"<-size; <<INTERVAL>> - Txn After valid GfcId, clientId:"+clientId);
			
			if (rptTransactionsList != null && rptTransactionsList.size() > 0) {
				TriggerIntervalReportThread task = new TriggerIntervalReportThread(clientId, scheduleDocument,
						rptFieldList, rptTransactionsList, dbFacadeService, fileName,
						FrontierConstants.REPORT_FILE_EXTENSION, outboundPath+ FrontierConstants.REPORT_FOLDERS[0],frontierActiveProfile,getHostDetail().getHostName());
				executor.submit(task);
			}else {
				scheduleDocument.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_READY);
				scheduleDocument = dbFacadeService.addUpdateReportConfigDocument(scheduleDocument);				
			}
			
			}catch(Exception ex) {
				scheduleDocument.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_READY);
				scheduleDocument = dbFacadeService.addUpdateReportConfigDocument(scheduleDocument);	
				log.error("<<INTERVAL>> - "+ex.getMessage());
				ex.printStackTrace();
			}
		}
		executor.shutdown();
	}
	public void triggerDailyEmail() {
		
		if(!amIActiveHost())
			return;
		List<ReportScheduleDocument> reportConfigs = dbFacadeService.findAllReportConfigByDailyReady();
		if(reportConfigs==null || reportConfigs.size()<=0)
			return;
		List<ReportFieldsDocument> reportFields = dbFacadeService.findAllReportFields();
		if(reportFields==null || reportFields.size()<=0 )
			return;
		List<ReportFieldsEntityMapping> reportHeader = reportFields.get(0).getFields();
		if(reportHeader==null || reportHeader.size()<=0)
			return;
		log.info("<<DAILY>> reportConfigs: "+reportConfigs.size());
		LocalDateTime currentTs=LocalDateTime.now(Clock.systemUTC());
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (ReportScheduleDocument reportConfig : reportConfigs) {
			boolean isStatusMadeRunning=false;
			try {
				String clientId = reportConfig.getClientId();
				String dailyReportTime = reportConfig.getDailyTime();
				if (!FrontierUtil.isTimeForDaily(dailyReportTime, reportConfig.getDailyEmailSentTS(), currentTs))
					continue;
				long currTs = FrontierUtil.convertLocalDateTimeToEpoch(currentTs);
				long lstEmlSentTs= FrontierUtil.convertLocalDateTimeToEpoch(reportConfig.getDailyEmailSentTS()==null?currentTs.minusDays(1):reportConfig.getDailyEmailSentTS());
				//File Name is with Today - 1 whereas data pulls from lastEmailSentTs
				List<ReportingTransDocument> transactions = dbFacadeService.getDailyTransactions(clientId, currTs, lstEmlSentTs);
				List<ClientConfigDocument> validClientCfgList=dbFacadeService.getActiveClientsGFCID(clientId);
				transactions=getValidTransactionWithGFCID(transactions,validClientCfgList);
				if(transactions==null || transactions.size()<=0)
					continue;
				log.info("<<DAILY>> creating report for clientId:"+clientId+", with transactions:"+transactions.size());
				reportConfig.setDailyJobStatus(FrontierConstants.CLIENT_JOB_RUNNING);
				dbFacadeService.addUpdateReportConfigDocument(reportConfig);
				isStatusMadeRunning=true;
				
				EmailSentLogDocument emailBo=new EmailSentLogDocument();
				emailBo.setClientId(clientId);
				emailBo.setReportType(FrontierConstants.CLIENT_REPORT_TYPE[1]);
				emailBo.setEmailTo(reportConfig.getDailyEmailTo());
				emailBo.setEmailCc(reportConfig.getDailyEmailCc());
				emailBo.setEmailBcc(reportConfig.getDailyEmailBcc());
				
				String fileName 	= FrontierUtil.generateDailyReportFileName(clientId, currentTs.minusDays(1), currentTs);
				String emailMessage	= FrontierUtil.generateEmailMessage(reportConfig.getOrganization());
				String emailSubject = FrontierUtil.generateEmailSubject(clientId, FrontierConstants.CLIENT_REPORT_TYPE[1],currentTs);
				if(this.frontierActiveProfile.equalsIgnoreCase("uat") || this.frontierActiveProfile.equalsIgnoreCase("qat"))
					emailSubject=frontierActiveProfile.toUpperCase()+"-"+emailSubject;
				emailBo.setAttachment(fileName);
				emailBo.setSubject(emailSubject);
				emailBo.setHostName(FrontierUtil.getHostName());
				emailBo.setEmailSentTS(currentTs);
				
				FrontierReportEmailThread thread=new FrontierReportEmailThread(fileName, FrontierConstants.REPORT_FILE_EXTENSION, 
						outboundPath+FrontierConstants.REPORT_FOLDERS[1], emailMessage, reportHeader, 
						transactions, reportConfig, null, emailBo, dbFacadeService);
				executor.submit(thread);
			}catch(Exception e){
				log.error("<<DAILY>> error:"+e.getMessage());
				try{
					if(isStatusMadeRunning){
						reportConfig.setDailyJobStatus(FrontierConstants.CLIENT_JOB_READY);
						dbFacadeService.addUpdateReportConfigDocument(reportConfig);
					}
				}catch(Exception ex){log.error("<<DAILY>> dbUpdate error:"+e.getMessage());}
			}
		}
		executor.shutdown();
	}
	
	public void triggerDailyEmail_1() {
		
		if(!amIActiveHost())
			return;
		
		log.info("<<DAILY>> - I am active host");

		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<ReportScheduleDocument> scheduleDocumentList = dbFacadeService.getDailyRptCfgList();
		ReportScheduleDocument rptCfgDocument = null;
		List<ReportFieldsDocument> rptFieldsList = dbFacadeService.findAllReportFields();
		log.info("<<DAILY>> rptFieldsList: "+rptFieldsList);
		if(rptFieldsList==null || rptFieldsList.size()<=0 || rptFieldsList.get(0).getFields()==null || 
				rptFieldsList.get(0).getFields().size()<=0)
			return;
		List<ReportFieldsEntityMapping> rptFieldList = rptFieldsList.get(0).getFields();
		
		for (ReportScheduleDocument scheduleDocument : scheduleDocumentList) {
			try {
			log.info("<<DAILY>> - Started Processing for client:"+scheduleDocument.getClientId());	
			String reportTime = scheduleDocument.getDailyTime();

//			if (!isRightTimeForDaily(scheduleDocument.getClientId(), reportTime, scheduleDocument.getDailyEmailSentTS())) {
//				log.info("<<DAILY>> - Not Right time for Client:"+scheduleDocument.getClientId());
//				continue;
//			}			
			
			if (scheduleDocument.getDailyJobStatus() != null
					&& !scheduleDocument.getDailyJobStatus().trim().equals("")
					&& !FrontierConstants.CLIENT_JOB_READY.equalsIgnoreCase(scheduleDocument.getDailyJobStatus())) {
				log.info("<<DAILY>> - Client not READY:"+scheduleDocument.getClientId());
				continue;
			}
			
			long curr = getCurrentTime();
			long ystrday = getYesterdayTime();
			
			
			long lastEmailDay = getLastEmailSentTime(scheduleDocument.getDailyEmailSentTS());
			
			scheduleDocument.setDailyJobStatus(FrontierConstants.CLIENT_JOB_RUNNING);
			rptCfgDocument = dbFacadeService.addUpdateReportConfigDocument(scheduleDocument);
			
			String docId = scheduleDocument.getId();
			String clientId = scheduleDocument.getClientId();
			
			//File Name is with Today - 1 whereas data pull is from last email sent ts.
			String fileName = generateDailyCSVFileName(clientId, FrontierConstants.REPORT_FILE_EXTENSION, ystrday,
					curr);
			
			List<ReportingTransDocument> rptTransactionsList = dbFacadeService.getDailyTransactions(clientId, curr,
					ystrday);
			
			log.info("<<DAILY>> - Txn before, clientId:"+clientId +", curr:"+curr+", ystrday:"+ystrday);
			
			log.info(rptTransactionsList.size()+"<-size: <<DAILY>> - Txn before, clientId:"+clientId);
			List<ClientConfigDocument> validClientCfgList=dbFacadeService.getActiveClientsGFCID(clientId);
			rptTransactionsList=getValidTransactionWithGFCID(rptTransactionsList,validClientCfgList);
			log.info(rptTransactionsList.size()+"<-size; <<DAILY>> - Txn After valid GfcId, clientId:"+clientId);
			
			if (rptTransactionsList != null && rptTransactionsList.size() > 0) {
//				List<ReportingFieldsDocument> rptFieldsList = dbFacadeService.getReportingFieldsList();
				TriggerDailyReportThread task = new TriggerDailyReportThread(fileName,
						FrontierConstants.REPORT_FILE_EXTENSION, outboundPath+ FrontierConstants.REPORT_FOLDERS[1], FrontierConstants.CLIENT_REPORT_TYPE_DAILY, scheduleDocument, rptFieldList,
						rptTransactionsList, dbFacadeService,frontierActiveProfile,getHostDetail().getHostName());
				executor.submit(task);
			}else {
				scheduleDocument.setDailyJobStatus(FrontierConstants.CLIENT_JOB_READY);
				rptCfgDocument = dbFacadeService.addUpdateReportConfigDocument(scheduleDocument);
			}
			}catch(Exception ex) {				
				scheduleDocument.setDailyJobStatus(FrontierConstants.CLIENT_JOB_READY);
				rptCfgDocument = dbFacadeService.addUpdateReportConfigDocument(scheduleDocument);
				log.error("<<DAILY>> - "+ex.getMessage());
				ex.printStackTrace();
			}	
		}
		executor.shutdown();
	}
	
	public void triggerResendEmail() throws Exception{
		if(!amIActiveHost())
			return;
		List<ResendConfigDocument> resendConfigList = dbFacadeService.findAllResendConfigByStatus("OPEN");
		if(resendConfigList==null || resendConfigList.size()<=0)
			return;
		log.info("<<RESEND>> resendConfigs: "+resendConfigList.size());
		List<ReportFieldsDocument> rptFieldsList = dbFacadeService.findAllReportFields();
		if(rptFieldsList==null || rptFieldsList.size()<=0 )
			return;
		List<ReportFieldsEntityMapping> rptFieldList = rptFieldsList.get(0).getFields();
		if(rptFieldList==null || rptFieldList.size()<=0)
			return;
		String reportDate="";
		String reportTime="";
		String fromDate="";
		String fromTime="";
		LocalDateTime rptLdt=null;
		LocalDateTime frmLdt=null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HHmmssSSS");
		LocalDateTime currTs=LocalDateTime.now(Clock.systemUTC());
		for(ResendConfigDocument resendConfig:resendConfigList){
			try{
				String clientId=resendConfig.getClientId();
				String reportType=resendConfig.getReportType();
				if(FrontierConstants.CLIENT_REPORT_TYPE[0].equalsIgnoreCase(reportType)){
					reportDate=resendConfig.getReportDate();
					reportTime=resendConfig.getReportTime();
					fromDate=resendConfig.getFromDate();
					fromTime=resendConfig.getFromTime();
					rptLdt= LocalDateTime.parse(reportDate+" "+reportTime+"00001", formatter);
					frmLdt= LocalDateTime.parse(fromDate+" "+fromTime+"00001", formatter);
				}else if(FrontierConstants.CLIENT_REPORT_TYPE[1].equalsIgnoreCase(reportType)){
					reportDate=resendConfig.getReportDate();
					reportTime=resendConfig.getReportTime();
					rptLdt= LocalDateTime.parse(reportDate+" "+reportTime+"00001", formatter);
					frmLdt=rptLdt.minusDays(1);
				}
				long frmTs=convertLocalDateTimeToEpoch(frmLdt);
				long toTs=convertLocalDateTimeToEpoch(rptLdt);
				List<ReportingTransDocument> transactions = dbFacadeService.getReportingTransactionByClientIdnLastUpdatedTs(clientId, frmTs, toTs);
				
				if (transactions == null || transactions.size() <= 0)
					throw new Exception("ZERO_TRANSACTION");
				
				List<ClientConfigDocument> validClientCfgList=dbFacadeService.getActiveClientsGFCID(clientId);
				transactions=getValidTransactionWithGFCID(transactions,validClientCfgList);
				
				if (transactions == null || transactions.size() <= 0)
					throw new Exception("ZERO_GFC_TRANSACTION");
				
				log.info("<<RESEND>> creating report for clientId:"+clientId+", frmLdt:"+frmLdt+", rptLdt:"+rptLdt+", with Transactions:"+transactions.size());
				
				EmailSentLogDocument emailBo=new EmailSentLogDocument();
				emailBo.setClientId(clientId);
				if(FrontierConstants.CLIENT_REPORT_TYPE[0].equalsIgnoreCase(reportType))
					emailBo.setReportType(FrontierConstants.CLIENT_REPORT_TYPE[2]);
				else if(FrontierConstants.CLIENT_REPORT_TYPE[1].equalsIgnoreCase(reportType))
					emailBo.setReportType(FrontierConstants.CLIENT_REPORT_TYPE[3]);
				emailBo.setEmailTo(resendConfig.getEmailTo());
				emailBo.setEmailCc(resendConfig.getEmailCc());
				emailBo.setEmailBcc(resendConfig.getEmailBcc());
				
				String fileName		= FrontierUtil.generateResendReportFileName(clientId, frmLdt, rptLdt, currTs);
				String emailMessage	= FrontierUtil.generateEmailMessage(resendConfig.getOrganization());
				String emailSubject = FrontierUtil.generateEmailSubject(clientId, emailBo.getReportType(),currTs);
				
				emailBo.setHostName(FrontierUtil.getHostName());
				if(this.frontierActiveProfile.equalsIgnoreCase("uat") || this.frontierActiveProfile.equalsIgnoreCase("qat"))
					emailSubject=frontierActiveProfile.toUpperCase()+"-"+emailSubject;
				emailBo.setAttachment(fileName);
				emailBo.setSubject(emailSubject);
				
				FrontierReportEmailThread thread=new FrontierReportEmailThread(fileName, FrontierConstants.REPORT_FILE_EXTENSION, 
						outboundPath+FrontierConstants.REPORT_FOLDERS[4], emailMessage, rptFieldList, 
						transactions, null, resendConfig, emailBo, dbFacadeService);
				new Thread(thread).start();
				}catch(Exception e){
					log.error("<<RESEND>> error:"+e.getMessage());
					try{
						if(e.getMessage().equals("ZERO_TRANSACTIONS") || e.getMessage().equals("ZERO_VALID_TRANSACTIONS"))
							resendConfig.setStatus(e.getMessage());
						else
							resendConfig.setStatus("PROCESS_FAILED");
						resendConfig.setLastUpdatedTs(currTs);
						dbFacadeService.saveResendConfig(resendConfig);
					}catch(Exception ex){log.error("<<RESEND>> dbUpdateAfterException error:"+ex.getMessage());}
				}
			}
		}

	public void triggerDeliveryReport(){
		if(!amIActiveHost())
			return;
		LocalDateTime currTs=LocalDateTime.now(Clock.systemUTC());
		try{
			long toLong=convertLocalDateTimeToEpoch(currTs);
			long fromLong=convertLocalDateTimeToEpoch(currTs.minusDays(1));
			
			List<EmailSentLogDocument> emailSentList=dbFacadeService.findAllEmailSentLogByEmailSentTs(fromLong,toLong);
			if(emailSentList==null || emailSentList.size()<=0)
				return;
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
			String fileName="EMAIL_DELIVERY_REPORT_"+currTs.format(formatter)+FrontierConstants.XLSX_EXTENSION;
			
			DeliveryReportEmailThread thread=new DeliveryReportEmailThread(fileName, outboundPath+ FrontierConstants.REPORT_FOLDERS[2], 
					FrontierConstants.DELIVERY_REPORT_HEADER, emailSentList,frontierActiveProfile);
			new Thread(thread).start();
		}catch(Exception e){
			log.error("triggerDeliveryReport() error:"+e.getMessage());
			}
	}
	
	public void triggerDeleteTransaction(){
		
		if(!amIActiveHost())
			return;
		
		long beyondDT=convertLocalDateTimeToEpoch(LocalDateTime.now().minusDays(30));
		List<ReportingTransDocument> deletableTrns=dbFacadeService.getDeletableTransactions(beyondDT);
		if(deletableTrns==null || deletableTrns.size()<=0)
			return;
		dbFacadeService.deleteTransactions(deletableTrns);
		log.info(deletableTrns.size()+" Records has been deleted from Transaction collection");
		return;
		
//		List<ReportingTransDocument> allTrns=dbFacadeService.getReportingTransDocumentList();
//		System.out.println(deletableTrns.size()+":deletableTrns"+deletableTrns);
//		System.out.println(allTrns.size()+":allTrns"+allTrns);
//		dbFacadeService.deleteTransactions(deletableTrns);
//		allTrns=dbFacadeService.getReportingTransDocumentList();
//		System.out.println("after delete allTrns:"+allTrns.size());
//		deletableTrns=dbFacadeService.saveReportingTransDocumentList(deletableTrns);
//		allTrns=dbFacadeService.getReportingTransDocumentList();
//		System.out.println("deletableTrns saved:"+deletableTrns.size());
//		System.out.println("after delete and save all trns:"+allTrns.size());
	}
	
	
	private String generateIntervalCSVFileName(String clientId, String extension) {
		String pattern = "YYYY_MM_dd_HH_mm";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String date = sdf.format(new Date());
		return clientId + "_CITI_MIFID_II_recaps_" + date + extension;
	}

	private String generateDailyCSVFileName(String clientId, String extension, long fromDate, long toDate) {
		//LocalDateTime frmdt = convertNanoTimeStampToLocalDateTime(fromDate);
		//LocalDateTime todt = convertNanoTimeStampToLocalDateTime(toDate);
		LocalDateTime frmdt = LocalDateTime.now(Clock.systemUTC()).minusDays(1);
		LocalDateTime todt = LocalDateTime.now(Clock.systemUTC());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		return clientId + "_CITI_MIFID_II_recaps_" + frmdt.format(formatter) + "_To_" + todt.format(formatter)
				+ extension;
	}

	public String getClientConfigMapJson(List<ClientConfigDocument> clientConfigDocumentList) {
		JSONObject mainJSON = new JSONObject();
		JSONObject clientJSON = null;
		JSONArray gfcidList = null;
		JSONArray leiList = null;
		for (ClientConfigDocument clientEntity : clientConfigDocumentList) {
			String clientId = clientEntity.getClientId();
			if (clientId != null && !clientId.trim().equals("")) {

				clientJSON = (JSONObject) mainJSON.get(clientId);
				if (clientJSON == null) {
					clientJSON = new JSONObject();

					gfcidList = new JSONArray();
					leiList = new JSONArray();
					clientJSON.put("gfcidList", gfcidList);
					clientJSON.put("leiList", leiList);
				}
				clientJSON.put("clientId", clientId);

				String gfcid = clientEntity.getGfcId();
				if (gfcid != null && !gfcid.trim().equals("")) {
					JSONArray gfcidLocalList = (JSONArray) clientJSON.get("gfcidList");
					gfcidLocalList.add(gfcid);
				}

				String lei = clientEntity.getLei();
				if (lei != null && !lei.trim().equals("")) {
					JSONArray leiLocalList = (JSONArray) clientJSON.get("leiList");
					leiLocalList.add(lei);
				}

				mainJSON.put(clientId, clientJSON);

			}
		}

		return mainJSON.toString();
	}

	public String getClientConfigListJson(List<ClientConfigDocument> clientConfigDocumentList) {
		JSONObject mainJSON = new JSONObject();
		JSONObject clientJSON = null;
		JSONArray clientList = new JSONArray();
		for (ClientConfigDocument clientEntity : clientConfigDocumentList) {
			clientJSON = new JSONObject();
			clientJSON.put("clientId", clientEntity.getClientId());
			clientJSON.put("gfcid", clientEntity.getGfcId());
			clientJSON.put("lei", clientEntity.getLei());
			clientList.add(clientJSON);
		}
		mainJSON.put("clientList", clientList);

		return mainJSON.toString();
	}

//	public String getReportingTransJson(List<ReportingTransDocument> reportingTransactionsList) {
//		JSONObject mainJSON = new JSONObject();
//		JSONObject clientJSON = null;
//		JSONArray gfcidList = null;
//		JSONArray leiList = null;
//		for (ReportingTransDocument transDocument : reportingTransactionsList) {
//
//			String clientId = transDocument.getId();
//			if (clientId != null && !clientId.trim().equals("")) {
//
//				clientJSON = (JSONObject) mainJSON.get(clientId);
//				if (clientJSON == null) {
//					clientJSON = new JSONObject();
//
//					gfcidList = new JSONArray();
//					leiList = new JSONArray();
//					clientJSON.put("gfcidList", gfcidList);
//					clientJSON.put("leiList", leiList);
//				}
//				clientJSON.put("clientId", clientId);
//				JSONArray gfcidLocalList = (JSONArray) clientJSON.get("gfcidList");
//				// gfcidLocalList.add(transDocument.getGfcId());
//
//				JSONArray leiLocalList = (JSONArray) clientJSON.get("leiList");
//				// leiLocalList.add(transDocument.getLei());
//				mainJSON.put(clientId, clientJSON);
//
//			}
//		}
//
//		return mainJSON.toString();
//	}

	private Long convertLocalDateTimeToEpoch(LocalDateTime currentTs) {
		long epochSec = currentTs.atZone(Clock.systemUTC().getZone()).toInstant().getEpochSecond();
		Integer nanos = currentTs.getNano();
		Long nanoTime = (epochSec * 1000000000) + nanos;
		return nanoTime;
	}

	private LocalDateTime convertNanoTimeStampToLocalDateTime_1(Long timeInLong) {

		Long timeInMillis = timeInLong / 1000000;
		Integer timeInNanos = (int) (timeInLong % 1000000000);

		Instant inst = Instant.ofEpochMilli(timeInMillis);
		LocalDateTime ldt = LocalDateTime.ofInstant(inst, Clock.systemUTC().getZone());
		ldt = ldt.withNano(timeInNanos);

		return ldt;

	}
	
	private Long getYesterdayTime(){		
		return convertLocalDateTimeToEpoch(LocalDateTime.now(Clock.systemUTC()).minusDays(1));			
	}
	
	private Long getLastEmailSentTime(LocalDateTime lastUpdatedTS){
		if(lastUpdatedTS==null) {
			return convertLocalDateTimeToEpoch(LocalDateTime.now(Clock.systemUTC()).minusDays(1));
		}
		return convertLocalDateTimeToEpoch(lastUpdatedTS);
		
	}

	public Long getCurrentTime() {
		return convertLocalDateTimeToEpoch(LocalDateTime.now(Clock.systemUTC()));
	}

//	private boolean isRightTimeForInterval(String clientId, String intervalMinuteStr, LocalDateTime emailSentTs) {	
//		
//		if (intervalMinuteStr == null || intervalMinuteStr.trim().equals("")) {
//			 log.info("<<INTERVAL>> - Interval Minute not defined for Client:"+clientId);	
//			return false;
//		}
//		
//		try {
//			/*
//			double d = Double.parseDouble(intervalMinuteStr);
//			emailSentTs = emailSentTs.plusMinutes((int) d);
//			LocalDateTime ldtNow = LocalDateTime.now();
//			long emlPlsIntrvlTime = convertLocalDateTimeToEpoch(emailSentTs);
//			long nowTime = convertLocalDateTimeToEpoch(ldtNow);
//			// System.out.println("nowTime:"+nowTime+" emlPlsIntrvlTime:"+emlPlsIntrvlTime);
//			return nowTime > emlPlsIntrvlTime;
//			*/
//			
//			int invervalMinute = Integer.parseInt(intervalMinuteStr);
//			int currentMinute = LocalDateTime.now().getMinute();
//			if(currentMinute == 0 ) currentMinute = 60;
//			
//			int modulusMinute = currentMinute%invervalMinute;
//			
//			log.info("<<INTERVAL>> - Client:"+clientId + ",invervalMinute:"+invervalMinute+ ", currentMinute:"+currentMinute +", modulusMinute:"+modulusMinute);	
//			if(  modulusMinute== 0) {
//				return true;
//			}else {
//				return false;
//			}
//		} catch (Exception e) {
//			log.error("Exception in isRightTimeForInterval():" + e.getMessage());
//		}
//
//		return false;
//	}

//	private boolean isRightTimeForDaily(String clientId, String reportTime, LocalDateTime emailSentTs) {
//		int scheduledHour = 0, scheduledMinute = 0;
//		if(emailSentTs != null && LocalDateTime.now().getDayOfMonth() == emailSentTs.getDayOfMonth()) {
//			log.info("<<DAILY>> - Client:"+clientId +"Email has already been sent today");
//			return false;
//		}
//		
//		try {
//			if (reportTime == null)
//				return false;
//			int rptTime = Integer.parseInt(reportTime);
//
//			scheduledHour = rptTime / 100;
//			scheduledMinute = rptTime % 100;
//			
//			int currentHour = LocalDateTime.now().getHour();
//			int currentMinute = LocalDateTime.now().getMinute();
//			/*
//			LocalDateTime ldtSchdl = LocalDateTime.now().withHour(hour).withMinute(minute);
//			LocalDateTime ldtNow = LocalDateTime.now();
//			long longSchdl = convertLocalDateTimeToEpoch(ldtSchdl);
//			long longNow = convertLocalDateTimeToEpoch(ldtNow);
//			return longNow > longSchdl;
//			*/
//			log.info("<<DAILY>> - Client:"+clientId + ",scheduledHour:"+scheduledHour+ ", scheduledMinute:"+scheduledMinute +", currentHour:"+currentHour +", currentMinute:"+currentMinute);	
//
//			if(scheduledHour == currentHour && currentMinute - scheduledMinute >= 0 && currentMinute - scheduledMinute < 3 ) {
//				return true;
//			}			
//		} catch (Exception e) {
//			log.error("Exception in isRightTimeForDaily():" + e.getMessage());
//		}
//		return false;
//	}
	
//	private ClientEmailProcessDocument getEmailProcessDocument(ReportScheduleDocument scheduleDocument, List<ClientEmailProcessDocument> emailProcessList, String reportType) {
//		
//		ClientEmailProcessDocument validEmailDocument = null;
//		for(ClientEmailProcessDocument emailDocument: emailProcessList) {
//			if(emailDocument.getClientId().equals(scheduleDocument.getClientId())) {
//				validEmailDocument = emailDocument;
//				continue;
//			}			
//		}
//		if(validEmailDocument == null) {
//			validEmailDocument = new ClientEmailProcessDocument();
//			validEmailDocument.setClientId(scheduleDocument.getClientId());	
//			validEmailDocument.setReportType(reportType);
//		}
//		return validEmailDocument;
//	}
	
	private boolean amIActiveHost(){
		
		ActiveHostDocument thisHost=getHostDetail();
		if(thisHost==null) return false; //unable to get local host detail
		List<ActiveHostDocument> activeHostList=dbFacadeService.findAllActiveHost();
		
		//there is no active host
		if(activeHostList==null || activeHostList.size()<=0){
			thisHost.setLastActiveTS(LocalDateTime.now());
			activeHostList=new ArrayList<ActiveHostDocument>();
			activeHostList.add(thisHost);
			activeHostList=dbFacadeService.saveActiveHost(activeHostList);
			return true;
		}
		
		ActiveHostDocument activeHost=activeHostList.get(0);
		// there is some host, who is myself
		if(thisHost.getHostName().equalsIgnoreCase(activeHost.getHostName())
				&& thisHost.getHostAddress().equalsIgnoreCase(activeHost.getHostAddress())){
			
			activeHost.setLastActiveTS(LocalDateTime.now());
			activeHostList=dbFacadeService.saveActiveHost(activeHostList);
			return true;
			}
		//there is some host,other than me
		else {
				int graceMinuteForActive=5;
				//if that other host is inactive i will make my self active
				if(!isHostStillAlive(graceMinuteForActive,activeHost.getLastActiveTS())){ 
					activeHost.setHostName(thisHost.getHostName());
					activeHost.setHostAddress(thisHost.getHostAddress());
					activeHost.setPort(thisHost.getPort());
					activeHost.setLastActiveTS(LocalDateTime.now());
					activeHostList=new ArrayList<ActiveHostDocument>();
					activeHostList.add(activeHost);
					activeHostList=dbFacadeService.saveActiveHost(activeHostList);
					log.info("Existing Host Inactive, made myself Active:"+activeHostList);
					return true;
				}
			}
		return false;
	}
	
	private boolean isHostStillAlive(int graceMinute, LocalDateTime lastActiveTS) {
		if (lastActiveTS == null)
			return true;
		try {
			lastActiveTS = lastActiveTS.plusMinutes(graceMinute);
			LocalDateTime ldtNow = LocalDateTime.now();
			long lstActivePlsGracelTime = convertLocalDateTimeToEpoch(lastActiveTS);
			long nowTime = convertLocalDateTimeToEpoch(ldtNow);
			return lstActivePlsGracelTime > nowTime;
		} catch (Exception e) {
			log.error("Exception in checking activeHost:" + e.getMessage());
		}
	 return false;
	}
	
	private ActiveHostDocument getHostDetail(){
		ActiveHostDocument hostDetail=new ActiveHostDocument();
		try{
			hostDetail.setPort(env.getProperty("server.port"));
			hostDetail.setHostAddress(InetAddress.getLocalHost().getHostAddress());
	    	hostDetail.setHostName(InetAddress.getLocalHost().getHostName());
		}catch(Exception e){return null;}
		return hostDetail;
	}
	
	private List<ReportingTransDocument> getValidTransactionWithGFCID(List<ReportingTransDocument> rptTransactionsList, List<ClientConfigDocument> clientConfList){
		
		List<ReportingTransDocument> validTransanctionList = new ArrayList<ReportingTransDocument>();
		
		for(ReportingTransDocument rptTxn:rptTransactionsList){
			
			Object cfgid=rptTxn.getInfo().get("clientGFCID");
			if(cfgid==null) continue;
			
			for(ClientConfigDocument clientCfg:clientConfList){
				if(clientCfg.getGfcId().equalsIgnoreCase(cfgid.toString())){
					validTransanctionList.add(rptTxn);
					break;
				}
			}
		}
		return validTransanctionList;
	}
	
}