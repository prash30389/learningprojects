package POM.Encapsulation;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import UtilityClasses.BrowserFactory;

public class LoginPageTest {

	WebDriver driver;

	@Test
	public void ValidLogin() {
		driver = BrowserFactory.LaunchBrowser("firefox");
		driver.get("http://127.0.0.1/login.do");
		LoginPage lp = new LoginPage(driver);
		lp.getUnTxtBx().sendKeys("admin");
		lp.getPwdTxtBx().sendKeys("manager");
		lp.getLoginBtn().click();
	}

	@Test
	public void Invalidlogin() {
		driver = BrowserFactory.LaunchBrowser("firefox");
		driver.get("http://127.0.0.1/login.do");
		LoginPage lp = new LoginPage(driver);
		lp.getUnTxtBx().sendKeys("admin");
		lp.getPwdTxtBx().sendKeys("12345");
		lp.getLoginBtn().click();
	}

}
