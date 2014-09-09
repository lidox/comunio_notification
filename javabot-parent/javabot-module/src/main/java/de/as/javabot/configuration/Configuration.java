package de.as.javabot.configuration;


public interface Configuration {
	  public String getLoginUrl();
	  public String getLoginUrlMobile();
	  public String getProxy();
	  public int getPort();
	  public String getAccount();
	  public String getPassword();
	  public void setAccount(String acc);
	  public void setPassword(String psw);
}
