package de.as.javabot.report;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.as.javabot.bots.CommunioClientBot;
import de.as.javabot.comunio.ComunioPlayer;
import de.as.javabot.comunio.User;
import de.as.javabot.configuration.CommunioConfig;
import de.as.javabot.configuration.Configuration;

@Ignore
public class TimeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testLocalTime1() throws Exception {
		LocalTime localTime = LocalTime.parse( "15:30" );
		assertEquals(localTime.toString(),"15:30");
	}

	
	@Test
	public void trst(){
		User user = new User("lidox", "lidox");
		Configuration config = new CommunioConfig();
		config.setAccount(user.getLogin());
		config.setPassword(user.getPassword());

		CommunioClientBot bot = new CommunioClientBot();
		bot.setConfiguration(config);

		ArrayList<ComunioPlayer> list = bot.getInjuredPlayer();
		StringBuilder msg = getInjuredPlayersAsString(user, list);
		msg.toString();
	}
	
	private StringBuilder getInjuredPlayersAsString(User user,
			ArrayList<ComunioPlayer> list) {
		StringBuilder msg = new StringBuilder();
		msg.append(" " + user.getLogin() + "," + "\n");

		if (list == null) {
			msg.append("Leider ist beim Einloggen auf www.comunio.de ein Fehler aufgetretten "
					+ "\n");
			msg.append("Deswegen liegen keine Informationen über deine Spieler vor."
					+ "\n");
			msg.append("Morgen bekommst du deine Info's dann hoffentlich :)"
					+ "\n");
		} else if (list.size() > 0) {
			msg.append("\n"
					+ "es sind folgende Spieler verletzt und in der Startaufstellung:"
					+ "\n");
			int i = 1;
			for (ComunioPlayer p : list) {
				msg.append(i + ". " + p.getName() + " ist verletzt "
						+ p.getPosition() + "." + "\n");
				i++;
			}
		} else {
			msg.append("In der Startaufstellung hast du keinen verletzten Spieler! :)"
					+ "\n");
		}
		
		return msg;
	}
}
