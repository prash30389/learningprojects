package AssignmentQspiders;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments1 {
	
	WebDriver driver;	
	@BeforeMethod
	public void setUp()
	{
		
		
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumContent\\selenium assigment\\SeleniumAssigment\\driver\\chromedriver.exe");
	
		 driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		
	}	
	
	//-------------------------write a script to verify that login page is loaded within 3 sec---------------------------
	@Test
	public void scriptOne()
	{
		driver.get("http://127.0.0.1/login.do");
		System.out.println("On script end" + new Date());
		
		
		
	}
	
	
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}

}
