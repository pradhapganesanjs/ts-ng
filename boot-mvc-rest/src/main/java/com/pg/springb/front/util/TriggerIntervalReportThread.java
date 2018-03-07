package com.pg.springb.front.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.documents.EmailSentLogDocument;
import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportScheduleDocument;
import com.pg.springb.front.documents.ReportingTransDocument;
import com.pg.springb.front.service.DBFacadeService;

public class TriggerIntervalReportThread implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(TriggerIntervalReportThread.class);

	String clientId = "";
	List<ReportingTransDocument> reportingTransactionsList = null;
//	List<ReportingFieldsDocument> reportingFieldsList = null;
	private List<ReportFieldsEntityMapping> rptFieldList;
	ReportScheduleDocument rptConfigDocument;
	
	DBFacadeService dbFacadService;
	String fileName = "";
	String fileExtension = "";
	String outboundPath = "";
	private String frontierActiveProfile;
	private String hostName;
	
	public TriggerIntervalReportThread(String clientId, 
			ReportScheduleDocument rptConfigDocument, List<ReportFieldsEntityMapping> rptFieldList,
			List<ReportingTransDocument> reportingTransactionsList, DBFacadeService dbFacadService, String fileName,
			String extension, String outboundPath,
			String frontierActiveProfile,
			String hostName) {

		this.clientId = clientId;
		this.rptConfigDocument = rptConfigDocument;
//		this.reportingFieldsList = reportingFieldsList;
		this.rptFieldList=rptFieldList;
		this.reportingTransactionsList = reportingTransactionsList;
		this.dbFacadService = dbFacadService;
		this.fileName = fileName;
		this.fileExtension = extension;
		this.outboundPath = outboundPath;
		this.frontierActiveProfile=frontierActiveProfile;
		this.hostName=hostName;
	}

	public void run() {

		boolean isFileGenerated = false;
		boolean isEmailSent = false;
//		String documentId = this.rptConfigDocument.getId();
		String hashKey=this.rptConfigDocument.getHashKey();
		String secretZipKey="";
		String subject="";
		try {
			// Create File
			log.info("<<INTERVAL>> Start generating file for client:"+clientId);

			isFileGenerated = FileWriterFactory.getFileWriter(this.fileExtension).writeFile(this.fileName,
					this.outboundPath, this.rptFieldList, this.reportingTransactionsList);
			
			log.info("<<INTERVAL>> Finished generating file for client:"+clientId + ", isFileGenerated:"+isFileGenerated);
			// Zip File
			String reportSentFileName = this.fileName;
			
			if(isFileGenerated){
				
			// Email File as attachment
			String emailTo = rptConfigDocument.getIntervalEmailTo();//getReportEmail();
			String emailCc =rptConfigDocument.getIntervalEmailCc();
			String emailBcc=rptConfigDocument.getIntervalEmailBcc();
			
//				String subject = "MIFID II Post Trade Recap - Interval";
			LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
			subject = this.rptConfigDocument.getClientId()+"/CITI MIFID II recaps (interval) – "+now.format(formatter);
			if(this.frontierActiveProfile.equalsIgnoreCase("uat") || this.frontierActiveProfile.equalsIgnoreCase("qat"))
				subject=frontierActiveProfile.toUpperCase()+"-"+subject;
			
			
			//String message = "MIFID II Post Trade Recap reporting file attached.";
			/*
			Resource resource = new ClassPathResource("interval_disclaimer.txt");			
			String message = FileUtils.readFileToString(resource.getFile());
			message = message.replace("XXXX",this.rptConfigDocument.getClientId());
			*/
			
			log.info("Interval subject:"+subject);
			InputStream inputStream = null;
			String message = "";
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				inputStream = classLoader.getResourceAsStream("interval_disclaimer.txt");
				message = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
				message = message.replace("XXXX",this.rptConfigDocument.getOrganization());				
			}catch (Exception ex) {
				log.error(ex.getMessage());
			}
			
			
			String fullPathName = this.outboundPath + reportSentFileName;
			
			/*
			if(!"Y".equalsIgnoreCase(this.rptConfigDocument.getMtls())){
				secretZipKey = CryptoUtil.encrypt(hashKey);
				FileZipper fileZipper = new FileZipper();
				fileZipper.pack(secretZipKey, this.outboundPath, this.fileName);
				
				reportSentFileName = this.fileName.replace(".csv", ".zip");
				reportSentFileName = this.fileName.replace(".xlsx", ".zip");
				reportSentFileName = this.fileName.replace(".psv", ".zip");
				fullPathName = this.outboundPath + reportSentFileName;
			}
			*/	
			String[] attachFiles = { fullPathName };
			log.info("Sending interval email with full file name:"+fullPathName);
			MailSender.sendEmailWithAttachments(emailTo, emailCc, emailBcc, subject, message, attachFiles);
			isEmailSent = true;
			}
			
			// Mongo update
			if (isEmailSent){
				for (ReportingTransDocument trnsRecord : this.reportingTransactionsList) {
					 trnsRecord.setStatus(FrontierConstants.TRANSACTION_STATUS_REPORTED); //enabled
					// this at last
					trnsRecord.setLastUpdatedTs(LocalDateTime.now());
				}
				this.reportingTransactionsList = dbFacadService.addUpdateReportingTransDocumentList(this.reportingTransactionsList);
				log.info("clientId:"+this.clientId +", Txn updated:" + this.reportingTransactionsList.size());
				EmailSentLogDocument reportLog=new EmailSentLogDocument();
				reportLog.setClientId(this.rptConfigDocument.getClientId());
				reportLog.setEmailSentTS(LocalDateTime.now());
				reportLog.setAttachment(reportSentFileName);
				reportLog.setSubject(subject);
				reportLog.setEmailTo(this.rptConfigDocument.getIntervalEmailTo());//getReportEmail());
				reportLog.setEmailCc(this.rptConfigDocument.getIntervalEmailCc());
				reportLog.setEmailBcc(this.rptConfigDocument.getIntervalEmailBcc());
				reportLog.setReportType(FrontierConstants.CLIENT_REPORT_TYPE_INTERVAL);
				reportLog.setHashKey(hashKey);
				reportLog.setSecretKey(secretZipKey);
				reportLog.setHostName(this.hostName);
//				List<ReportSentLogDocument> list=new ArrayList<>();
//				list.add(reportLog);
				this.dbFacadService.saveEmailSentLog(reportLog);
//				log.info("Log updated:" + list.size());
			}
			
			this.rptConfigDocument.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_READY);
			this.rptConfigDocument.setIntervalEmailSentTS(LocalDateTime.now());
			this.rptConfigDocument.setSecretKey(secretZipKey);
			this.rptConfigDocument = dbFacadService.addUpdateReportConfigDocument(this.rptConfigDocument);
			// end mongo update
			
		} catch (Exception ex) {
			log.error("Email send error:" + ex.getMessage());
			this.rptConfigDocument.setIntervalJobStatus(FrontierConstants.CLIENT_JOB_READY);
			this.rptConfigDocument = dbFacadService.addUpdateReportConfigDocument(this.rptConfigDocument);			
			// Update the Status in map	
		}
	}
}
