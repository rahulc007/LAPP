package com.svarks.lapp.order.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ProfileUpdateRequest {
	
	private int pid;
	private String emailId;
	private String firstname;
	private String lastname;
	private String state;
	private String city;
	private String phonenumber;
	
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
