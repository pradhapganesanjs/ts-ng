package com.pg.springb.front.documents;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client_email_process_config")
public class ClientEmailProcessDocument {
	 private static final long serialVersionUID = 1L; 
	 private String id;
	 private String clientId;
	 private String intervalJobStatus;
	 private String dailyJobStatus;
	
	 private LocalDateTime intervalEmailSentTS;
	 private LocalDateTime dailyEmailSentTS;
	 
	 private String reportType;
	
	
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

	public String getIntervalJobStatus() {
		return intervalJobStatus;
	}

	public void setIntervalJobStatus(String intervalJobStatus) {
		this.intervalJobStatus = intervalJobStatus;
	}

	public String getDailyJobStatus() {
		return dailyJobStatus;
	}
	
	public void setDailyJobStatus(String dailyJobStatus) {
		this.dailyJobStatus = dailyJobStatus;
	}

	public LocalDateTime getIntervalEmailSentTS() {
		return intervalEmailSentTS;
	}

	public void setIntervalEmailSentTS(LocalDateTime intervalEmailSentTS) {
		this.intervalEmailSentTS = intervalEmailSentTS;
	}

	public LocalDateTime getDailyEmailSentTS() {
		return dailyEmailSentTS;
	}

	public void setDailyEmailSentTS(LocalDateTime dailyEmailSentTS) {
		this.dailyEmailSentTS = dailyEmailSentTS;
	}
	

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Override
	public String toString() {		
		return  "Id:"+ this.id+
		 
		 " intervalJobStatus:"+ this.intervalJobStatus+
		 " dailyJobStatus:"+ this.dailyJobStatus;
		
	}
}
