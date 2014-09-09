package de.as.mail.notification;

import java.util.Properties;

import javax.mail.Session;
 
public class SimpleEmail {
     
    public static void main(String[] args) {
         
        System.out.println("SimpleEmail Start");
         
        String smtpHostServer = "smtp.gmail.com";
        String emailID = "lido.notification@gmail.com";
         
        Properties props = System.getProperties();
 
        props.put("mail.smtp.host", smtpHostServer);
 
        Session session = Session.getInstance(props, null);
         
        EmailUtil.sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");
    }
 
}
