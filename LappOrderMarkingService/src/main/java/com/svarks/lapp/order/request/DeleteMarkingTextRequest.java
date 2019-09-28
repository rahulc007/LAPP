package com.svarks.lapp.order.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DeleteMarkingTextRequest {
	
	private int lineItemId;
	private boolean isSubmit;
	private String emailId;
	private int legsCount;
	private int markingId;
	
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

	public int getMarkingId() {
		return markingId;
	}

	public void setMarkingId(int markingId) {
		this.markingId = markingId;
	}

	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
