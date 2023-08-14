package JavaScript;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class ScrollUpandDown {

	public static void main(String[] args) {
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://selenium.dev/");

		String jsCode1= "window.scrollBy(0,document.body.scrollHeight)";
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(jsCode1);
		
		String jsCode2="window.scrollBy(0,-2000)";
		je.executeScript(jsCode2);
		
		
	}

}
