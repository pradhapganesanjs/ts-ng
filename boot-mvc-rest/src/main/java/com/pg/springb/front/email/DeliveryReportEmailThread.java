package com.pg.springb.front.email;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.util.MailSender;
import com.pg.springb.front.util.XLReportSentFileWriter;
import com.pg.springb.front.documents.EmailSentLogDocument;

public class DeliveryReportEmailThread implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(DeliveryReportEmailThread.class);
	
	private String fileName;
	private String exportPath;	
	private String[] headers;
	private List<EmailSentLogDocument> emailSentList;
	private String activeProfile;
	
	public DeliveryReportEmailThread(String fileName, String exportPath, String[] headers, 
			List<EmailSentLogDocument> emailSentList,String activeProfile) {
		this.fileName = fileName;
		this.exportPath = exportPath;
		this.headers=headers;
		this.emailSentList = emailSentList;
		this.activeProfile=activeProfile;
	}
	
	@Override
	public void run() {
		try{
			boolean isFileGenerated=new XLReportSentFileWriter().writeFile(fileName, exportPath, headers, emailSentList);
			if(isFileGenerated){
				String emailTo = "dl.gt.global.frontier.reporting@imcnam.ssmb.com";
				String subject = activeProfile.toUpperCase()+"-Email Delivery Log";
				String message = "This report contains all the email sent in last 24 hours.";
				String []attachFiles = {exportPath + fileName};
				MailSender.sendEmailWithAttachments(emailTo, "", "", subject, message, attachFiles);
				log.info("DELIVERY_REPORT sent to "+emailTo);
			}
		} catch (Exception ex){
			log.error("DeliveryReport send error:" + ex.getMessage());
		}
	}
}