package HandleTypeOfWebElements;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import UtilityClasses.BrowserFactory;

public class FluentWaitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
		driver.findElement(By.cssSelector("[id='start'] button")).click();

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class);
		wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement BttnEle = driver.findElement(By.cssSelector("[id='finish'] h4"));
				if (BttnEle.isDisplayed()) {
					return BttnEle;
				} else
					return null;

			}
		});
		System.out.println(driver.findElement(By.cssSelector("[id='finish'] h4")).getText());
	}

}
