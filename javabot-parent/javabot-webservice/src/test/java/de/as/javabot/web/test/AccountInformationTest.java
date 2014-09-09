package de.as.javabot.web.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.as.javabot.bots.CommunioClientBot;
import de.as.javabot.configuration.CommunioConfig;
import de.as.javabot.configuration.Configuration;

public class AccountInformationTest {

	static Configuration config;
	static CommunioClientBot bot;
	
	@BeforeClass
	public static void setUp(){
		config = new CommunioConfig();
		config.setAccount("lidox");
		config.setPassword("lidox");	
		bot = new CommunioClientBot();
		bot.setConfiguration(config);
		
//		TLSEmail s = new TLSEmail();		
//		s.doMail("lidox" +" " + "lidox");
	}
	
	@Test
	public void test() {
		try {
			String ret = bot.getTeamValue();
			System.out.println("team: " +bot.getTeamValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
