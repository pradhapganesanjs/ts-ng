package com.citi.frontier.rest.helper;

import java.util.List;

public class ReportingTransWrapResponse<T> {

	List<String> headers = null;
	List<T> records = null;

	public ReportingTransWrapResponse() {
	}
	public ReportingTransWrapResponse(List<String> headers, List<T> records) {
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
