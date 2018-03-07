package com.pg.springb.front.documents;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "file_process_log")
public class FileProcessLogDocument {

	@Id
    private String id;
    private String docType;
    private String fileName;
    private String status;
    private String uploadedBy;
    private String uploadedPath;
    private LocalDateTime uploadedTs;
    private String processedFilePath;
    private LocalDateTime processedTs;
    private String hostName;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getUploadedPath() {
		return uploadedPath;
	}
	public void setUploadedPath(String uploadedPath) {
		this.uploadedPath = uploadedPath;
	}
	public LocalDateTime getUploadedTs() {
		return uploadedTs;
	}
	public void setUploadedTs(LocalDateTime uploadedTs) {
		this.uploadedTs = uploadedTs;
	}
	public String getProcessedFilePath() {
		return processedFilePath;
	}
	public void setProcessedFilePath(String processedFilePath) {
		this.processedFilePath = processedFilePath;
	}
	public LocalDateTime getProcessedTs() {
		return processedTs;
	}
	public void setProcessedTs(LocalDateTime processedTs) {
		this.processedTs = processedTs;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Override
    public String toString() {
    	return  " Id:"+id+
    			" docType:"+docType+
    			" status:"+status+
    			" fileName:"+fileName+
    			" uploadedBy:"+uploadedBy+
    			" uploadedPath:"+uploadedPath+
    			" uploadedTs:"+uploadedTs+
    			" processedFilePath:"+processedFilePath+
    			" processedTs:"+processedTs+
    			" hostName:"+hostName;
    }
}