package handlePopUPs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class FileDownloadPopupHandling 
{

	public static void main(String[] args) 
	{
		String key = "browser.helperApps.neverAsk.saveToDisk";
		String value ="application/zip";
		
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference(key, value);
		
			System.setProperty("webdriver.gecko.driver", "./ExeFiles/geckodriver.exe");
			WebDriver driver=new FirefoxDriver(options);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			
			driver.get("http://selenium.dev/downloads/");
			driver.findElement(By.linkText("32 bit Windows IE")).click();

	}

}
