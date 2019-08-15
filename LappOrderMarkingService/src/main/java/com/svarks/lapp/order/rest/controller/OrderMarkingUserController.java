package com.svarks.lapp.order.rest.controller;


import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.svarks.lapp.order.dao.service.UserAuthDao;
import com.svarks.lapp.order.dao.service.UserProfileDao;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.dao.service.impl.UserServiceDaoImpl;
import com.svarks.lapp.order.entity.UserAuthInfo;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.request.ForgotPasswordRequest;
import com.svarks.lapp.order.request.LoginRequest;
import com.svarks.lapp.order.request.ProfileUpdateRequest;
import com.svarks.lapp.order.request.ResetPasswordRequest;
import com.svarks.lapp.order.request.UpdatePasswordRequest;
import com.svarks.lapp.order.response.BaseResponse;
import com.svarks.lapp.order.response.LoginResponse;
import com.svarks.lapp.order.response.UserProfileDetails;

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
	SendMailService sendMailService;
	
	/*@Autowired
	UserProfileDaoServiceImpl profileSer;*/
	
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
		sendMailService.sendMail(mailRequest);
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
			if (profileUpdateRequest.getEmailId() != null && profileUpdateRequest.getPid() !=0) {
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
				res.setErrorMessage(OrderMarkingConstants.INVALID_USER);
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
	
	
/*	private UserProfileEntity getUserProfileByDto(ProfileUpdateRequest profileUpdateRequest) {
		UserProfileEntity userProfileEntity = new UserProfileEntity();
		userProfileEntity.setCity(profileUpdateRequest.getCity());
		userProfileEntity.setFirstname(profileUpdateRequest.getFirstname());
		userProfileEntity.setLastname(profileUpdateRequest.getLastname());
		userProfileEntity.setModifiedDate(new Date());
		userProfileEntity.setPhonenumber(profileUpdateRequest.getPhonenumber());
		userProfileEntity.setState(profileUpdateRequest.getState());
		userProfileEntity.setPid(profileUpdateRequest.getPid());
		//email id
		
		
		return userProfileEntity;
		
	}
*/
}
