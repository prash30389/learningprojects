package AssignmentQspiders;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments2 {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\SeleniumContent\\selenium assigment\\SeleniumAssigment\\driver\\chromedriver.exe");

		driver = new ChromeDriver();
	//	driver.manage().window().maximize();
		

	}

	// -------------------------write a script to login and logout and script as to
	// be synchronized without using implict and explicit
	// wait---------------------------
	@Test
	public void scriptOne() {
		driver.get("http://127.0.0.1/login.do");
		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("pwd")).sendKeys("manager");
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
