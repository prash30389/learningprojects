package Assignment;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import UtilityClasses.BrowserFactory;

public class Assignment7 {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.rahulshettyacademy.com/AutomationPractice/");
		driver.findElement(By.id("checkBoxOption2")).click();
		String txt = driver.findElement(By.xpath("//*[@id='checkbox-example']/fieldset/label[2]")).getText();
		System.out.println(txt);
		WebElement drpdwn = driver.findElement(By.id("dropdown-class-example"));
		Select sel = new Select(drpdwn);
		sel.selectByVisibleText(txt);
		WebElement txtbx = driver.findElement(By.id("name"));
		txtbx.click();
		txtbx.sendKeys(txt);
		Thread.sleep(2000);
		driver.findElement(By.id("alertbtn")).click();
		Alert alt = driver.switchTo().alert();
		String expectmsg = "Hello Option2, share this practice page and share your knowledge";
		String actualMsg = alt.getText();
		
		if(expectmsg.equals(actualMsg))
		{
			System.out.println("alert msg verified");
		}
		else
		{
			System.out.println("msg doesn't match");
		}
		alt.accept();
	}

}