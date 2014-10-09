package de.as.javabot.batch;

import org.quartz.Trigger;

/**
 * Speichert Informationen darüber, welcher Trigger verwendet wird: täglicher, crontrigger etc.
 *
 */
public interface TriggerPeriod {
	public Trigger getPeriod();
	public String getTriggerName();
	public String getGroupName();
	public String getJobName();
}
