package com.svarks.lapp.order.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svarks.lapp.order.entity.MarkingTextItem;

@Repository
public interface MarkingTextDao extends JpaRepository<MarkingTextItem,Integer>{

	List<MarkingTextItem> getTextByLineItem(@Param("lineItemid") int lineItemid);
	
	@Transactional
	@Modifying
	void updateMarkingtext(@Param("leftText") String leftText, @Param("rightText") String rightText,
			@Param("middleText") String middleText, @Param("markingId") int markingId);
	
	@Transactional
	@Modifying
	void deleteMarkingtext(@Param("markingId") int markingId);
	
}
