package com.svarks.lapp.order.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateUtilCommonSerive {
	
	public Long getCurrentTimeInMilliseconds() {
		
		Date dt = new Date();
		return dt.getTime();
	}
	
	public Date getDateByValue(String date) {
		try {
		return new SimpleDateFormat("yyyy-mm-dd").parse(date);
		}catch(Exception e) {
			return new Date();
		}
	}
}
