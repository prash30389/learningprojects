package Handlelocators;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoLocaters {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "./ExeFiles/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://demo.automationtesting.in/Register.html");
		driver.findElement(By.xpath("//select[@id='Skills']")).click();
		List<WebElement> Ele = driver.findElements(By.xpath("//select[@id='Skills']/option"));
		Iterator<WebElement> itr = Ele.iterator();
		while( itr.hasNext())
		{
			System.out.println(itr.next().getText());
		}
	}

}
