package Assignment;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class Assignment6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/");
		driver.findElement(By.xpath("//a[text()='Nested Frames']")).click();
		System.out.println(driver.findElement(By.tagName("frame")).getSize());
		driver.switchTo().frame("frame-top");// string frame
		// driver.switchTo().parentFrame();
		driver.switchTo().frame("frame-middle");
		String txt3 = driver.findElement(By.id("content")).getText();
		System.out.println(txt3);
		driver.switchTo().defaultContent();
		driver.navigate().back();

	}

}
