package com.svarks.lapp.order.response;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.svarks.lapp.order.entity.MarkingTextItem;

public class MarkingTextResponse extends BaseResponse {
	
	
	List<MarkingTextItem> markingTextList=new LinkedList<>();

	public List<MarkingTextItem> getMarkingTextList() {
		return markingTextList;
	}

	public void setMarkingTextList(List<MarkingTextItem> markingTextList) {
		this.markingTextList = markingTextList;
	}
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
