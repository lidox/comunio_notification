package de.as.mail.notification;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEMailViaGMail {

	private static final String username = "lido.notification";
	private static final String password = "Segelohr7";

	public static void main(String[] args) {

		String host = "smtp.gmail.com";
		String from = "lido.notification@gmail.com";
		String to = "lido.artur@gmail.com";

		// Set properties
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 25);
		props.put("mail.debug", "true");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Get session with authenticator
		Session session = Session.getInstance(props, new GMailAuthenticator());

		try {

		    // Instantiate a message
		    Message msg = new MimeMessage(session);

		    // Set the FROM message
		    msg.setFrom(new InternetAddress(from));

		    // The recipients can be more than one so we use an array but you can
		    // use 'new InternetAddress(to)' for only one address.
		    InternetAddress[] address = {new InternetAddress(to)};
		    msg.setRecipients(Message.RecipientType.TO, address);

		    // Set the message subject and date we sent it.
		    msg.setSubject("Email from JavaMail test");
		    msg.setSentDate(new Date());

		    // Set message content
		    msg.setText("Java Code Geeks - Java Examples & Code Snippets");

		    // Send the message
		    Transport.send(msg);

		}
		catch (MessagingException mex) {
		    mex.printStackTrace();
		}

	}

	private static class GMailAuthenticator extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

}
