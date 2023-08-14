package Handlelocators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import UtilityClasses.BrowserFactory;

/**
 * 
 * @author prashant
 *
 */

/*
 * Tools for identifying elements :

Installing Firebug, Firepath       � Firefox
Chrome Inspector                   GoogleChrome
F 12                               Internet Explorer

Firebug Download Links
https://addons.mozilla.org/en-US/firefox/addon/firebug/
FirePath download
https://addons.mozilla.org/en-US/firefox/addon/firepath/

Writing Customized xpath :

Using Attributes .//tagName[@attribute='value']

Traversing through TagNames

 */
public class AccuracyofXpath 
{
public static void main(String[] args) 
{
WebDriver driver = BrowserFactory.LaunchBrowser("firefox");
driver.get("http://facebook.com"); //URL in the browser

//tagName[@attribute='value']  - xpath
driver.findElement(By.xpath("//*[@id='email']")).sendKeys("myown xpath");
driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("hello");
driver.findElement(By.xpath("//input[@id='u_0_b']")).click();
	
	//tagName[v='value']  -CSS
driver.findElement(By.cssSelector("input[name='email']")).sendKeys("myowncss");


driver.get("https://rahulshettyacademy.com/AutomationPractice/");
WebElement Drpdwn =driver.findElement(By.xpath("//legend[contains(text(),'Dropdown Example')]"));
Select sel = new Select(Drpdwn);
sel.selectByIndex(1);
}
}
