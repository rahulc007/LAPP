package com.svarks.lapp.scheduler.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.svarks.lapp.mailer.service.MailerRequest;
import com.svarks.lapp.mailer.service.SendMailService;
import com.svarks.lapp.order.common.DataValidation;
import com.svarks.lapp.order.common.DateUtilCommonSerive;
import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.common.OrderMarkingEmailConstants;
import com.svarks.lapp.order.dao.service.OrderInfoDao;
import com.svarks.lapp.order.dao.service.OrderLineItemDao;
import com.svarks.lapp.order.dao.service.SAPFileDao;
import com.svarks.lapp.order.dao.service.UserProfileDao;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.entity.OrderInfo;
import com.svarks.lapp.order.entity.OrderLineItem;
import com.svarks.lapp.order.entity.SAPFileInfo;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.entity.UserProfileEntity;

@Service
public class FTPCronService {

	private static final Logger log = LoggerFactory.getLogger(SAPOrderCronJobService.class);

	@Autowired
	OrderInfoDao orderInfoService;

	@Autowired
	OrderLineItemDao orderItemServie;

	@Autowired
	UserServiceDao userService;

	@Autowired
	UserProfileDao profileService;

	@Autowired
	SendMailService sendMailService;

	@Autowired
	DateUtilCommonSerive commonService;

	@Autowired
	SAPFileDao sapFileService;

	@Autowired
	Environment env;

	@Autowired
	DataValidation dataValidation;

	public void executeFTPOperation() {

		try {
			log.info("******************FTP CRON JOB EXECUTING********************************");
			log.info("Current time is :: " + Calendar.getInstance().getTime());
			File dir = new File(env.getProperty(OrderMarkingConstants.FTP_SOURCE_LOCATION));
			File[] dir_contents = dir.listFiles();
			for (File file : dir_contents) {
				log.info("file name in ftp" + file.getName());
				String fileExtension = file.getName().split("\\.")[1];
				if (dataValidation.isExcelFile(fileExtension)) {
					executeFTPJob(file);
					String archivePath = env.getProperty(OrderMarkingConstants.FTP_ARCHIVED_LOCATION) + "\\"
							+ commonService.getCurrentTimeInMilliseconds() + "." + fileExtension;
					if (file.renameTo(new File(archivePath))) {
						log.info("File moved successfully...!");
						file.delete();
					}

				} else {
					file.delete();
				}

			}
			log.info("******************FTP CRON  JOB ENDS EXECUTING********************************");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception while executing cron job:" + e);
		}

	}

