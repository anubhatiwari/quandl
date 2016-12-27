package quandl.utils;

import java.util.List;

import org.openqa.selenium.WebDriver;

public interface PreTestSetup {

	/**
	 * Initializes web drivers with specified browser.
	 * 
	 * @param browser
	 *            the name of the browser
	 * @param acceptCertficate
	 *            flag to indicate whether certificates needs to accept by
	 *            default or not
	 * @param maxWindow
	 *            flag to set window size to maximum
	 * @return true if web driver initialization is successful.
	 */
	public boolean setupWebDriver(String browser, boolean acceptCertficate,
			boolean maxWindow);

	public boolean setupWebDriver(String browser);

	public boolean setupWebDriver(List<String> browser);

	public WebDriver getWebDriver();

	public String getBaseUrl();

	public void closeWebDriver(final boolean screenShotNeeded);

	boolean setupbaseUrl(String baseUrl);

}
