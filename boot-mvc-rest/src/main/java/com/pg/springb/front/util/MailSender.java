package com.pg.springb.front.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {
	private static final Logger log = LoggerFactory.getLogger(MailSender.class);
	static String emailTo = "Sanjiv1.kumar@citi.com";// change accordingly
	//static String emailFrom = "dl.citi.global.mifid.ii.post.trade.recap@imcnam.ssmb.com";// change accordingly
	static String emailFrom = "dl.citi.global.mifid.ii.post.trade.recap@citi.com";// change accordingly
	//static String emailFrom = "frontier@citi.com";// change accordingly
	static String host = "mail.citicorp.com";// or IP address


	public static void converDate() {
		long time = 1509758219851000000L;
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		System.out.println(sdf.format(new Date(time)));

	}

	private static void sendEmail(String emailFrom, String emailTo, String subject, String filePath) {
		
		// Get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		// compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFrom));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.setSubject("Ping");
			message.setText("Hello, this is example of sending email  ");

			// Send message
			Transport.send(message);
			System.out.println("message sent successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public static void sendEmailWithAttachments(String emailTo, String emailCc, String emailBcc, String subject,
			String message, String[] attachFiles)
			throws MessagingException, AddressException, UnsupportedEncodingException {
		// sets SMTP server properties
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		/*
		 * Properties properties = new Properties(); properties.put("mail.smtp.host",
		 * host); properties.put("mail.smtp.port", port);
		 * properties.put("mail.smtp.auth", "true");
		 * properties.put("mail.smtp.starttls.enable", "true");
		 * properties.put("mail.user", userName); properties.put("mail.password",
		 * password);
		 * Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		 */
		// Get the session object

		// creates a new session with an authenticator
		
		// Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(emailFrom));
		//emailTo = emailTo.replace(";", ",");
		emailTo = emailTo.replace(",", ";");
		//String[] emailToArray = emailTo.split(",");
		
	    StringTokenizer st = new StringTokenizer(emailTo,";");
	    while(st.hasMoreTokens()) {
	    	String toToken=st.nextToken().trim();
	    	if(toToken.length()>5)
	    	msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toToken,false));
	    }
		
	    if(emailCc!=null && emailCc.length()>0){
	    emailCc=emailCc.replace(",", ";");
	    st = new StringTokenizer(emailCc,";");
	    while(st.hasMoreTokens()){
	    	String ccToken = st.nextToken().trim();	    	
	    	if(ccToken != null && ccToken.length()> 5)
	    		msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccToken,false));
	    	}
	    }
	    
	    if(emailBcc!=null && emailBcc.length()>0){
	    	emailBcc=emailBcc.replace(",", ";");
		    st = new StringTokenizer(emailBcc,";");
		    while(st.hasMoreTokens()){ 
		    	String bccToken=st.nextToken().trim();
		    	if(bccToken != null && bccToken.length()> 5)
		    	msg.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccToken,false));
		    }
	    }
	    
//	    log.info("emailFrom:"+emailFrom);
//	    log.info("emailTo:"+emailTo);
//	    log.info("emailCc:"+emailCc);
//	    log.info("emailBcc:"+emailBcc);
		//InternetAddress[] toAddresses = { new InternetAddress(emailTo) };
		//msg.setRecipients(Message.RecipientType.TO, toAddresses);
		
		//msg.setSubject(subject);
		msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
		msg.setSentDate(new Date());

		// creates message part
		//String formattedMessage = MimeUtility.encodeText(message, "utf-8", "B");
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		//messageBodyPart.setContent(message, "text/html");
		messageBodyPart.setContent(message, "text/html; charset=UTF-8");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);
	}

	/**
	 * Test sending e-mail with attachments
	 */
	public static void main(String[] args) {
		// SMTP info
		String host = "smtp.gmail.com";
		String port = "587";
		String mailFrom = "your-email-address";
		String password = "your-email-password";

		// message info
		String mailTo = "your-friend-email";
		String mailCC = "your-friend-email";
		String subject = "New email with attachments";
		String message = "I have some attachments for you.";

		// attachments
		String[] attachFiles = new String[3];
		attachFiles[0] = "e:/Test/Picture.png";
		attachFiles[1] = "e:/Test/Music.mp3";
		attachFiles[2] = "e:/Test/Video.mp4";

		try {
//			sendEmailWithAttachments( mailTo, subject, message, attachFiles);
			System.out.println("Email sent.");
		} catch (Exception ex) {
			System.out.println("Could not send email.");
			ex.printStackTrace();
		}
	}
}
