package Handlelocators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class ImportantLocaters 
{

	public static void main(String[] args) throws InterruptedException 
	{
	System.setProperty("webdriver.chrome.driver", "./ExeFiles/chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	
	
	
	
	
		//Use X path to input values : 
			driver.get("http://demo.automationtesting.in/Index.html");
			driver.findElement(By.cssSelector("input[placeholder*='Email id for Sign Up'")).sendKeys("prashsharma89@gmail.com");
				driver.findElement(By.xpath("//img[@id='enterimg']")).click();
				driver.findElement(By.xpath("//textarea[@class='form-control ng-pristine ng-untouched ng-valid']")).sendKeys("anusandhan bhawan, rafi marg, new delhi-110001");
				driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("vikas");
				
		//use CSS Selector
			driver.findElement(By.cssSelector("input[ng-model ='LastName'")).sendKeys("Randwa");
			driver.findElement(By.cssSelector("input[ng-model*='EmailAd']")).sendKeys("Bhadwa.raandsupply@gbroad.com");
			driver.findElement(By.cssSelector("input[ng-model='Phone']")).sendKeys("9999999999");
	Thread.sleep(10000);
		//HOW TO USE MULTIPLE ATTRIBUTE IN X-PATH ?
			driver.get("file:///C:/Users/prashant/Desktop/Automation Files/NSSW22.html");
			driver.findElement(By.xpath("//input[@name='z' or @class='female']")).click();
			
			
			
			driver.findElement(By.name("pwd")).sendKeys("madarchod@12345");		//Name 
		driver.findElement(By.className("MyCheckbox"));							//ClassName 
		driver.findElement(By.id("user")).sendKeys("MahaMadarchod");			//id
		driver.findElement(By.tagName("a")).click();							//tag name
		driver.findElement(By.linkText("documentation")).click();				//linktext
		
	}

}
	