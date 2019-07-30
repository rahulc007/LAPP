package com.svarks.lapp.order.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class LoginResponse extends BaseResponse {
	private int userType;
	private boolean isFirstTimeLogin;
	private String token;

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
