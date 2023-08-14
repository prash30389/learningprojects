package handleExcelFileNApachePOIMethodology;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import UtilityClasses.BrowserFactory;

public class TestUploadFile {

	public static void main(String[] args) {
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.zamzar.com/");
		driver.findElement(By.cssSelector("//*[@id='inputFile']")).sendKeys(""); 
		

	}

}
