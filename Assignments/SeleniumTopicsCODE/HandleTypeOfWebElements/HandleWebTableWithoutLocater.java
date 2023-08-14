package HandleTypeOfWebElements;

import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilityClasses.BrowserFactory;

public class HandleWebTableWithoutLocater 
{
public static void main(String[] args) throws InterruptedException 
	{
	WebDriver driver = BrowserFactory.LaunchBrowser("Chrome");
	driver.get("file:///C:/Users/CSIR/Desktop/practiceSelenium.html");
	WebElement table =driver.findElement(By.name("Num"));
	List<WebElement> rows = table.findElements(By.tagName("tr"));
	Thread.sleep(2000);
	System.out.println("total rows :" + rows.size());
	int sum =0;
	for(int i =0;i<rows.size();i++)
		{
			WebElement row = rows.get(i);
			List<WebElement> cols = row.findElements(By.tagName("td"));
			Thread.sleep(2000);
			System.out.println("Rows : " + i + "Total Columns : " + cols.size());
			Thread.sleep(2000);
			for(int j =0;j<cols.size();j++)
			{
				 WebElement column = cols.get(j);
				String ColsText= column.getText();
				System.out.println(ColsText);
				sum = sum + Integer.parseInt(ColsText);
				
			}
			
		}
	System.out.println(sum);
	}
}
