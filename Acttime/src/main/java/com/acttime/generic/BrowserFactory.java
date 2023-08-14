package com.acttime.generic;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
/**
 * 
 * @author prashant
 *
 */

public class BrowserFactory 
{
	/**
	 * @author prashant
	 * @param browsername
	 * @return WebDriver
	 */
	
	public static WebDriver driver =null;	
	

		//Properties prop = new Properties();
		//FileInputStream fis = new FileInputStream("F:\\projects\\Act-Time-Maven\\src\\main\\java\\com\\acttime\\generic\\datadriven.properties");
		//prop.load(fis);
		
		
		public static WebDriver launchbrowser(String BrowserName)
		{
		WebDriver driver =null;
		if(BrowserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "./ExeFiles/chromedriver.exe");
				driver = new ChromeDriver();
				
			System.out.println("Chrome Launched");
		}
		if(BrowserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "./ExeFiles/geckodriver.exe");
				driver = new FirefoxDriver();
				
			System.out.println("Firefox Launched");
		}
		if(BrowserName.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver", "./ExeFiles/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				
			System.out.println("IE Launched");
		}
		return driver;
		
		}
	}
	
