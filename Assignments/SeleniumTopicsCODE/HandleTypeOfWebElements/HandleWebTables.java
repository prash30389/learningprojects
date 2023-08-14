package HandleTypeOfWebElements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilityClasses.BrowserFactory;

public class HandleWebTables {

	public static void main(String[] args) throws InterruptedException 
	{
		//How to find total number of rows present inside the web table?
		
		WebDriver driver = BrowserFactory.LaunchBrowser("Chrome");
//		driver.get("http://127.0.0.1/administration/workingdays.do");
//		driver.findElement(By.name("username")).sendKeys("admin");
//		driver.findElement(By.name("pwd")).sendKeys("manager");
//		driver.findElement(By.id("loginButton")).click();
		driver.get("file:///C:/Users/prashant/Desktop/Interview Questions/NSSW22.html");
		
	//How to find total number of rows??
		
		String rowxp = "//table[@name = 'Num']//tbody//tr";
		List<WebElement> rows = driver.findElements(By.xpath(rowxp));
		int count = rows.size();
		System.out.println("Total No of rows : " + count);
		
	//How to find total number of column inside each row??
		int sum =0;
		for(int i =1;i<=count;i++)		//loop for x path movement for getting webelement one by one
		{
			String Colxp="//table[@name = 'Num']//tbody//tr["+i+"]/td";
			List<WebElement> Columns = driver.findElements(By.xpath(Colxp));
			System.out.println("Rows : " + i + "Total Columns : " + Columns.size());
			Thread.sleep(2000);

	//WAS to retrieve all the data from each rows & columns ??
		
			for(int j =1;j<=Columns.size();j++)		//loop for x path movement for getting webelement data one by one
			{
				
				String xp="//table[@name = 'Num']//tbody//tr["+i+"]/td["+j+"]";
				Thread.sleep(2000);
				String Text =driver.findElement(By.xpath(xp)).getText();
				System.out.println(Text);
				sum = sum + Integer.parseInt(Text);
				
			}
			
		}
		System.out.println(sum);
	}

}
