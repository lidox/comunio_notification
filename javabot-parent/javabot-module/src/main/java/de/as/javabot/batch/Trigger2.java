package de.as.javabot.batch;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import de.as.javabot.batch.jobs.GetInjuredPlayersJob;

@Singleton
@Startup
public class Trigger2 {

	private static Scheduler scheduler;

	public Trigger2() {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			System.out.println("############### STARTED #################");
		} catch (SchedulerException e) {
			System.out.println("Schedular not started!");
			e.printStackTrace();
		}

	}

	public static void unscheduleJob(String triggerName, String groupName) {
		try {
			scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName,
					groupName));
			System.out.println("############### STOPPED #################");
		} catch (SchedulerException e) {
			System.out.println("Job not found");
			e.printStackTrace();
		}
	}

	public static void startTrigger(String triggerName, String groupName,
			String login, String psw) throws SchedulerException {
		// define the job and tie it to our HelloJob class
		JobDetail job = JobBuilder.newJob(GetInjuredPlayersJob.class)
				.withIdentity("job1", "group1").build();

		job.getJobDataMap().put("login", login);
		job.getJobDataMap().put("password", psw);

		// Trigger the job to run now, and then repeat every 40 seconds
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(triggerName, groupName)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(10).repeatForever())
				.build();

		scheduler.scheduleJob(job, trigger);
	}

	public static void startDailyTrigger(String triggerName, String groupName, String login, String psw, int h, int m) throws SchedulerException{
	    // define the job and tie it to our HelloJob class
	    JobDetail job = JobBuilder.newJob(GetInjuredPlayersJob.class)
	        .withIdentity("job1", "group1")
	        .build();
	    
	    job.getJobDataMap().put("login", login);
	    job.getJobDataMap().put("password", psw);

	    // Trigger the job to run now, and then repeat every 40 seconds
	    Trigger trigger = TriggerBuilder.newTrigger()
	    	        .withIdentity(triggerName, groupName)
	    	        .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(h,m))
	    	        .forJob("job1",groupName)
	    	        .build();
	    	    //.withSchedule(CronScheduleBuilder.cronSchedule("0 6 15 * * ?")) //"0 29 5 ? * MON-SUN"  
//	    	    .forJob("job1", "group1")
//	    	    .build();


	    scheduler.scheduleJob(job, trigger);	            
	}
}
