package com.svarks.lapp.mailer.service;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MailerRequest {
	
	private String name;
	private String to;
	private String p1;
	private String p2;
	private String p3;
	private String p;
	private String url;
	private String buttonName;
	private String genericdesc;
	private String subject;
	private String label1;
	private String label2;
	private String label3;
	
	
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getLabel3() {
		return label3;
	}
	public void setLabel3(String label3) {
		this.label3 = label3;
	}
	public String getLabel1() {
		return label1;
	}
	public void setLabel1(String label1) {
		this.label1 = label1;
	}
	public String getLabel2() {
		return label2;
	}
	public void setLabel2(String label2) {
		this.label2 = label2;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getP3() {
		return p3;
	}
	public void setP3(String p3) {
		this.p3 = p3;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	public String getGenericdesc() {
		return genericdesc;
	}
	public void setGenericdesc(String genericdesc) {
		this.genericdesc = genericdesc;
	}
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }


}
