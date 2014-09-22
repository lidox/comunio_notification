package de.as.javabot.batch.jobs;

import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.as.javabot.bots.CommunioClientBot;
import de.as.javabot.comunio.ComunioPlayer;
import de.as.javabot.configuration.CommunioConfig;
import de.as.javabot.configuration.Configuration;
import de.as.mail.notification.TLSEmail;

public class GetInjuredPlayersJob implements Job{
	
	private String getParam(JobExecutionContext context, String key){
		JobDataMap data = context.getJobDetail().getJobDataMap();
		return data.getString(key);
	}
	
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		try {
			Configuration config = new CommunioConfig();
			config.setAccount(getParam(context, "login"));
			config.setPassword(getParam(context, "password"));
			
			CommunioClientBot bot = new CommunioClientBot();
			bot.setConfiguration(config);
			
			
			ArrayList<ComunioPlayer> list = bot.getInjuredPlayer();
			StringBuilder msg = new StringBuilder();
			msg.append(""+"\n");
			msg.append("Benutzer: "+getParam(context, "login") +"\n");
			
			if(list.size()>0){
				msg.append("Folgende Spieler sind verletzt und in der Startaufstellung:" +"\n");
				for (ComunioPlayer p : list) {
					msg.append(p.getName() + " ist verletzt und aufgestellt "+p.getPosition() +"\n");
				}
			}
			else{
				msg.append("In der Startaufstellung hast du keinen verletzten Spieler! :)");
			}
			
			try {
				TLSEmail s = new TLSEmail();		
				s.doMail(msg.toString());
			} catch (Exception e) {/*muhaha kann passiert sollte aber nicht*/
				System.out.println("mail nicht versendet");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
