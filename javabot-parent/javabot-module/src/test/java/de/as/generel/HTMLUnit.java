package de.as.generel;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


public class HTMLUnit {

//	@Test
//	public void homePage() throws Exception {
//	    final WebClient webClient = new WebClient();
//	    ProxyConfig proxyConfig = new ProxyConfig("proxy-internet.localnet",8080);
//	    webClient.setProxyConfig(proxyConfig);
//	    
//	    final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
//	    Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
//
//	    final String pageAsXml = page.asXml();
//	    Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
//
//	    final String pageAsText = page.asText();
//	    Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
//	    webClient.closeAllWindows();
//	}
	
//	@Test
//	public void submittingForm() throws Exception {
//	    final WebClient webClient = new WebClient();
//	    ProxyConfig proxyConfig = new ProxyConfig("proxy-internet.localnet",8080);
//	    webClient.setProxyConfig(proxyConfig);
//	    // Get the first page
//	    final HtmlPage page1 = webClient.getPage("http://www.comunio.de");
//
//	    // Get the form that we are dealing with and within that form, 
//	    // find the submit button and the field that we want to change.
//	    final HtmlForm form = page1.getFormByName("login");
//
//	    final HtmlSubmitInput button = form.getInputByName(">> Login");
//	    final HtmlTextInput textFieldlog = form.getInputByName("login");
//	    final HtmlTextInput textFieldpsw = form.getInputByName("pass");
//
//	    // Change the value of the text field
//	    textFieldlog.setValueAttribute("lidox");
//	    textFieldpsw.setValueAttribute("lidox");
//
//	    // Now submit the form by clicking the button and get back the second page.
//	    final HtmlPage page2 = button.click();
//	    final String pageAsText = page2.asText();
//	    System.out.println("LOL: "+pageAsText);
//	    webClient.closeAllWindows();
//	}
	
//	@Test
//	public void openProjectTest() throws Exception {
//		
//		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
//	
//	    final HtmlPage page = webClient.getPage("http://albali:8081/login");
//
//	    // Get the form that we are dealing with and within that form, 
//	    // find the submit button and the field that we want to change.
//	    final HtmlForm form = page.getFirstByXPath("//form[@action='/login']");
//
//	    final HtmlSubmitInput button = form.getInputByName("login");
//	    final HtmlTextInput textFieldlog = form.getInputByName("username");
//	    final HtmlPasswordInput textFieldpsw = (HtmlPasswordInput) form.getInputByName("password");
//
//	    // Change the value of the text field
//	    textFieldlog.setValueAttribute("artur");
//	    textFieldpsw.setValueAttribute("asr7!");
//
//	    // Now submit the form by clicking the button and get back the second page.
//	    final HtmlPage page2 = button.click();
//
//	    System.out.println("LOL: "+page2.asText());
//	    webClient.closeAllWindows();
//	}
	
	@Test
	public void test(){
        HtmlPage page = null;
        boolean savePagesLocally = false;
        String url = "http://www.comunio.de/login";

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
        webClient.setThrowExceptionOnScriptError(false);

        String savePagesLocallyString = System.getProperty("savePagesLocally");
        if(savePagesLocallyString != null )
        { savePagesLocally = Boolean.valueOf(savePagesLocallyString); }

        int pageNum = 1;
        String localFilePath = null;

        try
        {
             page = webClient.getPage( url );

             System.out.println("Current page: Comunio.de Login");

             // Current page:
             // Title=Comunio.de Login
             // URL=http://www.comunio.de/login

             HtmlTextInput textField1 = (HtmlTextInput) page.getElementByName("login");
             textField1.setValueAttribute("lidox");

             HtmlPasswordInput passwordField4 = (HtmlPasswordInput) page.getElementByName("pass");
             passwordField4.setValueAttribute("lidox");

             List<HtmlAnchor> anchors2 =  page.getAnchors();
             HtmlAnchor link3 = null;
             for(HtmlAnchor anchor: anchors2)
             {
                  if(anchor.asText().indexOf(">> Login") > -1 )
                  {
                       link3 = anchor;
                       break;
                  }
             }
             page = link3.click();

             System.out.println("Current page: Team lineup of artur");

             // Current page:
             // Title=Team lineup of artur
             // URL=http://www.comunio.de/lineup.phtml



             System.out.println("Test has completed successfully");
        }
        catch ( FailingHttpStatusCodeException e1 )
        {
             System.out.println( "FailingHttpStatusCodeException thrown:" + e1.getMessage() );
             e1.printStackTrace();

        }
        catch ( MalformedURLException e1 )
        {
             System.out.println( "MalformedURLException thrown:" + e1.getMessage() );
             e1.printStackTrace();

        }
        catch ( IOException e1 )
        {
             System.out.println( "IOException thrown:" + e1.getMessage() );
             e1.printStackTrace();

        }
        catch( Exception e )
        {
       e.printStackTrace();
        }
   }
	

}
