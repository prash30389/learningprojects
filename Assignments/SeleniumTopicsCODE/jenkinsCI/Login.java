package jenkinsCI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Login {
	public static WebDriver driver;

	@BeforeSuite
	public void setUp() {

		driver = new FirefoxDriver();
	}

	@AfterSuite
	public void tearDown() {
		System.out.println("Quitting the driver");
		driver.quit();
	}

	@Test(priority = 1)
	public void navigateURL() {

		driver.get("http://google.com");

	}

	@Test(priority = 2)
	public void validateTitle() {

		Assert.assertEquals("Gmail", driver.getTitle());
	}
}
