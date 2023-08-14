package handlePopUPs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class AlertPopup {

	public static void main(String[] args) throws InterruptedException 
	{
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//first type
		driver.get("https://retail.onlinesbi.com/retail/login.htm");
		String xp = "//div[@id='banner']//a[starts-with(text(),'CONTINUE')]";
		driver.findElement(By.xpath(xp)).click();
		driver.findElement(By.id("Button2")).click();
		Alert alt = driver.switchTo().alert();
		System.out.println(alt.getText());
		alt.accept();
		driver.findElement(By.id("username")).sendKeys("admin");
		
		//SecondType
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		driver.findElement(By.id("name")).sendKeys("Madarchod");
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("[id='alertbtn']")).click();
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();
		driver.findElement(By.id("confirmbtn")).click();
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().dismiss();

	}

}
