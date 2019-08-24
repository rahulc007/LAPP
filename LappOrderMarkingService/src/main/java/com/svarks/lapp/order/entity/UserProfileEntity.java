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
@Table(name = "user_profile")
@NamedQueries({
		@NamedQuery(name = "UserProfileEntity.getUserProfileData", query = "SELECT u FROM UserProfileEntity u where u.uemailId=:username "),
		@NamedQuery(name = "UserProfileEntity.getAllUserDetails", query = "SELECT e FROM UserProfileEntity e where e.userType > 1 order by e.pid desc"),
		@NamedQuery(name = "UserProfileEntity.fetchAllUserByCreation", query = "SELECT e FROM UserProfileEntity e where (e.createdBy=:createdBy AND e.userType > 1)"),
		@NamedQuery(name = "UserProfileEntity.updateProfile", query = "UPDATE UserProfileEntity p SET p.firstname=:firstname,"
				+ " p.lastname=:lastname, p.state=:state, p.city=:city,p.phonenumber=:phonenumber,p.modifiedDate=now() where p.pid=:pid AND p.uemailId=:pemailId") })

public class UserProfileEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int pid;
	private String uemailId;
	private String firstname;
	private String lastname;
	private String consumerId;
	private String country;
	private String state;
	private String city;
	private String phonenumber;
	private int userType;
	private String createdBy;
	private Date createdDate;
	private Date modifiedDate;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getUemailId() {
		return uemailId;
	}

	public void setUemailId(String uemailId) {
		this.uemailId = uemailId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
