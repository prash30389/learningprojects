package HandleTypesOfHandling;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import UtilityClasses.BrowserFactory;

public class NavigateGetFramesWindowhandling 
{	
	public static void main(String[] args) throws InterruptedException 
		{
			WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
			//for Navigate or go to url
			
				driver.navigate().to("http://demo.automationtesting.in/Register.html");
				driver.navigate().back();
				driver.navigate().forward();
				driver.navigate().refresh();
				String s1= driver.getCurrentUrl();
				System.out.println(s1);
				String s2 = driver.getPageSource();
				System.out.println(s2);
				driver.getTitle();
				Thread.sleep(2000);
			
			//Go to Frames
				
					driver.findElement(By.xpath("//a[contains(text(),'SwitchTo')]")).click();
					driver.findElement(By.xpath("//a[contains(text(),'Frames')]")).click();
					driver.findElement(By.xpath("//a[contains(text(),'Iframe with in an Iframe')]")).click();
				BrowserFactory.IndexFrame(driver,1); 
				Thread.sleep(2000);
				BrowserFactory.IndexFrame(driver, 0);
					driver.findElement(By.xpath("//div[@class='col-xs-6 col-xs-offset-5']/input")).sendKeys("SAB MADACHOD HAIN");
				
			//Window Handling
			
				driver.navigate().to("https://www.naukri.com");
			Set<String> s = driver.getWindowHandles();
			ArrayList<String> as = new ArrayList<String>(s);
				for(int i=0; i<s.size();i++)
					{
						String S = as.get(i);
						driver.switchTo().window(S);
						System.out.println(driver.getTitle());
					}
		}
}
