package de.as.mail.notification;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class AttachExample { 
   public static void main (String args[]) throws Exception 
   { 
      System.getProperties().put("proxySet","true");
      System.getProperties().put("socksProxyPort","8080");
      System.getProperties().put("socksProxyHost","proxy");
      Properties props = System.getProperties(); 
     
      String from = "lido.notification@gmail.com"; 
      String to = "lido.artur@gmail.com";
      //String filename = "AttachExample.java";
   
   
   // Get system properties 
      final String username = "lido.notification@gmail.com";
      final String password = "Segelohr7";
   
      props.put("mail.user", username);
      props.put("mail.host", "mail.gmail.com");
      //props.put("mail.debug", "true");
      //props.put("mail.store.protocol", "pop3");
      props.put("mail.transport.protocol", "smtp");
   
   
      Session session = Session.getDefaultInstance(props, 
                           new Authenticator(){
                              protected PasswordAuthentication getPasswordAuthentication() {
                                 return new PasswordAuthentication(username, password);
                              }});
   
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
      message.setSubject("Hello JavaMail Attachment"); 
      BodyPart messageBodyPart = new MimeBodyPart(); 
      messageBodyPart.setText("Here's the file"); 
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart); 
      messageBodyPart = new MimeBodyPart(); 
      multipart.addBodyPart(messageBodyPart); 
      message.setContent(multipart);
      Transport.send(message);
      System.out.println("finish");
   } 
}
