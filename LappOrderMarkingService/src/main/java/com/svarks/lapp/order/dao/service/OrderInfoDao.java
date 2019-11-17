package com.svarks.lapp.order.dao.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.OrderInfo;


@Repository
public interface OrderInfoDao extends JpaRepository<OrderInfo, Integer> {
	
	boolean findBySalesOrder(@Param("salesOrderno") String salesOrderno );
	OrderInfo getOrderBySalesOrder(@Param("salesOrderno") String salesOrderno );
	List<OrderInfo> getOrderByAdmin(@Param("countryCode") String countryCode,@Param("startLimit")int startLimit,@Param("endLimit")int endLimit);
	List<OrderInfo> getProcessedOrderByAdmin(@Param("countryCode") String countryCode,@Param("startLimit")int startLimit,@Param("endLimit")int endLimit);
	List<OrderInfo> getProcessedOrderByUser(@Param("userEmailId") String userEmailId,@Param("startLimit")int startLimit,@Param("endLimit")int endLimit);
	List<OrderInfo> getMyOrderBySales(@Param("salesOrderno") String salesOrderno,@Param("userEmailId") String userEmailId);
	List<OrderInfo> getOrderBySales(@Param("salesOrderno") String salesOrderno);
	List<OrderInfo> getMyOrderBySalesAndAdmin(@Param("salesOrderno") String salesOrderno,@Param("countryCode") String countryCode);
	List<OrderInfo> getMyOrderByDate(@Param("countryCode") String countryCode,@Param("startDate") String startDate,@Param("endDate") String endDate
			,@Param("startLimit")int startLimit,@Param("endLimit")int endLimit);
	List<OrderInfo> getProcessedOrderByDate(@Param("countryCode") String countryCode,@Param("startDate") String startDate,@Param("endDate") String endDate
			,@Param("startLimit")int startLimit,@Param("endLimit")int endLimit);
	
	List<OrderInfo> getMyOrderByProdOrder(@Param("userEmailId") String userEmailId,@Param("productionOrderno") String productionOrderno);
	int getMyOrderCount(@Param("countryCode") String countryCode);
	int getMyProcessedCount(@Param("countryCode") String countryCode);
	List<OrderInfo> getMyOrderByProdOrderAndAdmin(@Param("countryCode") String countryCode,@Param("productionOrderno") String productionOrderno);
	
	
	List<OrderInfo> getOderByUser(@Param("userEmailId") String userEmailId,@Param("startLimit")int startLimit,@Param("endLimit")int endLimit);
	//List<OrderInfo> getOderByUser(@Param("userEmailId") String userEmailId,@Param("startLimit")int startLimit,@Param("endLimit") int endLimit);//
	//List<OrderInfo> getOderByUser(@Param("userEmailId") String userEmailId,Pageable pageable);
}
