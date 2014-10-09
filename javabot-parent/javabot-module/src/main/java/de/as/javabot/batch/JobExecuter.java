package de.as.javabot.batch;

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
	private static TriggerPeriod jobPeriod;
	private static Class<?> jobClass;
	
	public JobExecuter() {
		try {
			SCHEDULER = new StdSchedulerFactory().getScheduler();
			SCHEDULER.start();
			LOG.info("+++ daily schedular instance is ready +++");
		} catch (SchedulerException e) {
			LOG.error("Could not load daily schedular instance.");
			e.printStackTrace();
		}
	}
	
	public static void addTriggerAndJob(TriggerPeriod jobPeriode,String jobClassName){
		jobClass = getClass(jobClassName);
		jobPeriod = jobPeriode;
	}

	/**
	 * Add or remove job from trigger
	 * @param user
	 * @param r
	 * @return
	 */
	public static String addJob(User user, Report r) {
		if(r.isActive()){
			return addJobToSchedular(user, jobPeriod.getJobName(), jobClass, r);
		}
		return unscheduleJob(jobPeriod);
	}

	/**
	 * Remove job from schedular
	 * @param user
	 * @param jobName
	 * @param r
	 * @return
	 */
	public static String unscheduleJob(TriggerPeriod jobPeriod) {
		try {
			SCHEDULER.unscheduleJob(TriggerKey.triggerKey(jobPeriod.getJobName(),
					jobPeriod.getJobName()));
			return "Die taegliche Service Mail an: "+jobPeriod.getJobName()+" wurde deaktiviert!";
		} catch (SchedulerException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String addJobToSchedular(User user, String jobName, Class clazz, Report report) {
		try {
			JobDetail job = JobBuilder.newJob(clazz)
					.withIdentity(jobName, jobPeriod.getGroupName()).build();

			SCHEDULER.getContext().put("user", user);
			SCHEDULER.getContext().put("report", report);	
			
			SCHEDULER.scheduleJob(job, jobPeriod.getPeriod());
			
			return "Du bekommst eine Nachricht um 'unkown hard coded' Uhr.";
		} catch (SchedulerException e3) {
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
}
