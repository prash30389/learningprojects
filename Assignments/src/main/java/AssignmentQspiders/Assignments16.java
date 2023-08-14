package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments16 {

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

	// --------------------Write a script to Handle slider
	// widgets---------------------------
	@Test
	public void scriptOne() throws InterruptedException {

		driver.get("http://demo.automationtesting.in/Slider.html");
		WebElement slider = driver.findElement(By.xpath("//div[@id='slider']/a"));

		Actions action = new Actions(driver);
		action.clickAndHold(slider).moveByOffset(824, 304).release().build().perform();
		slider.click();

	}

	@AfterMethod
	public void tearDown() {
		// driver.quit();
	}

}
