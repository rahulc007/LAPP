package com.svarks.lapp.order.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.OrderLineItem;

@Repository
public interface OrderLineItemDao extends JpaRepository<OrderLineItem, Integer> {
	
	List<OrderLineItem> getSalesOrderItem(@Param("salesOrderno") String salesOrderno);
	List<OrderLineItem> getLineItemBySales(@Param("salesOrderno") String salesOrderno);
	List<OrderLineItem> getProcessedSalesOrderItem(@Param("salesOrderno") String salesOrderno);
	List<OrderLineItem> getLineItemByItemno(@Param("lineItemId") int lineItemId);
	boolean findByLineItemNo(@Param("lineItemno") String lineItemno );
	
	@Transactional
	 @Modifying
	void updateLineItem(@Param("isSubmit") boolean isSubmit,@Param("legsCount") int legsCount,@Param("lineItemId") int lineItemId);
	

	@Transactional
	 @Modifying
	void updateOrderStatus(@Param("lineItemno") String lineItemno,@Param("salesOrderno") String salesOrderno,@Param("productionOrderStatus") String productionOrderStatus);

}
