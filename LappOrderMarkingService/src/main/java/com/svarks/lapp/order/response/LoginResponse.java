package com.svarks.lapp.order.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class LoginResponse extends BaseResponse {
	private int userType;
	private boolean isFirstTimeLogin;
	private String username;
	private String countryCode;
	private String token;

	
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
	public boolean isFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setFirstTimeLogin(boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}

	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }


}
