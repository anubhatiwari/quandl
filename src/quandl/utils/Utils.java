package quandl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {

	public static final int DEFAULT_EXPLICIT_WEBDRIVER_WAIT_FOR_TEXT_ENTRY = 10000;
	public static final int DEFAULT_EXPLICIT_WEBDRIVER_WAIT_FOR_CLICK = 10000;

	public static WebElement pauseWebDriverUntilElementBecomeVisible(
			final WebDriver driver, final WebElement webElement,
			final int timeOutInMilliSeconds) {
		return (new WebDriverWait(driver, timeOutInMilliSeconds / 1000))
				.until(ExpectedConditions.visibilityOf(webElement));
	}

	public static void scrollToElement(final WebElement element) {
		((Locatable) element).getCoordinates().inViewPort();
	}

	public static void introduceSleep(int inMilliSec) {
		try {
			Thread.sleep(inMilliSec);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void prepareElementAndEnterText(final WebDriver driver,
			final WebElement element, final String textToEnter) {
		Utils.pauseWebDriverUntilElementBecomeVisible(driver, element,
				Utils.DEFAULT_EXPLICIT_WEBDRIVER_WAIT_FOR_TEXT_ENTRY);
		scrollToElement(element);
		element.clear();
		element.sendKeys(textToEnter);
	}

	public static void prepareElementAndPerformClick(final WebDriver driver,
			final WebElement element) {
		Utils.pauseWebDriverUntilElementBecomeVisible(driver, element,
				Utils.DEFAULT_EXPLICIT_WEBDRIVER_WAIT_FOR_CLICK);
		scrollToElement(element);
		element.click();
	}

	/**
	 * Get the provided key value from specified property file if exists. If key
	 * doesn't exist then it return null as value.
	 * 
	 * @param propertyFileLocation
	 *            the name of the property file with location.
	 * @param key
	 *            the name of the key
	 * @return the value of the provided key. null if key doesn't exist.
	 */
	public String getValue(final String propertyFileLocation, final String key) {
		// If key is empty or null then we want to pass same value what
		// specified in feature file.
		if (isEmptyOrBlank(key)) {
			return key;
		}

		String value = null;

		final Properties prop = new Properties();
		FileInputStream propFileInputStream;
		try {
			propFileInputStream = new FileInputStream(new File(
					propertyFileLocation));
			prop.load(propFileInputStream);
			value = prop.getProperty(key);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		if (null == value) {
			value = key;
		}
		return value;
	}

	public boolean isEmptyOrBlank(final String string) {
		if (string == null || string.length() == 0) {
			return true;
		}
		for (int index = 0; index < string.length(); index++) {
			if (!Character.isWhitespace(string.charAt(index))) {
				return false;
			}
		}
		return true;
	}

	public static void prepareElementtoSelectFromDropDown(
			final WebDriver driver, final WebElement element, final String value) {

		Utils.prepareElementAndPerformClick(driver, element);
		final Select dropdown = new Select(element);
		dropdown.selectByValue(value);
	}

	public static void prepareElementtoSelectFromDropDownWithVisibleText(
			final WebDriver driver, final WebElement element, final String value) {

		Utils.prepareElementAndPerformClick(driver, element);
		final Select dropdown = new Select(element);
		dropdown.selectByVisibleText(value);
	}

}
