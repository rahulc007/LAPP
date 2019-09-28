package com.svarks.lapp.order.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UpdateMarkingTextRequest {
	
	private int lineItemId;
	private boolean isSubmit;
	private String emailId;
	private int legsCount;
	private int markingId;
	private String leftText;
	private String rightText;
	private String middleText;

	public int getLineItemId() {
		return lineItemId;
	}


	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}


	public int getLegsCount() {
		return legsCount;
	}


	public void setLegsCount(int legsCount) {
		this.legsCount = legsCount;
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


	public int getMarkingId() {
		return markingId;
	}


	public void setMarkingId(int markingId) {
		this.markingId = markingId;
	}


	public String getLeftText() {
		return leftText;
	}


	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}


	public String getRightText() {
		return rightText;
	}


	public void setRightText(String rightText) {
		this.rightText = rightText;
	}


	public String getMiddleText() {
		return middleText;
	}


	public void setMiddleText(String middleText) {
		this.middleText = middleText;
	}


	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
