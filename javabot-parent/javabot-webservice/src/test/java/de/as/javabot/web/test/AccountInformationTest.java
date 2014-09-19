package de.as.javabot.web.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Scanner;

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
	public void testTeamValue1() {
		try {
			String ret = bot.getTeamValue();
			//System.out.println("team: " +bot.getTeamValue());
			assertTrue("Mannschaftswert beinhaltet ein Eurozeichen", ret.contains("€"));
		} catch (IOException e) {
			fail("Excetion!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMobileKontostand1() {
		try {
			String ret = bot.getBankBalance();
			assertTrue("Kontostand beinhaltet ein Eurozeichen", ret.contains("€"));
			//System.out.println("Kontostand: "+ret);
		} catch (IOException e) {
			fail("Excetion!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAufstellung() {
		try {
			StringBuilder aufstellung=new StringBuilder();
			HtmlPage ret;// = bot.login(true);
			ret = bot.getTeamFormation();
			String res = ret.asXml();
			
			int start = res.indexOf("<div style=\"width:px;height:px;background-image:url");
			int end = res.indexOf("Aufstellung bestätigen");
			StringBuilder information = new StringBuilder(res.substring(start, end));
			//System.out.println(information.toString());
			Scanner sc = new Scanner(information.toString());
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if(!line.contains("<") && !line.equals("")){
					//System.out.println(line);
					aufstellung.append("Spieler: "+ line +"\n");
				}
			}
			sc.close();
			//System.out.println(aufstellung);
			assertTrue(aufstellung.toString().contains("Spieler"));
		} catch (IOException e) {
			fail("Excetion!");
			e.printStackTrace();
		}
	}

}
