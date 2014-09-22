package de.as.javabot.main;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import de.as.javabot.batch.Trigger2;
import de.as.javabot.bots.CommunioClientBot;
import de.as.javabot.configuration.CommunioConfig;
import de.as.javabot.configuration.Configuration;
import de.as.mail.notification.MailNotificator;
import de.as.mail.notification.SendMail;


public class Main {
	
	public static void main(String[] args) {
		try {
//			long start = System.currentTimeMillis();
//			System.out.println("Starte Programm");
//			
//			Configuration config = new CommunioConfig();
//			
//			CommunioClientBot bot = new CommunioClientBot();
//			
//			bot.setConfiguration(config);
//
////			MailNotificator mail = new MailNotificator();
////			mail.service(login +" " + password);
////			SendMail s2 = new SendMail();
////			s2.send2();
//			String s = bot.getTeamValue();
//			System.out.println(s+ " ");
//			
//			long time = System.currentTimeMillis() - start;
//			System.out.println("TIME: " +time/1000.0 + " s");
			//Trigger2.startDailyTrigger("egak", roupName, login, psw)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

