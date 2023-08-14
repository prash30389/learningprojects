package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments15 {

	WebDriver driver;// ----------------------for click on particular web
	// element-----------------------------------------

	public static void sendKeysByJS(WebElement element, WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].value='h'", element);
	}

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\SeleniumContent\\selenium assigment\\SeleniumAssigmentt\\Driver\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	// --------------------Write a script to Handle DatePicker
	// widgets---------------------------
	@Test
	public void scriptOne() throws InterruptedException {

		driver.get("http://demo.automationtesting.in/Datepicker.html");
		// driver.findElement(By.xpath("//div[contains(@class,'form-group')][2]/div[1]/input")).click();

		// -----------------------------------List out all the lement and click on
		// 14---------------------------------

//		List<WebElement> dateElmList = driver
//				.findElements(By.xpath("//table[contains(@class,'ui-datepicker-calendar')]//td/a"));
//
//		int size = dateElmList.size();
//
//		for (int i = 0; i < size; i++) {
//
//			if (dateElmList.get(i).getText().equals("14")) {
//				
//				dateElmList.get(i).click();
//				break;
//
//			}

		// -------------------------------List out all the lement and click on 14
		// -------------------------------

		driver.findElement(By.xpath("//div[contains(@class,'form-group')][2]/div[3]/input")).click();

		WebElement monthElement = driver
				.findElement(By.xpath("//div[contains(@class,'datepick-month-header')]/select[1]"));

		Select s = new Select(monthElement);
		s.selectByVisibleText("August");

		WebElement yearElement = driver
				.findElement(By.xpath("//div[contains(@class,'datepick-month-header')]/select[2]"));

		Select s1 = new Select(yearElement);
		s1.selectByVisibleText("2026");

		List<WebElement> dateElList = driver.findElements(By.xpath("//div[contains(@class,'datepick-popup')]//tr//a"));
		int size = dateElList.size();
		
		for (int i = 0; i < size; i++) {
			if (dateElList.get(i).getText().equals("14")) {
	
				dateElList.get(i).click();
			break;
			
		}
		}

	}

	@AfterMethod
	public void tearDown() {
		// driver.quit();
	}

}
