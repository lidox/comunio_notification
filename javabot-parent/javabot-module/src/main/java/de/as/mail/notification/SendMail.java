package de.as.mail.notification;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingException;

import org.junit.Test;

public class SendMail {
    private String from = "lido.notification@gmail.com";
    private String to ="lido.artur@gmail.com";
    private String subject ="Test";
    private String text ="hi";

/**
 *   <property name="http.proxyHost" value="proxy"/>
    <property name="http.proxyPort" value="8080"/>
    <property name="https.proxyHost" value="proxy"/>
    <property name="https.proxyPort" value="8080"/>
    <property name="http.nonProxyHosts" value="localhost"/>
 * @throws NamingException
 * @throws MessagingException 
 */
    @SuppressWarnings("static-access")
	public void send() throws NamingException, MessagingException{
    	System.out.println("SendMail");
    	 Properties props1=new Properties();
    	 props1.put("mail.smtp.port", "465");
    	 props1.put("mail.smtp.auth", "true");
    	 props1.put("mail.smtp.starttls.enable", "true");
         props1.setProperty("http.proxyHost", "proxy");
         props1.setProperty("http.proxyPort", "8080");
         props1.setProperty("https.proxyHost", "proxy");
         props1.setProperty("https.proxyPort", "8080");
    	 Session session1 = Session.getInstance(props1);
    	 Transport transport = session1.getTransport("smtp");
    	 transport.connect("smtp.gmail.com", "lido.notification@gmail.com", "Segelohr7");
    	 
    	 Properties props2 = System.getProperties();
    	 props2.setProperty("mail.store.protocol", "imaps");
    	 props2.setProperty("mail.imaps.partialfetch", "false");
    	 props2.setProperty("mail.imap.host", "imap.gmail.com");
    	 props2.setProperty("mail.imap.port", "993");
    	 props2.setProperty("mail.imap.connectiontimeout", "5000");
    	 props2.setProperty("mail.imap.timeout", "5000");
    	 Session session2 = Session.getDefaultInstance(props2, null);
    	 Store store = session2.getStore("imaps");
    	 store.connect("imap.gmail.com", "lido.notification@gmail.com", "Segelohr7");
    	 Folder folder = store.getFolder("INBOX");
    	 folder.open(Folder.READ_WRITE);
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "465");
//        props.setProperty("mail.user", "lido.notification@gmail.com");
//        props.setProperty("mail.password", "Segelohr7");
        

        Session mailSession = Session.getDefaultInstance(props2);
        Message simpleMessage = new MimeMessage(mailSession);


        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;

        try {
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }

        try {
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(RecipientType.TO, toAddress);
            simpleMessage.setSubject(subject);
            simpleMessage.setText(text);
            transport.send(simpleMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    
    public void send2(){
    	final String username = "lido.notification@gmail.com";
    	final String password = "Segelohr7";

    	Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.host", "smtp.gmail.com");
    	props.put("mail.smtp.port", "465"); // 587 oder 465

    	
    	Session session = Session.getInstance(props,null);

    	try {

    	    Message message = new MimeMessage(session);
    	    message.setFrom(new InternetAddress("lido.notification@gmail.com"));
    	    message.setRecipients(Message.RecipientType.TO,
    	            InternetAddress.parse("lido.artur@gmail.com"));
    	    message.setSubject("Test Subject");
    	    message.setText("Test");

    	    Transport transport = session.getTransport("smtp");
    	    String mfrom = "lido.notification";// example laabidiraissi 
    	    transport.connect("smtp.gmail.com", mfrom, "Segelohr7");
    	    transport.sendMessage(message, message.getAllRecipients());

    	    System.out.println("Done");

    	} catch (MessagingException e) {
    	    throw new RuntimeException(e);
    	}
    }
    
    @Test
    public void test(){
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "lido.notification");
        props.put("mail.smtp.password", "Segelohr7");
        props.put("mail.smtp.port", "465"); //587
        props.put("mail.smtp.auth", true);
        props.setProperty("mail.smtp.timeout", "5000");
        
        props.setProperty("http.proxyHost", "proxy");
        props.setProperty("http.proxyPort", "8080");
        props.setProperty("https.proxyHost", "proxy");
        props.setProperty("https.proxyPort", "8080");

        Session session = Session.getInstance(props,null);
        MimeMessage message = new MimeMessage(session);

        System.out.println("Port: "+session.getProperty("mail.smtp.port"));

        // Create the email addresses involved
        try {
            InternetAddress from = new InternetAddress("lido.notification");
            message.setSubject("Yes we can");
            message.setFrom(from);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("lido.artur@gmail.com"));

            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart("alternative");

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("some text to send");

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();
            String htmlMessage = "Our html text";
            messageBodyPart.setContent(htmlMessage, "text/html");


            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);

            // Associate multi-part with message
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "lido.notification@gmail.com", "Segelohr7");
            System.out.println("Transport: "+transport.toString());
            transport.sendMessage(message, message.getAllRecipients());


        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
   @Test
   public void sendTemplateEmail() {

       Properties props = new Properties();  
       props.put("mail.smtp.host", "smtp.gmail.com");  
       props.put("mail.smtp.auth", "true");  
       props.put("mail.debug", "true");  
       props.put("mail.smtp.port", 25);  
       props.put("mail.smtp.socketFactory.port", 25);  
       props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.transport.protocol", "smtp");
       Session mailSession = null;

       mailSession = Session.getInstance(props,  
               new javax.mail.Authenticator() {  
           protected PasswordAuthentication getPasswordAuthentication() {  
               return new PasswordAuthentication("lido.notification", "Segelohr7");  
           }  
       });  


       try {

           Transport transport = mailSession.getTransport();

           MimeMessage message = new MimeMessage(mailSession);

           message.setSubject("Sample Subject");
           message.setFrom(new InternetAddress("lido.notification@gmail.com"));
           String []to = new String[]{"lido.artur@gmail.com"};
           message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[0]));
           String body = "Sample text";
           message.setContent(body,"text/html");
           transport.connect();

           transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
           transport.close();
       } catch (Exception exception) {

       }
   }

}
