package de.as.javabot.bots;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import de.as.javabot.configuration.Configuration;

public class CommunioClientBot implements Bot{
	
	Logger logger=LoggerFactory.getLogger("CommunioClientBotLogger");
	private WebClient webClient;
	private ProxyConfig proxyConfig;
	private Configuration config;
	
	public HtmlPage getTransferMarket(HtmlPage page) throws IOException{
		return getSection(page, "Transfer list");
	}
	
	public HtmlPage getStandings(HtmlPage page) throws IOException{
        return getSection(page, "Standings");
	}
	
	public String getTeamValue() throws IOException{
		HtmlPage login = login();
		String results = login.asText();
		return getTextRowAt(results, "Team value:");
	}
	
	@SuppressWarnings("resource")
	private String getTextRowAt(String text, String line){
		Scanner scanner = new Scanner(text);
		while (scanner.hasNextLine()) {
		  String row = scanner.nextLine();
		  if(row.startsWith(line)){
			  return row.substring(line.length()).trim();
		  }
		}
		scanner.close();
		return "Error! There is no result for: "+ line;
	}
	
	/**
	 * 
	 * @param page
	 * @param sectionName
	 * @return The section as HtmlPage 
	 * @throws IOException
	 */
	private HtmlPage getSection(HtmlPage page, String sectionName) throws IOException{
        List<HtmlAnchor> anchors =  page.getAnchors();
        HtmlAnchor link = null;
        for(HtmlAnchor anchor: anchors)
        {
             if(anchor.asText().indexOf(sectionName) > -1 )
             {
                  link = anchor;
                  break;
             }
        }
        return link.click();
	}
	
	/**
	 * Logs in into communio
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public HtmlPage login() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		turnOffWarnings();
//		BrowserVersion bv = new BrowserVersion("Chrome", "Mozilla/5.0", "Mozilla/5.0 (Windows NT 6.1) "
//				+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36", 28);
		//Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko)
		//Version/4.0 Mobile/7A341 Safari/528.16
		webClient = getWebClient();
		//TODO: klappt der login so? teste! 
		proxyConfig = getProxyConfig();
		HtmlPage page = getWebClient().getPage(config.getLoginUrl());
		HtmlForm form = page.getFormByName("login");
		HtmlTextInput textField1 = form.getInputByName("login");
		textField1.setValueAttribute(config.getAccount());

		HtmlPasswordInput passwordField4 = (HtmlPasswordInput) page
				.getElementByName("pass");
		passwordField4.setValueAttribute(config.getPassword());

		List<HtmlAnchor> anchors2 = page.getAnchors();
		HtmlAnchor link = null;
		for (HtmlAnchor anchor : anchors2) {
			if (anchor.asText().indexOf(">> Login") > -1) {
				link = anchor;
				break;
			}
		}
		//logger.info("Info: Login war erfolgreich!");
		return link.click();
	}

	private void turnOffWarnings() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
				.setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(
				java.util.logging.Level.OFF);
	}
	
	public WebClient getWebClient() {
		if(webClient!=null){
			return webClient;
		}
		WebClient client = new WebClient();
		client.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) "
				+ "Chrome/28.0.1468.0 Safari/537.36");
	    client.setTimeout(60000);
	    client.setRedirectEnabled(true);
	    client.setJavaScriptEnabled(true);
	    client.setThrowExceptionOnFailingStatusCode(false);
	    client.setThrowExceptionOnScriptError(false);
	    client.setCssEnabled(false);
		return client;
	}

	public ProxyConfig getProxyConfig() {
		if(proxyConfig!=null){
			return proxyConfig;
		}
		proxyConfig = new ProxyConfig(config.getProxy(),config.getPort());
		getWebClient().setProxyConfig(proxyConfig);
		return proxyConfig;
	}
	
	public void setConfiguration(Configuration con){
		this.config = con;
	}
	
	
	

}
