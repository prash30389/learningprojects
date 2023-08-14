package driverMethods;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilityClasses.BrowserFactory;

public class HandleBulkElements {

	public static void main(String[] args) 
	{
	//We use FindElements()	method
		WebDriver driver = BrowserFactory.LaunchBrowser("Chrome");
		
	//WAS to find total no. of links in webpage and print all the link Text ???
		
		driver.get("https://www.istqb.in/");
		List<WebElement> Links = driver.findElements(By.tagName("a"));
		int Count = Links.size();
		System.out.println("Total Number of Links : " + Count);
		for(int i =0;i<Count;i++)
		{
			WebElement Link = Links.get(i);
			String LinkText = Link.getText();
			System.out.println(LinkText);
		}
		
	//WAS to print the linktext only for the displayed links???
		
		int DisplayCount =0;
		int HiddenCount = 0;
		for(int i =0;i<Count;i++)
		{
			WebElement Link = Links.get(i);
			boolean Flag = Link.isDisplayed();
			if (Flag)	//true
			{
				DisplayCount ++;
				String LinkText = Link.getText();
				System.out.println(LinkText);
				
			}
			else		//false
			{
				HiddenCount ++;
			}
		}
		System.out.println("Total Link displayed : " + DisplayCount);	
		System.out.println("Total Link hidden : " + HiddenCount);
		
		
		
		
	}

}
