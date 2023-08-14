package HandleTypeOfWebElements;
/**
 * @author prashant
 */

import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import UtilityClasses.BrowserFactory;

public class HandleDifferentWebElementParts {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 20);

		driver.get("https://www.spicejet.com/");
		// Dynamic dropdown Handle with WebDriver
		driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).click();
		driver.findElement(By.xpath("//a[@value='BLR']")).click();
		// driver.findElement(By.xpath("(//a[@value='MAA'])[2]")).click();
		// indexing not recommennded for good x-path
		String xp = "//div[@id='glsctl00_mainContent_ddl_destinationStation1_CTNR'] //a[@value='MAA']";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp))).click();

		// handle ui calender
		String cssEle = ".ui-state-default.ui-state-highlight.ui-state-active";
		driver.findElement(By.cssSelector(cssEle)).click();

		String verifyEle = driver.findElement(By.id("Div1")).getAttribute("style");
		System.out.println(verifyEle);
		// driver.findElement(By.name("ctl00$mainContent$view_date2")).isEnabled();
		driver.findElement(By.cssSelector("#ctl00_mainContent_rbtnl_Trip_1")).click();
		System.out.println(driver.findElement(By.id("Div1")).getAttribute("style"));

		if (verifyEle.contains("1")) {
			System.out.println("Its Enabled");
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		// static Dropdown handle with select class
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("divpaxinfo"))).click();
		System.out.println(driver.findElement(By.id("divpaxinfo")).getText());
		Select s = new Select(driver.findElement(By.name("ctl00$mainContent$ddl_Adult"))); 																		
		// s.selectByValue("2");
		// s.selectByIndex(4);
		s.selectByVisibleText("3");
		s.getAllSelectedOptions();

		// handle checkbox
		WebElement CheckBxEle = driver.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']"));
		Assert.assertFalse(CheckBxEle.isSelected());
		System.out.println(CheckBxEle.isSelected());
		CheckBxEle.click();
		Assert.assertTrue(CheckBxEle.isSelected());
		System.out.println(CheckBxEle.isSelected());
		driver.findElements(By.cssSelector("input[type='checkbox']")); // count checkboxes
		// Auto-suggestive dropdown with keys
		/*
		 * driver.get("https://www.makemytrip.com"); // URL in the browser
		 * driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		 * driver.findElement(By.id("fromCity")).click(); WebElement source =
		 * driver.findElement(By.xpath("//div[@role='combobox']//input"));
		 * source.sendKeys("MUM"); source.sendKeys(Keys.ARROW_DOWN);
		 * source.sendKeys(Keys.ENTER); Actions act = new Actions(driver);
		 * WebElement ele = driver.findElement(By.id("toCity"));
		 * act.moveToElement(ele).click().perform(); WebElement destination =
		 * driver.findElement(By.cssSelector("input[autocomplete='off']")); //
		 * destination.clear(); destination.sendKeys("DEL");
		 * destination.sendKeys(Keys.ARROW_DOWN);
		 * destination.sendKeys(Keys.ARROW_DOWN);
		 * destination.sendKeys(Keys.ENTER);
		 */
		
		// old another type of dropdown
		 System.out.println(driver.findElement(By.id("divpaxinfo")).getText()) ;
		  for(int i=1;i<5;i++){
		  driver.findElement(By.id("hrefIncAdt")).click();}
		  int i=1;
		 while(i<5){driver.findElement(By.id("hrefIncAdt")).click();//4 times
		 i++;}
		 
		 driver.findElement(By.id("btnclosepaxoption")).click();
		 Assert.assertEquals(driver.findElement(By.id("divpaxinfo")).getText(), "5 Adult");
		 System.out.println(driver.findElement(By.id("divpaxinfo")).getText());
		 

		String url = "https://rahulshettyacademy.com/AutomationPractice/";
		driver.get(url);
		List<WebElement> CheckBoxes = driver.findElements(By.cssSelector("input[id*='checkBoxOption']"));
		int Size = CheckBoxes.size();
		System.out.println(Size);
		for (int j = 0; j < CheckBoxes.size(); j++) {
			// WebElement checkbox =
			// driver.findElement(By.xpath("//input[contains(@id,'checkBoxOption')]"));
			WebElement ele = CheckBoxes.get(j);
			ele.click();
			boolean bool2 = ele.isSelected();
			Assert.assertTrue(bool2);
			System.out.println(bool2);
		}
	}
}