package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments7 {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\SeleniumContent\\selenium assigment\\SeleniumAssigmentt\\Driver\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	//--------------------Write a sc ript to print the data of web table--------------------------------------
	@Test
	public void scriptOne() {
		driver.get("http://demo.automationtesting.in/WebTable.html");
		List<WebElement> list = driver.findElements(By.xpath(
				"//div[contains(@class,'ui-grid-canvas')]//div[contains(@class,'ui-grid-cell-contents ng-binding ng-scope')]"));
		int size = list.size();

		for (int i = 0; i < 100; i++) 
		{

			for (int j = 0; j < size; j++)
			{
				String str = list.get(j).getText();
				System.out.print(str);
				System.out.println();
				System.out.println("------------------------------------------------");
			}
			driver.findElement(By.xpath("(//div[@role='navigation']/div/button)[3]")).click();
			//list = driver.findElements(By.xpath(
					//"//div[contains(@class,'ui-grid-canvas')]//div[contains(@class,'ui-grid-cell-contents ng-binding ng-scope')]"));

		}

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
