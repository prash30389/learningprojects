package AssignmentQspiders;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments4 {
	
	WebDriver driver;	
	@BeforeMethod
	public void setUp()
	{
		
		
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumContent\\selenium assigment\\SeleniumAssigment\\driver\\chromedriver.exe");
	
		 driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start:" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		
	}	
	
	//-------------------------write a script to upload a file---------------------------
	@Test
	public void scriptOne() throws IOException
	{
		driver.get("http://demo.automationtesting.in/Register.html");
		WebElement web=driver.findElement(By.xpath("//input[@id='imagesrc']"));
		Actions act= new Actions(driver);
		act.moveToElement(web).click().build().perform();
		Runtime.getRuntime().exec("C://Users//vikas//Desktop//current.exe");		
		
		
	}
	
	
	
	@AfterMethod
	public void tearDown()
	{
		//driver.quit();
	}

}
