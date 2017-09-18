package com.bridgeit.TodoApp.validator;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bridgeit.TodoApp.model.User;

public class UserEmailValidator {

	public static void mailVerification(String emailId,String token) {
		final String username = "demorajwar@gmail.com";
		final String password = "rajwar@123";
		
		
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
		});
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("demorajwar@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailId));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
			message.setContent("<a href='http://localhost:8080/TodoApp/verifyMail?userToken="+token+"'>click here to verify email</a>", "text/html");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
