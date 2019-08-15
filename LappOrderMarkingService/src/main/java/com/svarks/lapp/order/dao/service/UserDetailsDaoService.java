package com.svarks.lapp.order.dao.service;


import com.svarks.lapp.order.entity.UserEntity;
 

public interface UserDetailsDaoService {
	
	
	boolean findByEmailId(String emailId );
	 boolean findByCustId(String customerId );
	 void updateIsEmailVerified(String emailId);
	 UserEntity findUserByCredentials(String emailId,String password,String countryCode);
	 void resetNewPassword(String emailId,String password);
	 //UserEntity findUserByCustomerId(String customerId, @Param("password") String password ,@Param("countryCode")String countryCode);
	// List<UserEntity> getUserByEmail(@Param("emailId") String emailId);

}
