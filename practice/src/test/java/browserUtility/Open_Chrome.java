package browserUtility;

import java.net.MalformedURLException;

import org.openqa.selenium.By;


public class Open_Chrome extends Browser {

	public static void searchText(String searchText) throws MalformedURLException
	{
		SetUp();
		driver.navigate().to("http://www.google.com");
		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searchText);
	}

	public static void main(String[] args) throws MalformedURLException
	{
		searchText("360LOGICA");
		searchText("SAKSOFT");
		searchText("JAVATPOINT");
		searchText("W3SCHOOL");
		tearDown();
	}
}
