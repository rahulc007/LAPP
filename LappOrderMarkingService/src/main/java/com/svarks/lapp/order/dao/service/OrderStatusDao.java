package com.svarks.lapp.order.dao.service;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.OrderStatusUpdate;

@Repository
public interface OrderStatusDao extends JpaRepository<OrderStatusUpdate, Integer> {
	
	
	boolean findByName(@Param("fname") String fname );
	List<OrderStatusUpdate> getByUser(@Param("uploadBy") String uploadBy );


}
