package com.svarks.lapp.order.common;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DateUtilCommonSerive {
	
	public Long getCurrentTimeInMilliseconds() {
		
		Date dt = new Date();
		return dt.getTime();
	}
}
