package Assignment;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class Assignment5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver= BrowserFactory.LaunchBrowser("chrome");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String url = "https://the-internet.herokuapp.com/";
		driver.get(url);
		driver.findElement(By.xpath("//a[text()='Multiple Windows']")).click();
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		System.out.println(driver.getTitle());
		Set<String> session = driver.getWindowHandles();
		Iterator<String> itr = session.iterator();
		String parent = itr.next();
		String child = itr.next();
		driver.switchTo().window(child);
		System.out.println(driver.getTitle());
		String Childmsg = driver.findElement(By.xpath("//h3[contains(text(),'New Window')]")).getText();
		System.out.println(Childmsg);
		driver.switchTo().window(parent);
		System.out.println(driver.getTitle());
		String defaultmsg=driver.findElement(By.xpath("//h3[contains(text(),'Opening a new window')]")).getText();
		System.out.println(defaultmsg);
	}

}
