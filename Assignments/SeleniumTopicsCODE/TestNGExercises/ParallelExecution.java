package TestNGExercises;

import org.testng.annotations.Test;
import UtilityClasses.BrowserFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class ParallelExecution 
{
	WebDriver driver;
  @Test
  public void Login()
  {
	  driver.findElement(By.name("username")).sendKeys("admin");
	  driver.findElement(By.name("pwd")).sendKeys("manager" + Keys.ENTER);
	  System.out.println("login script is done");
  
  }
  @BeforeMethod
  @Parameters({"browser", "baseurl"})
	public void PreConditionBrowser(String browserName, String url)
	{
		System.out.println(browserName+" "+url);
			driver = BrowserFactory.LaunchBrowser(browserName);
			driver.navigate().to(url); 
	}
  }
