package test.rdfasquare;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

public abstract class RegressionTest extends SeleneseTestCase{

	private static final String DEFAULT_SEVER = "127.0.0.1";
	private static final int DEFAULT_PORT = 4444;
	private static final String DEFAULT_BROWSER = "*firefox";
	private static final String DEFAULT_URL = "http://lcoalhost";
	
	private String server = System.getProperty("mySeleniumServer");
	private String port = System.getProperty("mySeleniumPort");
	private String browser = System.getProperty("mySeleniumBrowser");
	private String url = System.getProperty("mySeleniumUrl");
	
	public void setUp() throws Exception {
		selenium = new DefaultSelenium(
			server == null ? DEFAULT_SEVER : server,
			port == null ? DEFAULT_PORT : Integer.parseInt(port),
			browser == null ? DEFAULT_BROWSER : browser,
			url == null ? DEFAULT_URL : url
		);
		selenium.start();
	}
}
