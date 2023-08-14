package handleRealTImeExercises;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import UtilityClasses.BrowserFactory;

public class E2ETestScript {

	public static void main(String[] args) {
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.spicejet.com/");
		driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).click();
		driver.findElement(By.xpath("//a[@value='BLR']")).click();
		String xp = "//div[@id='glsctl00_mainContent_ddl_destinationStation1_CTNR'] //a[@value='MAA']";
		driver.findElement(By.xpath(xp)).click();
		String cssEle = ".ui-state-default.ui-state-highlight.ui-state-active";
		driver.findElement(By.cssSelector(cssEle)).click();
		if (driver.findElement(By.id("Div1")).getAttribute("style").contains("0.5")) {
			System.out.println("Its disabled");
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		driver.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).click();
		driver.findElement(By.id("divpaxinfo")).click();
		Select s = new Select(driver.findElement(By.name("ctl00$mainContent$ddl_Adult")));
		s.selectByVisibleText("3");
		driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency")).click();
		String xp2 = "//select[@id='ctl00_mainContent_DropDownListCurrency']/option[contains(text(),'USD')]";
		driver.findElement(By.xpath(xp2)).click();
		driver.findElement(By.cssSelector("input[type='submit']")).click();
	}

}
