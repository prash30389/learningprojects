package Assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class Assignment9 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		driver.findElement(By.cssSelector("*[id='autocomplete']")).sendKeys("IN");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String jsscript = " return document.getElementById(\"autocomplete\").value";
		String text = (String) js.executeScript(jsscript);
		int i = 0;
		while (!text.equalsIgnoreCase("india")) {
			i++;
			driver.findElement(By.cssSelector("*[id='autocomplete']")).sendKeys(Keys.DOWN);
			text = (String) js.executeScript(jsscript);
			System.out.println(text);
			if (i > 20) {
				break;
			}
		}
		if (i > 20) {
			System.out.println("element not found");
		} else {
			System.out.println("element found");
		}
	}

}
