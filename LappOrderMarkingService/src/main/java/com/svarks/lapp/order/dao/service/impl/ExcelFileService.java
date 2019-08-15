package com.svarks.lapp.order.dao.service.impl;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.entity.SAPFileInfo;
import com.svarks.lapp.order.entity.UserProfileEntity;

@Service
public class ExcelFileService {

	
	private static final Logger log = LoggerFactory.getLogger(ExcelFileService.class);
	
	public void createCustomerDataExcel(List<UserProfileEntity> userProfileList) {
		
		try {
			if(userProfileList != null && !userProfileList.isEmpty()) {
		 String filename = OrderMarkingConstants.EXCEL_LOCATION+OrderMarkingConstants.CUSTOMER_DATA_FILE_NAME;
         HSSFWorkbook workbook = new HSSFWorkbook();
         HSSFSheet sheet = workbook.createSheet(OrderMarkingConstants.CUSTOMER_SHEET_NAME);  
         int rowIndex=-1;

         HSSFRow rowhead = sheet.createRow((short)++rowIndex);
         rowhead.createCell(0).setCellValue("Profile ID.");
         rowhead.createCell(1).setCellValue("Customer Email ID");
         rowhead.createCell(2).setCellValue("Firstname");
         rowhead.createCell(3).setCellValue("Lastname");
         rowhead.createCell(4).setCellValue("Customer ID.");
         rowhead.createCell(5).setCellValue("Country");
         rowhead.createCell(6).setCellValue("State");
         rowhead.createCell(7).setCellValue("City");
         rowhead.createCell(8).setCellValue("Mobile Number");
         rowhead.createCell(9).setCellValue("Created Date");
         
         HSSFRow row = sheet.createRow((short)++rowIndex);
         for(UserProfileEntity userProfile:userProfileList) {
         row.createCell(0).setCellValue(userProfile.getPid());
         row.createCell(1).setCellValue(userProfile.getUemailId());
         row.createCell(2).setCellValue(userProfile.getFirstname());
         row.createCell(3).setCellValue(userProfile.getLastname());
         row.createCell(4).setCellValue(userProfile.getConsumerId());
         row.createCell(5).setCellValue(userProfile.getCountry());
         row.createCell(6).setCellValue(userProfile.getState());
         row.createCell(7).setCellValue(userProfile.getCity());
         row.createCell(8).setCellValue(userProfile.getPhonenumber());
         row.createCell(9).setCellValue(userProfile.getCreatedDate());
         row = sheet.createRow((short)++rowIndex);
         }

         FileOutputStream fileOut = new FileOutputStream(filename);
         workbook.write(fileOut);
         fileOut.close();
         workbook.close();
         log.info("****Customer Data Excel file generereated successfully*****");
			}

     } catch ( Exception ex ) {
    	 log.error("Exception while creating customer excel data==>",ex);
    	 ex.printStackTrace();
     }
		
}	
	
	
public void createSAPDataExcel(List<SAPFileInfo> sapFileDataList) {
		
		try {
			if(sapFileDataList != null && !sapFileDataList.isEmpty()) {
		 String filename = OrderMarkingConstants.EXCEL_LOCATION+OrderMarkingConstants.CUSTOMER_SAP_FILE_NAME;
         HSSFWorkbook workbook = new HSSFWorkbook();
         HSSFSheet sheet = workbook.createSheet(OrderMarkingConstants.SAP_SHEET_NAME);  
         int rowIndex=-1;

         HSSFRow rowhead = sheet.createRow((short)++rowIndex);
         rowhead.createCell(0).setCellValue("File ID.");
         rowhead.createCell(1).setCellValue("File Name");
         rowhead.createCell(2).setCellValue("File Size");
         rowhead.createCell(3).setCellValue("Sales Order Count");
         rowhead.createCell(4).setCellValue("Content Type.");
         rowhead.createCell(5).setCellValue("Uploaded By");
         rowhead.createCell(6).setCellValue("Created Date");
         
         HSSFRow row = sheet.createRow((short)++rowIndex);
         for(SAPFileInfo sapFile:sapFileDataList) {
         row.createCell(0).setCellValue(sapFile.getFileId());
         row.createCell(1).setCellValue(sapFile.getFileName());
         row.createCell(2).setCellValue(sapFile.getFileSize());
         row.createCell(3).setCellValue(sapFile.getOrderCount());
         row.createCell(4).setCellValue(sapFile.getContentType());
         row.createCell(5).setCellValue(sapFile.getUploadedBy());
         row.createCell(6).setCellValue(sapFile.getCreatedDate());
         row = sheet.createRow((short)++rowIndex);
         }

         FileOutputStream fileOut = new FileOutputStream(filename);
         workbook.write(fileOut);
         fileOut.close();
         workbook.close();
         log.info("****Customer Data Excel file generereated successfully*****");
			}

     } catch ( Exception ex ) {
    	 log.error("Exception while creating customer excel data==>",ex);
    	 ex.printStackTrace();
     }
		
}	
}
