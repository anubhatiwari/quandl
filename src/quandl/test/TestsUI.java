package quandl.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import quandl.utils.*;
import quandl.pageobjects.*;

public class TestsUI extends PreTestSetupImpl implements AbstractFunctions {

	private final ScenarioCacheManager scenarioCacheManager = ScenarioCacheManager
			.getInstance();
	final Utils Utils = new Utils();
	quandlHomePage quandlHomePage = null;

	@Override
	@Before
	public void setEnvironment() throws Exception {
		scenarioCacheManager.clear();

		final String url = "https://www.quandl.com/"
				, username="abcd",password="abcdefg",browser="Chrome",email="abc@gmail.com";
				
		login(url, username, password, browser,email);
	}

	public void login(final String url, final String userName,
			final String password, final String browser, final String email) throws Exception {

		scenarioCacheManager.store(Cache.USER, userName);
		scenarioCacheManager.store(Cache.PASSWORD, password);
		scenarioCacheManager.store(Cache.BASE_URL, url);
		scenarioCacheManager.store(Cache.BROWSER, browser);
		scenarioCacheManager.store(Cache.EMAIL, email);
		
		if (this.getWebDriver() != null) {
			this.closeWebDriver(false);
		}
		setup(browser);
		setupbaseUrl(url);

	}

	@Override
	public void setup(final String browser) throws Exception {

		setupWebDriver(browser);
	}
    
	
	@Test
	public void VerfiyQuandlLogo() throws Exception {
	
	quandlHomePage= loadquandlHomePage();

	final boolean result = quandlHomePage.verifyQuandlLogo();
		
	if (result == true) {
		
		quandlHomePage.verifyLinkToHomePage();
			}
}

	
	@Test
	public void VerfiyCareersLinkInFooter() throws Exception {
	
	quandlHomePage= loadquandlHomePage();

	quandlHomePage.verifyCareerinFooter();
	}
	
	
	@Test
	public void VerifySignUP() throws Exception {
	
		String username = scenarioCacheManager.retrieveAsString(Cache.USER);
		String password = scenarioCacheManager.retrieveAsString(Cache.PASSWORD);
		String email = scenarioCacheManager.retrieveAsString(Cache.EMAIL);
		
	quandlHomePage= loadquandlHomePage();

	quandlHomePage.verifySignUp(username,password,email);
	}
	

	public quandlHomePage loadquandlHomePage() throws Exception {

		quandlHomePage quandlHomePage = null;
		try {
			quandlHomePage = new quandlHomePage(getWebDriver(), getBaseUrl()).get();

		} catch (final Exception exp) {
			throw new Exception("Failed to load quandl home page");
		}
		scenarioCacheManager.store(Cache.QUANDLHOME_PAGE, quandlHomePage);


		Assert.assertTrue(quandlHomePage.whoAmI().contains("quandl"));

		quandlHomePage.verifyUrl();
		return quandlHomePage;
		
		
	}

	@After
	public void clean() throws Exception {

		this.closeWebDriver(false);
	}

	@Override
	public void login(String baseUrl, String userName, String password, String browser) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
