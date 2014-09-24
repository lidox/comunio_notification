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
			msg.append(" "+getParam(context, "login") +","+"\n");
			
			if(list==null){
				msg.append("Leider ist beim Einloggen auf www.comunio.de ein Fehler aufgetretten "+ "\n");
				msg.append("Deswegen liegen keine Informationen über deine Spieler vor."+ "\n");
				msg.append("Morgen bekommst du deine Info's dann hoffentlich :)"+ "\n");
			}
			else if(list.size()>0){
				msg.append("\n"+"es sind folgende Spieler verletzt und in der Startaufstellung:" +"\n");
				int i = 1;
				for (ComunioPlayer p : list) {
					msg.append(i+". "+p.getName() + " ist verletzt "+p.getPosition() +"."+"\n");
					i++;
				}
			}
			else{
				msg.append("In der Startaufstellung hast du keinen verletzten Spieler! :)"+ "\n");
			}
			
			try {
				TLSEmail s = new TLSEmail();		
				s.doMail(msg.toString(),getParam(context, "email"));
			} catch (Exception e) {/*muhaha kann passiert sollte aber nicht*/
				System.out.println("mail nicht versendet");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
