package com.pg.springb.front.documents;

public class ReportFieldsEntityMapping {
	
	private String fieldName;
	private String attributeName;
	private String attributeLevel;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeLevel() {
		return attributeLevel;
	}
	public void setAttributeLevel(String attributeLevel) {
		this.attributeLevel = attributeLevel;
	}
	
	@Override
	public String toString() {
		return "{fieldName="+this.fieldName+", attributeName="+this.attributeName+", attributeLevel="+this.attributeLevel+"}";
	}
	
}
