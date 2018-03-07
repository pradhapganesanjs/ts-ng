package com.pg.springb.front.documents;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "report_schedule_config")
public class ReportScheduleDocument {
	 
	 @Id
	 private String id;
	 //xlsx mandate params
	 private String clientId;
	 private String organization;
	 
	 private String intervalFile;
	 private String intervalMinute;
	 private String intervalEmailTo;
	 private String intervalEmailCc;
	 private String intervalEmailBcc;
	 
	 private String dailyFile;
	 private String dailyTime;
	 private String dailyEmailTo;
	 private String dailyEmailCc;
	 private String dailyEmailBcc;
	 
	 //xlsx optional params
	 private String weeklyFile;
	 private String dayOfWeek;
	 private String hashKey;
	 private String secretKey;
	 private String mtls;
	 
	 //system params
	 private String requestor;
	 private String intervalJobStatus;
	 private String dailyJobStatus;
	 private LocalDateTime lastUpdatedTs;
	 private LocalDateTime intervalEmailSentTS;
	 private LocalDateTime dailyEmailSentTS;
	 
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
	public String getIntervalFile() {
		return intervalFile;
	}
	public void setIntervalFile(String intervalFile) {
		this.intervalFile = intervalFile;
	}
	public String getIntervalMinute() {
		return intervalMinute;
	}
	public void setIntervalMinute(String intervalMinute) {
		this.intervalMinute = intervalMinute;
	}
	public String getIntervalEmailTo() {
		return intervalEmailTo;
	}
	public void setIntervalEmailTo(String intervalEmailTo) {
		this.intervalEmailTo = intervalEmailTo;
	}
	public String getIntervalEmailCc() {
		return intervalEmailCc;
	}
	public void setIntervalEmailCc(String intervalEmailCc) {
		this.intervalEmailCc = intervalEmailCc;
	}
	public String getIntervalEmailBcc() {
		return intervalEmailBcc;
	}
	public void setIntervalEmailBcc(String intervalEmailBcc) {
		this.intervalEmailBcc = intervalEmailBcc;
	}
	public String getDailyFile() {
		return dailyFile;
	}
	public void setDailyFile(String dailyFile) {
		this.dailyFile = dailyFile;
	}
	public String getDailyTime() {
		return dailyTime;
	}
	public void setDailyTime(String dailyTime) {
		this.dailyTime = dailyTime;
	}
	public String getDailyEmailTo() {
		return dailyEmailTo;
	}
	public void setDailyEmailTo(String dailyEmailTo) {
		this.dailyEmailTo = dailyEmailTo;
	}
	public String getDailyEmailCc() {
		return dailyEmailCc;
	}
	public void setDailyEmailCc(String dailyEmailCc) {
		this.dailyEmailCc = dailyEmailCc;
	}
	public String getDailyEmailBcc() {
		return dailyEmailBcc;
	}
	public void setDailyEmailBcc(String dailyEmailBcc) {
		this.dailyEmailBcc = dailyEmailBcc;
	}
	public String getWeeklyFile() {
		return weeklyFile;
	}
	public void setWeeklyFile(String weeklyFile) {
		this.weeklyFile = weeklyFile;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
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
	public String getMtls() {
		return mtls;
	}
	public void setMtls(String mtls) {
		this.mtls = mtls;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
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
	public LocalDateTime getLastUpdatedTs() {
		return lastUpdatedTs;
	}
	public void setLastUpdatedTs(LocalDateTime lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
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
	
	@Override
	public String toString(){	
		return  "Id:"+ this.id+
		 " clientId:"+this.clientId+
		 " organization:"+this.organization+
		 " intervalMinute:"+ this.intervalMinute+
		 " dailyTime:"+ this.dailyTime+
		 " dailyEmailTo:"+this.dailyEmailTo+
//		 " hashKey:"+ this.hashKey+
//		 " MTLS:"+ this.mtls+
		 " requestor:"+this.requestor+
		 " intervalJobStatus:"+this.intervalJobStatus+
		 " dailyJobStatus:"+this.dailyJobStatus+
		 " lastUpdatedTs:"+this.lastUpdatedTs;
//		 " intervalEmailSentTS:"+ this.intervalEmailSentTS+
//		 " dailyEmailSentTS:"+ this.dailyEmailSentTS;
	}
}
