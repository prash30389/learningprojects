package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments11 {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\SeleniumContent\\selenium assigment\\SeleniumAssigmentt\\Driver\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	// --------------------Write a script to handle
	// Frame--------------------------------------
	@Test
	public void scriptOne() throws InterruptedException {
		
		driver.get("http://demo.automationtesting.in/Frames.html");

		WebElement frameElement = driver.findElement(By.xpath("//iframe[@name='SingleFrame']"));
		
		driver.switchTo().frame(frameElement);
		
		driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Ye galiya ye kooche");
		
		driver.switchTo().defaultContent();
		
		driver.findElement(By.linkText("Iframe with in an Iframe")).click();
		
		
		driver.switchTo().frame(1);
		
		driver.switchTo().frame(0);
		
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@class='container']//input[@xpath='1']")).sendKeys("Umeeed se lambe bade ocche");
		
		driver.switchTo().defaultContent();
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
