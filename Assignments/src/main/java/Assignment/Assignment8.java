package Assignment;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilityClasses.BrowserFactory;

public class Assignment8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		System.out.println(rows.size());
		List<WebElement> cols = driver.findElements(By.tagName("th"));
		System.out.println(cols.size());
		
		List<WebElement> data = driver.findElements(By.xpath("//table[@id='product']//tr[2]//td"));
		int size = data.size();
		
		for(int i =0; i<size;i++)
		{
			String value = data.get(i).getText();
			System.out.println(value);
			if(i==size)
			{
				break;
			}
		}
	}

}
