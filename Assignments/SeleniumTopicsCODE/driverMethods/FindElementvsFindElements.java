package driverMethods;

import java.util.List;

import org.openqa.selenium.*;

import UtilityClasses.BrowserFactory;

public class FindElementvsFindElements 
{
//Was to see the difference between find element() and find elements() method ??
	
	public static void main(String[] args) 
	{
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.get("file:///C:/Users/prashant/Desktop/Interview Questions/NSSW22.html");
			List<WebElement> Wbs = driver.findElements(By.tagName("span")); // used to find bulk web elements.
			boolean flag = Wbs.isEmpty();
			if (flag)
			{
				System.out.println("empty list : " + Wbs.size());
			}
			else
			{
				System.out.println("list is not empty : " + Wbs.size());
			}
	
			try
			{
				driver.findElement(By.tagName("span"));		// if F(E) fails, it gives nosuchelementexception.
				System.out.println("element Exist");
			}
	
			catch(NoSuchElementException Exception)
			{
				System.out.println("element not exist in html doc");
			}
	}

}
