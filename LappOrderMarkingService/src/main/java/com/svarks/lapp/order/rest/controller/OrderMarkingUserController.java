package com.svarks.lapp.order.rest.controller;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.svarks.lapp.mailer.service.MailerRequest;
import com.svarks.lapp.mailer.service.SendMailService;
import com.svarks.lapp.order.common.DateUtilCommonSerive;
import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.common.OrderMarkingEmailConstants;
import com.svarks.lapp.order.dao.service.MarkingTextDao;
import com.svarks.lapp.order.dao.service.OrderInfoDao;
import com.svarks.lapp.order.dao.service.OrderLineItemDao;
import com.svarks.lapp.order.dao.service.UserAuthDao;
import com.svarks.lapp.order.dao.service.UserProfileDao;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.entity.MarkingTextItem;
import com.svarks.lapp.order.entity.OrderInfo;
import com.svarks.lapp.order.entity.OrderLineItem;
import com.svarks.lapp.order.entity.SAPFileInfo;
import com.svarks.lapp.order.entity.UserAuthInfo;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.request.DeleteMarkingTextRequest;
import com.svarks.lapp.order.request.ForgotPasswordRequest;
import com.svarks.lapp.order.request.LoginRequest;
import com.svarks.lapp.order.request.MarkingTextRequest;
import com.svarks.lapp.order.request.ProfileUpdateRequest;
import com.svarks.lapp.order.request.ResetPasswordRequest;
import com.svarks.lapp.order.request.UpdateMarkingTextRequest;
import com.svarks.lapp.order.request.UpdatePasswordRequest;
import com.svarks.lapp.order.response.BaseResponse;
import com.svarks.lapp.order.response.LoginResponse;
import com.svarks.lapp.order.response.MarkingTextResponse;
import com.svarks.lapp.order.response.OrderDetailsResponse;
import com.svarks.lapp.order.response.UserProfileDetails;
import com.svarks.lapp.order.service.impl.ExcelFileService;

