package com.pg.springb.front.documents;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="active_host")
public class ActiveHostDocument {
	
	private String id;
	private String hostName;
	private String hostAddress;
	private String port;
	private LocalDateTime lastActiveTS;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostAddress() {
		return hostAddress;
	}
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public LocalDateTime getLastActiveTS() {
		return lastActiveTS;
	}
	public void setLastActiveTS(LocalDateTime lastActiveTS) {
		this.lastActiveTS = lastActiveTS;
	}
	
	@Override
	public String toString() {
		return " _Id:"+this.id+
				" hostName:"+this.hostName+
				" hostAddress:"+this.hostAddress+
				" port:"+this.port+
				" lastActiveTS:"+this.lastActiveTS;
	}
}
