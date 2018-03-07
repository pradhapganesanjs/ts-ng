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

public class TriggerDailyReportThread implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(TriggerDailyReportThread.class);
	
	private String fileName="";
	private String fileExt="";
	private String outboundPath="";
	private String reportType="";
	private ReportScheduleDocument rptSchduleDocument;
//	private List<ReportingFieldsDocument> rptFieldsList;
	private List<ReportFieldsEntityMapping> rptFieldList;
	private List<ReportingTransDocument> rptTransList;
	private DBFacadeService facadService;
	private String frontierActiveProfile;
	private String hostName;
	
	public TriggerDailyReportThread(String fileName,String fileExt,String outboundPath,String reportType,
			ReportScheduleDocument rptSchduleDocument,
			List<ReportFieldsEntityMapping> rptFieldList,
			List<ReportingTransDocument> rptTransList,
			DBFacadeService facadService,
			String frontierActiveProfile,
			String hostName){
		
		this.fileName = fileName;
		this.fileExt = fileExt;
		this.outboundPath = outboundPath;
		this.reportType=reportType;
		this.rptSchduleDocument = rptSchduleDocument;
		this.rptFieldList = rptFieldList;
		this.rptTransList = rptTransList;
		this.facadService = facadService;
		this.frontierActiveProfile=frontierActiveProfile;
		this.hostName=hostName;
	}
	
	public void run(){
		
		boolean isFileGenerated=false;
		boolean isEmailSent=false;
		String hashKey=this.rptSchduleDocument.getHashKey();
		String secretZipKey="";
		String subject="";
		try{
		log.info("<<DAILY>> Start generating file for client:"+rptSchduleDocument.getClientId());
			
		isFileGenerated=FileWriterFactory.getFileWriter(this.fileExt).writeFile(this.fileName, this.outboundPath, this.rptFieldList,this.rptTransList);
		
		log.info("<<DAILY>> Finished generating file for client:"+rptSchduleDocument.getClientId() + ", isFileGenerated:"+isFileGenerated);
		
		//Zip the file
		
		String reportSentFileName = this.fileName;
		if(isFileGenerated){
		
		// Email File as attachment
		String emailTo = this.rptSchduleDocument.getDailyEmailTo();//getReportEmail();
		String emailCc = this.rptSchduleDocument.getDailyEmailCc();
		String emailBcc= this.rptSchduleDocument.getDailyEmailBcc();
//		String subject = "MIFID II Post Trade Recap - Daily";
		LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        subject =this.rptSchduleDocument.getClientId()+"/CITI MIFID II recaps (Daily Summary) – "+now.format(formatter);
        if(this.frontierActiveProfile.equalsIgnoreCase("uat") || this.frontierActiveProfile.equalsIgnoreCase("qat"))
			subject=frontierActiveProfile.toUpperCase()+"-"+subject;
        
		log.info("Email Subject(Daily)= "+subject);

		InputStream inputStream = null;
		String message = "";
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			inputStream = classLoader.getResourceAsStream("daily_disclaimer.txt");
			message = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			message = message.replace("XXXX",this.rptSchduleDocument.getOrganization());				
		}catch (Exception ex) {
			log.error(ex.getMessage());
		}
		
		String fullPathName = this.outboundPath + reportSentFileName;
		
		/*
		if(!"Y".equalsIgnoreCase(this.rptSchduleDocument.getMtls())){
			secretZipKey = CryptoUtil.encrypt(hashKey);
			FileZipper fileZipper = new FileZipper();
			fileZipper.pack(secretZipKey, this.outboundPath, this.fileName);
			
			reportSentFileName = this.fileName.replace(".csv", ".zip");
			reportSentFileName = this.fileName.replace(".xlsx", ".zip");
			reportSentFileName = this.fileName.replace(".psv", ".zip");
			fullPathName = this.outboundPath + reportSentFileName;
		}
		*/
		String []attachFiles = {fullPathName};
		log.info("Sending daily email full file name:"+fullPathName);
		MailSender.sendEmailWithAttachments(emailTo, emailCc, emailBcc, subject, message, attachFiles);
		isEmailSent = true;
		}
		
		// Mongo update
		if (isEmailSent){
			EmailSentLogDocument reportLog=new EmailSentLogDocument();
			reportLog.setClientId(this.rptSchduleDocument.getClientId());
			reportLog.setEmailSentTS(LocalDateTime.now());
			reportLog.setAttachment(reportSentFileName);
			reportLog.setSubject(subject);
			reportLog.setEmailTo(this.rptSchduleDocument.getDailyEmailTo());//getReportEmail());
			reportLog.setEmailCc(this.rptSchduleDocument.getDailyEmailCc());
			reportLog.setEmailBcc(this.rptSchduleDocument.getDailyEmailBcc());
			reportLog.setReportType(this.reportType);
			reportLog.setHashKey(hashKey);
			reportLog.setSecretKey(secretZipKey);
			reportLog.setHostName(this.hostName);
			this.facadService.saveEmailSentLog(reportLog);
		}
		
		this.rptSchduleDocument.setDailyJobStatus(FrontierConstants.CLIENT_JOB_READY);
		this.rptSchduleDocument.setDailyEmailSentTS(LocalDateTime.now());
		this.rptSchduleDocument.setSecretKey(secretZipKey);
		this.rptSchduleDocument = facadService.addUpdateReportConfigDocument(this.rptSchduleDocument);
		
		} catch (Exception ex) {
			log.error("Email send error:" + ex.getMessage());
			this.rptSchduleDocument.setDailyJobStatus(FrontierConstants.CLIENT_JOB_READY);
			this.rptSchduleDocument.setDailyEmailSentTS(LocalDateTime.now());
			this.rptSchduleDocument = this.facadService.addUpdateReportConfigDocument(this.rptSchduleDocument);			
		}
	}
}
