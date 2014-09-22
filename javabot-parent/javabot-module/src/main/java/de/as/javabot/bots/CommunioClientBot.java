package de.as.javabot.bots;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import de.as.javabot.comunio.ComunioPlayer;
import de.as.javabot.configuration.Configuration;

public class CommunioClientBot implements Bot {

	Logger logger = LoggerFactory.getLogger("CommunioClientBotLogger");
	private WebClient webClient;
	private ProxyConfig proxyConfig;
	private Configuration config;

	public HtmlPage getTransferMarket(HtmlPage page) throws IOException {
		return getSection(page, "Transfer list");
	}

	public HtmlPage getTeamFormation() throws IOException {
		HtmlPage teamFormation = login(true);
		String standigsLink = "http://m.comunio.de/li3/";
		List<HtmlElement> spans = teamFormation.getElementsByTagName("a");
		for (HtmlElement element : spans) {
			List<HtmlElement> img = element.getElementsByTagName("img");
			for (HtmlElement link : img) {
				if (link.getAttribute("alt").equals("Eigene Aufstellung")) {
					standigsLink = element.getAttribute("href");
				}
			}
		}
		teamFormation = getWebClient().getPage(standigsLink);
		return teamFormation;
	}

	public HtmlPage getStandings(HtmlPage page) throws IOException {
		return getSection(page, "Standings");
	}

	// public ArrayList<String> getInjuredPlayers(){
	// ArrayList<String> ret = new ArrayList<String>();
	// Scanner scanner = new Scanner(text);
	// while (scanner.hasNextLine()) {
	// String row = scanner.nextLine();
	// if (row.startsWith(line)) {
	// return row.substring(line.length()).trim();
	// }
	// }
	// scanner.close();
	// }

	public String getBankBalance() throws IOException {
		HtmlPage login = login(true);
		String results = login.asText();
		// System.out.println(results);
		return getTextRowContaining(results, "€");
	}

	public String getTeamValue() throws IOException {
		HtmlPage login = login(false);
		String results = login.asText();
		// System.out.println(login.getUrl());
		return getTextRowAt(results, "Team value:");
	}

	@SuppressWarnings("resource")
	private String getTextRowAt(String text, String line) {
		Scanner scanner = new Scanner(text);
		while (scanner.hasNextLine()) {
			String row = scanner.nextLine();
			if (row.startsWith(line)) {
				return row.substring(line.length()).trim();
			}
		}
		scanner.close();
		return "Error! There is no result for: " + line;
	}

	@SuppressWarnings("resource")
	private String getTextRowContaining(String text, String line) {
		Scanner scanner = new Scanner(text);
		while (scanner.hasNextLine()) {
			String row = scanner.nextLine();
			if (row.contains(line)) {
				return row.trim();
			}
		}
		scanner.close();
		return "Error! There is no result for: " + line;
	}

	/**
	 * 
	 * @param page
	 * @param sectionName
	 * @return The section as HtmlPage
	 * @throws IOException
	 */
	private HtmlPage getSection(HtmlPage page, String sectionName)
			throws IOException {
		List<HtmlAnchor> anchors = page.getAnchors();
		HtmlAnchor link = null;
		for (HtmlAnchor anchor : anchors) {
			// System.out.println(anchor.asText());
			if (anchor.asText().indexOf(sectionName) > -1) {
				link = anchor;
				break;
			}
		}
		return link.click();
	}

