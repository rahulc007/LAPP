package com.svarks.lapp.order.common;

public interface OrderMarkingConstants {
	
	
	//BASE PACKAGE
	 String BASE_PACKAGE_NAME="com.svarks.lapp.*";
	 String ENTITY_PACKAGE="com.svarks.lapp.order.entity";
	 String APPLICATION_JSON = "application/json";
	 String UNDER_SCORE="_";
	 
	 
	 //API ENDPOINT 
	 String VALIDATE_USER_API = "/validateUser";
	 String UPLOAD_SAP_DATA="/uploadSAPData";
	 String UPLOAD_ORDER_STATUS_DATA="/uploadOrderStatus";
	 String CREATE_NEW_USER="/createUser";
	 String FORGOT_PASSWORD_API = "/forgotPassword";
	 String GET_SAP_FILE_DETAILS="/getSapFileInfo";
	 String GET_SAP_FILE_DETAILS_ADMIN="/getSapFileInfoByUser";
	 String GET_ALL_USER_DETAILS="/getAllUserDetails";
	 String GET_USER_DETAILS_ADMIN="/getUserByCreated";
	 String GET_ORDER_DETAILS_USER="/getOrderDetailsByUser";
	 String GET_ORDER_DETAILS_ADMIN="/getOrderDetailsByAdmin";
	 String DOWNLOAD_CUSTOMER_DATA="/downloadCustData";
	 String DOWNLOAD_SAP_DATA="/downloadSAPData";
	 String GET_USER_PROFILE="/getUserProfile";
	 String GET_MARKING_TEXT_DETAILS="/getMarkingText";
	 String RESET_PASSWORD_API = "/resetPassword";
	 String UPDATE_PASSWORD_API = "/updatePwd";
	 String UPDATE_USER_PROFILE = "/updateProfile";
	 String ADD_MARKING_TEXT = "/addMarkingText";
	 
	 
	// EMAIL SUBJECT

		String VERIFY_EMAIL_SUBJECT = "Verify your email account";
		String USER_CREDENTIALS = "OPT NAUKRI LOGIN DEAILS";
		String FORGOT_PASSWORD_EMAIL_SUBJECT = "Forgot Password..!";
		String INAVLID_USER="Unauthorized request..!";
		
		
		// ERROR CODE
		int SUCCESS_STATUS = 200;
		int INTERNAL_SERVER_ERROR = 500;
		int NOT_FOUND = 404;

		
		// ERROR MESSAGES
		String SUCCESS_MSG = "success";
		String ERROR_MSG = "error";
		String INVALID_HEADER = "SAP EXCEL contains Invalid header::";
		String UNAUTHRORIZED_REQUEST="Un-Authorized request";
		
		String EMAIL_ALREADY_EXISTS = "EmailId already exists..!";
		String CUSTOMER_ID_EXISTS = "Customer ID already exists..!";
		String RESUME_FILE_MANDATORY = "Please upload your resume";
		String NETWORK_ISSUE = "Network error..! Please try after sometime";
		String INSERT_SUCCESS = "Saved Successfully..!";
		String LOGIN_ERROR = "EmailId or Password is incorrect";
		String INVALID_USER = "Unauthorized request..!";
		String INVALID_REQUEST = "Invalid request..!";
		String EMAILCONFIRM = "Please verify your email address";
		String PROFILE_UPDATE_SUCCESS="Updated user profile successfully..!";
		String MARKING_TEXT_SUCCESS="Marking text added successfully..!";
	 
	 //USER TYPE AND ROLRES
	 
	 int SUPER_ADMIN=1;
	 int ADMIN=2;
	 int CUSTOMER=3;
	 
	// CORS FILTERS
		int MAX_AGE = 3600;
		String CORS_ORIGINS = "*";
		String CORS_HEADERS = "*";
		
		
		//SAP FILE UPLAOD LOCATION
		// String EXCEL_UPLOAD="D:/sapFile/";
		  String EXCEL_UPLOAD="/home/ubuntu/sapFile/";
		  int  UPLOADED=1;
		  int  IN_PROGRESS=2;
		  int  SUCCESS=3;
		  int EXCEL_NO_ROWS=12;
		
		  
		  //ORDER STATUS
		  int NEW_ORDER=1;
		//EXCEL FILE DOWNLOAD FOR CREATION LOCATION
		
		//String EXCEL_LOCATION="D://";
		String EXCEL_LOCATION="/home/ubuntu/lappExcel/";
		String CUSTOMER_DATA_FILE_NAME="CustomerData.xls";
		String CUSTOMER_SAP_FILE_NAME="SAPData.xls";
		String CUSTOMER_SHEET_NAME="Customer Data";
		String SAP_SHEET_NAME="SAP Data";
		String EXCEL_CONTENT_TYPE="application/octet-stream";
}
