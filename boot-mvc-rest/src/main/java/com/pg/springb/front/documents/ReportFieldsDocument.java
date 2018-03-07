package com.pg.springb.front.documents;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reporting_fields")
public class ReportFieldsDocument{

	@Id
	private String id;
	private List<ReportFieldsEntityMapping> fields;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ReportFieldsEntityMapping> getFields() {
		return fields;
	}
	public void setFields(List<ReportFieldsEntityMapping> fields) {
		this.fields = fields;
	}
	
	@Override
	public String toString() {
		return this.fields.toString();
	}
}