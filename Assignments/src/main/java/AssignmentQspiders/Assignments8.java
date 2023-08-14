package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments8 {

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

	//--------------------Write a script to handle alert--------------------------------------
	@Test
	public void scriptOne() {
		driver.get("http://demo.automationtesting.in/Alerts.html");
		driver.findElement(By.cssSelector("div[id='OKTab']>button")).click();
		driver.switchTo().alert().accept();
		driver.findElement(By.cssSelector("ul[class*='nav nav-tabs nav-stacked']>li:nth-child(2)")).click();
		
		driver.findElement(By.cssSelector("div[id='CancelTab']>button")).click();
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();
		
		driver.findElement(By.cssSelector("ul[class*='nav nav-tabs nav-stacked']>li:nth-child(3)")).click();
		
		driver.findElement(By.cssSelector("div[id='Textbox']>button")).click();
		
		driver.switchTo().alert().sendKeys("I am cute");
		
		driver.switchTo().alert().accept();
		
		
		
		
		}

	

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
