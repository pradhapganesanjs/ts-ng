package com.citi.frontier.rest.jwtsecured;

import java.util.List;

import com.citi.frontier.documents.UserDocument;

public class JwtUser extends UserDocument{

	private String authToken;
	private String userName;
	private String password;
	private List<String> roles;
	
	public static JwtUser copyOf(UserDocument userDoc) {
		if(null == userDoc) return null;
		JwtUser jwtUser = new JwtUser();

		jwtUser.setId(userDoc.getId());
		jwtUser.setUserId(userDoc.getUserId());
		jwtUser.setActive(userDoc.getActive());
		jwtUser.setUserName(userDoc.getUserId());
		//jwtUser.setUserPassword(userDoc.getUserPassword());
		//jwtUser.setPassword(userDoc.getUserPassword());
		return jwtUser;
	}
	
	public JwtUser() {
	}
	
	public JwtUser(String userName, String password, List<String> roles){
		this.userName = userName;
		this.roles = roles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
