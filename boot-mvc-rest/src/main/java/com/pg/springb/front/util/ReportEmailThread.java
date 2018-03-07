package com.pg.springb.front.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.documents.EmailSentLogDocument;
import com.pg.springb.front.documents.ReportFieldsEntityMapping;
import com.pg.springb.front.documents.ReportingTransDocument;
import com.pg.springb.front.documents.ResendConfigDocument;
import com.pg.springb.front.service.DBFacadeService;

public class ReportEmailThread{
	
//	private static final Logger log = LoggerFactory.getLogger(ReportEmailThread.class);
//	
//	private String fileName;
//	private String fileExtension;
//	private String exportPath;
//	private List<ReportingTransDocument> rptTransactionList;
////	private List<ReportingFieldsDocument> rptFieldList;
//	private List<ReportFieldsEntityMapping> rptFieldList;
//	private ResendConfigDocument resendConfig;
//	private DBFacadeService dbFacadService;
//	private String activeProfile;
//	private String hostName;
	
//	public ReportEmailThread(String fileName, String fileExtension, String exportPath, 
//			List<ReportFieldsEntityMapping> rptFieldList,List<ReportingTransDocument> reportingTransactionsList, 
//			ResendConfigDocument resendConfig, DBFacadeService dbFacadService, String activeProfile, String hostName) {
//
//		this.fileName = fileName;
//		this.fileExtension = fileExtension;
//		this.exportPath = exportPath;
//		this.rptFieldList = rptFieldList;
//		this.rptTransactionList = reportingTransactionsList;
//		this.resendConfig=resendConfig;
//		this.dbFacadService=dbFacadService;
//		this.activeProfile=activeProfile;
//		this.hostName=hostName;
//	}

//	public void run() {
//
//		boolean isFileGenerated = false;
//		boolean isEmailSent = false;
//		String subject="";
//		try {
//			// Create File
//			isFileGenerated = FileWriterFactory.getFileWriter(this.fileExtension).writeFile(this.fileName,
//					this.exportPath, this.rptFieldList, this.rptTransactionList);
//			log.info("isFileGenerated:"+isFileGenerated);
			// Zip File
//			String reportSentFileName = this.fileName;
//			
//			if(isFileGenerated){
//			// Email File as attachment
//			String emailTo = this.resendConfig.getEmailTo();
//			String emailCc = this.resendConfig.getEmailCc();
//			String emailBcc= this.resendConfig.getEmailBcc();
//			
////			String subject = "MIFID II Post Trade Recap - Interval";
//			LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
//			subject = this.resendConfig.getClientId()+"/CITI MIFID II recaps ("+this.resendConfig.getReportType()+") – "+now.format(formatter);
//			
//			if(this.activeProfile.equalsIgnoreCase("uat") || this.activeProfile.equalsIgnoreCase("qat"))
//				subject=activeProfile.toUpperCase()+"-"+subject;
//			
//			InputStream inputStream = null;
//			String message = "";
//			try {
//				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//				inputStream = classLoader.getResourceAsStream("interval_disclaimer.txt");
//				message = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
//				message = message.replace("XXXX",this.resendConfig.getOrganization());				
//			}catch (Exception ex) {
//				log.error(ex.getMessage());
//			}
//			
//			String fullPathName = this.exportPath + this.fileName;
			
//			log.info("<<RESEND EMAIL>> ClientId:"+this.resendConfig.getClientId());
//			log.info("<<RESEND EMAIL>> Organization:"+this.resendConfig.getOrganization());
//			log.info("<<RESEND EMAIL>> ReportType:"+this.resendConfig.getReportType());
//			log.info("<<RESEND EMAIL>> Subject:"+subject);
//			log.info("<<RESEND EMAIL>> FileName:"+this.fileName);
			
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
//			String[] attachFiles = { fullPathName };
//			MailSender.sendEmailWithAttachments(emailTo, emailCc, emailBcc, subject, message, attachFiles);
//			log.info("Email Resent");
//			isEmailSent = true;
//			}
			// Mongo update
//			if (isEmailSent){
//				EmailSentLogDocument reportLog=new EmailSentLogDocument();
//				reportLog.setClientId(this.resendConfig.getClientId());
//				reportLog.setEmailSentTS(LocalDateTime.now());
//				reportLog.setAttachment(reportSentFileName);
//				reportLog.setSubject(subject); //needToDiscuss message field removd
//				reportLog.setEmailTo(this.resendConfig.getEmailTo());
//				reportLog.setEmailCc(this.resendConfig.getEmailCc());
//				reportLog.setEmailBcc(this.resendConfig.getEmailBcc());
//				reportLog.setReportType(this.resendConfig.getReportType()+"_RESENT");//needToDiscuss
//				reportLog.setHostName(this.hostName);
//				reportLog=dbFacadService.saveReportSentLog(reportLog);
//				log.info("Log updated:" + reportLog);
//			}
			
//			this.resendConfig.setEmailSentTs(LocalDateTime.now());
//			this.resendConfig.setStatus("CLOSE");
//			this.resendConfig = dbFacadService.saveResendConfig(this.resendConfig);
//			log.info("resendConfig after update:" + this.resendConfig);
//		}catch (Exception ex) {
//			this.resendConfig.setEmailSentTs(LocalDateTime.now());
//			this.resendConfig.setStatus("CLOSE");
//			this.resendConfig = dbFacadService.saveResendConfig(this.resendConfig);
//			log.error("Email send error:" + ex.getMessage());
//			
//		}
//	}
}
