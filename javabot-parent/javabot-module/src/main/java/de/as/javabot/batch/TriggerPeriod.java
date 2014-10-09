package de.as.javabot.batch;

import org.quartz.Trigger;

/**
 * Speichert Informationen dar�ber, welcher Trigger verwendet wird: t�glicher, crontrigger etc.
 *
 */
public interface TriggerPeriod {
	public Trigger getPeriod();
	public String getTriggerName();
	public String getGroupName();
	public String getJobName();
}
