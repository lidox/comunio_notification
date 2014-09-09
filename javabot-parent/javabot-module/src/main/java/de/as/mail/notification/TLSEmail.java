package de.as.mail.notification;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
 
public class TLSEmail {
 
    /**
       Outgoing Mail (SMTP) Server
       requires TLS or SSL: smtp.gmail.com (use authentication)
       Use Authentication: Yes
       Port for TLS/STARTTLS: 587
     */
    public static void main(String[] args) {
        final String fromEmail = "lido.notification@gmail.com"; //requires valid gmail id
        final String password = "Segelohr7"; // correct password for gmail id
        final String toEmail = "lido.artur@gmail.com"; // can be any email id
         
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        
                //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
         
        EmailUtil.sendEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
         
    }
    
    public String doMail(String msg){
        final String fromEmail = "lido.notification@gmail.com"; //requires valid gmail id
        final String password = "Segelohr7"; // correct password for gmail id
        final String toEmail = "lido.artur@gmail.com"; // can be any email id
         
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        
		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
        Session session = Session.getInstance(props, auth);
        String content = "Hi Artur,"+"\n"+"User logged in: "+msg+"\n" + "Greetings, JBoss :)";
        EmailUtil.sendEmail(session, toEmail,"Communio Notification", content);
        return "good. email was sent: " +msg;
    }
 
     
}
