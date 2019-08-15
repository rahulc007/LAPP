package com.svarks.lapp.order.dao.service;

import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.UserAuthInfo;

@Repository
public interface UserAuthDao extends JpaRepository<UserAuthInfo, Integer> {

	
	 boolean findByToken(@Param("token") String token );
	 void deleteAuthToken(@Param("token") String token );
	 
	 /*Query query = em.createQuery(
		      "SELECT c FROM Country c WHERE c.name = 'Canada'");
		  Country c = (Country)query.getSingleResult();*/
}
