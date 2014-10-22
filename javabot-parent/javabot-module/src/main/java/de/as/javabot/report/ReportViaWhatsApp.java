package de.as.javabot.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportViaWhatsApp implements Report {

	private static Logger LOG = LoggerFactory.getLogger(ReportViaEmail.class);
	private boolean isActive;
	private String phoneNumber;

	/**
	 * 
	 * @param isActive
	 * @param phoneNumber
	 */
	public ReportViaWhatsApp(boolean isActive, String phoneNumber) {
		this.isActive = isActive;
		this.phoneNumber = correctPhoneNumber(phoneNumber);
	}

	private String correctPhoneNumber(String phoneNumber) {
		if (phoneNumber.startsWith("+")) {
			phoneNumber = phoneNumber.substring(1);
		}
		if (phoneNumber.startsWith("0")) {
			phoneNumber = "49" + phoneNumber.substring(1);
		}
		return phoneNumber;
	}

	public String sentMessage(String message) {
		try {
			String YOWSUP_HOME = "/middle/whatsapp/src";
			String PYTHONPATH = "/middle/whatsapp/src";
			String[] sendCommand = { "/bin/bash", YOWSUP_HOME+"/sendMessage.sh",
					phoneNumber, message};
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.directory(new File(YOWSUP_HOME));
			processBuilder.environment().put(PYTHONPATH, YOWSUP_HOME);
			processBuilder.command(sendCommand);
			Process process = processBuilder.start();
			LOG.info("Bereite Nachricht vor an: "+this.phoneNumber +" mit dem Inhalt: "+message); 
			process.waitFor();
			InputStream stream = process.getInputStream();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stream));
			String str = "***";
			while ((str = br.readLine()) != null) {
				str += str + "\n";
			}
			LOG.info("Nachricht wurde erfolgreich versendet! process.getInputStream(): "+str); 
			return "Nachricht wurde erfolgreich versendet!";
		} catch (Exception e) {
			LOG.error("Es ist ein Fehler beim Senden der WhatsApp Nachricht aufgetretten: "
					+ e.getMessage());
			return null;
		}
	}

	public boolean isActive() {
		return this.isActive;
	}

}
