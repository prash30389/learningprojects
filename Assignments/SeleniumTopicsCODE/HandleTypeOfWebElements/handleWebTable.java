package HandleTypeOfWebElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilityClasses.BrowserFactory;

public class handleWebTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	WebDriver driver = BrowserFactory.LaunchBrowser("Chrome");
	String url = "http://www.cricbuzz.com/live-cricket-scorecard/18970/pak-vs-sl-2nd-t20i-pakistan-v-sri-lanka-in-uae-2017";
	driver.get(url);
	String xpath ="//div[text()='Pakistan won by 2 wkts']/..";
	WebElement table=driver.findElement(By.xpath(xpath)); //table
	table.findElements(By.cssSelector("cb-col cb-col-100 cb-scrd-itms")).size();//rowcount
	int count=table.findElements(By.cssSelector("div[class='cb-col cb-col-100 cb-scrd-itms'] div:nth-child(3)")).size();//scores
	int sum =0;
	for(int i=0;i<count-14;i++)
	{
	String value=table.findElements(By.cssSelector("div[class='cb-col cb-col-100 cb-scrd-itms'] div:nth-child(3)")).get(i).getText();
	System.out.println(value);
	int valueinteger=  Integer.parseInt(value);
	sum=sum+valueinteger;//103
	}
	System.out.println(sum);
	String xp = "//div[text()='Extras']/following-sibling::div";
	String Extras=driver.findElement(By.xpath(xp)).getText();
	int extrasValue=Integer.parseInt(Extras);
	int TotalSumValue=sum+extrasValue;
	System.out.println(TotalSumValue);
	
	String xp2 = "//div[text()='Total']/following-sibling::div";
	String ActualTotal=driver.findElement(By.xpath(xp2)).getText();
	int ActualTotalVAlue=Integer.parseInt(ActualTotal);
	if(ActualTotalVAlue==TotalSumValue)
	{
		System.out.println("Count Matches");
	}
	else
	{
		System.out.println("count fails");
	}
	}
	}
