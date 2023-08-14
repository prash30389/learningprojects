package handleRealTImeExercises;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import UtilityClasses.BrowserFactory;

public class PaymentProcess {
	static WebDriver driver;

	public static void main(String[] args) {
		WebDriver driver = BrowserFactory.LaunchBrowser("firefox");
		driver.get("https://demo.midtrans.com/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean status = js.executeScript("return document.readyState").toString().equalsIgnoreCase("complete");
		if (status) {
			Reporter.log("page is loaded completely");
		} else {
			Reporter.log("Waiting for the page");
		}
		driver.findElement(By.xpath("//a[text()='BUY NOW']")).click();
		driver.findElement(By.xpath("//div[text()='CHECKOUT']")).click();
		driver.switchTo().frame("snap-midtrans");
		String cont_button_xpath = "//a[contains(@class, 'button-main-content')]";
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement cont_button = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(cont_button_xpath)));
		cont_button.click();
		driver.findElement(By.xpath("//div[@id='payment-list']/div[2]")).click();
		driver.findElement(By.xpath("//div[text()='Credit Card']")).click();
		driver.findElement(By.name("cardnumber")).sendKeys("4811111111111114");
		driver.findElement(By.xpath("//div[contains(@class, 'input-group col-xs-7')]/input")).sendKeys("0220");
		driver.findElement(By.xpath("//div[contains(@class, 'input-group col-xs-5')]/input")).sendKeys("123");
		driver.findElement(By.xpath(cont_button_xpath)).click();
		String xp = "//div[contains(@class, 'page-container scroll')]/iframe";
		driver.switchTo().frame(driver.findElement(By.xpath(xp)));
		// driver.findElement(By.xpath("//div[@class='col-xs-7']/input")).sendKeys("112233");
		WebElement paymentotp = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-xs-7']/input")));
		paymentotp.sendKeys("112233");
		driver.findElement(By.name("ok")).click();

	}

}
