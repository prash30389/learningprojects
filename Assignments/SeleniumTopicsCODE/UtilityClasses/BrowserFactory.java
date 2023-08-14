package UtilityClasses;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;

public class BrowserFactory {
	static WebDriver driver;

	// Launch BROWSERS UTILITIES
	public static WebDriver LaunchBrowser(String BrowserName) {
		if (BrowserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "./ExeFiles/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			System.out.println("Chrome Launched");
		}
		if (BrowserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "./ExeFiles/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			System.out.println("Firefox Launched");
		}
		if (BrowserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", "./ExeFiles/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			System.out.println("IE Launched");
		}
		return driver;

	}

	// FRAMES

	public static void ParentFrame(WebDriver driver) {
		driver.switchTo().parentFrame();
	}

	public static void IndexFrame(WebDriver driver, int Index) {
		driver.switchTo().frame(Index);
	}

	public static void NameFrame(WebDriver driver, String Name) {
		driver.switchTo().frame(Name);
	}

	public static void ElementFrame(WebDriver driver, WebElement Element) {
		driver.switchTo().frame(Element);
	}

	public static void ExitFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	// WEBELEMENT UTILITIES
	// HOW TO VERIFY THAT WHETHER wEBeLEMENT IS DISPLAYED OR NOT ???
	public static void VerifyElementDisplay(WebElement element) {
		boolean flag = element.isDisplayed();
		if (flag) {
			System.out.println("Element is displayed");
		} else {
			System.out.println("Element is not displayed");
		}
	}

	public static void VerifyElementselected(WebElement element) {
		boolean flag = element.isSelected();
		if (flag) {
			System.out.println("Element is Selected");
		} else {
			element.click();
		}
	}

	public static void VerifyElementEnable(WebElement element) {
		boolean flag = element.isEnabled();
		if (flag) {
			System.out.println("Element/button is enabled");
		} else {
			System.out.println("Element/button is not enabled");
		}
	}

	// screenshot
	public static void Screenshot(WebDriver driver, String fileName) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;

		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./Screenshots/" + fileName + ".png");

		try {

			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
