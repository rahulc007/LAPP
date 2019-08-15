package com.svarks.lapp.order.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.UserEntity;

@Repository
public interface UserServiceDao extends JpaRepository<UserEntity, Integer>{

	
	 boolean findByEmailId(@Param("emailId") String emailId );
	 boolean isValidUser(@Param("emailId") String emailId );
	 boolean findByCustId(@Param("customerId") String customerId );
	 @Transactional
	 @Modifying
	 void updateIsEmailVerified(@Param("emailId") String emailId);
	 UserEntity findUserByCredentials(@Param("emailId") String emailId, @Param("password") String password ,@Param("countryCode")String countryCode);
	 @Transactional
	 @Modifying
	 void resetNewPassword(@Param("emailId") String emailId, @Param("password") String password);
	 UserEntity findUserByCustomerId(@Param("customerId") String customerId, @Param("password") String password ,@Param("countryCode")String countryCode);
	// List<UserEntity> getUserByEmail(@Param("emailId") String emailId);
}
