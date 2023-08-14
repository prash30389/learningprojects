package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments5 {
	
	WebDriver driver;	
	@BeforeMethod
	public void setUp()
	{
		
		
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumContent\\selenium assigment\\SeleniumAssigmentt\\Driver\\chromedriver.exe");
	
		 driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start:" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		
	}	
	
	//-------------------------write a script to select today date in calender and also date 15 date after---------------------------
	@Test
	public void scriptOne()
	{
		driver.get("https://www.makemytrip.com/");
		WebElement web = driver.findElement(By.xpath("(((//div[contains(@class,'widgetSection appendBottom40' )]/div)[2]/div)[1]/div)[3]"));
		
		Actions act= new Actions(driver);
		act.moveToElement(web).click().build().perform();
		
		List<WebElement> calenderDateList = driver.findElements(By.xpath("(//div[contains(@class,'DayPicker-Body')])[1]//p"));
		int size=calenderDateList.size();
		System.out.println("The size of list is  :"  +size );
		
		for (int i = 0; i < size; i++) {
			
			String txt=calenderDateList.get(i).getText();
			
			//-------------------------------------------For selecting today date-------------------------------
//			if(txt.equals("12"))
//			{
//				calenderDateList.get(i).click();
//				break;
//			}
			
			//---------------------------------------	Select date 15 date after today date-----------------------------
			if(txt.contains("26"))
			{
				calenderDateList.get(i).click();
     			break;
			}
			
		}			
		
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}

}
