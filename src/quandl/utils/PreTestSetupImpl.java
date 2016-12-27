package quandl.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import quandl.test.*;

public class PreTestSetupImpl implements PreTestSetup {

	protected WebDriver driver;
	protected String baseUrl;
	private final ScenarioCacheManager scenarioCacheManager = ScenarioCacheManager
			.getInstance();

	@Override
	public boolean setupbaseUrl(final String baseUrl) {
		if (null == baseUrl) {
			this.baseUrl = "https://127.0.0.1";
		} else {
			this.baseUrl = baseUrl;
		}
		scenarioCacheManager.store(Cache.BASE_URL, this.baseUrl);
		return true;
	}

	@Override
	public boolean setupWebDriver(String browser, boolean acceptCertficate,
			boolean maxWindow) {
		if (null == browser || browser.equals(Browser.FIREFOX.getName())) {
			this.driver = new FirefoxDriver();

		} else if (browser.equals(Browser.CHROME.getName())) {

			System.setProperty("webdriver.chrome.driver",
					"chromedriver_win32/chromedriver.exe");
			this.driver = new ChromeDriver();

		} else if (browser.equals(Browser.IE.getName())) {
			final DesiredCapabilities ieCapabilities = DesiredCapabilities
					.internetExplorer();
			if (acceptCertficate) {
				ieCapabilities.setCapability(
						CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "accept");
			}
			this.driver = new InternetExplorerDriver(ieCapabilities);

		}
		try {
			this.setUp(maxWindow);
		} catch (final Exception e) {

			return false;
		}
		return true;

	}

	private void setUp(final boolean isMaxWindow) throws Exception {
		if (isMaxWindow) {
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Any page if not loaded within 5 mins will time out
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
	}

	@Override
	public boolean setupWebDriver(String browser) {
		return setupWebDriver(browser, true, true);
	}

	@Override
	public boolean setupWebDriver(List<String> browser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WebDriver getWebDriver() {
		return this.driver;
	}

	@Override
	public String getBaseUrl() {
		return this.baseUrl;
	}

	@Override
	public void closeWebDriver(boolean screenShotNeeded) {
		if (this.driver != null) {
			if (screenShotNeeded) {
				// wait a bit before capturing screen to let redirection settle
				// down
				Utils.introduceSleep(5000);
			}
			this.driver.quit();
			this.driver = null;
		}

	}


}
