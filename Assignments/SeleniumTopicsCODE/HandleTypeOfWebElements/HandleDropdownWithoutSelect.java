package HandleTypeOfWebElements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilityClasses.BrowserFactory;

public class HandleDropdownWithoutSelect 
{

	public static void main(String[] args)
	{
		WebDriver driver = BrowserFactory.LaunchBrowser("Chrome");
		driver.get("file:///C:/Users/prashant/Desktop/Automation Files/NSSW22.html");
		
		//WAS to handle dropdown without using Select class ???
		
			WebElement DrpDwn = driver.findElement(By.name("Courses"));
			List<WebElement> AllOps = DrpDwn.findElements(By.tagName("option"));
				int Count = AllOps.size();
				System.out.println(Count);
			for(int i =0;i<Count;i++)
			{
				WebElement Option = AllOps.get(i);
				String OptionText = Option.getText();
				System.out.println(OptionText);
				Option.click();
			}
	}

}
