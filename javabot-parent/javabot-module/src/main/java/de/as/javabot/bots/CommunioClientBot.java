package de.as.javabot.bots;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.ScriptResult;
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
	
	public String getBankBalance() throws IOException{
		HtmlPage login = login(true);
		String results = login.asText();
		return results;
	}
	
	public String getTeamValue() throws IOException{
		HtmlPage login = login(false);
		String results = login.asText();
		//System.out.println(login.getUrl());
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
	public HtmlPage login(boolean mobile) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		HtmlAnchor link = null;
		turnOffWarnings();
		webClient = getWebClient();
		proxyConfig = getProxyConfig();
		if(mobile){
			webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 2_2 like Mac OS X; fr-fr)"
					+ " AppleWebKit/525.18.1 (KHTML, like Gecko) Version/3.1.1 Mobile/5G77 Safari/525.20");
			HtmlPage page = getWebClient().getPage(config.getLoginUrlMobile());
			HtmlForm form = page.getForms().get(0);
			HtmlTextInput textField1 = form.getInputByName("username");
			textField1.setValueAttribute(config.getAccount());
			HtmlPasswordInput passwordField4 = (HtmlPasswordInput) page.getElementByName("password");
			passwordField4.setValueAttribute(config.getPassword());
			//System.out.println(page.asText());
			link = (HtmlAnchor) page.querySelector("a.whiteButton");
			
			//TODO: hier ist eine groﬂe Baustelle
//			webClient.waitForBackgroundJavaScript(10000);
//			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//			webClient.waitForBackgroundJavaScriptStartingBefore(10000);
//		    //String javaScriptCode = "document.getElementById('frmLogin').submit();";
//		    ScriptResult result = page.executeJavaScript(link.getOnClickAttribute());
//		    HtmlPage lol = (HtmlPage) result.getNewPage();
//		    System.out.println(lol.asText());
//		    System.out.println();
			return link.click();
		}
		else{
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
		if(webClient!=null){
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
