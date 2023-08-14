package handlePopUPs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class OtherDownloadPopupAutoIT {

	public static void main(String[] args) {
		WebDriver driver = BrowserFactory.LaunchBrowser("firefox");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.autoitscript.com/site/autoit/downloads/");

		String css = "img[title ='Download AutoIt']";
		String jscode = "arguments[0].scrollIntoView(true)";

		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(jscode, driver.findElement(By.cssSelector(css)));
		String jscode2 = "arguments[0].click()";
		je.executeScript(jscode2, driver.findElement(By.cssSelector(css)));

		try {
			Runtime.getRuntime().exec("F:/AutoItScripts/SCR1.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
