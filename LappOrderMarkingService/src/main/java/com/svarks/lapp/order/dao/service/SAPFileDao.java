package com.svarks.lapp.order.dao.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.SAPFileInfo;



@Repository
public interface SAPFileDao extends JpaRepository<SAPFileInfo, Integer>  {
	
	boolean findByFileName(@Param("fileName") String fileName );
	List<SAPFileInfo> getSAPDataByUser(@Param("uploadedBy") String uploadedBy );

}
