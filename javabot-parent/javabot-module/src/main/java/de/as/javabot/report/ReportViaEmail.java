package de.as.javabot.report;

import javax.ejb.Asynchronous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.as.mail.notification.TLSEmail;

public class ReportViaEmail implements Report{

	private static Logger LOG = LoggerFactory.getLogger(ReportViaEmail.class);
	private boolean isActive;
	private String mailAddress;
	
	/**
	 * 
	 * @param isActive
	 * @param mail
	 */
	public ReportViaEmail(boolean isActive, String mail){
		this.isActive = isActive;
		this.mailAddress = mail;
	}
	
	public String sentMessage(String message) {
		try {
			TLSEmail s = new TLSEmail();		
			return s.doMail(message,this.mailAddress);
		} catch (Exception e) {
			LOG.error("Es ist ein Fehler beim Senden der E-Mail aufgetretten: "+e.getMessage());
			return null;
		}
	}

	public boolean isActive() {
		return this.isActive;
	}
}
