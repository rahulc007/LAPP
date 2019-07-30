package com.svarks.lapp.order.common;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateUtilCommonSerive {
	
	public Long getCurrentTimeInMilliseconds() {
		
		Date dt = new Date();
		return dt.getTime();
	}

}
