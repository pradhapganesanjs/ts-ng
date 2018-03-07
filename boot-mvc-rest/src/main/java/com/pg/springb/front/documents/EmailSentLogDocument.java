package com.pg.springb.front.documents;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "email_sent_log")
public class EmailSentLogDocument {

	@Id
    private String id;
    private String clientId;
    private String reportType;
    
    private String emailFrom;
    private String emailTo;
    private String emailCc;
    private String emailBcc;
    private String subject;
    private String attachment;
    
    private LocalDateTime emailSentTS;
    private String hashKey;
    private String secretKey;
    private String hostName;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public String getEmailBcc() {
		return emailBcc;
	}

	public void setEmailBcc(String emailBcc) {
		this.emailBcc = emailBcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public LocalDateTime getEmailSentTS() {
		return emailSentTS;
	}

	public void setEmailSentTS(LocalDateTime emailSentTS) {
		this.emailSentTS = emailSentTS;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Override
	public String toString() {		
		return  "_Id:"+ this.id+
				" emailSentTS:"+ this.emailSentTS+
				" reportType:"+ this.reportType+
				" emailFrom:"+ this.emailFrom+
				" emailTo:"+ this.emailTo+
				" emailCc:"+ this.emailCc+
				" emailBcc:"+ this.emailBcc+
				" subject:"+ this.subject+
				" attachment:"+ this.attachment;
	}  
}