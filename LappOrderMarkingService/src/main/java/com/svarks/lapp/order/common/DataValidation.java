package com.svarks.lapp.order.common;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DataValidation {
	
	public boolean isHeaderMatches(String headerName) {
		return getSAPHeaderList().stream().anyMatch(headerName::equalsIgnoreCase);
	}
	
	public boolean isStatusHeaderMatches(String headerName) {
		return getOrderStatusHeaderList().stream().anyMatch(headerName::equalsIgnoreCase);
	}
	
	private List<String> getOrderStatusHeaderList(){
		List<String> headerList = new LinkedList<String>();
		headerList.add("ProductionOrder No");
		headerList.add("Status");
		headerList.add("SalesOrder No");
		return headerList;
		
	}
	
	
	private List<String> getSAPHeaderList(){
		List<String> headerList = new LinkedList<String>();
		headerList.add("LineItem");
		headerList.add("OpenQuantity");
		headerList.add("ProductionOrder No");
		headerList.add("Production Order Status");
		headerList.add("ArticleNo");
		headerList.add("Description");
		headerList.add("CustomerPart No");
		headerList.add("EmailID");
		headerList.add("CustomerNo");
		headerList.add("Contact");
		headerList.add("Country");
		headerList.add("SalesOrder No");
		//headerList.add("");
		return headerList;
		
	}
	
	public boolean isValidString(String str) {
		return (str != null && !str.isEmpty() );
	}

}
