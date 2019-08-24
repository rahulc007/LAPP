package com.svarks.lapp.order.rest.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.svarks.lapp.mailer.service.MailerRequest;
import com.svarks.lapp.mailer.service.SendMailService;
import com.svarks.lapp.order.common.DataValidation;
import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.common.OrderMarkingEmailConstants;
import com.svarks.lapp.order.dao.service.OrderInfoDao;
import com.svarks.lapp.order.dao.service.OrderStatusDao;
import com.svarks.lapp.order.dao.service.SAPFileDao;
import com.svarks.lapp.order.dao.service.UserProfileDao;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.entity.OrderStatusUpdate;
import com.svarks.lapp.order.entity.SAPFileInfo;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.entity.UserProfileEntity;
import com.svarks.lapp.order.request.NewUserRequest;
import com.svarks.lapp.order.response.BaseResponse;
import com.svarks.lapp.order.response.OrderDetailsResponse;
import com.svarks.lapp.order.response.SAPFileInfoResponse;
import com.svarks.lapp.order.response.UserCreationResponse;
import com.svarks.lapp.order.service.impl.ExcelFileService;

@RestController
public class OrderMarkingAdminController {

	private static final Logger log = LoggerFactory.getLogger(OrderMarkingAdminController.class);

	@Autowired
	UserServiceDao userService;

	@Autowired
	UserProfileDao profileService;

	@Autowired
	SendMailService sendMailService;

	@Autowired
	SAPFileDao sapFileService;

	@Autowired
	ExcelFileService excelService;
	
	@Autowired
	DataValidation dataValidation;
	
	@Autowired
	OrderInfoDao orderInfoService;
	
	@Autowired
	OrderStatusDao orderStatusService;
	

