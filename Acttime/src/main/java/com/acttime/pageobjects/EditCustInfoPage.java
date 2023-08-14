package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * 
 * @author prashant
 *
 */
public class EditCustInfoPage 
{
	@FindBy(css="input[value='Delete This Customer']")
	private WebElement DelcustEle;
	
	@FindBy(id="deleteButton")
	private WebElement DeletePopupEle;
	
	//------------->project webelements
	
	@FindBy(xpath="//input[contains(@value,'Delete This Project')]")
	private WebElement DelProjEle;
	
	@FindBy(xpath="//input[contains(@value,'Delete Project')]")
	private WebElement DelProjPopupEle;
public EditCustInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

public WebElement getDelcustEle() 
{
	return DelcustEle;
}

public WebElement getDeletePopupEle() 
{
	return DeletePopupEle;
}

public WebElement getDelProjEle() 
{
	return DelProjEle;
}

public WebElement getDelProjPopupEle() 
{
	return DelProjPopupEle;
}




}
