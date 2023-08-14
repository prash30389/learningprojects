package browserUtility;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;


public class Browser {
	
	public static WebDriver driver;
	public static final String USERNAME = "rajiv30389";
	public static final String ACCESS_KEY = "843e6f11-ebfc-49c0-980b-2507741214c0";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

	public static void SetUp() throws MalformedURLException {
		

			DesiredCapabilities caps = DesiredCapabilities.chrome();
			MutableCapabilities sauceOptions = new MutableCapabilities(caps);
	
			  ChromeOptions browserOptions = new ChromeOptions();
			  browserOptions.setExperimentalOption("w3c", true);
			  browserOptions.setCapability("platformName", "Windows 7");
			  browserOptions.setCapability("browserVersion", "57.0");
			  browserOptions.setCapability("sauce:options", sauceOptions);

		driver = new RemoteWebDriver(new URL(URL), sauceOptions);
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	public static void tearDown()
	{
	
		driver.quit();
	}
}