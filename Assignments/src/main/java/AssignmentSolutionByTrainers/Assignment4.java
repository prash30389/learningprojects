package AssignmentSolutionByTrainers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilityClasses.BrowserFactory;

public class Assignment4 {
	public static void main(String[] args) {
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.get("http://www.itgeared.com/demo/1506-ajax-loading.html");
		driver.findElement(By.cssSelector("a[href*='loadAjax']")).click();
		WebDriverWait d = new WebDriverWait(driver, 20);
		d.until(ExpectedConditions.elementToBeClickable(By.id("results")));
		System.out.println(driver.findElement(By.id("results")).getText());
		// System.out.println(
		// driver.findElement(By.xpath("//*[@id='checkbox-example']/fieldset/label[2]")).getText());
	}
}
