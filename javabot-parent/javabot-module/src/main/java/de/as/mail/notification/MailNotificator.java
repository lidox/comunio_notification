package de.as.mail.notification;

import java.io.IOException;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MailNotificator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(lookup = "java:jboss/mail/Default")
    private Session mailSession;

    public void service(String mailMessage) throws ServletException, IOException {

        try {
        	System.out.println("Mail Notificator");
            try {
                MimeMessage m = new MimeMessage(mailSession);
                Address from = new InternetAddress("lido.notification@gmail.com");
                Address[] to = new InternetAddress[] { new InternetAddress("lido.artur@gmail.com") };
                m.setFrom(from);
                m.setRecipients(Message.RecipientType.TO, to);
                m.setSubject("Google JavaMail Test");
                m.setContent("Test from inside JBoss AS7 Server. Input: "+mailMessage, "text/plain");
                System.out.println("Mail ready");
                Transport.send(m);
                System.out.println("Mail Sent Successfully.");
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        	System.out.println("Send Message Failed");
        	e.printStackTrace();
        }
    }
    
}
