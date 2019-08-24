package com.svarks.lapp.order.dao.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.UserProfileEntity;

@Repository
public interface UserProfileDao extends JpaRepository<UserProfileEntity, Integer> {

	UserProfileEntity getUserProfileData(@Param("username") String uemailId);
	List<UserProfileEntity> getAllUserDetails();
	List<UserProfileEntity> fetchAllUserByCreation(@Param("createdBy") String createdBy);
	
	@Transactional
	@Modifying
	void updateProfile(@Param("firstname") String firstname, @Param("lastname") String lastname,
			@Param("state") String state, @Param("city") String city, @Param("phonenumber") String phonenumber,
			@Param("pid") int pid,@Param("pemailId") String emailId);
	
}
