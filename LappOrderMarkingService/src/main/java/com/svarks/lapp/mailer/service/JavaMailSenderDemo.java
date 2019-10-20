package com.svarks.lapp.mailer.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class JavaMailSenderDemo {
	@Autowired
	 JavaMailSender sender;

	
	public  void sendFeedbackMail(String msg,String subject,String to){

		try {
			System.out.println("Sending mail now...!");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
System.out.println("Mimemessage object created..!");
		
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText("\nUser Email ID: "+to + "\n\n Message : " +msg);
			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println( "Error sending an Email");
		}
		

		System.out.println( "Email sent successfully...");
	}
	public void printTestMessage() {
		System.out.println("Successfully invoded print message...!");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String subject="Mail Sender example testing..!";
		String to="rajeshsavi123@gmail.com";
		String msg="welcome to java mail sender";
		JavaMailSenderDemo jmsd = new JavaMailSenderDemo();
		jmsd.printTestMessage();
		try {
		
		jmsd.sendFeedbackMail(msg, subject, to);
		System.out.println("Email sent successfully..!");
		}catch(Exception e) {
			System.out.println("Exception while sending email==>"+e);
			e.printStackTrace();
		}
	}

}
