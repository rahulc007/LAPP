package com.svarks.lapp.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Transactional
@Table(name = "user_auth_info")
/*@NamedQueries({
		@NamedQuery(name = "SAPFileInfo.getUserByEmail", query = "SELECT e FROM UserEntity e WHERE e.emailId =:emailId "),
		@NamedQuery(name = "SAPFileInfo.resetNewPassword", query = "UPDATE UserEntity e SET e.password =:password where e.emailId =:emailId ") })
*/
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
