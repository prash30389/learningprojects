package HandleTypesOfHandling;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

import UtilityClasses.BrowserFactory;

public class SeleniumScreenshot 
{
	public static void main(String[] args) throws Exception 
	{
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().window().maximize();
		driver.get("http://www.facebook.com");
		BrowserFactory.Screenshot(driver, "screenshot2");
		
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		 
			File srcFile= ts.getScreenshotAs(OutputType.FILE);
			File destFile = new File("./Screenshots/screenshot1.png");
			
			try 
			{
				Files.copy(srcFile, destFile);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		 
		

	}

}