@RestController
public class OrderMarkingUserController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderMarkingUserController.class);
	
	@Autowired
	UserServiceDao userService;
	
	@Autowired
	UserProfileDao userProfileService;
	
	@Autowired
	UserAuthDao userAuthService;
	
	@Autowired
	DateUtilCommonSerive dateUtilService;
	
	@Autowired
	OrderInfoDao orderInfoService;
	
	@Autowired
	SendMailService sendMailService;
	
	@Autowired
	OrderLineItemDao lineItemService;
	
	@Autowired
	ExcelFileService excelService;
	
	@Autowired
	MarkingTextDao markingTextService;
	
	@Autowired
	OrderLineItemDao orderLineItemService;
	/**
	 * Test sample get request Rest controller
	 * @return
	 */
	
	@GetMapping(value = "/ping", produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse testRestMessage() {
		
		BaseResponse response = new BaseResponse();
		try {
		response.setErrorMessage("No Error");
		response.setSuccessMessage("LAPP ORDER MARKING...!Rest Controller working fine..!");
		response.setStatusMessage("success");
		response.setStatus(200);
		MailerRequest mailRequest = new MailerRequest();
		mailRequest.setButtonName("Login");
		mailRequest.setTo("rajeshsavi123@gmail.com");
		mailRequest.setLabel1("Username:");
		mailRequest.setP1("rajeshsavi123@gmail.com");
		mailRequest.setSubject(OrderMarkingEmailConstants.REGISTER_SUCCESS_SUBJECT);
		mailRequest.setLabel2("Password:");
		mailRequest.setP2("lapp@1123");
		mailRequest.setP("Your registration has been completed successfully..! Please find the login credentials below");
		mailRequest.setUrl("http://34.202.67.90");
		mailRequest.setName("Rajesh Gowda");
		sendMailService.sendMail(mailRequest);
		
		//sendMail();
		}catch(Exception e) {
			log.error("Exception while sending mail:"+e);
		}
		return response;
	}
	
	/*private void sendMail() {
		log.info("Sendign mail====>");
		try {
		 Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com"); // for gmail use smtp.gmail.com
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.debug", "true"); 
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.socketFactory.port", "587");
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.fallback", "true");

	        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("noreplysvarks@gmail.com", "Svarks@123");
	            }
	        });

	        mailSession.setDebug(true); // Enable the debug mode

	        Message msg = new MimeMessage( mailSession );

	        //--[ Set the FROM, TO, DATE and SUBJECT fields
	        msg.setFrom( new InternetAddress( "noreplysvarks@gmail.com" ) );
	        msg.setRecipients( Message.RecipientType.TO,InternetAddress.parse("rajeshsavi123@gmail.com") );
	        msg.setSentDate( new Date());
	        msg.setSubject( "Hello World!" );

	        //--[ Create the body of the mail
	        msg.setText( "Hello from my first e-mail sent with JavaMail" );

	        //--[ Ask the Transport class to send our mail message
	        Transport.send( msg );

	    }catch(Exception E){
	    	log.error( "Oops something has gone pearshaped!"+E);
	    }
	}
*/
	
	/**
	 * Method is used to check whether the given user is valid or not
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = OrderMarkingConstants.VALIDATE_USER_API, produces = OrderMarkingConstants.APPLICATION_JSON)
	public LoginResponse validateUser(@RequestBody LoginRequest user) {
		LoginResponse res = new LoginResponse();
		try {
			log.info("username==>"+user.getEmailId());
			UserEntity userentity = null;
			if(user.getEmailId().contains("@"))
				userentity= userService.findUserByCredentials(user.getEmailId(), user.getPassword(),user.getCountryCode());
			else
				userentity= userService.findUserByCustomerId(user.getEmailId(), user.getPassword(),user.getCountryCode());
			
               if(userentity != null && userentity.getEmailId() != null) {
            	   res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
            	   res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
            	   res.setSuccessMessage("Login success");
            	   res.setUserType(userentity.getUtype());
            	   String token=generateToken(userentity.getEmailId());
            	   res.setToken(token);
            	   res.setUsername(userentity.getEmailId());
            	   res.setFirstTimeLogin(userentity.isFirstTimeLogin());
            	   res.setCountryCode(userentity.getCountryCode());
            	   //Store Session token info
            	   UserAuthInfo userAuthInfo = new UserAuthInfo();
            	   userAuthInfo.setEmailId(user.getEmailId());
            	   userAuthInfo.setToken(token);
            	   userAuthInfo.setCreatedDate(new Date());
            	   userAuthService.save(userAuthInfo);
            	   
               }else {
            	   res.setErrorMessage("Invalid username and password");
            	   res.setStatus(200);
            	   res.setStatusMessage("error");
               }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private String generateToken(String username) {
		String tokenStr = username+":"+dateUtilService.getCurrentTimeInMilliseconds();
		return Base64.getEncoder().encodeToString(tokenStr.getBytes());
	}
	
	
	/**
	 * Method is used to create a new password when user forgot old password
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = OrderMarkingConstants.RESET_PASSWORD_API, produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse resetPassword(@RequestBody UpdatePasswordRequest forgotPassword) {
		BaseResponse res = new BaseResponse();
		try {
			UserEntity user = userService.findUserByCredentials(forgotPassword.getEmailId(), forgotPassword.getOldPassword(), forgotPassword.getCountryCode());
			if (user != null && user.getEmailId() != null) {
				userService.resetNewPassword(forgotPassword.getEmailId(), forgotPassword.getNewPassword());
				res.setSuccessMessage(OrderMarkingConstants.SUCCESS_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			} else {
				res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setErrorMessage(OrderMarkingConstants.INVALID_USER);
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
			res.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	
	/**
	 * Method is used to create a new password when user forgot old password
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = OrderMarkingConstants.UPDATE_PASSWORD_API, produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse updateNewpassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		BaseResponse res = new BaseResponse();
		try {
			boolean user = userService.findByEmailId(resetPasswordRequest.getEmailId());
			if (user) {
				userService.resetNewPassword(resetPasswordRequest.getEmailId(), resetPasswordRequest.getPassword());
				res.setSuccessMessage(OrderMarkingConstants.SUCCESS_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			} else {
				res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setErrorMessage(OrderMarkingConstants.INVALID_USER);
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
			res.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
		}
		return res;
	}

	
	
	/**
	 * Method is used to send forgot password link to registered emailid
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = OrderMarkingConstants.FORGOT_PASSWORD_API, produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse forgotPassword(@RequestBody ForgotPasswordRequest forgotPassword) {
		BaseResponse res = new BaseResponse();
		try {

			if (userService.findByEmailId(forgotPassword.getEmailId())) {
				
				//Send email
				MailerRequest mailRequest = new MailerRequest();
				mailRequest.setButtonName(OrderMarkingEmailConstants.FORGOT_PASSWORD_BUTTON);
				mailRequest.setP(OrderMarkingEmailConstants.FORGOT_PASSWORD_CONTENT);
				mailRequest.setName(forgotPassword.getEmailId());
				mailRequest.setSubject(OrderMarkingEmailConstants.FORGOT_PASSWORD_SUBJECT);
				mailRequest.setUrl(OrderMarkingEmailConstants.FORGOT_PASSWORD_URL+forgotPassword.getEmailId());
				mailRequest.setTo(forgotPassword.getEmailId());
				sendMailService.sendMail(mailRequest);
				res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			} else {
				res.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
				res.setStatus(200);
				res.setErrorMessage(OrderMarkingConstants.UNAUTHRORIZED_REQUEST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.setStatusMessage(OrderMarkingConstants.ERROR_MSG);
			res.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	
	
	/**
	 * Method is used to update the existing profile details by user or admin
	 * 
	 * @param user
	 * @return
	 */
	@PutMapping(value = OrderMarkingConstants.UPDATE_USER_PROFILE, produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse updateUserData(@RequestBody ProfileUpdateRequest profileUpdateRequest) {
		BaseResponse res = new BaseResponse();
		try {
			if (profileUpdateRequest.getEmailId() != null && profileUpdateRequest.getPid() > 0) {
				userProfileService.updateProfile(profileUpdateRequest.getFirstname(),profileUpdateRequest.getLastname(),
						profileUpdateRequest.getState(),profileUpdateRequest.getCity(),profileUpdateRequest.getPhonenumber(),
						profileUpdateRequest.getPid(),profileUpdateRequest.getEmailId());
				res.setSuccessMessage(OrderMarkingConstants.PROFILE_UPDATE_SUCCESS);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
				log.info("User profile details updated successfully..!");
			} else {
				log.info("invalid request for user update profile"+profileUpdateRequest);
				res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setErrorMessage(OrderMarkingConstants.INVALID_REQUEST);
			}

		} catch (Exception e) {
			log.error("Exception while updating user profile==>"+e);
			e.printStackTrace();
			res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
			res.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	@GetMapping(value = OrderMarkingConstants.GET_USER_PROFILE, produces = OrderMarkingConstants.APPLICATION_JSON)
	public UserProfileDetails getUserProfileDetails(@RequestParam(name = "emailId") String emailId) {
		log.info("calling getUserProfileDetails ");
		UserProfileDetails response = new UserProfileDetails();
		if(emailId != null && !emailId.isEmpty()) {
		response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
		response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
		response.setUserProfileEntity(userProfileService.getUserProfileData(emailId));
		}
		return response;
	}
	
	/**
	 * Method is used to update the existing profile details by user or admin
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = OrderMarkingConstants.ADD_MARKING_TEXT, produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse addMarkingTextDetails(@RequestBody MarkingTextRequest markingTextRequest) {
		BaseResponse res = new BaseResponse();
		try {
			log.info("marking text is final submit==>"+markingTextRequest.isSubmit());
			if(markingTextRequest.getLineItemId() !=0) {
				lineItemService.updateLineItem(markingTextRequest.isSubmit(),markingTextRequest.getLegsCount(), markingTextRequest.getLineItemId());
				if(!markingTextRequest.getMarkingTextList().isEmpty()) {
				markingTextService.saveAll(markingTextRequest.getMarkingTextList());
				}
				
				if(markingTextRequest.isSubmit()) {
					sendMarkingTextMail(markingTextRequest.getEmailId());
				}
				
				
			res.setSuccessMessage(OrderMarkingConstants.MARKING_TEXT_SUCCESS);
			res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			log.info("Marking text added successfully....!");
			
			
			
			}else {
				log.info("invalid request for marking text");
				res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setErrorMessage(OrderMarkingConstants.INVALID_REQUEST);
			}
		} catch (Exception e) {
			log.error("Exception while addMarkingTextDetails==>"+e);
			e.printStackTrace();
			res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
			res.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	/**
	 * Method is used to update the existing profile details by user or admin
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = OrderMarkingConstants.UPDATE_MARKING_TEXT, produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse updateMarkingItemDetails(@RequestBody UpdateMarkingTextRequest markingTextRequest) {
		BaseResponse res = new BaseResponse();
		try {
			log.info("marking text is final submit==>"+markingTextRequest.isSubmit());
			if(markingTextRequest.getMarkingId() !=0) {
				//lineItemService.updateLineItem(markingTextRequest.isSubmit(),markingTextRequest.getLegsCount(), markingTextRequest.getLineItemId());
				markingTextService.updateMarkingtext(markingTextRequest.getLeftText(), markingTextRequest.getRightText(), markingTextRequest.getMiddleText(), 
						markingTextRequest.getRmPartnoLeft(),markingTextRequest.getRmPartnoRight(),markingTextRequest.getRmPartnomiddle(),markingTextRequest.getMarkingId());
				
				if(markingTextRequest.isSubmit()) {
					sendMarkingTextMail(markingTextRequest.getEmailId());
				}
				
				
			res.setSuccessMessage(OrderMarkingConstants.MARKING_TEXT_SUCCESS);
			res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			log.info("Marking text updated successfully....!");
			
			
			
			}else {
				log.info("invalid request for updating marking text");
				res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setErrorMessage(OrderMarkingConstants.INVALID_REQUEST);
			}
		} catch (Exception e) {
			log.error("Exception while updating marking text==>"+e);
			res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
			res.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	/**
	 * Method is used to update the existing profile details by user or admin
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = OrderMarkingConstants.DELETE_MARKING_TEXT, produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse deleteMarkingItemDetails(@RequestBody DeleteMarkingTextRequest markingTextRequest) {
		BaseResponse res = new BaseResponse();
		try {
			log.info("marking text is final submit==>"+markingTextRequest.isSubmit());
			if(markingTextRequest.getMarkingId() !=0) {
				lineItemService.updateLineItem(markingTextRequest.isSubmit(),markingTextRequest.getLegsCount(), markingTextRequest.getLineItemId());
				markingTextService.deleteMarkingtext(markingTextRequest.getMarkingId());
				
				if(markingTextRequest.isSubmit()) {
					sendMarkingTextMail(markingTextRequest.getEmailId());
				}
				
				
			res.setSuccessMessage(OrderMarkingConstants.MARKING_TEXT_SUCCESS);
			res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			log.info("Marking text updated successfully....!");
			
			
			
			}else {
				log.info("invalid request for updating marking text");
				res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
				res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
				res.setErrorMessage(OrderMarkingConstants.INVALID_REQUEST);
			}
		} catch (Exception e) {
			log.error("Exception while updating marking text==>"+e);
			res.setErrorMessage(OrderMarkingConstants.ERROR_MSG);
			res.setStatus(OrderMarkingConstants.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	
	
	@GetMapping(value = OrderMarkingConstants.GET_MARKING_TEXT_DETAILS, produces = OrderMarkingConstants.APPLICATION_JSON)
	public MarkingTextResponse getMarkingTextDetails(@RequestParam(name = "lineItemid") Integer lineItemid) {
		log.info("calling getUserProfileDetails ");
		MarkingTextResponse response = new MarkingTextResponse();
		if(lineItemid != null && lineItemid > 0) {
		response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
		response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
		response.setMarkingTextList(markingTextService.getTextByLineItem(lineItemid));
		}
		return response;
	}
	
	@GetMapping(value = OrderMarkingConstants.GET_ORDER_DETAILS_BY_SALES_GENERIC, produces = OrderMarkingConstants.APPLICATION_JSON)
	public OrderDetailsResponse getOderDetailsBySalesOrderNo(@RequestParam(name = "salesOrderno") String salesOrderno,
			@RequestParam(name = "userEmailId") String userEmailId,
			@RequestParam(name = "countryCode") String countryCode) {
		log.info("calling getUserProfileDetails ");
		OrderDetailsResponse response = new OrderDetailsResponse();
		if (salesOrderno != null) {
			response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			if(userEmailId != null && userEmailId.length() !=0)
			response.setOrderInfoList(orderInfoService.getMyOrderBySales(salesOrderno,userEmailId));
			else if(countryCode != null && countryCode.length() !=0) {
				response.setOrderInfoList(orderInfoService.getMyOrderBySalesAndAdmin(salesOrderno,countryCode));
			}
		}
		return response;
	}
	
	@GetMapping(value = OrderMarkingConstants.GET_ORDER_DETAILS_BY_PRODUCTIONORDER, produces = OrderMarkingConstants.APPLICATION_JSON)
	public OrderDetailsResponse getOderDetailsByProductionNo(@RequestParam(name = "productionOrderno") String productionOrderno,
			@RequestParam(name = "userEmailId") String userEmailId,
			@RequestParam(name = "createdBy") String countryCode) {
		log.info("calling getUserProfileDetails ");
		OrderDetailsResponse response = new OrderDetailsResponse();
		if (productionOrderno != null) {
			response.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
			response.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
			if(userEmailId != null && userEmailId.length() !=0)
			response.setOrderInfoList(orderInfoService.getMyOrderByProdOrder(productionOrderno,userEmailId));
			else if(countryCode != null && countryCode.length() !=0) {
				response.setOrderInfoList(orderInfoService.getMyOrderByProdOrderAndAdmin(productionOrderno,countryCode));
			}
		}
		return response;
	}
	
	@GetMapping(value = OrderMarkingConstants.DOWNLOAD_MARKING_DATA, produces = OrderMarkingConstants.APPLICATION_JSON)
	public ResponseEntity<ByteArrayResource> downloadMarkingTextData(@RequestParam(name = "lineItemid") int lineItemid,
			@RequestParam(name = "salesOrderno") String salesOrderno,
			@RequestParam(name = "productionOrderno") String productionOrderno,@RequestParam(name = "articleno") String articleno) {
		log.info("calling downloadSapData");
		try {
			if (salesOrderno != null && !salesOrderno.isEmpty()) {
				
				List<MarkingTextItem> markingTextList = markingTextService.getTextByLineItem(lineItemid);
				List<OrderLineItem> orderLineItem = orderLineItemService.getLineItemByItemno(lineItemid);
				if (markingTextList != null && !markingTextList.isEmpty()) {
					excelService.createMarkingTextDataExcel(markingTextList,salesOrderno,productionOrderno,articleno,orderLineItem.get(0));
					String filePath = OrderMarkingConstants.EXCEL_LOCATION
							+ OrderMarkingConstants.CUSTOMER_MARKING_TEXT_NAME;
					File file = new File(filePath);
					// InputStreamResource resource = new InputStreamResource(new
					// FileInputStream(filePath))

					Path path = Paths.get(file.getAbsolutePath());
					ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
					HttpHeaders header = new HttpHeaders();
					header.add(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + OrderMarkingConstants.CUSTOMER_MARKING_TEXT_NAME);
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

	
	@GetMapping(value = OrderMarkingConstants.ORDER_DOWNLOAD_MARKING_DATA, produces = OrderMarkingConstants.APPLICATION_JSON)
	public ResponseEntity<ByteArrayResource> downloadMarkingBySales(@RequestParam(name = "salesOrderno") String salesOrderno) {
		log.info("calling download marking text from sales order no");
		try {
			if (salesOrderno != null && !salesOrderno.isEmpty()) {
				
					excelService.createOrderMarkingTextDataExcel(salesOrderno);
					String filePath = OrderMarkingConstants.EXCEL_LOCATION
							+ OrderMarkingConstants.SALES_CUSTOMER_MARKING_TEXT_NAME;
					File file = new File(filePath);
					// InputStreamResource resource = new InputStreamResource(new
					// FileInputStream(filePath))

					Path path = Paths.get(file.getAbsolutePath());
					ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
					HttpHeaders header = new HttpHeaders();
					header.add(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + OrderMarkingConstants.SALES_CUSTOMER_MARKING_TEXT_NAME);
					header.add("Cache-Control", "no-cache, no-store, must-revalidate");
					header.add("Pragma", "no-cache");
					header.add("Expires", "0");
					return ResponseEntity.ok().headers(header).contentLength(file.length())
							.contentType(MediaType.parseMediaType(OrderMarkingConstants.EXCEL_CONTENT_TYPE)).body(resource);
			}
		} catch (Exception e) {

		}
		return null;
	}


	@GetMapping(value = OrderMarkingConstants.SAMPLE_DOWNLOAD_MARKING_DATA, produces = OrderMarkingConstants.APPLICATION_JSON)
	public ResponseEntity<ByteArrayResource> downloadSampleMarkingText() {
		log.info("calling download marking text from sales order no");
		try {
				
					String filePath = OrderMarkingConstants.EXCEL_LOCATION
							+ OrderMarkingConstants.SAMPLE_MARKING_TEXT;
					File file = new File(filePath);
					// InputStreamResource resource = new InputStreamResource(new
					// FileInputStream(filePath))

					Path path = Paths.get(file.getAbsolutePath());
					ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
					HttpHeaders header = new HttpHeaders();
					header.add(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + OrderMarkingConstants.SAMPLE_MARKING_TEXT);
					header.add("Cache-Control", "no-cache, no-store, must-revalidate");
					header.add("Pragma", "no-cache");
					header.add("Expires", "0");
					return ResponseEntity.ok().headers(header).contentLength(file.length())
							.contentType(MediaType.parseMediaType(OrderMarkingConstants.EXCEL_CONTENT_TYPE)).body(resource);
			}
		 catch (Exception e) {

		}
		return null;
	}


	
	private void sendMarkingTextMail(String emailId) {
		//Send email
		MailerRequest mailRequest = new MailerRequest();
		mailRequest.setButtonName(OrderMarkingEmailConstants.LOGIN_BUTTON_NAME);
		mailRequest.setP(OrderMarkingEmailConstants.MARKING_TEXT_COTNENT);
		mailRequest.setName(emailId);
		mailRequest.setSubject(OrderMarkingEmailConstants.MARKING_TEXT_SUBJECT);
		mailRequest.setUrl(OrderMarkingEmailConstants.LOGIN_URL);
		mailRequest.setTo(emailId);
		sendMailService.sendMail(mailRequest);
	}
	
}
