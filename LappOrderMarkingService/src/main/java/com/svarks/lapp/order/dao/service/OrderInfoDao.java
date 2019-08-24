package com.svarks.lapp.order.dao.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.OrderInfo;


@Repository
public interface OrderInfoDao extends JpaRepository<OrderInfo, Integer> {
	
	boolean findBySalesOrder(@Param("salesOrderno") String salesOrderno );
	List<OrderInfo> getOrderByAdmin(@Param("createdBy") String createdBy);
	List<OrderInfo> getOderByUser(@Param("userEmailId") String userEmailId);

}
