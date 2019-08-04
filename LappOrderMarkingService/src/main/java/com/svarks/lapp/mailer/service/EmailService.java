package com.svarks.lapp.mailer.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.svarks.lapp.order.common.OrderMarkingEmailConstants;

@Service
public class EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;

	

	public void sendMail(MailerRequest mailRequest) {
		
		try {

			setMailServerProperties();
			createEmailMessage(mailRequest);
			sendEmail();
			
		}catch(Exception e) {
			
		}
		
		
	}
	
	public void createEmailMessage(MailerRequest mailRequest) throws AddressException,
	MessagingException {

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);
		
		/*for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		}
		*/
		
		emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailRequest.getTo()));
		emailMessage.setSubject(mailRequest.getSubject());
		//emailMessage.setContent(emailBody, "text/html");//for a html email
		//emailMessage.setText(emailBody);// for a text email
			emailMessage.setContent(generateHtmlEmailBody(mailRequest), "text/html");

}
	
	
	public void sendEmail() throws AddressException, MessagingException {

		String emailHost = "smtp.gmail.com";
		String fromUser = "noreplysvarks@gmail.com";//just the id alone without @gmail.com
		String fromUserEmailPassword = "Svarks@123";

		Transport transport = mailSession.getTransport("smtp");

		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		log.info("Email sent successfully.");
	}
	
	public void setMailServerProperties() {

		String emailPort = "587";//gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");

	}
	
	/*public void sendMail(MailerRequest mailRequest) {
		try {
			MimeMessage mail = sender.createMimeMessage();
			String body = generateHtmlEmailBody(mailRequest);// templateEngine.process(templateName, context);
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setFrom("rajeshsavi123@gmail.com", "LAPP ORDER");

			helper.setTo(mailRequest.getTo());
			helper.setSubject(mailRequest.getSubject());
			helper.setText(body, true);
			sender.send(mail);
			log.info("****MAIL SENT SUCCESSFULLY****:");
		} catch (Exception e) {
			log.info("Error while sending mail==>" + e);
			log.info("Failed to send email=>" + mailRequest.getTo());

		}

	}

*/
	private String generateHtmlEmailBody(MailerRequest mailRequest) {
		String mailBody = "";

		mailBody += "<!doctype html>";
		mailBody += "<html lang='en'>";

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
		mailBody += " Hi <b>"+mailRequest.getName()+",</b>";
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
