package com.svarks.lapp.order.dao.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.UserAuthInfo;

@Repository
public interface UserAuthDao extends JpaRepository<UserAuthInfo, Integer> {

}