	@PostMapping(path = OrderMarkingConstants.UPLOAD_SAP_DATA, produces = OrderMarkingConstants.APPLICATION_JSON, headers = "Content-Type=multipart/form-data")
	public BaseResponse uploadSAPDataInfo(@RequestParam(name = "emailId", required = false) String emailId,
			@RequestParam(name = "orderData", required = false) MultipartFile orderData) {

		BaseResponse response = new BaseResponse();
		if (!userService.isValidUser(emailId)) {
			response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			response.setErrorMessage(OrderMarkingConstants.UNAUTHRORIZED_REQUEST);
			return response;

		}

		try {
			if (orderData != null && orderData.getInputStream() != null) {

				if (sapFileService.findByFileName(orderData.getOriginalFilename())) {
					response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
					response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
					response.setSuccessMessage("");
					response.setErrorMessage("File Name already exists");
					return response;
				}
				XSSFWorkbook workbook = new XSSFWorkbook(orderData.getInputStream());
				XSSFSheet worksheet = workbook.getSheetAt(0);
				XSSFRow row = worksheet.getRow(0);
				for(int i=0;i<OrderMarkingConstants.EXCEL_NO_ROWS;i++) {
					if(!dataValidation.isHeaderMatches(row.getCell(i).getStringCellValue())) {
						log.info("Invalid header matches:"+row.getCell(i).getStringCellValue());
						response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
						response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
						response.setErrorMessage(OrderMarkingConstants.INVALID_HEADER+row.getCell(i).getStringCellValue());
						return response;
					}
				}
				log.info("Header matches. Start uploading file..");
				String filePath=OrderMarkingConstants.EXCEL_UPLOAD+orderData.getOriginalFilename();
				File file = new File(OrderMarkingConstants.EXCEL_UPLOAD, orderData.getOriginalFilename());
				orderData.transferTo(file);
				
				SAPFileInfo sapFileInfo = new SAPFileInfo();
				sapFileInfo.setContentType(orderData.getContentType());
				sapFileInfo.setCreatedDate(new Date());
				sapFileInfo.setModifiedDate(new Date());
				sapFileInfo.setFileName(orderData.getOriginalFilename());
				sapFileInfo.setFileSize(orderData.getSize());
				sapFileInfo.setFileStatus(OrderMarkingConstants.UPLOADED);
				sapFileInfo.setFilePath(filePath);
				sapFileInfo.setUploadedBy(emailId);
				sapFileService.save(sapFileInfo);

				response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
				response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				response.setSuccessMessage("Excel uploaded successfully");
			} else {
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
	
	
	@PostMapping(path = OrderMarkingConstants.UPLOAD_ORDER_STATUS_DATA, produces = OrderMarkingConstants.APPLICATION_JSON, headers = "Content-Type=multipart/form-data")
	public BaseResponse uploadOrdetStatusInfo(@RequestParam(name = "emailId", required = false) String emailId,
			@RequestParam(name = "orderData", required = false) MultipartFile orderData) {

		BaseResponse response = new BaseResponse();
		if (!userService.isValidUser(emailId)) {
			response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			response.setErrorMessage(OrderMarkingConstants.UNAUTHRORIZED_REQUEST);
			return response;

		}

		try {
			if (orderData != null && orderData.getInputStream() != null) {

				if (orderStatusService.findByName(orderData.getOriginalFilename())) {
					response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
					response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
					response.setSuccessMessage("");
					response.setErrorMessage("File Name already exists");
					return response;
				}
				XSSFWorkbook workbook = new XSSFWorkbook(orderData.getInputStream());
				XSSFSheet worksheet = workbook.getSheetAt(0);
				XSSFRow row = worksheet.getRow(0);
				for(int i=0;i<OrderMarkingConstants.EXCEL_NO_ROWS;i++) {
					if(!dataValidation.isStatusHeaderMatches(row.getCell(i).getStringCellValue())) {
						log.info("Invalid header matches:"+row.getCell(i).getStringCellValue());
						response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
						response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
						response.setErrorMessage(OrderMarkingConstants.INVALID_HEADER+row.getCell(i).getStringCellValue());
						return response;
					}
				}
				log.info("Header matches. Start uploading file..");
				String filePath=OrderMarkingConstants.EXCEL_UPLOAD+orderData.getOriginalFilename();
				File file = new File(OrderMarkingConstants.EXCEL_UPLOAD, orderData.getOriginalFilename());
				orderData.transferTo(file);
				
				OrderStatusUpdate orderStatusInfo = new OrderStatusUpdate();
				orderStatusInfo.setContentType(orderData.getContentType());
				orderStatusInfo.setCreatedDate(new Date());
				orderStatusInfo.setModifiedDate(new Date());
				orderStatusInfo.setFileName(orderData.getOriginalFilename());
				orderStatusInfo.setFileSize(orderData.getSize());
				orderStatusInfo.setFileStatus(OrderMarkingConstants.UPLOADED);
				orderStatusInfo.setFilePath(filePath);
				orderStatusInfo.setUploadBy(emailId);
				orderStatusService.save(orderStatusInfo);

				response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
				response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				response.setSuccessMessage("Excel uploaded successfully");
			} else {
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
	

	

	@GetMapping(value = OrderMarkingConstants.GET_SAP_FILE_DETAILS, produces = OrderMarkingConstants.APPLICATION_JSON)
	public SAPFileInfoResponse getSAPOrderFileData() {
		log.info("calling getSAPOrderFileData to get all uploaded info ");
		SAPFileInfoResponse response = new SAPFileInfoResponse();
		response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
		response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
		response.setSapFileInofList(sapFileService.findAll());
		return response;
	}

	@GetMapping(value = OrderMarkingConstants.GET_SAP_FILE_DETAILS_ADMIN, produces = OrderMarkingConstants.APPLICATION_JSON)
	public SAPFileInfoResponse getSapDataByCreatedUser(@RequestParam(name = "emailId") String emailId) {
		log.info("calling getUserProfileDetails ");
		SAPFileInfoResponse response = new SAPFileInfoResponse();
		if (emailId != null && !emailId.isEmpty()) {
			response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			response.setSapFileInofList(sapFileService.getSAPDataByUser(emailId));
		}
		return response;
	}
	private long getCurrentTimeStamp() {
		Date date = new Date();
		return date.getTime();

	}

	@PostMapping(path = OrderMarkingConstants.CREATE_NEW_USER, produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse createNewUser(@RequestBody NewUserRequest newUserRequest) {

		BaseResponse response = new BaseResponse();

		try {
			if (newUserRequest != null && newUserRequest.getEmailId() != null) {

				// validation

				if (userService.findByEmailId(newUserRequest.getEmailId())) {
					log.info("Emaild ID already Exists..!");
					response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
					response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
					response.setErrorMessage(OrderMarkingConstants.EMAIL_ALREADY_EXISTS);
					return response;
				} else if (userService.findByCustId(newUserRequest.getConsumerId())) {
					log.info("CUSTOMER ID: already Exists..!");
					response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
					response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
					response.setErrorMessage(OrderMarkingConstants.CUSTOMER_ID_EXISTS);
					return response;
				}

				// USER LOGIN CREATION DONE
				UserEntity user = convertNewUserDtoToEntity(newUserRequest);
				String pwd = getAlphaNumericString(6, newUserRequest.getEmailId());
				user.setPassword(getBase64EncryptionPwd(pwd));
				userService.save(user);

				// USER PROFILE ADDITION

				profileService.save(convertNewUserDtoToUserProfile(newUserRequest));

				// send mail logic

				sendMailService.sendMail(sendCredentialsMail(newUserRequest, pwd));

				// Set success response
				response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
				response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				response.setSuccessMessage("New user created successfully...!");
				log.info("user created successfully..!");
			} else {
				response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
				response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				response.setSuccessMessage("");
				response.setErrorMessage("Invalid input request...!");
			}

		} catch (NullPointerException np) {
			np.printStackTrace();
			response.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			response.setSuccessMessage("");
			response.setErrorMessage("Invalid input request...!");
			return response;

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

	@GetMapping(value = OrderMarkingConstants.GET_ALL_USER_DETAILS, produces = OrderMarkingConstants.APPLICATION_JSON)
	public UserCreationResponse getUserProfileDataList() {
		log.info("calling getUserProfileDataList to get all uploaded info ");
		UserCreationResponse response = new UserCreationResponse();
		try {
			response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			response.setUserProfileList(profileService.getAllUserDetails());
			return response;
		} catch (Exception e) {
			response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			return response;
		}
	}

	@GetMapping(value = OrderMarkingConstants.GET_USER_DETAILS_ADMIN, produces = OrderMarkingConstants.APPLICATION_JSON)
	public UserCreationResponse getAllUserCreationCreatedBy(@RequestParam(name = "emailId") String emailId) {
		log.info("calling getAllUserCreationCreatedBy ");
		UserCreationResponse response = new UserCreationResponse();
		if (emailId != null && !emailId.isEmpty()) {
			response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			response.setUserProfileList(profileService.fetchAllUserByCreation(emailId));
		}
		return response;
	}
	
	
	@GetMapping(value = OrderMarkingConstants.GET_ORDER_DETAILS_USER, produces = OrderMarkingConstants.APPLICATION_JSON)
	public OrderDetailsResponse getOrderDataByUser(@RequestParam(name = "emailId") String emailId) {
		log.info("calling getAllUserCreationCreatedBy ");
		OrderDetailsResponse response = new OrderDetailsResponse();
		if (emailId != null && !emailId.isEmpty()) {
			response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			response.setOrderInfoList(orderInfoService.getOderByUser(emailId));
		}
		return response;
	}
	
	@GetMapping(value = OrderMarkingConstants.GET_ORDER_DETAILS_ADMIN, produces = OrderMarkingConstants.APPLICATION_JSON)
	public OrderDetailsResponse getOrderDataByAdmin(@RequestParam(name = "emailId") String emailId) {
		log.info("calling getAllUserCreationCreatedBy ");
		OrderDetailsResponse response = new OrderDetailsResponse();
		if (emailId != null && !emailId.isEmpty()) {
			response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			response.setOrderInfoList(orderInfoService.getOrderByAdmin(emailId));
		}
		return response;
	}

	@GetMapping(value = OrderMarkingConstants.DOWNLOAD_CUSTOMER_DATA, produces = OrderMarkingConstants.APPLICATION_JSON)
	public ResponseEntity<ByteArrayResource> downloadCustomerData(@RequestParam(name = "emailId") String emailId) {
		log.info("calling getAllUserCreationCreatedBy ");
		try {
			if (emailId != null && !emailId.isEmpty()) {

				List<UserProfileEntity> userProfileList = profileService.fetchAllUserByCreation(emailId);
				if (userProfileList != null && !userProfileList.isEmpty()) {
					excelService.createCustomerDataExcel(userProfileList);
					String filePath = OrderMarkingConstants.EXCEL_LOCATION
							+ OrderMarkingConstants.CUSTOMER_DATA_FILE_NAME;
					File file = new File(filePath);
					// InputStreamResource resource = new InputStreamResource(new
					// FileInputStream(filePath))

					Path path = Paths.get(file.getAbsolutePath());
					ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
					HttpHeaders header = new HttpHeaders();
					header.add(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + OrderMarkingConstants.CUSTOMER_DATA_FILE_NAME);
					header.add("Cache-Control", "no-cache, no-store, must-revalidate");
					header.add("Pragma", "no-cache");
					header.add("Expires", "0");
					return ResponseEntity.ok().headers(header).contentLength(file.length())
							.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
				}
			}
		} catch (Exception e) {

		}
		return null;
	}
	
	@GetMapping(value = OrderMarkingConstants.DOWNLOAD_SAP_DATA, produces = OrderMarkingConstants.APPLICATION_JSON)
	public ResponseEntity<ByteArrayResource> downloadSapData(@RequestParam(name = "emailId") String emailId) {
		log.info("calling downloadSapData");
		try {
			if (emailId != null && !emailId.isEmpty()) {
				
				List<SAPFileInfo> sapFileDataList=sapFileService.getSAPDataByUser(emailId);

				if (sapFileDataList != null && !sapFileDataList.isEmpty()) {
					excelService.createSAPDataExcel(sapFileDataList);
					String filePath = OrderMarkingConstants.EXCEL_LOCATION
							+ OrderMarkingConstants.CUSTOMER_SAP_FILE_NAME;
					File file = new File(filePath);
					// InputStreamResource resource = new InputStreamResource(new
					// FileInputStream(filePath))

					Path path = Paths.get(file.getAbsolutePath());
					ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
					HttpHeaders header = new HttpHeaders();
					header.add(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + OrderMarkingConstants.CUSTOMER_SAP_FILE_NAME);
					header.add("Cache-Control", "no-cache, no-store, must-revalidate");
					header.add("Pragma", "no-cache");
					header.add("Expires", "0");
					return ResponseEntity.ok().headers(header).contentLength(file.length())
							.contentType(MediaType.parseMediaType(OrderMarkingConstants.EXCEL_CONTENT_TYPE)).body(resource);
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

	private MailerRequest sendCredentialsMail(NewUserRequest newUserRequest, String pwd) {
		MailerRequest mailRequest = new MailerRequest();
		mailRequest.setButtonName(OrderMarkingEmailConstants.LOGIN_BUTTON);
		mailRequest.setName(newUserRequest.getFirstname());
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

	private UserEntity convertNewUserDtoToEntity(NewUserRequest newUserRequest) {
		UserEntity user = new UserEntity();
		user.setCountryCode(newUserRequest.getCountryCode());
		user.setCustomerId(newUserRequest.getConsumerId());
		user.setEmailConfirmed(true);
		user.setEmailId(newUserRequest.getEmailId());
		user.setFirstTimeLogin(true);
		user.setToken("");
		user.setUtype(newUserRequest.getUserType());
		user.setCreatedDate(new Date());
		user.setModifiedDate(new Date());
		return user;
	}

	private UserProfileEntity convertNewUserDtoToUserProfile(NewUserRequest newUserRequest) {
		UserProfileEntity userProfile = new UserProfileEntity();
		userProfile.setCity(newUserRequest.getCity());
		userProfile.setConsumerId(newUserRequest.getConsumerId());
		userProfile.setCountry(newUserRequest.getCountry());
		userProfile.setUemailId(newUserRequest.getEmailId());
		userProfile.setFirstname(newUserRequest.getFirstname());
		userProfile.setLastname(newUserRequest.getLastname());
		userProfile.setPhonenumber(newUserRequest.getPhonenumber());
		userProfile.setState(newUserRequest.getState());
		userProfile.setUserType(newUserRequest.getUserType());
		userProfile.setCreatedBy(newUserRequest.getCreatedBy());
		userProfile.setCreatedDate(new Date());
		userProfile.setModifiedDate(new Date());
		return userProfile;
	}

	private String getBase64EncryptionPwd(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	/*
	 * private String getBase64DecryptionPwd(String str) { byte[] actualByte =
	 * Base64.getDecoder().decode(str); return new String(actualByte);
	 * 
	 * }
	 */
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

}
