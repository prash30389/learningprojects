package handleRealTImeExercises;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilityClasses.BrowserFactory;

public class Scope {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.rahulshettyacademy.com/AutomationPractice/");
		
		System.out.println(driver.findElements(By.tagName("a")).size());
		//3-/WAS to find total no. of links in webpage
		WebElement FooterDriver = driver.findElement(By.cssSelector("*[id='gf-BIG']"));
		System.out.println(FooterDriver.findElements(By.tagName("a")).size());
		WebElement coloumndriver=FooterDriver.findElement(By.xpath("//table/tbody/tr/td[1]/ul"));
		int numberOfLinks = coloumndriver.findElements(By.tagName("a")).size();
		System.out.println(numberOfLinks);
		//4- click on each link in the coloumn and check if the pages are opening-
		for(int i =0;i<numberOfLinks;i++)
		{
			String clickonlinkTab=Keys.chord(Keys.CONTROL,Keys.ENTER);
			coloumndriver.findElements(By.tagName("a")).get(i).sendKeys(clickonlinkTab);
		}
		Set<String> abc=driver.getWindowHandles();//4
		Iterator<String> it=abc.iterator();
		while(it.hasNext())
		{
			
		   driver.switchTo().window(it.next());
		   System.out.println(driver.getTitle());
		
		}
		
		
		
	}

}
