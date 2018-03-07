package com.pg.springb.front.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "frontier_users")
public class UserDocument {

	private String id;
	private String userId;
	private String userPassword;
	private String active;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	@Override
	public String toString() {		
		return  "_Id:"+ this.id+
				" userId:"+ this.userId+
				" userPassword:"+ this.userPassword+
				" active:"+ this.active;
	}
}
