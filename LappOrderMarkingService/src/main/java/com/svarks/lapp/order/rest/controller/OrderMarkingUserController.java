package com.svarks.lapp.order.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.dao.service.UserServiceDao;
import com.svarks.lapp.order.entity.UserEntity;
import com.svarks.lapp.order.request.LoginRequest;
import com.svarks.lapp.order.response.BaseResponse;
import com.svarks.lapp.order.response.LoginResponse;

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
		response.setSuccessMessage("Spring Boot...!Rest Controller working fine..!");
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
	
}
