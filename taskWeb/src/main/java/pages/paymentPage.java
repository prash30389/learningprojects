package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class paymentPage {
	
	@FindBy(xpath="//div[contains(@class, 'dF padT20')]//button")
	private WebElement popup;
	
	@FindBy(xpath="//span[contains(text(),'Proceed To Payment')]")
	private WebElement proceedpayment;
	
	@FindBy(xpath="//div[contains(@class,'fr padTB10 padLR5')]")
	private WebElement payOption;
	
	public paymentPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getPopup() {
		return popup;
	}

	public WebElement getProceedpayment() {
		return proceedpayment;
	}

	public WebElement getPayOption() {
		return payOption;
	}
	
	

}
