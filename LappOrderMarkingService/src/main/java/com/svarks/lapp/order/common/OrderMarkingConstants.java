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
	 String GET_ORDER_STATUS_FILE_DETAILS_ADMIN="/getOrderStatusByUser";
	 String GET_ALL_USER_DETAILS="/getAllUserDetails";
	 String GET_USER_DETAILS_ADMIN="/getUserByCreated";
	 String GET_ORDER_DETAILS_USER="/getOrderDetailsByUser";
	 String GET_LINE_ITEM_USER="/getLineItemByUser";
	 String GET_PROCESSED_LINE_ITEM_USER="/getProcessedItem";
	 String GET_PROCESSED_ORDER_DETAILS_ADMIN="/getProcessedOrderByAdmin";
	 String GET_PROCESSED_ORDER_DETAILS_USER="/getProcessedOrderByUser";
	 String GET_ORDER_DETAILS_ADMIN="/getOrderDetailsByAdmin";
	 String GET_ORDER_DETAILS_DATE="/getOrderDetailsByDate";
	 String GET_ORDER_DETAILS_COUNT="/gerOrderDataCount";
	 String DOWNLOAD_CUSTOMER_DATA="/downloadCustData";
	 String DOWNLOAD_SAP_DATA="/downloadSAPData";
	 String DOWNLOAD_MARKING_DATA="/downloadMarkingText";
	 String ORDER_DOWNLOAD_MARKING_DATA="/orderDownloadText";
	 String SAMPLE_DOWNLOAD_MARKING_DATA="/getSampleTemplate";
	 String GET_USER_PROFILE="/getUserProfile";
	 String GET_MARKING_TEXT_DETAILS="/getMarkingText";
	 String GET_ORDER_DETAILS_BY_SALES_GENERIC="/getOrderBySales";
	 String GET_ORDER_DETAILS_BY_PRODUCTIONORDER="/getOrderByProductionOrder";
	 String RESET_PASSWORD_API = "/resetPassword";
	 String UPDATE_PASSWORD_API = "/updatePwd";
	 String UPDATE_USER_PROFILE = "/updateProfile";
	 String ADD_MARKING_TEXT = "/addMarkingText";
	 String UPDATE_MARKING_TEXT = "/updateMarkingText";
	 String DELETE_MARKING_TEXT = "/deleteMarkingText";
	 
	 
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
		//String EXCEL_UPLOAD="D:/sapFile/";
		  String EXCEL_UPLOAD="/home/ubuntu/sapFile/";
		  int  UPLOADED=1;
		  int  IN_PROGRESS=2;
		  int  SUCCESS=3;
		  int EXCEL_NO_ROWS=12;
		  String FTP_NAME="FTP";
		
		  
		  //ORDER STATUS
		  int NEW_ORDER=1;
		  String ADMIN_EMAIL_ID="";
		//EXCEL FILE DOWNLOAD FOR CREATION LOCATION
		
		//String EXCEL_LOCATION="D://";
		String EXCEL_LOCATION="/home/ubuntu/lappExcel/";
		String CUSTOMER_DATA_FILE_NAME="CustomerData.xls";
		String CUSTOMER_SAP_FILE_NAME="SAPData.xls";
		String CUSTOMER_MARKING_TEXT_NAME="MarkingText.xls";
		String SALES_CUSTOMER_MARKING_TEXT_NAME="MarkingText_Sales_Order.xls";
		String SAMPLE_MARKING_TEXT="samplemarkingtext.xlsx";
		String CUSTOMER_SHEET_NAME="Customer Data";
		String SAP_SHEET_NAME="SAP Data";
		String MARKING_TEXT_SHEET_NAME="Marking Text";
		String EXCEL_CONTENT_TYPE="application/octet-stream";
		
		//FTP
		
		String FTP_SOURCE_LOCATION="lapp.sap.order.ftp.source";
		String FTP_ARCHIVED_LOCATION="lapp.sap.order.ftp.archived";
}