	/**
	 * Logs in into communio
	 * 
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public HtmlPage login(boolean mobile)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		HtmlAnchor link = null;
		turnOffWarnings();
		webClient = getWebClient();
		
		if(config.isProxyActive()){
			proxyConfig = getProxyConfig(); 
		}
		
		if (mobile) {
			webClient
					.addRequestHeader(
							"User-Agent",
							"Mozilla/5.0 (iPhone; U; CPU iPhone OS 2_2 like Mac OS X; fr-fr)"
									+ " AppleWebKit/525.18.1 (KHTML, like Gecko) Version/3.1.1 Mobile/5G77 Safari/525.20");
			HtmlPage page = getWebClient().getPage(config.getLoginUrlMobile());
			HtmlForm form = page.getForms().get(0);
			HtmlTextInput textField1 = form.getInputByName("username");
			textField1.setValueAttribute(config.getAccount());
			HtmlPasswordInput passwordField4 = (HtmlPasswordInput) page
					.getElementByName("password");
			passwordField4.setValueAttribute(config.getPassword());

			// link = (HtmlAnchor) page.querySelector("a.whiteButton");

			// Workaround: create a 'fake' button and add it to the form
			HtmlButton submitButton = (HtmlButton) page.createElement("button");
			submitButton.setAttribute("type", "submit");
			form.appendChild(submitButton);

			// Workaround: use the reference to the button to submit the form.
			return submitButton.click();
		} else {
			HtmlPage page = getWebClient().getPage(config.getLoginUrl());
			HtmlForm form = page.getFormByName("login");
			HtmlTextInput textField1 = form.getInputByName("login");
			textField1.setValueAttribute(config.getAccount());

			HtmlPasswordInput passwordField4 = (HtmlPasswordInput) page
					.getElementByName("pass");
			passwordField4.setValueAttribute(config.getPassword());
			List<HtmlAnchor> anchors2 = page.getAnchors();
			for (HtmlAnchor anchor : anchors2) {
				if (anchor.asText().indexOf(">> Login") > -1) {
					link = anchor;
					break;
				}
			}
			return link.click();
		}

	}

	private void turnOffWarnings() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
				.setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(
				java.util.logging.Level.OFF);
	}

	public WebClient getWebClient() {
		if (webClient != null) {
			return webClient;
		}
		WebClient client = new WebClient();
		client.setTimeout(60000);
		client.setRedirectEnabled(true);
		client.setJavaScriptEnabled(true);
		client.setThrowExceptionOnFailingStatusCode(false);
		client.setThrowExceptionOnScriptError(false);
		client.setCssEnabled(false);
		return client;
	}

	public ProxyConfig getProxyConfig() {
		if (proxyConfig != null) {
			return proxyConfig;
		}
		proxyConfig = new ProxyConfig(config.getProxy(), config.getPort());
		getWebClient().setProxyConfig(proxyConfig);
		return proxyConfig;
	}

	public void setConfiguration(Configuration con) {
		this.config = con;
	}

	public static String getValueByEdges(StringBuilder builder,
			String beginningEdge, String endEdge) {
		if (builder.toString().contains(beginningEdge)) {
			int i = builder.indexOf(beginningEdge) + beginningEdge.length();
			int k = builder.indexOf(endEdge);
			return builder.toString().substring(i, k);
		}
		return "There was no value for this edge";
	}

	public ArrayList<ComunioPlayer> getTeamFormationAsList() {
		ArrayList<ComunioPlayer> players = new ArrayList<ComunioPlayer>();
		try {
			HtmlPage ret = login(true);
			ret = getTeamFormation();
			String res = ret.asXml();

			int start = res
					.indexOf("<div style=\"width:px;height:px;background-image:url");
			int end = res.indexOf("Aufstellung bestätigen");
			StringBuilder information = new StringBuilder(res.substring(start,
					end));

			String searchPattern = "<td align=\"center\" valign=\"bottom\">";
			int playerCount = StringUtils.countMatches(information.toString(),
					searchPattern);

			int startPattern = 0;
			int endPattern = 0;
			for (int i = 0; i < playerCount; i++) {
				startPattern = information.indexOf(searchPattern, startPattern);
				endPattern = information.indexOf("</td>", startPattern);
				StringBuilder aPlayer = new StringBuilder(
						information.substring(startPattern, endPattern));
				String playerName = getValueByEdges(aPlayer,
						"height:20px;line-height:10px;\">", "</div>").trim();
				String weblink = getValueByEdges(aPlayer, "<a href=\"",
						"\" style").trim();
				String position = "";
				if (weblink.contains("striker")) {
					position = "im Sturm";
				} else if (weblink.contains("midfielder")) {
					position = "im Mittelfeld";
				} else if (weblink.contains("defender")) {
					position = "in der Abwehr";
				} else if (weblink.contains("keeper")) {
					position = "im Tor";
				}
				String isInjured = getValueByEdges(aPlayer, "color:", ";\">")
						.trim();
				String status = "aktiv";
				if (!isInjured.equals("#eeeeee")) {
					status = "verletzt";
				}
				ComunioPlayer p = new ComunioPlayer();
				p.setName(playerName);
				p.setWeblink(weblink);
				p.setStatus(status);
				p.setPosition(position);
				players.add(p);
				startPattern = endPattern;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return players;
	}

	public ArrayList<ComunioPlayer> getInjuredPlayer() {
		ArrayList<ComunioPlayer> ret = getTeamFormationAsList();
		ArrayList<ComunioPlayer> injured = new ArrayList<ComunioPlayer>();
		for (ComunioPlayer item : ret) {
			if(!item.getStatus().contains("aktiv")){
				injured.add(item);
			}
		}
		return injured;
	}

}
