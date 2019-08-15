package com.svarks.lapp.mailer.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.svarks.lapp.order.common.OrderMarkingEmailConstants;

@Service
public class SendMailService {
	private static final Logger log = LoggerFactory.getLogger(SendMailService.class);

	@Autowired
	JavaMailSender sender;

	public void sendMail(MailerRequest mailRequest) {

		try {
			log.info("Sending mail now...!");
			MimeMessage mail = sender.createMimeMessage();
			String body = generateHtmlEmailBody(mailRequest);
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setFrom(OrderMarkingEmailConstants.EMAIL_USER_ID, OrderMarkingEmailConstants.FROM_NAME);
			helper.setTo(mailRequest.getTo());
			helper.setSubject(mailRequest.getSubject());
			helper.setText(body, true);
			sender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error sending an Email");
		}

		System.out.println("Email sent successfully...");
	}

	/*public void sendTestMail() {
		String subject = "Mail Sender example testing..!";
		String to = "shilpakala@smartcommercesolutions.in";
		String msg = "welcome to java mail sender";

		MailerRequest mailRequest = new MailerRequest();
		mailRequest.setButtonName("Login");
		mailRequest.setTo("shilpakala@smartcommercesolutions.in");
		mailRequest.setLabel1("Username:");
		mailRequest.setP1("rajeshsavi123@gmail.com");
		mailRequest.setSubject(OrderMarkingEmailConstants.REGISTER_SUCCESS_SUBJECT);
		mailRequest.setLabel2("Password:");
		mailRequest.setP2("lapp@1123");
		mailRequest
				.setP("Your registration has been completed successfully..! Please find the login credentials below");
		mailRequest.setUrl("http://3.17.182.133:8080");
		mailRequest.setName("Rajesh Gowda");

		sendFeedbackMail(mailRequest);
	}*/

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
		mailBody += "<img src=" + OrderMarkingEmailConstants.S3_CLOUDFRONT_LOGO
				+ " alt='placeholder' style='width: 100px;height: auto;' />";
		mailBody += "</td>";
		mailBody += "<td>";
		mailBody += "<div style='width: 100%;'>";
		mailBody += "<h2 style='margin: 10px 0 5px 15px;padding: 0;width: 100%;text-align: left;'>We are LAPP</h2>";
		mailBody += "<h4 style='padding: 0;margin: 10px 0 5px 15px;'>As leaders in cable technologies, it is our responsibility to provide our customers with advanced processes </h4>";
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
		mailBody += " Hi <b>" + mailRequest.getName() + ",</b>";
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
			mailBody += "<td>" + mailRequest.getLabel1() + "</td>";
			mailBody += "<td>" + mailRequest.getP1() + "</td>";
			mailBody += "</tr>";
		}
		if (mailRequest.getLabel2() != null && mailRequest.getP2() != null) {
			mailBody += "<tr>";
			mailBody += "<td>" + mailRequest.getLabel2() + "</td>";
			mailBody += "<td>" + mailRequest.getP2() + "</td>";
			mailBody += "</tr>";
		}
		if (mailRequest.getLabel3() != null && mailRequest.getP3() != null) {
			mailBody += "<tr>";
			mailBody += "<td>" + mailRequest.getLabel3() + "</td>";
			mailBody += "<td>" + mailRequest.getP3() + "</td>";
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
