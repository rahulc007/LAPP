package com.svarks.lapp.order.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.request.NewUserRequest;
import com.svarks.lapp.order.response.BaseResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OrderMarkingAdminController {
	
	
	private static final Logger log = LoggerFactory.getLogger(OrderMarkingAdminController.class);

	
	@Autowired
	UserServiceDao userService;
	
	
	
	@PostMapping(path = OrderMarkingConstants.UPLOAD_SAP_DATA,  produces = OrderMarkingConstants.APPLICATION_JSON, headers = "Content-Type=multipart/form-data")
	public BaseResponse updateNewProfile(@RequestParam(name = "orderData", required = false) MultipartFile orderData) {

		BaseResponse response = new BaseResponse();

		try {
			if(orderData != null && orderData.getInputStream() != null) {
				XSSFWorkbook workbook = new XSSFWorkbook(orderData.getInputStream());
			    XSSFSheet worksheet = workbook.getSheetAt(0);

			    for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
			    	XSSFRow row = worksheet.getRow(i);
			    	log.info("Cell 0==>"+row.getCell(0));
			    	log.info("Cell 0==>"+row.getCell(0));
			    	log.info("Cell 1==>"+row.getCell(1));
			    	log.info("Cell 2==>"+row.getCell(2));
			    	log.info("Cell 3==>"+row.getCell(3));
			    	log.info("Cell 4==>"+row.getCell(4));
			    	log.info("Cell 5==>"+row.getCell(5));
			    	log.info("Cell 6==>"+row.getCell(6));
			    	log.info("Cell 7==>"+row.getCell(7));
			    	log.info("Cell 8==>"+row.getCell(8));
			    	log.info("Cell 9==>"+row.getCell(9));
			    	log.info("Cell 10==>"+row.getCell(10));
			    	
			    }
				response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
				response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				response.setSuccessMessage("Excel uploaded successfully");
			}else {
				response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
				response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				response.setSuccessMessage("");
				response.setErrorMessage("Invalid input...!Please try to upload only excel file with data");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Excpetion while uploading new profile" + e);
			response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
			response.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
			response.setSuccessMessage("");
			response.setErrorMessage("Exception Occurred...!");
			return response;
		}
		return response;

	}

	
	
	@PostMapping(path = OrderMarkingConstants.CREATE_NEW_USER,  produces = OrderMarkingConstants.APPLICATION_JSON )
	public BaseResponse updateNewProfile(@RequestBody NewUserRequest newUserRequest) {

		BaseResponse response = new BaseResponse();

		try {
			if(newUserRequest != null && newUserRequest.getEmailId() != null) {
				response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
				response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				response.setSuccessMessage("NEW user created Successfully...!");
			}else {
				response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
				response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				response.setSuccessMessage("");
				response.setErrorMessage("Invalid input...!Please try to upload only excel file with data");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Excpetion while uploading new profile" + e);
			response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
			response.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
			response.setSuccessMessage("");
			response.setErrorMessage("Exception Occurred...!");
			return response;
		}
		return response;

	}

}
