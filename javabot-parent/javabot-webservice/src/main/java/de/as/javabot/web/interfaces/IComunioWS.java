package de.as.javabot.web.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IComunioWS {

	@WebMethod
	public String getDailyMailAboutInjuredPlayerInTeamformation(
			@WebParam(name = "login") String login,
			@WebParam(name = "password") String password,
			@WebParam(name = "mail") String mail,
			@WebParam(name = "zeit") String time,
			@WebParam(name = "activate") boolean activate);

	@WebMethod
	public String sentWhatsappMessage(@WebParam(name = "phoneNumber") String phoneNumber,
			@WebParam(name = "message") String message);

}
