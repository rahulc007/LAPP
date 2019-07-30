package com.svarks.lapp.order.response;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.svarks.lapp.order.entity.SAPFileInfo;

public class SAPFileInfoResponse extends BaseResponse {

	List<SAPFileInfo> sapFileInofList;

	public List<SAPFileInfo> getSapFileInofList() {
		return sapFileInofList;
	}

	public void setSapFileInofList(List<SAPFileInfo> sapFileInofList) {
		this.sapFileInofList = sapFileInofList;
	}

	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
