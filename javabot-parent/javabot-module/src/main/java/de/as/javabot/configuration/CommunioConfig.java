package de.as.javabot.configuration;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import com.gargoylesoftware.htmlunit.BrowserVersion;


public class CommunioConfig implements Configuration{

	private String account;
	private String password;

	public String getLoginUrl() {
		return getValue("login");
	}
	
	public BrowserVersion getBrowserVersion(){
		return BrowserVersion.FIREFOX_3_6;
	}


	public String getProxy() {
		return getValue("proxy");
	}


	public int getPort() {
		try {
			return Integer.parseInt( getValue("port"));
		} catch (Exception e) {
			return -1;
		}
	}


	public String getAccount() {
		if(account!=null){
			return account;
		}
		return getValue("account");
	}


	public String getPassword() {
		if(password!=null){
			return password;
		}
		return getValue("password");
	}
	
	public String getValue(String property){
		try { 
		      //Get the inputStream-->This time we have specified the folder name too.  
		      InputStream inputStream = this.getClass().getClassLoader()  
		              .getResourceAsStream("config/communio.properties");  
		      Properties properties = new Properties();         
		      //load the inputStream using the Properties  
		      properties.load(inputStream);  
		      //get the value of the property  
		      String propValue = properties.getProperty(property);  
		     
		      return propValue;  
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			return "Error! Could not find directory.";
		}
	}

	@Override
	public void setAccount(String acc) {
		this.account = acc;
	}

	@Override
	public void setPassword(String psw) {
		this.password = psw;
	}

	@Override
	public String getLoginUrlMobile() {
		return getValue("loginMobile");
	}

}
