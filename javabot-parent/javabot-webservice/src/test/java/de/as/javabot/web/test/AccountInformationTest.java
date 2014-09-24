package de.as.javabot.web.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.quartz.SchedulerException;

import de.as.javabot.batch.Trigger2;
import de.as.javabot.batch.jobs.GetInjuredPlayersJob;
import de.as.javabot.bots.CommunioClientBot;
import de.as.javabot.comunio.ComunioPlayer;
import de.as.javabot.configuration.CommunioConfig;
import de.as.javabot.configuration.Configuration;

@Ignore
public class AccountInformationTest {

	static Configuration config;
	static CommunioClientBot bot;
	static long startTime;
	@BeforeClass
	public static void setUp(){
		startTime = System.currentTimeMillis();
		config = new CommunioConfig();
		config.setAccount("lidox");
		config.setPassword("lidox");	
		bot = new CommunioClientBot();
		bot.setConfiguration(config);
		
//		TLSEmail s = new TLSEmail();		
//		s.doMail("lidox" +" " + "lidox");
	}
	
	@AfterClass
	public static void tearDown(){
		long time = System.currentTimeMillis() - startTime;
		System.out.println("Ausführungszeit: " +time/1000.0 + " s");
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
	public void testGetTeamList1() {
		ArrayList<ComunioPlayer> ret = bot.getTeamFormationAsList();
		assertEquals("Anzahl der spieler muss elf sein",11,ret.size());
	}
	
	@Test
	public void testGetVerletzteSpieler1() {
		ArrayList<ComunioPlayer> ret = bot.getTeamFormationAsList();
		ArrayList<ComunioPlayer> injured = new ArrayList<ComunioPlayer>();
		for (ComunioPlayer item : ret) {
			if(!item.getStatus().contains("aktiv")){
				injured.add(item);
			}
		}
		System.out.println(injured.toString());
		assertEquals("Anzahl der spieler muss elf sein",11,ret.size());
	}
	
	@Test
	public void testTime1() throws SchedulerException {
		String time = "05:30";
		int sep = time.indexOf(":");
		int hour = Integer.parseInt(time.substring(0, sep));
		int minute = Integer.parseInt(time.substring(sep+1, time.length()));
		assertEquals("5 30", hour + " "+minute);
	}
	
	

}
