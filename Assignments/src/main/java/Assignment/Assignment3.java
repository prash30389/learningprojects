package Assignment;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import UtilityClasses.BrowserFactory;

public class Assignment3 {

	public static void main(String[] args) {
		
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.cleartrip.com/");
		WebElement fromEle = driver.findElement(By.id("FromTag"));
		fromEle.click();
		fromEle.sendKeys("MUM");
		fromEle.sendKeys(Keys.ARROW_DOWN);
		fromEle.sendKeys(Keys.ENTER);
		WebElement toEle = driver.findElement(By.id("ToTag"));
		toEle.click();
		toEle.sendKeys("chen");
		toEle.sendKeys(Keys.ARROW_DOWN);
		toEle.sendKeys(Keys.ENTER);
		driver.findElement(By.id("DepartDate")).click();
		driver.findElement(By.xpath("//td/a[contains(text(),'21')]")).click();
		String Adult = "//select[@id='Adults']/option[contains(text(),'5')]";
		driver.findElement(By.xpath(Adult)).click();
		String Childrens = "//select[@id='Childrens']/option[contains(text(),'4')]";
		driver.findElement(By.xpath(Childrens)).click();
		driver.findElement(By.xpath("//a[@id='MoreOptionsLink']")).click();
		String Economy = "//select[@id='Class']/option[contains(text(),'Premium Economy')]";
		driver.findElement(By.xpath(Economy)).click();
		WebElement airlineName = driver.findElement(By.cssSelector("input[id='AirlineAutocomplete']"));
		airlineName.clear();
		airlineName.sendKeys("Indigo");
		driver.findElement(By.id("SearchBtn")).click();

		String css = "div[id='homeErrorMessage']";
		String actualMsg = driver.findElement(By.cssSelector(css)).getText();
		System.out.println(actualMsg);
		String homeErrorMessageEle = "Sorry, but we can't continue until you fix a few things. We've marked everything that needs fixing in RED. ";
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actualMsg, homeErrorMessageEle);
		Reporter.log("Incomplete Details Verified", true);

	}

}
