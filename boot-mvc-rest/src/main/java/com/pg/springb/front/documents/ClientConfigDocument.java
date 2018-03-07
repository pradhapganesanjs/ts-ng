package com.pg.springb.front.documents;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client_lei_gfcid_config")
public class ClientConfigDocument {
	
	 private static final long serialVersionUID = 1L; 
	
	 private String id;
	 private String clientId;
	 private String gfcId;
	 private String lei;
	 private String active;
	 private String requestor = "SYSTEM";
	 private LocalDateTime lastUpdatedTs;
	 
	 public LocalDateTime getLastUpdatedTs() {
		return lastUpdatedTs;
	}
	public void setLastUpdatedTs(LocalDateTime lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
	}
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
	public String getGfcId() {
		return gfcId;
	}
	public void setGfcId(String gfcId) {
		this.gfcId = gfcId;
	}
	public String getLei() {
		return lei;
	}
	public void setLei(String lei) {
		this.lei = lei;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	 
	@Override
	public String toString() {		
		return  " Id:"+ this.id+
		 " clientId:" +this.clientId+
		 " gfcid:"+ this.gfcId+
		 " lei:"+ this.lei+
		 " active:"+ this.active+
		 " requestor:"+ this.requestor+
		 " lastUpdatedTs:"+this.lastUpdatedTs;
	}


}
