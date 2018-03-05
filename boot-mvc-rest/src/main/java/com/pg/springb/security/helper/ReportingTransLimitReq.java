package com.citi.frontier.rest.helper;

import com.citi.frontier.documents.ReportingTransDocument;

public class ReportingTransLimitReq extends ReportingTransDocument{
	private String lastId;
	private int start;
	private int limit;
	
	public String getLastId() {
		return lastId;
	}
	public void setLastId(String lastId) {
		this.lastId = lastId;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	} 
}
