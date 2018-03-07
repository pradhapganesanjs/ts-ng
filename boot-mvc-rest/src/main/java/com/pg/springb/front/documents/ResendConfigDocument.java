package com.pg.springb.front.documents;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resend_report_config")
public class ResendConfigDocument {
	 
	 @Id
	 private String id;
	 private String clientId;
	 private String organization;
	 private String reportType;
	 
	 private String reportDate;
	 private String reportTime;
	 private String fromDate;
	 private String fromTime;
	 
	 private String emailTo;
	 private String emailCc;
	 private String emailBcc;
	 
	 private String status;
	 private String requestor;
	 private LocalDateTime lastUpdatedTs;
	 private LocalDateTime emailSentTs;
	 
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
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public LocalDateTime getLastUpdatedTs() {
		return lastUpdatedTs;
	}
	public void setLastUpdatedTs(LocalDateTime lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
	}
	public LocalDateTime getEmailSentTs() {
		return emailSentTs;
	}
	public void setEmailSentTs(LocalDateTime emailSentTs) {
		this.emailSentTs = emailSentTs;
	}

	@Override
	public String toString() {		
		return  " Id:"+ this.id+
		 " clientId:" +this.clientId+
		 " reportType:"+ this.reportType+
		 " reportDate:"+ this.reportDate+
		 " reportTime:"+ this.reportTime+
		 " fromDate:"+ this.fromDate+
		 " emailTo:"+ this.emailTo+
		 " requestor:"+ this.requestor+
		 " lastUpdatedTs:"+this.lastUpdatedTs+
		 " status:"+ this.status;
	}
}
