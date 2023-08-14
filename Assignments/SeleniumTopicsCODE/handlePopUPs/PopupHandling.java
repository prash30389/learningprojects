package handlePopUPs;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class PopupHandling 
{

	public static void main(String[] args) 
	{
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.get("https://retail.onlinesbi.com/retail/login.htm");
		String xp = "//div[@id='banner']//a";
		driver.findElement(By.xpath(xp)).click();
		driver.findElement(By.id("Button2")).click();
		
		Alert Alt = driver.switchTo().alert();
		String AlertMsg= Alt.getText();
		System.out.println(AlertMsg);
		
		//driver.get("http://admin:manager@127.0.0.1/login.do");		//to handle page load popup
		
		Alt.accept();//click on ok
		
		

	}

}
