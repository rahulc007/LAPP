package com.svarks.lapp.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Transactional
@Table(name = "user_entity")
@NamedQueries({
		@NamedQuery(name = "UserEntity.getUserByEmail", query = "SELECT e FROM UserEntity e WHERE e.emailId =:emailId "),
		@NamedQuery(name = "UserEntity.findByEmailId", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM UserEntity e WHERE e.emailId =:emailId "),
		@NamedQuery(name = "UserEntity.findByCustId", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM UserEntity e WHERE e.customerId =:customerId "),
		@NamedQuery(name = "UserEntity.updateIsEmailVerified", query = "UPDATE UserEntity e SET e.isEmailConfirmed =1 where e.emailId =:emailId "),
		@NamedQuery(name = "UserEntity.findUserByCredentials", query = "SELECT e FROM UserEntity e WHERE e.emailId =:emailId AND e.password =:password AND e.countryCode=:countryCode AND e.isEmailConfirmed=true"),
		@NamedQuery(name = "UserEntity.findUserByCustomerId", query = "SELECT e FROM UserEntity e WHERE e.customerId =:customerId AND e.password =:password AND e.countryCode=:countryCode AND e.isEmailConfirmed=true"),
		@NamedQuery(name = "UserEntity.resetNewPassword", query = "UPDATE UserEntity e SET e.password =:password where e.emailId =:emailId ") })

public class UserEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int uid;
    private String emailId;
    private String token;
    private String password;
    private String customerId;
    private int utype;
    private boolean isEmailConfirmed;
    private Date createdDate;
    private Date modifiedDate;
    private String countryCode;
    private boolean isFirstTimeLogin;
    
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public int getUtype() {
		return utype;
	}
	public void setUtype(int utype) {
		this.utype = utype;
	}
	public boolean isEmailConfirmed() {
		return isEmailConfirmed;
	}
	public void setEmailConfirmed(boolean isEmailConfirmed) {
		this.isEmailConfirmed = isEmailConfirmed;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public boolean isFirstTimeLogin() {
		return isFirstTimeLogin;
	}
	public void setFirstTimeLogin(boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
