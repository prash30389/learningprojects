package com.acttime.pageobjects;
/**
 * @author prashant
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditCustInformationPage 
{
@FindBy(css="input[value='Delete This Customer']")
private WebElement DelThisCustEle;

@FindBy(id="deleteButton")
private WebElement DeletePopupEle;

	public EditCustInformationPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getDelThisCustEle() 
	{
		return DelThisCustEle;
	}

	public WebElement getDeletePopupEle() 
	
	{
		
		return DeletePopupEle;
	}



}