	private synchronized void executeFTPJob(File sapExcelFile) {
		DataFormatter formatter = new DataFormatter();
		try {
			InputStream inputStream = new FileInputStream(sapExcelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet worksheet = workbook.getSheetAt(0);
			int orderItemCount = 0;
			
			
			Map<String,OrderInfo> mapList = new HashMap<>();

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = worksheet.getRow(i);
				if (!isRowValid(row,formatter)) {
					log.info("Invalid row number::"+i+"at file name:"+sapExcelFile.getName());
					continue;
				}
				//RegisterS
				registerNewUser(row, formatter, OrderMarkingConstants.ADMIN_EMAIL_ID);
				
				OrderLineItem orderLineItem = new OrderLineItem();
				orderItemCount++;
				OrderInfo orderInfo = orderInfoService.getOrderBySalesOrder(getCellValue(row.getCell(0),formatter));
				if (orderInfo != null && orderInfo.getUserEmailId() != null) {
					
					   if(orderItemServie.findByLineItemNo(getCellValue(row.getCell(1),formatter))) {
						   //Duplicate record exists 
						   //Skipping duplicate record
						   continue;
					   }
					orderLineItem.setArticleNo(getCellValue(row.getCell(5),formatter));
					orderLineItem.setCreatedDate(new Date());
					orderLineItem.setCustomerNo(getCellValue(row.getCell(9),formatter));
					orderLineItem.setCustomerPartNo(getCellValue(row.getCell(7),formatter));
					orderLineItem.setDescription(getCellValue(row.getCell(6),formatter));
					// orderLineItem.setLength(length);
					orderLineItem.setLineItemno(getCellValue(row.getCell(1),formatter));
					orderLineItem.setModifiedDate(new Date());
					orderLineItem.setSalesOrderno(getCellValue(row.getCell(0),formatter));
					orderLineItem.setProductionOrderno(getCellValue(row.getCell(3),formatter));
					orderLineItem.setProductionOrderStatus(getCellValue(row.getCell(4),formatter));
					orderLineItem.setSubmit(false);
					orderLineItem.setUpdatedBy("");
					List<OrderLineItem> orderLineItemList = orderInfo.getOrderLineItem();
					orderLineItemList.add(orderLineItem);
					orderInfo.setOrderLineItem(orderLineItemList);
					mapList.put(orderInfo.getSalesOrderno(), orderInfo);
					
					//orderInfoService.save(orderInfo);
					//orderInfo.setOrderLineItem(orderLineItemList);
					//if(orderLineItemList.size() >=500) {
						
						//orderItemServie.saveAll(orderLineItemList);
						//orderInfoService.save(orderInfo);
						//orderLineItemList.clear();
					//}
				} else {
					 orderInfo = new OrderInfo();
					List<OrderLineItem> lineItemList = new LinkedList<>();
					orderInfo.setCountryCode(getCellValue(row.getCell(11),formatter));
					orderInfo.setCreatedBy(OrderMarkingConstants.FTP_NAME);
					orderInfo.setCreatedDate(new Date());
					orderInfo.setModifiedDate(new Date());
					orderInfo.setOrderDate(new Date());
					orderInfo.setOrderStatus(OrderMarkingConstants.NEW_ORDER);
					orderInfo.setSalesOrderno(getCellValue(row.getCell(0),formatter));
					orderInfo.setUserEmailId(getCellValue(row.getCell(8),formatter));

					orderLineItem.setArticleNo(getCellValue(row.getCell(5),formatter));
					orderLineItem.setCreatedDate(new Date());
					orderLineItem.setCustomerNo(getCellValue(row.getCell(9),formatter));
					orderLineItem.setCustomerPartNo(getCellValue(row.getCell(7),formatter));
					orderLineItem.setDescription(getCellValue(row.getCell(6),formatter));
					orderLineItem.setLineItemno(getCellValue(row.getCell(1),formatter));
					orderLineItem.setModifiedDate(new Date());
					orderLineItem.setSalesOrderno(getCellValue(row.getCell(0),formatter));
					orderLineItem.setProductionOrderno(getCellValue(row.getCell(3),formatter));
					orderLineItem.setProductionOrderStatus(getCellValue(row.getCell(4),formatter));
					orderLineItem.setUpdatedBy("");
					lineItemList.add(orderLineItem);
					orderInfo.setOrderLineItem(lineItemList);
					orderInfoService.save(orderInfo);
					log.info("New Order Created Sales Order no:==>" + orderInfo.getSalesOrderno());
				}
				/*
				 * log.info("Cell 0==>" + row.getCell(0)); log.info("Cell 1==>" +
				 * row.getCell(1)); log.info("Cell 2==>" + row.getCell(2)); log.info("Cell 3==>"
				 * + row.getCell(3)); log.info("Cell 4==>" + row.getCell(4));
				 * log.info("Cell 5==>" + row.getCell(5)); log.info("Cell 6==>" +
				 * row.getCell(6)); log.info("Cell 7==>" + row.getCell(7)); log.info("Cell 8==>"
				 * + row.getCell(8)); log.info("Cell 9==>" + row.getCell(9));
				 * log.info("Cell 10==>" + row.getCell(10));
				 */
				
			}
			//TODO Need to test performance for 10000
			if(mapList.size() !=0) {
			mapList.entrySet().stream().map(map->orderInfoService.save(map.getValue()));
			}
			
			//mapList.entrySet().parallelStream().map(map->orderInfoService.save(map.getValue()));
			
			
						saveSAPFileInfo(sapExcelFile, orderItemCount);

			log.info("Excel file deleted successfully..!");
		} catch (Exception e) {
			log.error("Exception while reading excel file==>" + e);
		}
	}

	private void saveSAPFileInfo(File file, int orderItemCount) {
		SAPFileInfo sapFileInfo = new SAPFileInfo();
		sapFileInfo.setContentType(OrderMarkingConstants.EXCEL_CONTENT_TYPE);
		sapFileInfo.setCreatedDate(new Date());
		sapFileInfo.setModifiedDate(new Date());
		sapFileInfo.setFileName(file.getName());
		sapFileInfo.setOrderCount(orderItemCount);
		sapFileInfo.setOrderItemCount(orderItemCount);
		sapFileInfo.setFileSize(23);
		sapFileInfo.setFileStatus(OrderMarkingConstants.SUCCESS);
		sapFileInfo.setFilePath(file.getAbsolutePath());
		sapFileInfo.setUploadedBy(OrderMarkingConstants.FTP_NAME);
		sapFileService.save(sapFileInfo);
	}

