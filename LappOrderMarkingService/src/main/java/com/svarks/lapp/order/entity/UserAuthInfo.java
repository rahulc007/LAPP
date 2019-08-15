package com.svarks.lapp.order.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "user_auth_info")
@NamedQueries({
		@NamedQuery(name = "UserAuthInfo.findByToken", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM UserAuthInfo e WHERE e.token =:token "),
		@NamedQuery(name = "UserAuthInfo.deleteAuthToken", query = "DELETE from UserAuthInfo u where u.token =:token ") })

public class UserAuthInfo  implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int authid;
	private String emailId;
	private String token;
	private Date createdDate;
	public int getAuthid() {
		return authid;
	}
	public void setAuthid(int authid) {
		this.authid = authid;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }
}
