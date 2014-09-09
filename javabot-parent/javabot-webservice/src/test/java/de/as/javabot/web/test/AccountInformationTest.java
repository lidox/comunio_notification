package de.as.javabot.web.test;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.validation.constraints.AssertFalse;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

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
	
	@Test
	public void testMobileKontostand1() {
		try {
			String ret = bot.getBankBalance();
			assertTrue("Kontostand beinhaltet ein Eurozeichen", ret.contains("€"));
			System.out.println("Kontostand: "+ret);
		} catch (IOException e) {
			fail("Excetion!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		try {
			HtmlPage ret;// = bot.login(true);
			ret = bot.getTeamFormation();
			String res = ret.asText();
			//assertTrue("Kontostand beinhaltet ein Eurozeichen", ret.contains("€"));
			System.out.println("Aufstellung: "+res);
		} catch (IOException e) {
			fail("Excetion!");
			e.printStackTrace();
		}
	}

}