	private void registerNewUser(XSSFRow row, DataFormatter formatter, String uploadedby) {
		String userEmailId = getCellValue(row.getCell(8), formatter);
		String customerno = getCellValue(row.getCell(9), formatter);
		String countryCode = getCellValue(row.getCell(11), formatter);
		if (!userService.findByEmailId(userEmailId) && !userService.findByCustId(customerno)) {
			// USER LOGIN CREATION DONE
			log.info("New User Registration through SAP FILE UPLAOD::", userEmailId);
			UserEntity user = convertNewUserDtoToEntity(countryCode, formatter, userEmailId, customerno);
			String pwd = getAlphaNumericString(6, userEmailId);
			user.setPassword(getBase64EncryptionPwd(pwd));
			userService.save(user);

			// USER PROFILE ADDITION

			profileService
					.save(convertNewUserDtoToUserProfile(user, uploadedby, getCellValue(row.getCell(10), formatter)));

			// send mail logic

			sendMailService.sendMail(sendCredentialsMail(user, pwd));

		}
	}

	private boolean isRowValid(XSSFRow row, DataFormatter formatter) {
		if (getCellValue(row.getCell(1), formatter).isEmpty() || getCellValue(row.getCell(0), formatter).isEmpty()
				|| getCellValue(row.getCell(8), formatter).isEmpty()) {
			return false;
		}
		return true;
	}

	private String getCellValue(XSSFCell cellValue, DataFormatter formatter) {

		try {
			String cellValueInStr = formatter.formatCellValue(cellValue);
			return (cellValueInStr != null && !cellValueInStr.isEmpty()) ? cellValueInStr.trim() : "";

		} catch (NullPointerException e) {
			log.error("Cell value is getting NULL pointer==>" + e);
			// System.gc();
			return "";
		} catch (Exception e) {
			log.error("Exception while reading cell value==>" + e);
			e.printStackTrace();
			return "";
		}

	}

	private MailerRequest sendCredentialsMail(UserEntity newUserRequest, String pwd) {
		MailerRequest mailRequest = new MailerRequest();
		mailRequest.setButtonName(OrderMarkingEmailConstants.LOGIN_BUTTON);
		mailRequest.setName(newUserRequest.getEmailId());
		mailRequest.setTo(newUserRequest.getEmailId());
		mailRequest.setUrl(OrderMarkingEmailConstants.LOGIN_URL);
		mailRequest.setSubject(OrderMarkingEmailConstants.REGISTER_SUCCESS_SUBJECT);
		mailRequest.setP(OrderMarkingEmailConstants.REGISTER_SUCCESS_MESSAGE);
		mailRequest.setLabel1(OrderMarkingEmailConstants.USERNAME);
		mailRequest.setP1(newUserRequest.getEmailId());
		mailRequest.setLabel2(OrderMarkingEmailConstants.PASSWORD);
		mailRequest.setP2(pwd);
		return mailRequest;
	}

	private UserEntity convertNewUserDtoToEntity(String countryCode, DataFormatter formatter, String emailId,
			String customerno) {
		UserEntity user = new UserEntity();
		user.setCountryCode(countryCode);
		user.setCustomerId(customerno);
		user.setEmailConfirmed(true);
		user.setEmailId(emailId);
		user.setFirstTimeLogin(true);
		user.setToken("");
		user.setUtype(OrderMarkingConstants.CUSTOMER);
		user.setCreatedDate(new Date());
		user.setModifiedDate(new Date());
		return user;
	}

	private UserProfileEntity convertNewUserDtoToUserProfile(UserEntity newUserRequest, String createdBy,
			String phoneNumber) {
		UserProfileEntity userProfile = new UserProfileEntity();
		userProfile.setConsumerId(newUserRequest.getCustomerId());
		userProfile.setCountry(newUserRequest.getCountryCode());
		userProfile.setCountryCode(newUserRequest.getCountryCode());
		userProfile.setUemailId(newUserRequest.getEmailId());
		// userProfile.setFirstname(newUserRequest.getFirstname());
		// userProfile.setLastname(newUserRequest.getLastname());
		userProfile.setPhonenumber(phoneNumber);
		userProfile.setUserType(OrderMarkingConstants.CUSTOMER);
		userProfile.setCreatedBy(createdBy);
		userProfile.setCreatedDate(new Date());
		userProfile.setModifiedDate(new Date());
		return userProfile;
	}

	private String getBase64EncryptionPwd(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	static String getAlphaNumericString(int n, String name) {
		String autoGeneratePwd = (name.length() > 3) ? name.substring(0, 4) : name.substring(0, name.length() - 1);
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return autoGeneratePwd + sb.toString();
	}

	private void updateSAPFileStatus(int status, int fileId, int orderCount, int orderItemCount) {
		sapFileService.updateFileStatus(status, fileId, orderCount, orderItemCount);
	}
}
