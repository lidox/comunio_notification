package de.as.javabot.batch.jobs;

import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.as.javabot.batch.JobExecuter;
import de.as.javabot.bots.CommunioClientBot;
import de.as.javabot.comunio.ComunioPlayer;
import de.as.javabot.comunio.User;
import de.as.javabot.configuration.CommunioConfig;
import de.as.javabot.configuration.Configuration;
import de.as.javabot.report.Report;

public class GetInjuredPlayersJob2 implements Job {

	private static Logger LOG = LoggerFactory.getLogger(JobExecuter.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			User user = (User) getParam(context, "user");
			System.out.println(user.toString());
			Report report = (Report) getParam(context, "report");

			Configuration config = new CommunioConfig();
			config.setAccount(user.getLogin());
			config.setPassword(user.getPassword());

			CommunioClientBot bot = new CommunioClientBot();
			bot.setConfiguration(config);

			ArrayList<ComunioPlayer> list = bot.getInjuredPlayer();
			StringBuilder msg = getInjuredPlayersAsString(user, list);

			LOG.warn("NEUE BENACHRITIGUNG geht raus an " + user.getLogin()
					+ "!" + "\n" + "Folgende Nachricht: " + msg.toString());
			report.sentMessage(msg.toString());
			
			//TODO: hier ein test
			addParamToJobContext(context, "test", "this is a amazing test! :)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Erstellt ein String mit den verletzen Spielern je nach dem ob die
	 * uebergebene Liste befuellt ist
	 * 
	 * @param user
	 * @param list
	 * @return
	 */
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

	private Object getParam(JobExecutionContext context, String key) throws SchedulerException {
		return context.getScheduler().getContext().get(key);
	}

	private void addParamToJobContext(JobExecutionContext context, String key, Object value) {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		data.put(key, value);
	}
}
