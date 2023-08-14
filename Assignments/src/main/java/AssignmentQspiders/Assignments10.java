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
public class Assignments10 {

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
	// window pop up--------------------------------------
	@Test
	public void scriptOne() {
		String parent = driver.getWindowHandle();
		driver.get("http://demo.automationtesting.in/Windows.html");

		driver.findElement(By.cssSelector("div[class*='tab-content']>div:nth-child(1)>a")).click();

		String child1 = driver.getWindowHandle();

		driver.switchTo().window(child1);
		
		System.out.println(driver.getTitle());
		//driver.close();
		driver.switchTo().window(parent);
		driver.findElement(By.cssSelector("ul[class*='nav nav-tabs nav-stacked']>li:nth-child(2)")).click();

		driver.findElement(By.cssSelector("div[id='Seperate'] button")).click();

		String child2 = driver.getWindowHandle();

		driver.switchTo().window(child2);
		System.out.println(driver.getTitle());
		//driver.close();
		driver.switchTo().window(parent);

		driver.findElement(By.cssSelector("ul[class*='nav nav-tabs nav-stacked']>li:nth-child(3)")).click();
		
		driver.findElement(By.cssSelector("div[id='Multiple'] button")).click();
		String child3 = driver.getWindowHandle();

		driver.switchTo().window(child3);
		
		System.out.println(driver.getTitle());
		//driver.close();
		driver.switchTo().window(parent);

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
