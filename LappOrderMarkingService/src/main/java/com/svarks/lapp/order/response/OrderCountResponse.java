package com.svarks.lapp.order.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class OrderCountResponse extends BaseResponse{
	
	int myOrderCount;
	int processedOrderCount;
	
	public int getMyOrderCount() {
		return myOrderCount;
	}
	public void setMyOrderCount(int myOrderCount) {
		this.myOrderCount = myOrderCount;
	}
	public int getProcessedOrderCount() {
		return processedOrderCount;
	}
	public void setProcessedOrderCount(int processedOrderCount) {
		this.processedOrderCount = processedOrderCount;
	}
	
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }
	

}
