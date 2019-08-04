package com.svarks.lapp.order.rest.controller;

import java.util.Base64;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.svarks.lapp.mailer.service.EmailService;
import com.svarks.lapp.mailer.service.MailerRequest;
import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.common.OrderMarkingEmailConstants;
import com.svarks.lapp.order.dao.service.SAPFileDao;
import com.svarks.lapp.order.dao.service.UserProfileDao;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.entity.SAPFileInfo;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.entity.UserProfileEntity;
import com.svarks.lapp.order.request.NewUserRequest;
import com.svarks.lapp.order.response.BaseResponse;
import com.svarks.lapp.order.response.NewUserResponse;
import com.svarks.lapp.order.response.SAPFileInfoResponse;
import com.svarks.lapp.order.response.UserCreationResponse;

@RestController
public class OrderMarkingAdminController {

	private static final Logger log = LoggerFactory.getLogger(OrderMarkingAdminController.class);

	@Autowired
	UserServiceDao userService;

	@Autowired
	UserProfileDao profileService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	SAPFileDao sapFileService;
	

	@PostMapping(path = OrderMarkingConstants.UPLOAD_SAP_DATA, produces = OrderMarkingConstants.APPLICATION_JSON, headers = "Content-Type=multipart/form-data")
	public BaseResponse updateNewProfile(@RequestParam(name = "emailId", required = false) String emailId,
			@RequestParam(name = "orderData", required = false) MultipartFile orderData) {

		BaseResponse response = new BaseResponse();

		try {
			if (orderData != null && orderData.getInputStream() != null) {
				XSSFWorkbook workbook = new XSSFWorkbook(orderData.getInputStream());
				XSSFSheet worksheet = workbook.getSheetAt(0);
				int orderCount=0;
				for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
					XSSFRow row = worksheet.getRow(i);
					log.info("Cell 0==>" + row.getCell(0));
					log.info("Cell 0==>" + row.getCell(0));
					log.info("Cell 1==>" + row.getCell(1));
					log.info("Cell 2==>" + row.getCell(2));
					log.info("Cell 3==>" + row.getCell(3));
					log.info("Cell 4==>" + row.getCell(4));
					log.info("Cell 5==>" + row.getCell(5));
					log.info("Cell 6==>" + row.getCell(6));
					log.info("Cell 7==>" + row.getCell(7));
					log.info("Cell 8==>" + row.getCell(8));
					log.info("Cell 9==>" + row.getCell(9));
					log.info("Cell 10==>" + row.getCell(10));
					orderCount++;
				}
				SAPFileInfo sapFileInfo = new SAPFileInfo();
				sapFileInfo.setContentType(orderData.getContentType());
				sapFileInfo.setCreatedDate(new Date());
				sapFileInfo.setModifiedDate(new Date());
				sapFileInfo.setFileName(orderData.getOriginalFilename());
				sapFileInfo.setOrderCount(orderCount);
				sapFileInfo.setFileSize(orderData.getSize());
				sapFileInfo.setOrderItemCount(orderCount);
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

	
	@GetMapping(value = OrderMarkingConstants.GET_SAP_FILE_DETAILS, produces = OrderMarkingConstants.APPLICATION_JSON)
	public SAPFileInfoResponse getSAPOrderFileData() {
		log.info("calling getSAPOrderFileData to get all uploaded info ");
		SAPFileInfoResponse response = new SAPFileInfoResponse();
		response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
		response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
		response.setSapFileInofList(sapFileService.findAll());
		return response;
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
				String pwd=getAlphaNumericString(6, newUserRequest.getEmailId());
				user.setPassword(getBase64EncryptionPwd(pwd));
				userService.save(user);

				// USER PROFILE ADDITION

				profileService.save(convertNewUserDtoToUserProfile(newUserRequest));

				// send mail logic

				emailService.sendMail(sendCredentialsMail(newUserRequest,pwd));

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
		log.info("calling getSAPOrderFileData to get all uploaded info ");
		UserCreationResponse response = new UserCreationResponse();
		response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
		response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
		response.setUserProfileList(profileService.findAll());
		return response;
	}
	private MailerRequest sendCredentialsMail(NewUserRequest newUserRequest,String pwd) {
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
		user.setCountryCode("");
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
		userProfile.setEmailId(newUserRequest.getEmailId());
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
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

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
