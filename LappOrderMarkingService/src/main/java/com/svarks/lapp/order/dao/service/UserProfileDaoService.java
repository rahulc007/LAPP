package com.svarks.lapp.order.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.svarks.lapp.order.entity.UserProfileEntity;

public interface UserProfileDaoService {
	
	List<UserProfileEntity> getUserProfileDt(String uemailId);
	/*List<UserProfileEntity> getAllUserDetails();
	List<UserProfileEntity> fetchAllUserByCreation(@Param("createdBy") String createdBy);
	
	@Transactional
	@Modifying
	void updateProfile(@Param("firstname") String firstname, @Param("lastname") String lastname,
			@Param("state") String state, @Param("city") String city, @Param("phonenumber") String phonenumber,
			@Param("pid") int pid,@Param("emailId") String emailId);*/

}
