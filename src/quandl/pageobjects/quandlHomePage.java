package quandl.pageobjects;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import quandl.utils.*;

public class quandlHomePage extends LoadableComponent<quandlHomePage> implements PageObject {
	private final WebDriver webDriver;
	private final String baseUrl;

	@FindBy(how = How.ID, using = "ember671")
	private WebElement logo;

	@FindBy(how = How.ID, using = "ember656")
	private WebElement aboutUs;

	@FindBy(how = How.ID, using = "ember1927")
	private WebElement careers;

	@FindBy(how = How.XPATH, using = ".//*[@id='ember531']/nav[1]/div[3]/a[3]")
	private WebElement signin;

	@FindBy(how = How.LINK_TEXT, using = "Create one")
	private WebElement createAcc;
	
	@FindBy(how = How.XPATH, using = "//input[@autocomplete = 'username']")
	private WebElement name;

	@FindBy(how = How.XPATH, using = "//input[contains(@class, 'qa-password-confirmation')]")
	private WebElement confirnPassword;
	
	@FindBy(how = How.ID, using = "//input[@autocomplete = 'email']")
	private WebElement email;

	@FindBy(how = How.ID, using = "//input[@autocomplete = 'new-password']")
	private WebElement password;

	@FindBy(how = How.XPATH, using = "//button[@type = 'submit']")
	private WebElement signup;
	
	@FindBy(how = How.XPATH, using = ".//*/section/section/h1")
	private WebElement signupWelcome;
	
	public quandlHomePage(final WebDriver driver, final String baseUrl) {
		this.webDriver = driver;
		this.baseUrl = baseUrl;
		PageFactory.initElements(driver, this);

	}

	@Override
	protected void isLoaded() throws Error {

		int triesRemaining = 20; // allow repeated tests, in case page is slow
									// to load
		String url = this.webDriver.getCurrentUrl();
		while (!url.contains("quandl") && triesRemaining > 0) {
			Utils.introduceSleep(100);
			triesRemaining--;
			url = this.webDriver.getCurrentUrl();
		}
		if (!url.contains("quandl")) {
			throw new Error("Page not loaded");
		}

	}

	@Override
	protected void load() {
		this.webDriver.get(this.baseUrl);

	}

	@Override
	public String whoAmI() {

		return "quandlHomePage";
	}


	public void verifyUrl() {

		String url = this.webDriver.getTitle().toString();
		System.out.println("url is"+ url);
		Assert.assertTrue(url.contains("Quandl"));

	}

	public boolean verifyQuandlLogo() {
		
		Utils.pauseWebDriverUntilElementBecomeVisible(this.webDriver, this.logo,
				Utils.DEFAULT_EXPLICIT_WEBDRIVER_WAIT_FOR_TEXT_ENTRY);
		
		
		
		List<WebElement> allQuandlLinks = this.webDriver.findElements(By
				.xpath(".//a[contains(text(),'Quandl')]"));
		
		System.out.println("size of all links" + allQuandlLinks.size());
		
		for (WebElement element : allQuandlLinks) {

			String classDetails = element.getAttribute("class").toString();
			System.out.println(classDetails);
			if(classDetails.contains("quandl-logo")){

				continue;
			}
           return false;

		}
		return true;

		
	}

	public void verifyLinkToHomePage() {
		
		Utils.prepareElementAndPerformClick(this.webDriver, this.aboutUs);
		// ensure you are not on Home page
		String url = this.webDriver.getTitle().toString();
		System.out.println("page title is "+ url);
		Assert.assertTrue(url.contains("About Quandl"));

		//Click on the Quandl logo
		
		Utils.prepareElementAndPerformClick(this.webDriver, this.logo);
		//ensure now you are on Home page
		String urlHome = this.webDriver.getTitle().toString();
		System.out.println("page title  is"+ urlHome);
		Assert.assertTrue(url.contains("Quandl"));
		
	}

	public void verifyCareerinFooter() {
		
		//Utils.scrollToElement(this.careers);
		
		Utils.pauseWebDriverUntilElementBecomeVisible(this.webDriver, this.careers,
				20000);

		//Utils.prepareElementAndPerformClick(this.webDriver, this.careers);
		
		String classDetails = this.careers.getAttribute("class").toString();
		System.out.println(classDetails);
		Assert.assertTrue(classDetails.contains("footer"));
	
	}

	public void verifySignUp(String username, String password2, String email2) {
		
		String parentWindow = this.webDriver.getWindowHandle();
		Utils.prepareElementAndPerformClick(this.webDriver,this.signin);
		Utils.prepareElementAndPerformClick(this.webDriver, this.createAcc);
		
		Utils.prepareElementAndEnterText(this.webDriver, this.name, username);
		Utils.prepareElementAndEnterText(this.webDriver, this.password, password2);
		Utils.prepareElementAndEnterText(this.webDriver, this.confirnPassword, password2);
		Utils.prepareElementAndEnterText(this.webDriver, this.email, email2);
		Utils.prepareElementAndPerformClick(this.webDriver, this.signup);
		
		
		Set<String> handles = this.webDriver.getWindowHandles();

		for (String windowHandle : handles) {

			if (!windowHandle.equals(parentWindow)) {

				this.webDriver.switchTo().window(windowHandle);
				try {
					  WebElement element = this.webDriver.findElement((By) this.signupWelcome);
					  String welcome = element.getText();
					  Assert.assertTrue(welcome.contains("Welcome"));
					
				} catch (org.openqa.selenium.NoSuchElementException e) {
					throw new NoSuchElementException("Cannot find elements ");
				}
				this.webDriver.switchTo().window(parentWindow);
			}
		}

		
	}


}