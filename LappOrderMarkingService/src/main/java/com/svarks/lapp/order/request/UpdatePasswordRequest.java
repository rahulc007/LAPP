package com.svarks.lapp.order.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UpdatePasswordRequest {
	
	private String emailId;
	private String oldPassword;
	private String newPassword;
	private String countryCode;
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }
}
