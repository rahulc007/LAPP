package com.svarks.lapp.order.dao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.OrderInfo;


@Repository
public interface OrderInfoDao extends JpaRepository<OrderInfo, Integer> {
	
	boolean findBySalesOrder(@Param("salesOrderno") String salesOrderno );
	OrderInfo getOrderBySalesOrder(@Param("salesOrderno") String salesOrderno );
	List<OrderInfo> getOrderByAdmin(@Param("createdBy") String createdBy,Pageable pageable);
	List<OrderInfo> getProcessedOrderByAdmin(@Param("createdBy") String createdBy,Pageable pageable);
	List<OrderInfo> getProcessedOrderByUser(@Param("userEmailId") String userEmailId,Pageable pageable);
	List<OrderInfo> getMyOrderBySales(@Param("salesOrderno") String salesOrderno,@Param("userEmailId") String userEmailId);
	List<OrderInfo> getMyOrderBySalesAndAdmin(@Param("salesOrderno") String salesOrderno,@Param("createdBy") String createdBy);
	
	List<OrderInfo> getMyOrderByProdOrder(@Param("productionOrderno") String salesOrderno,@Param("userEmailId") String userEmailId);
	List<OrderInfo> getMyOrderByProdOrderAndAdmin(@Param("productionOrderno") String salesOrderno,@Param("createdBy") String createdBy);
	
	
	//List<OrderInfo> getOderByUser(@Param("userEmailId") String userEmailId,@Param("startLimit")int startLimit,@Param("endLimit")int endLimit);
	//List<OrderInfo> getOderByUser(@Param("userEmailId") String userEmailId,@Param("startLimit")int startLimit,@Param("endLimit") int endLimit);//
	List<OrderInfo> getOderByUser(@Param("userEmailId") String userEmailId,Pageable pageable);
}
