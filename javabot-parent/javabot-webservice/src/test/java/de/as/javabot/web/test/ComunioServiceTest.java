package de.as.javabot.web.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.SchedulerException;

import de.as.javabot.bots.CommunioClientBot;
import de.as.javabot.configuration.CommunioConfig;
import de.as.javabot.configuration.Configuration;
import de.as.javabot.web.ComunioService;

//@Ignore
public class ComunioServiceTest {

   private static Configuration config;
   private static CommunioClientBot bot;
   private static long startTime;
   private static Logger log = Logger.getLogger(ComunioServiceTest.class);

   private static EJBContainer ejbContainer;

   private static ComunioService service;

   @BeforeClass
   public static void oneTimeSetUp() throws Exception {
      Properties properties = new Properties();
      properties.setProperty("openejb.configuration",
            "src/test/resources/openejb.xml");
      ejbContainer = EJBContainer.createEJBContainer(properties);

      // Get EJB Instance
      service = InitialContext.doLookup("ComunioServiceLocalBean");
      
      startTime = System.currentTimeMillis();
      config = new CommunioConfig();
      config.setAccount("lidox");
      config.setPassword("lidox");
      bot = new CommunioClientBot();
      bot.setConfiguration(config);
   }

   @AfterClass
   public static void tearDownAfterClass() throws Exception {
      if (ejbContainer != null) {
         ejbContainer.close();
      }
      long time = System.currentTimeMillis() - startTime;
      System.out.println("Ausführungszeit: " + time / 1000.0 + " s");
      log.debug("Ausführungszeit: " + time / 1000.0 + " s");
   }
   
   @Test
   public void testTime1() throws SchedulerException {
      String time = "05:30";
      int sep = time.indexOf(":");
      int hour = Integer.parseInt(time.substring(0, sep));
      int minute = Integer.parseInt(time.substring(sep + 1, time.length()));
      assertEquals("5 30", hour + " " + minute);
   }

   @Test
   public void testMobileKontostand1() {
      try {
         String ret = bot.getBankBalance();
         assertTrue("Kontostand beinhaltet ein Eurozeichen", ret.contains("€"));
         // System.out.println("Kontostand: "+ret);
      } catch (IOException e) {
         fail("Excetion!");
         e.printStackTrace();
      }
   }

   @Test
   public void testGetTeamList1() {
      try {      
         assertEquals("Anzahl der spieler muss elf sein",11, bot.getTeamFormationAsList().size());
      } catch (Exception e) {
         fail();
      }
   }

//   @Test
//   public void testGetVerletzteSpieler1() {
//      ArrayList<ComunioPlayer> ret = bot.getTeamFormationAsList();
//      ArrayList<ComunioPlayer> injured = new ArrayList<ComunioPlayer>();
//      for (ComunioPlayer item : ret) {
//         if (!item.getStatus().contains("aktiv")) {
//            injured.add(item);
//         }
//      }
//      System.out.println(injured.toString());
//      assertEquals("Anzahl der spieler muss elf sein", 11, ret.size());
//   }


}
