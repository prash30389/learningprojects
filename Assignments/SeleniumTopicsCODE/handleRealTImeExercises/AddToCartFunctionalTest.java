package handleRealTImeExercises;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import UtilityClasses.BrowserFactory;

public class AddToCartFunctionalTest {
	public static void main(String[] args) {
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		String[] itemsNeeded = { "Cucumber", "Brocolli", "Carrot", "Beetroot" };
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait w = new WebDriverWait(driver, 20);
		driver.get("https://rahulshettyacademy.com/seleniumPractise/");

		addItems(driver, itemsNeeded);
		driver.findElement(By.cssSelector("img[alt='Cart']")).click();
		driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")).click();
		WebElement inputpromocode = w
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.promocode")));
		inputpromocode.sendKeys("rahulshettyacademy");
		driver.findElement(By.xpath("//button[contains(text(),'Apply')]")).click();
		WebElement PromoVerify = w.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Code applied ..!')]")));
		String Actualmsg = PromoVerify.getText();
		String ExpectedMsg = "Code applied ..!";
		Assert.assertEquals(Actualmsg, ExpectedMsg);
		System.out.println("promo code applied successfully");

	}

	public static void addItems(WebDriver driver, String[] itemsNeeded) {
		int j = 0;
		List<WebElement> products = driver.findElements(By.cssSelector("h4.product-name"));
		for (int i = 0; i <= products.size(); i++) {
			// Brocolli - 1 Kg
			// Brocolli, 1 kg
			String[] name = products.get(i).getText().split("-");
			// format it to get actual vegetable name
			String formattedName = name[0].trim();
			// convert array into array list for easy search
			List<String> itemsNeededList = Arrays.asList(itemsNeeded);
			// check whether name you extracted is present in arrayList or not-
			if (itemsNeededList.contains(formattedName)) {
				j++;
				driver.findElements(By.xpath("//div[@class='product-action']/button")).get(i).click();
				if (j == itemsNeeded.length) {
					break;
				}
			}
		}
	}
}
