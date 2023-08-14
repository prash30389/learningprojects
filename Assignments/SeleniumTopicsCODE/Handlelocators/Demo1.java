package Handlelocators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import UtilityClasses.BrowserFactory;

public class Demo1 {

	public static void main(String[] args)
	{
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		
		  driver.navigate().to("http://demo.automationtesting.in/Index.html");
		  
		 
		driver.findElement(By.cssSelector("input[placeholder*='Email id for Sign Up'")).sendKeys("prashsharma89@gmail.com");
		driver.findElement(By.xpath("//img[@id='enterimg']")).click();
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("vikas");
		driver.findElement(By.cssSelector("input[ng-model ='LastName'")).sendKeys("Randwa");
		driver.findElement(By.xpath("//textarea[@class='form-control ng-pristine ng-untouched ng-valid']")).sendKeys("anusandhan bhawan, rafi marg, new delhi-110001");
		driver.findElement(By.cssSelector("input[ng-model*='EmailAd']")).sendKeys("Bhadwa.raandsupply@gbroad.com");
		driver.findElement(By.cssSelector("input[ng-model='Phone']")).sendKeys("9999999999");
		driver.findElement(By.name("radiooptions")).click();
		driver.findElement(By.id("checkbox1")).click();
		 WebElement ele = driver.findElement(By.cssSelector("div[id='msdd']"));
		ele.click();
		Select Sel = new Select(ele);
		Sel.getOptions();
		Sel.isMultiple();
		Sel.selectByVisibleText("English");
		Sel.selectByVisibleText("Hindi");
		
//		driver.findElement(By.xpath("//a[contains(text(),'SwitchTo')]")).click();
//		driver.findElement(By.xpath("//a[contains(text(),'Frames')]")).click();
//		driver.findElement(By.xpath("//a[contains(text(),'Iframe with in an Iframe')]")).click();
//		BrowserFactory.IndexFrame(driver,1); BrowserFactory.IndexFrame(driver, 0);
//		driver.findElement(By.xpath("//div[@class='col-xs-6 col-xs-offset-5']/input")).sendKeys("SAB MADACHOD HAIN");
		
		
		
	
	}

}
