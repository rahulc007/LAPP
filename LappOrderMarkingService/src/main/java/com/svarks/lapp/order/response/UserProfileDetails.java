package com.svarks.lapp.order.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.svarks.lapp.order.entity.UserProfileEntity;

public class UserProfileDetails extends BaseResponse {
	
	private UserProfileEntity userProfileEntity;

	public UserProfileEntity getUserProfileEntity() {
		return userProfileEntity;
	}

	public void setUserProfileEntity(UserProfileEntity userProfileEntity) {
		this.userProfileEntity = userProfileEntity;
	}
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }
	
	

}
