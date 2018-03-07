package com.pg.springb.front.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pg.springb.front.documents.EmailSentLogDocument;

public class ReportForEmailSentThread implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(ReportForEmailSentThread.class);
	
	private String fileName="";
	private String outboundPath="";	
	private String[] headers;
	private List<EmailSentLogDocument> data;
	private String frontierActiveProfile;
	
	public ReportForEmailSentThread(String fileName,String outboundPath,
			String[] headers,List<EmailSentLogDocument> data,
			String frontierActiveProfile){
		this.fileName = fileName;
		this.outboundPath = outboundPath;
		this.headers=headers;
		this.data = data;
		this.frontierActiveProfile=frontierActiveProfile;
	}
	
	@Override
	public void run(){
		
		boolean isFileGenerated = false;
		try{
			isFileGenerated=new XLReportSentFileWriter().writeFile(this.fileName, this.outboundPath, this.headers,this.data);
			
			if(isFileGenerated){
				String emailTo = "dl.gt.global.frontier.reporting@imcnam.ssmb.com";
				//String emailTo = "dl.citi.global.mifid.ii.post.trade.recap@citi.com";			
				
				String emailCc = "";
				String emailBcc= "";
				String subject = "Email Delivery Log";
				
				//Prefix All emails with Environment variable
				//if(this.frontierActiveProfile.equalsIgnoreCase("uat"))
				
				subject=frontierActiveProfile.toUpperCase()+"-"+subject;
				log.info("Email Subject(Delivery)= "+subject);
				String message = "This report contains all the email sent in last 24 hours.";
				String fullPathName = this.outboundPath + this.fileName;
				String []attachFiles = {fullPathName};
				MailSender.sendEmailWithAttachments(emailTo, emailCc, emailBcc, subject, message, attachFiles);
				log.info("Delivery report sent");
			}
		} catch (Exception ex){
			log.error("Delivery Email send error:" + ex.getMessage());
		}
	}
}
