package de.as.mail.notification;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TLSEmail {

   private static Logger LOG = LoggerFactory.getLogger(EmailUtil.class);

   public String doMail(String msg, String mail) {
      LOG.info("Prepare properties for sending E-mail");
      final String fromEmail = "lido.notification@gmail.com";
      final String password = "Segelohr7"; // correct password for gmail id

      Properties props = new Properties();
      props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
      props.put("mail.smtp.port", "587"); // TLS Port
      props.put("mail.smtp.auth", "true"); // enable authentication
      props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

      // create Authenticator object to pass in Session.getInstance argument
      Authenticator auth = new Authenticator() {
         // override the getPasswordAuthentication method
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(fromEmail, password);
         }
      };

      Session session = Session.getInstance(props, auth);
      String content = "Hallo" + msg + "\n" + "Viele Grüße," + "\n"
            + "dein E-Mail Service";
      LOG.info("Preparing properties finished");
      EmailUtil.sendEmail(session, mail, "Communio Mail Service", content);
      return "good. email was sent: " + msg;
   }

}
