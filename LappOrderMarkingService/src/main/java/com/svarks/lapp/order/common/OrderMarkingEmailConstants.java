package com.svarks.lapp.order.common;

public interface OrderMarkingEmailConstants {
	
	
	//BASE PACKAGE
		 String REGISTER_SUCCESS_SUBJECT="Congratulations! Your Lapp Cable Marking Application Registration Successful..!";
		 String LOGIN_BUTTON="Login";
		 String USERNAME="Username:";
		 String PASSWORD="Password:";
		 //TEST
		 //String LOGIN_URL="http://52.206.130.36:8080/lapp";
		 //PROD
		 String LOGIN_URL="http://34.202.67.90/lapp";
		
		 String REGISTER_SUCCESS_MESSAGE="Your registration has been completed successfully..! Please find the login credentials below";
		 
		 
		 
		 //IMAGES URL
		 String S3_CLOUDFRONT_LOGO = "https://s3.amazonaws.com/optnaukri-devprofile/lapp_logo.png";
		 String S3_CLOUD_BANNER = "https://s3.amazonaws.com/optnaukri-devprofile/banner.png";

		 
		 String EMAIL_USER_ID="noreplysvarks@gmail.com";
		 String EMAIL_AUTHENTICATION="Svarks@123";
		 
		 String SMTP_ENABLE="mail.smtp.starttls.enable";
		 String SMTP_HOST="mail.smtp.host";
		 String SMTP_PORT="mail.smtp.port";
		 String SMTP_HOST_VALUE="smtp.gmail.com";
		 String SMPT_PORT_VALUE="587";
		 String FROM_NAME="LAPP CABLE GROUP";
		 
		 String FORGOT_PASSWORD_SUBJECT="Lapp OrderMarking Forgot Password Notification..!";
		 String MARKING_TEXT_SUBJECT="Marking text update Notification..!";
		 String MARKING_TEXT_COTNENT="Thank you for updating marking text for line item. Your order will be processed further for successful order completion";
		 String FORGOT_PASSWORD_CONTENT="Seems like you forgot your password lapp order marking application.If this is true,Click the below to reset your password";
		 String FORGOT_PASSWORD_BUTTON="Reset My Password";
		 String LOGIN_BUTTON_NAME="Login";
		 //Test
		// String FORGOT_PASSWORD_URL="http://52.206.130.36:8080/lapp/#/forgot-password?emailId=";
		 String FORGOT_PASSWORD_URL="http://34.202.67.90/lapp/#/forgot-password?emailId=";
}
