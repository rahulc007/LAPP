package com.svarks.lapp.order.rest.controller;


import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.svarks.lapp.mailer.service.EmailService;
import com.svarks.lapp.mailer.service.MailerRequest;
import com.svarks.lapp.order.common.DateUtilCommonSerive;
import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.common.OrderMarkingEmailConstants;
import com.svarks.lapp.order.dao.service.UserAuthDao;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.entity.UserAuthInfo;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.request.ForgotPasswordRequest;
import com.svarks.lapp.order.request.LoginRequest;
import com.svarks.lapp.order.request.ResetPasswordRequest;
import com.svarks.lapp.order.request.UpdatePasswordRequest;
import com.svarks.lapp.order.response.BaseResponse;
import com.svarks.lapp.order.response.LoginResponse;

@RestController
public class OrderMarkingUserController {
	
	
	@Autowired
	UserServiceDao userService;
	

	@Autowired
	EmailService emailService;
	
	@Autowired
	UserAuthDao userAuthService;
	
	@Autowired
	DateUtilCommonSerive dateUtilService;
	
	/**
	 * Test sample get request Rest controller
	 * @return
	 */
	
	@GetMapping(value = "/ping", produces = OrderMarkingConstants.APPLICATION_JSON)
	public BaseResponse testRestMessage() {
		BaseResponse response = new BaseResponse();
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
		mailRequest.setUrl("http://3.17.182.133:8080");
		mailRequest.setName("Rajesh Gowda");
		emailService.sendMail(mailRequest);
		return response;
	}

	
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
			System.out.println("username==>"+user.getEmailId());
			System.out.println("password==>"+user.getPassword());
			UserEntity userentity = null;
			if(user.getEmailId().contains("@"))
				userentity= userService.findUserByCredentials(user.getEmailId(), user.getPassword(),user.getCountryCode());
			else
				userentity= userService.findUserByCustomerId(user.getEmailId(), user.getPassword(),user.getCountryCode());
			
			System.out.println("userentity===>"+userentity);
               if(userentity != null && userentity.getEmailId() != null) {
            	   res.setStatus(OrderMarkingConstants.SUCCESS_STATUS);
            	   res.setStatusMessage(OrderMarkingConstants.SUCCESS_MSG);
            	   res.setSuccessMessage("Login success");
            	   res.setUserType(userentity.getUtype());
            	   String token=generateToken(user.getEmailId());
            	   res.setToken(token);
            	   res.setUsername(user.getEmailId());
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
		LoginResponse res = new LoginResponse();
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
		LoginResponse res = new LoginResponse();
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
				emailService.sendMail(mailRequest);
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
	
}
