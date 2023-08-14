package Concepts;

import java.net.MalformedURLException;

import java.net.URL;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SauceLabsCloudTestCaseRun {

	public static final String USERNAME = "rajiv30389";

	public static final String ACCESS_KEY = "843e6f11-ebfc-49c0-980b-2507741214c0";

	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

	public static void main(String[] args) throws MalformedURLException {

		DesiredCapabilities caps = DesiredCapabilities.chrome();
		//DesiredCapabilities caps = DesiredCapabilities.firefox();
		MutableCapabilities sauceOptions = new MutableCapabilities(caps);

		
		  ChromeOptions browserOptions = new ChromeOptions();
		  browserOptions.setExperimentalOption("w3c", true);
		  browserOptions.setCapability("platformName", "Windows 7");
		  browserOptions.setCapability("browserVersion", "57.0");
		  browserOptions.setCapability("sauce:options", sauceOptions);
		 
		
			/*
			 * sauceOptions.setCapability("platform", "macOS 10.14");
			 * sauceOptions.setCapability("version", "37.0");
			 */

		WebDriver driver = new RemoteWebDriver(new URL(URL), sauceOptions);
		driver.get("http://google.com");
		System.out.println(driver.getTitle());
		System.out.println( driver.manage().getCookies());

	}
}
