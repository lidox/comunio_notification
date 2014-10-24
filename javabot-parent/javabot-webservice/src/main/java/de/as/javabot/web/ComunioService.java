package de.as.javabot.web;

import java.time.LocalTime;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.as.javabot.batch.JobExecuter;
import de.as.javabot.batch.TriggerDaily;
import de.as.javabot.batch.TriggerPeriod;
import de.as.javabot.comunio.User;
import de.as.javabot.report.Report;
import de.as.javabot.report.ReportViaEmail;
import de.as.javabot.report.ReportViaWhatsApp;
import de.as.javabot.web.interfaces.IComunioWS;

@Stateless
@LocalBean
@WebService
public class ComunioService implements IComunioWS {

   private static Logger LOG = LoggerFactory.getLogger(ComunioService.class);

   @WebMethod
   public String getDailyMailAboutInjuredPlayerInTeamformation(String login,
         String password, String mailAddress, String hourAndMinute,
         boolean activate) {

      /*
       * TODO: Valedierung der eingaben und anschließend das Comunio Login prüfen
       * und login persistieren
       */
      
      LocalTime time = LocalTime.parse(hourAndMinute);

      LOG.warn("Achtung! Momentan funktioniert nur ein Job pro user, da der Jobname = E-Mail Adresse ist!");
      TriggerPeriod period = new TriggerDaily(mailAddress, time);

      JobExecuter.addTriggerAndJob(period,
            "de.as.javabot.batch.jobs.GetInjuredPlayersJob2");
      /*
       * TODO: Bestätigung per mail/whatsapp senden, dass Benachrichtigung aktiviert
       * bzw. deaktiviert wurde
       */

      Report report = new ReportViaEmail(activate, mailAddress);

      User user = new User(login, password);

      return JobExecuter.addJob(user, report);
   }

   @WebMethod
   public String sentWhatsappMessage(String number, String message) {
      Report report = new ReportViaWhatsApp(true, number);
      return report.sentMessage(message);
   }

}
