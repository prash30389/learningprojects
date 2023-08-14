package HandleTypesOfHandling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import UtilityClasses.BrowserFactory;

public class WindowHandling {

	public static void main(String[] args) throws InterruptedException 
	{
		WebDriver driver= BrowserFactory.LaunchBrowser("chrome");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("http://prashant-laptop/login.do");
		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("pwd")).sendKeys("manager");
		driver.findElement(By.id("loginButton")).click();
		
		driver.findElement(By.xpath("//span[text()='Help & Support']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'User Guide')]")).click();
		
		Set<String> s = driver.getWindowHandles();
		ArrayList<String> as = new ArrayList<String>(s);
			for(int i=0; i<s.size();i++)
				{
					String S = as.get(i);
					driver.switchTo().window(S);
					System.out.println(driver.getTitle());
				}
		
			Set<String> sessions = driver.getWindowHandles();
				System.out.println(sessions);
				System.out.println(sessions.size());
				
			Iterator<String> itr = sessions.iterator();
				String parentid = itr.next();
					System.out.println(parentid);
				String childid = itr.next();
					System.out.println(childid);
					
		driver.manage().window().maximize();	
			driver.switchTo().window(childid);
			driver.findElement(By.linkText("Reporting Overview")).click();
Thread.sleep(4000);
		
			driver.switchTo().window(parentid);
			driver.findElement(By.id("logoutLink")).click();
			
			
	}

}
