package features;



import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import pages.FilterPage;
import pages.TravellerDetailsPage;
import pages.homePage;
import pages.paymentPage;

public class homePageFeatures {
	WebDriver driver;
	homePage hp;
	FilterPage fp;
	paymentPage pp;
	TravellerDetailsPage tdp;
	public homePageFeatures(WebDriver driver) {
		this.driver = driver;
		hp = new homePage(driver);
		fp = new FilterPage(driver);
		tdp = new TravellerDetailsPage(driver);
		pp = new paymentPage(driver);
	}

	public void flightBooking() {
			hp.getRound().click();
			hp.getFromBtn().sendKeys("del");
			hp.getFromBtn().sendKeys(Keys.ARROW_DOWN);
			hp.getFromBtn().sendKeys(Keys.ENTER);
			hp.getDestBtn().sendKeys("Mum");
			WebElement unwantedblocker = driver.findElement(By.xpath("//*[@id='ctabutton']/a"));
			if (unwantedblocker.isDisplayed())
				{
				unwantedblocker.click();
					hp.getDestBtn().sendKeys("Mum");
					hp.getDestBtn().sendKeys(Keys.ARROW_DOWN);
					hp.getDestBtn().sendKeys(Keys.ENTER);
					hp.getDepdate().click();
					hp.getReturndate().click();
					hp.getSearchBtn().click();
				}
				else
				{
					hp.getDestBtn().sendKeys("Mum");
					hp.getDestBtn().sendKeys(Keys.ARROW_DOWN);
					hp.getDestBtn().sendKeys(Keys.ENTER);
					hp.getDepdate().click();
					hp.getReturndate().click();
					hp.getSearchBtn().click();
				}
			
			fp.getSortPrice().click();
			fp.getSortPrice2().click();
			fp.getChkBtn1().click();
			fp.getChkBtn2().click();
			fp.getBookBtn().click();
			String jsCode="window.scrollBy(0,10000)";
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript(jsCode);
			WebElement titleSelect = tdp.getTitle();
			Select Sel = new Select(titleSelect);
			Sel.selectByValue("Mr");
			Scanner s = new Scanner(System.in);

			System.out.println("Enter the name:");

			String value1=s.nextLine();
			tdp.getFirstName().sendKeys(value1);

			System.out.println("middle name:");
			String value2=s.nextLine();
			tdp.getMiddleName().sendKeys(value2);

			System.out.println("last name:");
			String value3=s.nextLine();
			tdp.getLastName().sendKeys(value3);
			
			System.out.println("last name:");
			String value4=s.nextLine();
			tdp.getMobile().sendKeys(value4);
			
			System.out.println("Email Id: ");
			String value5=s.nextLine();
			tdp.getEmail().sendKeys(value5);
			
			tdp.getNoriskchkbtn().click();
			tdp.getProceedBtn().click();
			pp.getPopup().click();
			je.executeScript(jsCode);
			pp.getProceedpayment().click();
			pp.getPayOption().click();
			
		}

}
