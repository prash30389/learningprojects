package HandleTypeOfWebElements;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import UtilityClasses.BrowserFactory;

public class ActionsClassBasic {
	//In order to handle mouse operation we use Action Class in selenium. To perform any Mouse operation in Action Class, PERFORM() method is mandatory in use.
			/* non-static methods and its available in interaction package available
			 * perform() 
			 * sendkeys() 
			 * click() 
			 * movetoelement() 
			 * clickandhold() 
			 * release()
			 * movebyoffset() 
			 * draganddropby() 
			 * contextclick() 
			 * doubleclick()
			 */
			
			/* operations
			 * 
			 * Mouse Click --> click() + perform() 
			 * Mouse Over --> movetoelement() + perform() 
			 * Right Click --> consentclick() + perform() 
			 * Double Click -->doubleclick() + perform()
			 * range slider --> clickandhold()+ perform(), movebyoffset() + perform(), release() + perform() 
			 * drag and drop --> clickandhold()+ perform(),draganddropby() + perform(), release() + perform() 
			 * Keyboard operations --> sendkeys()+perform()
			 */
	public static void main(String[] args) throws InterruptedException
	{
		
		
			WebDriver driver = BrowserFactory.LaunchBrowser("chrome");
			driver.get("https://www.flipkart.com/");
			driver.findElement(By.xpath("//button[text()='✕']")).click();
			Thread.sleep(2000);
			Actions Act = new Actions(driver);
			
			String xp = "//span[text()='Home & Furniture']";				//Mouse Over
			WebElement homeNfur = driver.findElement(By.xpath(xp));
			Act.moveToElement(homeNfur).perform();		//movetoelement()	
			Thread.sleep(2000);
			
			WebElement blankeWB = driver.findElement(By.linkText("Blankets"));
			Act.click(blankeWB).perform();									//Mouse Click
			
			String xp2= "//div[@class='_3G9WVX _2N3EuE']";					//range slider
			Thread.sleep(2000);
			WebElement rightslider = driver.findElement(By.xpath(xp2));
			Point loc = rightslider.getLocation();
			System.out.println(loc.x);
			System.out.println(loc.y);
			Act.clickAndHold(rightslider).perform();
			
			Act.moveByOffset(-120, 0).perform();
	
			Act.release(rightslider).perform();
			
			String xp3="//a[text()='FINCH Solid Single Fleece Blanket']";		//drag and drop
			WebElement dragNdropsource=driver.findElement(By.xpath(xp3));
			WebElement searchtxtbx = driver.findElement(By.name("q"));
			Act.clickAndHold(dragNdropsource).perform();
			Act.dragAndDrop(dragNdropsource, searchtxtbx).perform();
			Act.release(searchtxtbx).perform();
			
					
			Act.sendKeys(searchtxtbx, "samsung galaxy").perform();			//Keyboard operations
			Thread.sleep(2000);
			Act.sendKeys(Keys.ARROW_DOWN).perform();
			Thread.sleep(2000);
			Act.sendKeys(Keys.ARROW_DOWN).perform();
			
			driver.get("https://www.amazon.com/");
			WebElement move = driver.findElement(By.cssSelector("a[id='nav-link-accountList']"));
			Act.moveToElement(driver.findElement(By.id("twotabsearchtextbox"))).click().keyDown(Keys.SHIFT).sendKeys("hello").build().perform();
			//move to specific element
			Act.moveToElement(move).contextClick().build().perform();

	}

}
