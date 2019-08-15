package com.svarks.lapp.order.dao.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.entity.UserProfileEntity;

@Repository
public class UserServiceDaoImpl {//implements UserServiceDao {
	
	@PersistenceContext	
	private EntityManager entityManager;	

	
	
	public UserProfileEntity findProfilByEmail(String emailId) {
		String query="SELECT u FROM UserProfileEntity u WHERE u.uemailId =:uemailId";
		List results= entityManager.createQuery(query).setParameter("uemailId", emailId).getResultList();
		if (!results.isEmpty())
			 return (UserProfileEntity) results.get(0);
			else
				return null;
	}
	
/*
	@Override
	public boolean findByEmailId(String emailId) {
		String hql = "FROM Article as atcl ORDER BY atcl.articleId";
		return false;
	}

	@Override
	public boolean findByCustId(String customerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateIsEmailVerified(String emailId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserEntity findUserByCredentials(String emailId, String password, String countryCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetNewPassword(String emailId, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserEntity findUserByCustomerId(String customerId, String password, String countryCode) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
