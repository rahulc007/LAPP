package com.svarks.lapp.mailer.service;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import com.svarks.lapp.order.common.OrderMarkingEmailConstants;


public class SendMail {

	public static void main(String[] args) {

		
		  // Recipient's email ID needs to be mentioned.
        String to = "sanjay.svarks@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "noreplysvarks@gmail.com";


        // Get system properties
        Properties properties = System.getProperties();

        properties.put("mail.smtp.starttls.enable", "true"); 
        properties.put("mail.smtp.host", "smtp.gmail.com");

        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        Authenticator authenticator = new Authenticator () {
              public PasswordAuthentication getPasswordAuthentication(){
                  return new PasswordAuthentication("noreplysvarks@gmail.com","Svarks@123");//userid and password for "from" email address 
              }
          };

          Session session = Session.getDefaultInstance( properties , authenticator);  
        try{
           // Create a default MimeMessage object.
           MimeMessage message = new MimeMessage(session);

           // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           // Set To: header field of the header.
           message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(to));

           // Set Subject: header field
           message.setSubject("This is the Subject Line!");

           // Now set the actual message
         //  message.setText("This is actual message");
           
        // Send the actual HTML message, as big as you like
           MailerRequest mailRequest = new MailerRequest();
           mailRequest.setButtonName("Login");
   		mailRequest.setTo("rajesh.svarks@gmaill.com");
   		mailRequest.setLabel1("Username:");
   		mailRequest.setP1("rajeshsavi123@gmaill.com");
   		mailRequest.setSubject(OrderMarkingEmailConstants.REGISTER_SUCCESS_SUBJECT);
   		mailRequest.setLabel2("Password:");
   		mailRequest.setP2("lapp@1123");
   		mailRequest.setP("Your registration has been completed successfully..! Please find the login credentials below");
   		mailRequest.setUrl("http://3.17.182.133:8080");
   		mailRequest.setName("Rajesh Gowda");
    	   message.setContent(generateHtmlEmailBody(mailRequest), "text/html");

           // Send message
           Transport.send(message);
           System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
           mex.printStackTrace();
        }
        
        
        
        
        
        
     /*   Properties properties = System.getProperties();

        properties.put(OrderMarkingEmailConstants.SMTP_ENABLE, "true"); 
        properties.put(OrderMarkingEmailConstants.SMTP_HOST, OrderMarkingEmailConstants.SMTP_HOST_VALUE);

        properties.put(OrderMarkingEmailConstants.SMTP_PORT,OrderMarkingEmailConstants.SMPT_PORT_VALUE);
        properties.put("mail.smtp.auth", "true");
        Authenticator authenticator = new Authenticator () {
              public PasswordAuthentication getPasswordAuthentication(){
                  return new PasswordAuthentication(OrderMarkingEmailConstants.EMAIL_USER_ID,OrderMarkingEmailConstants.EMAIL_AUTHENTICATION);//userid and password for "from" email address 
              }
          };*/
	}

	
	private static String generateHtmlEmailBody(MailerRequest mailRequest) {
		String mailBody = "";

		/*mailBody += "<!doctype html>";
		mailBody += "<html lang='en'>";*/
		
	//	mailBody += "<!doctype html>";
		mailBody += "<html>";

		mailBody += "<head>";
		mailBody += "<meta charset='utf-8'>";
		mailBody += "<title>LAPP</title>";

		mailBody += "<meta name='viewport' content='width=device-width, initial-scale=1'>";
		mailBody += "<link rel='icon' type='image/x-icon' href='favicon.ico'>";

		mailBody += "</head>";

		mailBody += "<body style='margin: 0;padding: 0;'>";
		mailBody += "<div style='background: #ececec; width: 100%; height: 100%; display: block;'>";
		mailBody += "<table border='0' cellspacing='0' cellpadding='0' style='width:600px;max-width:620px;margin:0 auto;background:#fff;'>";
		mailBody += "<tbody>";
		mailBody += "<tr>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "<td>";
		mailBody += "<img src=" + OrderMarkingEmailConstants.S3_CLOUDFRONT_LOGO +" alt='placeholder' style='width: 100px;height: auto;' />";
		mailBody += "</td>";
		mailBody += "<td>";
		mailBody += "<div style='width: 100%;'>";
		mailBody += "<h2 style='margin: 10px 0 5px 15px;padding: 0;width: 100%;text-align: left;'>Placeholder for heading</h2>";
		mailBody += "<h4 style='padding: 0;margin: 10px 0 5px 15px;'>Placeholder for sub heading </h4>";
		mailBody += "</div>";
		mailBody += "</td>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "</tr>";
		mailBody += "<tr>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "<td colspan='2'>";
		mailBody += "<div style='background-image: url(" + OrderMarkingEmailConstants.S3_CLOUD_BANNER
				+ ");background-size: 100% 100%;width: 100%;height: 135px;'>&nbsp;</div>";
		mailBody += "</td>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "</tr>";
		mailBody += "<tr>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "<td colspan='2'></td>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "</tr>";
		mailBody += "<tr>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "<td colspan='2'>";
		mailBody += "<!-- header start -->";
		mailBody += "<br/><p style='width: 100%;margin: 0;padding: 0;'>";
		mailBody += " Hi <b>"+mailRequest.getName()+"</b>";
		mailBody += "</p>";
		mailBody += "<!-- header end -->";
		mailBody += "<br/>";
		mailBody += "<!-- body start -->";
		if (mailRequest.getP() != null) {
			mailBody += "<p style='width: 100%;margin: 0;padding: 0;'>";
			mailBody += mailRequest.getP();
			mailBody += "</p>";
		}
		mailBody += "</br>";
		mailBody += "<p style='width: 100%;margin: 0;padding: 0;'>";
		mailBody += "<table border='0'>";

		if (mailRequest.getLabel1() != null && mailRequest.getP1() != null) {
			mailBody += "<tr>";
			mailBody += "<td>"+mailRequest.getLabel1()+"</td>";
			mailBody += "<td>"+mailRequest.getP1()+"</td>";
			mailBody += "</tr>";
		}
		if (mailRequest.getLabel2() != null && mailRequest.getP2() != null) {
			mailBody += "<tr>";
			mailBody += "<td>"+mailRequest.getLabel2()+"</td>";
			mailBody += "<td>"+mailRequest.getP2()+"</td>";
			mailBody += "</tr>";
		}
		if (mailRequest.getLabel3() != null && mailRequest.getP3() != null) {
			mailBody += "<tr>";
			mailBody += "<td>"+mailRequest.getLabel3()+"</td>";
			mailBody += "<td>"+mailRequest.getP3()+"</td>";
			mailBody += "</tr>";
		}
		mailBody += "</table>";
		mailBody += "</p>";
		mailBody += "<p style='width: 100%;margin: 0;padding: 0;'>";
		mailBody += "<a href=" + mailRequest.getUrl()
				+ " style='background: #e57930;color: #fff;text-align: center;text-decoration: none;padding: 5px 15px;border-radius: 2px;'>"
				+ mailRequest.getButtonName() + "</a>";
		mailBody += "</p>";
		mailBody += "<!-- body end -->";
		mailBody += "<br/>";
		mailBody += "<!-- footer start -->";
		mailBody += "<p style='width: 100%;margin: 0;padding: 0;font-size: 12px;'>";
		mailBody += "Regards,";
		mailBody += "</p>";
		mailBody += "<p style='width: 100%;margin: 0;padding: 0;font-size: 14px;'>";
		mailBody += "  LAPP";
		mailBody += "</p>";
		mailBody += "<!-- footer end -->";
		mailBody += "</td>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "</tr>";
		mailBody += "<tr>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "<td colspan='2'>&nbsp;</td>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "</tr>";
		mailBody += "<tr>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "<td colspan='2' style='background: #e57930;color: #fff;padding: 5px;font-size: 12px;text-align: center;'>";
		mailBody += "© 2019 <a href='https://www.lappgroup.com/' style='color: #fff; text-decoration:none;'>www.lappgroup.com</a>";
		mailBody += "</td>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "</tr>";
		mailBody += "<tr>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "<td colspan='2'>&nbsp;</td>";
		mailBody += "<td style='width: 15px;display:block; '></td>";
		mailBody += "</tr>";
		mailBody += "</tbody>";
		mailBody += "</table>";
		mailBody += "</div>";
		mailBody += "</body>";

		mailBody += "</html>";

		return mailBody;
	}


}
