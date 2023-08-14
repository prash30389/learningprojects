package POM.Encapsulation;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import UtilityClasses.BrowserFactory;

public class SeleniumPageTest 
{
@Test
public void Script()
{
	WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
	driver.get("https://selenium.dev/");
	
//without POM
	SeleniumPage sp = new SeleniumPage(driver);
	sp.getDwnloadEle().click();
	sp.getProjectEle().click();
	sp.getSupportEle().click();
	sp.getBlogEle().click();
	
	//with POM
	SeleniumPagePOM spp = new SeleniumPagePOM(driver);
	spp.getDwnloadEle().click();
	spp.getProjectEle().click();
	spp.getSupportEle().click();
	spp.getBlogEle().click();
}
}