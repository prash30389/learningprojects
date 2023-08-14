package BASIC1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirstProgram 
{
	static WebDriver driver;
	public static void main(String[] args) 
	{
		System.setProperty("webdriver.gecko.driver", "DriversExE\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("http://www.facebook.com");
		driver.findElement(By.id("email")).sendKeys("madarchod facebook");
		//driver.close();
	}

}
