package com.svarks.lapp.order.dao.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.UserProfileEntity;

@Repository
public interface UserProfileDao extends JpaRepository<UserProfileEntity, Integer> {

	UserProfileEntity getProfileByEmail(@Param("emailId") String emailId);
}
