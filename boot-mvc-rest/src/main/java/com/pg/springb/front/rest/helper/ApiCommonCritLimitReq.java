package com.pg.springb.front.rest.helper;

public class ApiCommonCritLimitReq<T> {
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
