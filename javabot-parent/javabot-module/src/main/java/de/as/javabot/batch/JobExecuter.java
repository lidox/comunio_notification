package de.as.javabot.batch;

import java.time.LocalTime;
import java.util.ArrayList;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.as.javabot.comunio.User;
import de.as.javabot.report.Report;

@Singleton
@Startup
public class JobExecuter {

   private static Logger LOG = LoggerFactory.getLogger(JobExecuter.class);
   private static Scheduler SCHEDULER;
   private static ArrayList<TriggerPeriod> triggerList = new ArrayList<TriggerPeriod>();
   private static TriggerPeriod jobPeriod;
   private static Class<?> jobClass;

   public JobExecuter() {
      try {
         SCHEDULER = new StdSchedulerFactory().getScheduler();
         SCHEDULER.start();
         LOG.info("     _       _ _            _       _          ");
         LOG.info("    | |     (_) |          (_)     | |         ");
         LOG.info("    | | ____ _| |_   _      _  ___ | | _   ___ ");
         LOG.info(" / || |/ _  | | | | | |    | |/ _ \\| || \\ /___)");
         LOG.info("( (_| ( ( | | | | |_| |    | | |_| | |_) )___ |");
         LOG.info(" \\____|\\_||_|_|_|\\__  |   _| |\\___/|____/(___/ ");
         LOG.info("                (____/   (__/  +++ daily schedular ready +++");
         LOG.info(" ");
      } catch (SchedulerException e) {
         LOG.error("Could not load daily schedular instance.");
         e.printStackTrace();
      }
   }

   /**
    * Add or remove job from trigger and send message about
    * activation/deactivation
    * 
    * @param user
    * @param report
    * @return
    */
   public static String addJob(User user, Report report, LocalTime time,
         String jobClassName) {
      jobClass = getClass(jobClassName);
      String jobName = report.getAddress();
      String groupName = user.getLogin();
     
      jobPeriod = new TriggerDaily(jobName, groupName, time);
      //TODO: kann nicht funktionieren!

      if (report.isActive()) {
         LOG.info("Der user "+user.toString()+" hat eine teagliche Benachrichtigung um "+time.toString() +" angefordert!"
               + " JobName="+jobName+" und GroupName="+groupName + " an: "+report.getAddress());
         return addJobToSchedular(user, jobClass, report, time);
      }
      LOG.info("Der user "+user.toString()+" hat die teagliche Benachrichtigung deaktiviert!"
            + " JobName="+jobName+" und GroupName="+groupName + " an: "+report.getAddress());
      return unscheduleJob(jobName, groupName, report);
   }

   /**
    * Remove job from schedular
    * 
    * @param jobName
    * @param groupName
    * @param report
    * @return
    */
   public static String unscheduleJob(String jobName, String groupName,
         Report report) {
      try {
         SCHEDULER.unscheduleJob(TriggerKey.triggerKey(jobName, groupName));
         report.sentMessage("Du bekommst absofort keine automatische Benachrichtigung mehr.");
         return "Die automatische Benachrichtigung an: " + jobName
               + " wurde deaktiviert!";
      } catch (SchedulerException e) {
         report.sentMessage("Beim Abschalten der automatischen Benachrichtigung ist ein Fehler aufgetretten: "
               + e.getMessage());
         return e.getMessage();
      }
   }

   @SuppressWarnings({ "rawtypes", "unchecked" })
   private static String addJobToSchedular(User user,
         Class clazz, Report report, LocalTime time) {
      try {
         JobDetail job = JobBuilder.newJob(clazz)
               .withIdentity(jobPeriod.getJobName(), jobPeriod.getGroupName()).build();

         SCHEDULER.getContext().put("user", user);
         SCHEDULER.getContext().put("report", report);

         SCHEDULER.scheduleJob(job, jobPeriod.getPeriod());
         
         //TODO: hier asynchron besser
         report.sentMessage("Du bekommst absofort automatisch Benachrichtigungen von mir.");
         
         return "Du bekommst taeglich eine Nachricht um "+time.toString() +" Uhr.";
      } catch (SchedulerException e3) {
         report.sentMessage("Du bekommst keine Benachrichtigungen, weil ein Fehler aufgetretten ist:"
               + e3.getMessage());
         return e3.getMessage();
      }
   }

   @SuppressWarnings("rawtypes")
   private static Class getClass(String jobClassName) {
      try {
         return Class.forName(jobClassName);
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
         return null;
      }
   }
   
   public static String getJobStatistics(){
      StringBuilder ret = new StringBuilder();
      try {
         ret.append("JobNames: "+SCHEDULER.getJobGroupNames().toString()+"\n");
         ret.append("TriggerGroups: "+SCHEDULER.getTriggerGroupNames().toString()+"\n");
         ret.append("SchedulerName: "+SCHEDULER.getSchedulerName()+"\n");
         ret.append("TriggerDescription: "+jobPeriod.getPeriod().getDescription()+"\n");
         LOG.info(ret.toString());
         return ret.toString();
      } catch (SchedulerException e) {
         e.printStackTrace();
      }
      return "Fehler bei der Ermittlung der Statistik!";
   }
}
