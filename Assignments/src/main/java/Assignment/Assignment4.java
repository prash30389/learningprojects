package Assignment;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilityClasses.BrowserFactory;

public class Assignment4 {
	public static void main(String[] args) {
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		String url = "http://www.itgeared.com/demo/1506-ajax-loading.html";
		driver.get(url);
		WebDriverWait d = new WebDriverWait(driver, 20);
		String xp = "//a[contains(text(),'Click to load get data via Ajax!')]";
		WebElement Ele = d.until(ExpectedConditions.elementToBeClickable(By.xpath(xp)));
		Ele.click();
		String verifyMsg = "//div[contains(text(),'This response has been loaded')]";
		WebElement msgEle = d.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(verifyMsg)));
		String msg = msgEle.getText();
		System.out.println(msg);

	}
}
