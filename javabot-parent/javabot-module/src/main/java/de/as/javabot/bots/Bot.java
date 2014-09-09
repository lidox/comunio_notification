package de.as.javabot.bots;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import de.as.javabot.configuration.Configuration;

public interface Bot {
	public void setConfiguration(Configuration con);
	public HtmlPage login(boolean mobile) throws FailingHttpStatusCodeException, MalformedURLException, IOException;
}
