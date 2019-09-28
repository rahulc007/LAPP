package com.svarks.lapp.order.response;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.svarks.lapp.order.entity.OrderStatusUpdate;

public class OrderStatusResponse extends BaseResponse{
	
	List<OrderStatusUpdate> orderStatusList;
	
	public List<OrderStatusUpdate> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<OrderStatusUpdate> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}


	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
