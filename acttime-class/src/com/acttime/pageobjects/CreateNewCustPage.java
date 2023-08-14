package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewCustPage 
{
	@FindBy(css="input[name ='name']")
	private WebElement CustTxtBx;
	
	@FindBy(name="createCustomerSubmit")
	private WebElement createCustomerBtn;

public CreateNewCustPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

public WebElement getCustTxtBx() 
	{
		return CustTxtBx;
	}

public WebElement getCreateCustomerBtn() 
	{
		return createCustomerBtn;
	}
}
