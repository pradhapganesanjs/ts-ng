package com.citi.frontier.rest.helper;

public class ReportingTransCriteriaReq {
	private String lastId;
	private int limit;
	private String critKey;
	private String critVal;

	public String getLastId() {
		return lastId;
	}

	public int getLimit() {
		return limit;
	}

	public String getCritKey() {
		return critKey;
	}

	public void setLastId(String lastId) {
		this.lastId = lastId;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setCritKey(String critKey) {
		this.critKey = critKey;
	}

	public String toString() {
		return String.format("lastId: %s, critKey: %s, critVal: %s, limit: %d", this.lastId, this.critKey, this.critVal, this.limit);
	}

	public String getCritVal() {
		return critVal;
	}

	public void setCritVal(String critVal) {
		this.critVal = critVal;
	}
}
