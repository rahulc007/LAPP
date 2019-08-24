package com.svarks.lapp.order.response;

import java.util.List;

import com.svarks.lapp.order.entity.OrderInfo;

public class OrderDetailsResponse extends BaseResponse{

	List<OrderInfo> orderInfoList;

	public List<OrderInfo> getOrderInfoList() {
		return orderInfoList;
	}

	public void setOrderInfoList(List<OrderInfo> orderInfoList) {
		this.orderInfoList = orderInfoList;
	}
	
	
}
