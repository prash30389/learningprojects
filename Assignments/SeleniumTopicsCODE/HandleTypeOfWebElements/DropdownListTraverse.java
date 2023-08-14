package HandleTypeOfWebElements;


import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DropdownListTraverse {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://demo.automationtesting.in/Register.html");
		
		// HOW TO FIND THAN TRAVERSE THE WEB ELEMENT IN DROPDOWN LIST.???
		
		driver.findElement(By.xpath("//select[@id='Skills']")).click();
		List<WebElement> Ele = driver.findElements(By.xpath("//select[@id='Skills']//option"));
		
		//bY USING ITERATOR
		
	Iterator<WebElement> itr = Ele.iterator();
		  while( itr.hasNext()) 
		  { 
			  String str = itr.next().getText();
		  if(str.equalsIgnoreCase("Art Design")) 
			  {
			  System.out.println("yuppie we find this asshole"); 
			  break; 
			  } 
		  }
		 
		 //BY USING FOR LOOP
		
		for(int i =0; i<Ele.size();i++)
		{
				String str = itr.next().getText();
			  if(str.equalsIgnoreCase("Art Design")) 
			  {
			  System.out.println("yuppie we find this ass hole"); 
			  break; 
			  }
		}
		
		//BY USING FOR NEXT LOOP
		  for(WebElement str : Ele) 
			 { 
			  	String str2 = str.getText();
			  	if(str2.equalsIgnoreCase("Art Design")) 
				  {
				  System.out.println("yuppie we find this ass hole"); 
				  break; 
				  } 
			 }
	
	}
		//driver.findElement(By.xpath("//td[text()='Java']/parent::tr/td/a[text()='Download']")).click();
		//driver.findElement(By.xpath("//td[text()='Java']/parent::tr/descendant::a[text()='Download']")).click();
}
	


