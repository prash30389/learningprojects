package HandleTypesOfHandling;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilityClasses.BrowserFactory;

public class FrameHandling 
{
public static void main(String[] args)
	{
	
	WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	driver.get("https://selenium.dev/selenium/docs/api/java/index.html");
	
	driver.switchTo().frame("packageListFrame");
	String xp = "//ul[@title='Packages']//a[text()='com.thoughtworks.selenium']";
	driver.findElement(By.xpath(xp)).click();
	
	driver.switchTo().defaultContent();
	
	driver.switchTo().frame("packageFrame");
	driver.findElement(By.linkText("DefaultSelenium")).click();
	
	driver.switchTo().defaultContent();
	
	//driver.switchTo().frame("classFrame");
					//or
	WebElement frame3 = driver.findElement(By.xpath("//frame[@name='classFrame']"));
	driver.switchTo().frame(frame3);
	driver.findElement(By.linkText("WebDriverBackedSelenium")).click();
	
	
		
	}
}
