package com.svarks.lapp.order.response;


public class LoginResponse extends BaseResponse {
	private int userType;

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "LoginResponse [userType=" + userType + "]";
	}

}
