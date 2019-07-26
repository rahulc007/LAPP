package com.svarks.lapp.order.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.request.ForgotPasswordRequest;
import com.svarks.lapp.order.request.LoginRequest;
import com.svarks.lapp.order.response.BaseResponse;
import com.svarks.lapp.order.response.LoginResponse;

@CrossOrigin(maxAge = OrderMarkingConstants.MAX_AGE)
@RestController
public class OrderMarkingUserController {
	
	
	@Autowired
	UserServiceDao userService;
	
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
			UserEntity userentity = userService.findUserByCredentials(user.getEmailId(), user.getPassword());
			System.out.println("userentity===>"+userentity);
               if(userentity != null && userentity.getEmailId() != null) {
            	   res.setStatus(200);
            	   res.setStatusMessage("success");
            	   res.setSuccessMessage("Login success");
            	   res.setUserType(userentity.getUtype());
            	   res.setFirstTimeLogin(userentity.isFirstTimeLogin());
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
				/*employeeFeedbackService.sendForgotPasswordHtmlMail(forgotPassword.getEmailId().split("@")[0],
						forgotPassword.getEmailId(), OrderMarkingConstants.FORGOT_PASSWORD_EMAIL_SUBJECT);*/
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
