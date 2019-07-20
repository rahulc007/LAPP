package com.svarks.lapp.order.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class LoginRequest {
	
	private String emailId;
	private String password;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }


}
