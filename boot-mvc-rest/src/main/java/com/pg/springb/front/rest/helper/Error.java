package com.pg.springb.front.rest.helper;

public enum Error {
	
	NUM("0"), MSG("");
	private String val;
	
	Error(String valp){
		this.val = valp;
	}
	public Error set(String valp){
		this.val = valp;
		return this;
	}
	public String val(){
		return this.val;
	}
}
