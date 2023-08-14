package TestNGExercises;

import org.testng.annotations.Test;

import UtilityClasses.BrowserFactory;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;

public class DataProviderANDParameter 
{
	WebDriver driver;
	
	@BeforeMethod
	@Parameters({"browser", "baseurl"}) // we use this annotation to read the parameter value from testng suite file by using parameter name
	public void PreConditionBrowser(String browserName, String url)
	{
		System.out.println(browserName+" "+url); // when we need to execute same script in multiple browsers
		driver = BrowserFactory.LaunchBrowser(browserName);
		driver.navigate().to(url); 
	  }
	
	
	
	  @DataProvider // it used to provide the multiple data to execute a script
	  public Object[][] getData() 
	  { 
		  return new Object[][]	
				  {{"abc","test123"},{"def","test456"}};
	  
	  }
	 
	 
	
	
	  @Test( dataProvider="getData", priority =2, dependsOnMethods=("getTitle"), enabled=true)
	  
	  public void MainActionValid(String FirstData, String SecondData) 
	  {
	  System.out.println("my first script");
	  driver.findElement(By.xpath("//input[@id='email']")).sendKeys(FirstData);
	  driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(SecondData);
	  }
	 
	
	@Test(priority =1)
	public void getTitle()
	{
		System.out.println(driver.getTitle());
	}
	
	  @AfterMethod
	  public void PostCondition() 
	  {
		  System.out.println("i will execute last");
		  driver.close();
		  
		  
	  }
}
//enabled=true,invocationCount =1