package JavaScript;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class ScrollIntoView {

	public static void main(String[] args) 
	{
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://selenium.dev/");
		
		String jsCode = "arguments[0].scrollIntoView(true)";		//jscode
		String css = "img[Src*='testbirds.png']";
		
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(jsCode, driver.findElement(By.cssSelector(css)));
		
		String jsClick = "arguments[0].click()";
		je.executeScript(jsClick, driver.findElement(By.cssSelector(css)));
		
		
		

	}

}
