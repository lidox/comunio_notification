package de.as.javabot.batch;

import java.time.LocalTime;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerDaily implements TriggerPeriod {

	private static Logger LOG = LoggerFactory.getLogger(TriggerDaily.class);
	private String TRIGGERNAME = "TriggerDaily";
	private String GROUPNAME = "ComunioGroup";
	private String jobName;
	private LocalTime time;

	/**
	 * 
	 * @param jobName
	 * @param time
	 */
	public TriggerDaily(String jobName, LocalTime time) {
		this.jobName = jobName;
		this.time = time;
		this.TRIGGERNAME = jobName;
		this.GROUPNAME = jobName;

	}

	public Trigger getPeriod() {
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(TRIGGERNAME, GROUPNAME)
				.withSchedule(
						CronScheduleBuilder.dailyAtHourAndMinute(
								time.getHour(), time.getMinute()))
				.forJob(jobName, GROUPNAME).build();
		LOG.debug("Es wurde ein neuer Job (jobName(id)="+jobName+")für den taeglichen Trigger angelegt. Der Job wird jeden Tag um "
				+ time.toString() + " Uhr ausgefuehrt.");
		return trigger;
	}

	public String getTriggerName() {
		return TRIGGERNAME;
	}

	public String getGroupName() {
		return GROUPNAME;
	}

	public String getJobName() {
		return jobName;
	}

}
