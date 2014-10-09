package de.as.javabot.report;

/**
 * Spezifiziert, wie eine Benachrichtigung abgesendet wird. 
 * Zum Beispiel via E-Mail, WhatsApp oder SMS
 */
public interface Report {
	/**
	 * Sendet eine Benachrichtigung z.B. über E-Mail, WhatsApp oder SMS
	 * @param message
	 * @return
	 */
	public String sentMessage(String message);
	public boolean isActive();
}
