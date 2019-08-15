package com.svarks.lapp.order.response;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.svarks.lapp.order.entity.UserProfileEntity;

public class UserCreationResponse extends BaseResponse {

	List<UserProfileEntity> userProfileList;

	public List<UserProfileEntity> getUserProfileList() {
		return userProfileList;
	}

	public void setUserProfileList(List<UserProfileEntity> userProfileList) {
		this.userProfileList = userProfileList;
	}


	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }
	
}
