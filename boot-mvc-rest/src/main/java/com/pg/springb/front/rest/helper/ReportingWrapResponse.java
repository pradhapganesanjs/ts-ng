package com.pg.springb.front.rest.helper;

import java.util.List;

public class ReportingWrapResponse<T> {

	List<String> headers = null;
	List<T> records = null;

	public ReportingWrapResponse() {
	}
	public ReportingWrapResponse(List<String> headers, List<T> records) {
		this.headers = headers;
		this.records = records;
	}
	
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}
}
