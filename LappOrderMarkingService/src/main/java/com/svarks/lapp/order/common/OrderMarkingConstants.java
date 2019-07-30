package com.svarks.lapp.order.common;

public interface OrderMarkingConstants {
	
	
	//BASE PACKAGE
	 String BASE_PACKAGE_NAME="com.svarks.lapp.*";
	 String ENTITY_PACKAGE="com.svarks.lapp.order.entity";
	 String APPLICATION_JSON = "application/json";
	 
	 
	 
	 //API ENDPOINT 
	 String VALIDATE_USER_API = "/validateUser";
	 String UPLOAD_SAP_DATA="/uploadSAPData";
	 String CREATE_NEW_USER="/createUser";
	 String FORGOT_PASSWORD_API = "/forgotPassword";
	 String GET_SAP_FILE_DETAILS="/getSapFileInfo";
	 String GET_ALL_USER_DETAILS="/getAllUserDetails";
	 
	 
	// EMAIL SUBJECT

		String VERIFY_EMAIL_SUBJECT = "Verify your email account";
		String USER_CREDENTIALS = "OPT NAUKRI LOGIN DEAILS";
		String FORGOT_PASSWORD_EMAIL_SUBJECT = "Forgot Password..!";
		
		
		// ERROR CODE
		int SUCCESS_STATUS = 200;
		int INTERNAL_SERVER_ERROR = 500;
		int NOT_FOUND = 404;

		
		// ERROR MESSAGES
		String SUCCESS_MSG = "success";
		String ERROR_MSG = "error";
		String UNAUTHRORIZED_REQUEST="Un-Authorized request";
		
		String EMAIL_ALREADY_EXISTS = "EmailId already exists..!";
		String CUSTOMER_ID_EXISTS = "Customer ID already exists..!";
		String RESUME_FILE_MANDATORY = "Please upload your resume";
		String NETWORK_ISSUE = "Network error..! Please try after sometime";
		String INSERT_SUCCESS = "Saved Successfully..!";
		String LOGIN_ERROR = "EmailId or Password is incorrect";
		String INVALID_USER = "Unauthorized request..!";
		String EMAILCONFIRM = "Please verify your email address";
	 
	 //USER TYPE AND ROLRES
	 
	 int SUPER_ADMIN=1;
	 int ADMIN=2;
	 int CUSTOMER=3;
	 
	// CORS FILTERS
		int MAX_AGE = 3600;
		String CORS_ORIGINS = "*";
		String CORS_HEADERS = "*";
}
