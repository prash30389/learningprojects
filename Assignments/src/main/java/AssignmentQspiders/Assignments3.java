package AssignmentQspiders;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments3 {
	
	WebDriver driver;	
	public boolean isAlertPresent(){
		 try{
		  driver.switchTo().alert();
		  return true;
		 }catch(NoAlertPresentException ex){
		  return false;
		 }
		}
	
	

	
	
	@BeforeMethod
	public void setUp()
	{
		
		
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumContent\\selenium assigment\\SeleniumAssigment\\driver\\chromedriver.exe");
	
		 driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start:" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		
	}	
	
	//-------------------------write a script to verify that login page is loaded within 3 sec---------------------------
	@Test
	public void scriptOne()
	{
		driver.get("http://127.0.0.1/login.do");
		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("pwd")).sendKeys("manager");
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.xpath("//div[text()='Users']")).click();
		driver.findElement(By.xpath("//div[contains(@id,'ext-comp-1002')]/span[text()='Create New User']")).click();
		driver.findElement(By.name("username")).sendKeys("vikas");
		driver.findElement(By.name("passwordText")).sendKeys("Admin123$");
		driver.findElement(By.name("passwordTextRetype")).sendKeys("Admin123$");
		driver.findElement(By.name("firstName")).sendKeys("Manoj");
		driver.findElement(By.name("lastName")).sendKeys("Kumar");
		driver.findElement(By.name("email")).sendKeys("abc@gmail.com");
		driver.findElement(By.xpath("//td[@class='formbuttonpane']/input[contains(@value,'Create User')]")).click();		
		driver.findElement(By.xpath("//table[contains(@class,'userListTable')]//tr[3]//a")).click();
		
		driver.findElement(By.xpath("//input[@value='Delete This User']")).click();
		Alert alt=driver.switchTo().alert();
		String str=alt.getText();
		
		
		//-----------------------------First verification---------------------------------------------
		if(str.equals("Are you sure you want to delete this user account?"))
		{
			System.out.println("Script is passed");
		}
		
		else
		{
			
			System.out.println("Script failed");
		}
		
		
		//------------------------------Second verification--------------------------------------------
		
		if(isAlertPresent())
		{
			System.out.println("Alert is displayed");
		}
		
		else
		{
			System.out.println("Alert is not displayed");
		}
		
		
		//-----------------------------------To print the message in popup and click on cancel button--------------------------
		
		System.out.println(alt.getText());
		alt.dismiss();
		
		
		
		
		

		
		
		
	}
	
	
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}

}
