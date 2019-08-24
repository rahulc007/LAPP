package com.svarks.lapp.order.request;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.svarks.lapp.order.entity.MarkingTextItem;

public class MarkingTextRequest {
	
	private int lineItemId;
	private boolean isSubmit;
	private String emailId;
	private int legsCount;
	
	List<MarkingTextItem> markingTextList=new LinkedList<>();
	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public int getLegsCount() {
		return legsCount;
	}
	public void setLegsCount(int legsCount) {
		this.legsCount = legsCount;
	}
	public int getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}
	public boolean isSubmit() {
		return isSubmit;
	}
	public void setSubmit(boolean isSubmit) {
		this.isSubmit = isSubmit;
	}
	public List<MarkingTextItem> getMarkingTextList() {
		return markingTextList;
	}
	public void setMarkingTextList(List<MarkingTextItem> markingTextList) {
		this.markingTextList = markingTextList;
	}
	
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
