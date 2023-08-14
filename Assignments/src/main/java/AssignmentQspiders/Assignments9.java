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
public class Assignments9 {

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
	// alert--------------------------------------
	@Test
	public void scriptOne() {
		driver.get("http://laptop-rdmatuke/login.do");
		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("pwd")).sendKeys("manager");

		driver.findElement(By.id("loginButton")).click();

		driver.findElement(By.xpath("//div[text()='Tasks']")).click();
		driver.findElement(By.xpath("//a[text()='Projects & Customers']")).click();
		driver.findElement(By.cssSelector("input[value='Create New Customer']")).click();

		driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("vikas");

		driver.findElement(By.name("createCustomerSubmit")).click();

		driver.findElement(By.xpath("//td[contains(@class,'selectCustomer')]/input")).click();

		driver.findElement(By.cssSelector("input[value='Delete Selected']")).click();

		String hiddenDivisionPopupTxt = driver.findElement(By.xpath("//td[@id='deleteMessage']")).getText();
		
		System.out.println(hiddenDivisionPopupTxt);
		
		Assert.assertEquals(hiddenDivisionPopupTxt, "You are about to delete a customer. Please confirm.","Hidden dission popup is not displayed");
		
		driver.findElement(By.xpath("(//input[@value='Cancel'])[position()=3]")).click();
		
		
		
		System.out.println("Task completed");
		
		
		
		

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
