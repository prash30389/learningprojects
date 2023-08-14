package driverMethods;



import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import UtilityClasses.BrowserFactory;

public class WebElementMethods 
{
public static void main(String[] args) throws InterruptedException
{
System.setProperty("webdriver.chrome.driver", "./ExeFiles/chromedriver.exe");	// Launch Browser
WebDriver driver = new ChromeDriver();											// Browser Driver Call
driver.get("file:///C:/Users/prashant/Desktop/Interview Questions/NSSW22.html");				// navigate to Url

													//WEB ELEMENT METHODS

//HOW TO CLEAR THE DATA FROM TEXTBOX WITHOUT CLEAR METHOD?

driver.findElement(By.id("user")).sendKeys("bhosdiwala"); 					// send keys()
Thread.sleep(2000);
WebElement UntxtBx=driver.findElement(By.id("user")); 						// find element()
UntxtBx.sendKeys(Keys.CONTROL+"a");
UntxtBx.sendKeys(Keys.DELETE);
UntxtBx.sendKeys("Jhattu");
Thread.sleep(5000);
UntxtBx.clear();																//clear()
Thread.sleep(5000);

//HOW TO FINDELEMENT() method of  INSIDE WEB ELEMENT interface in ARCHITECTURE ???

WebElement BodyEle = driver.findElement(By.tagName("body"));
WebElement PwdTxtBx = BodyEle.findElement(By.name("pwd"));
PwdTxtBx.sendKeys("12345");													//SENDKEYS()


//HOW TO RETRIEVE THE HTML TEXT FROM WEB ELEMENT???

String headerText = BodyEle.findElement(By.tagName("h1")).getText();
System.out.println(headerText);
Thread.sleep(5000);
String LinkText = BodyEle.findElement(By.tagName("a")).getText();				//gettext()
System.out.println(LinkText);
Thread.sleep(5000);

//HOW TO RETRIEVE VALUE OF THE ATTRIBUTE FROM WEB ELEMENT???

String attr1 = PwdTxtBx.getAttribute("type");
System.out.println(attr1);
Thread.sleep(5000);
String attr2 = PwdTxtBx.getAttribute("placeholder");					//getAttribute()
System.out.println(attr2);
Thread.sleep(5000);
String attr3 = PwdTxtBx.getAttribute("maxlength");
System.out.println(attr3);
Thread.sleep(5000);
PwdTxtBx.sendKeys("6789");
String attr4 = PwdTxtBx.getAttribute("value");
System.out.println(attr4);
Thread.sleep(5000);

//HOW TO RETRIEVE THE CSS ATTRIBUTE VALUE FROM WEBELEMENT ???

String css1 = BodyEle.findElement(By.tagName("h1")).getCssValue("font-size");
System.out.println(css1);
Thread.sleep(5000);
String css2 = driver.findElement(By.tagName("a")).getCssValue("font-size");	//getCssValue()
System.out.println(css2);
Thread.sleep(5000);

//HOW TO FIND THE DIMENSION OF WEBELEMENT ??
driver.manage().window().maximize();
Dimension dim =driver.findElement(By.tagName("img")).getSize();			//getSize()
int height = dim.getHeight();
int width = dim.getWidth();
System.out.println("height of image : " + height);
System.out.println("width of image : " + width);
int PageHeight = driver.findElement(By.tagName("body")).getSize().getHeight();
System.out.println("Entire page height :" + PageHeight);
Thread.sleep(5000);

//HOW TO VERIFY WHETHER CHCKBOX IS SELECTED OR NOT ???

WebElement ChkBx = driver.findElement(By.className("MyCheckbox"));
boolean flag = ChkBx.isSelected();										//ISSELECTED()
System.out.println(flag);
	if (flag) //true
	{
		System.out.println("It is Selected");
	}
	else 	  //False
	{
		ChkBx.click();
	}
	Thread.sleep(5000);
	

//HOW TO VERIFY THAT WEBELEMENT IS PRESENT IN HTMLDOCUMENT OR NOT ???

	try
	{
		driver.findElement(By.tagName("span"));
		System.out.println("element Exist");
	}
	catch(NoSuchElementException e)
	{
		System.out.println("�lement doesnot exist in html document");
	}
	Thread.sleep(5000);
	//HOW TO VERIFY THAT WHETHER WEBELEMENT IS DISPLAYED OR NOT ???
	
	driver.manage().window().maximize();
	driver.get("https://www.istqb.in/");
		String xp ="//div[@id ='tmMainMenu']//a[text()='FOUNDATION']";
			WebElement FoundationEle = driver.findElement(By.xpath(xp));
			BrowserFactory.VerifyElementDisplay(FoundationEle);										//is Displayed()
		String xp2="//a[text()='FOUNDATION']/following-sibling::div//a[text()='EXAM DATES']";
			WebElement FoundationTestEle = driver.findElement(By.xpath(xp2));
			BrowserFactory.VerifyElementDisplay(FoundationTestEle);
			Thread.sleep(5000);
	//HOW TO VERIFY WHETHER WEBELEMENT IS ENABLED OR nOT ???
	driver.get("https://www.gmail.com");
	String sx = "//span[@class='RveJvd snByac']";
	driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("prashsharma89@gmail.com");
		WebElement EnableEle = driver.findElement(By.xpath(sx));
		BrowserFactory.VerifyElementEnable(EnableEle);
		EnableEle.click(); 																// if Attribute value is SUBMIT than use Submit() method of Web ElemenT
	//driver.findElement(By.xpath("//input[@jsname='YPqjbf']")).sendKeys("rockers30389");
	Thread.sleep(5000);
	driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
	
} //MAIN METHOD INNER SCOPE

 
}//CLASS OUTER SCOPE

