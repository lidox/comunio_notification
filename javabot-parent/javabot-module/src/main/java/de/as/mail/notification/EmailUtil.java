package de.as.mail.notification;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtil {

   private static Logger LOG = LoggerFactory.getLogger(EmailUtil.class);

   /**
    * Utility method to send simple HTML email
    * 
    * @param session
    *           the session
    * @param toEmail
    *           email address which you want so send a mail
    * @param subject
    *           the subject of the mail
    * @param body
    *           the message of the mail
    */
   public static void sendEmail(Session session, String toEmail,
         String subject, String body) {
      try {
         MimeMessage msg = new MimeMessage(session);
         msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
         msg.addHeader("format", "flowed");
         msg.addHeader("Content-Transfer-Encoding", "8bit");

         msg.setFrom(new InternetAddress("no_reply@ase.com", "Benachrichtigung"));

         msg.setReplyTo(InternetAddress.parse("no_reply@ase.com", false));

         msg.setSubject(subject, "UTF-8");

         msg.setText(body, "UTF-8");

         msg.setSentDate(new Date());

         msg.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(toEmail, false));

         LOG.info("E-mail message is ready for sending.");
         Transport.send(msg);
         LOG.info("E-Mail sent successfully to " + toEmail + ".");
         LOG.info("Message: " + msg);
      } catch (Exception e) {
         LOG.error("Failure! E-Mail not sent!");
         LOG.error(e.getMessage());
      }
   }
}
