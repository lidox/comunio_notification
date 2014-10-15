package de.as.javabot.web;

import java.time.LocalTime;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.as.javabot.batch.JobExecuter;
import de.as.javabot.batch.TriggerDaily;
import de.as.javabot.batch.TriggerPeriod;
import de.as.javabot.comunio.User;
import de.as.javabot.comunio.User2;
import de.as.javabot.report.Report;
import de.as.javabot.report.ReportViaEmail;
import de.as.javabot.web.interfaces.IComunioWS;

@WebService
public class ComunioService implements IComunioWS {

	private static Logger LOG = LoggerFactory.getLogger(ComunioService.class);

	@WebMethod
	public String getDailyMailAboutInjuredPlayerInTeamformation(String login,
			String password, String mailAddress, String hourAndMinute,
			boolean activate) {		
		
		LocalTime time = LocalTime.parse(hourAndMinute);

		LOG.warn("Achtung! Momentan funktioniert nur ein Job pro user, da der Jobname = E-Mail Adresse ist!");
		TriggerPeriod period = new TriggerDaily(mailAddress, time);

		JobExecuter.addTriggerAndJob(period,"de.as.javabot.batch.jobs.GetInjuredPlayersJob2");

		Report report = new ReportViaEmail(activate, mailAddress);

		User user = new User(login, password);

		return JobExecuter.addJob(user, report);
	}
	
	@WebMethod
	public String doit(String s){
		java.util.Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("testPU", map);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		// we'll put some codes here later
		User2 u = new User2();
		
		u.setLogin("webservice-user");
		u.setPassword("top secret");
		em.persist(u);
		//
		em.getTransaction().commit();
		em.close();
		factory.close();
		return "done";
	}

}
