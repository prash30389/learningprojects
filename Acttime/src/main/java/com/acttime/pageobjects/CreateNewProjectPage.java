package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewProjectPage 
{
	@FindBy(css= "input[value='Create New Project']")
	private WebElement createProjectButton;
	
	@FindBy(name="customerId")
	private WebElement CustDrpDwn;
	
	@FindBy(name="name")
	private WebElement ProjNameTxt;
	
	@FindBy(name="createProjectSubmit")
	private WebElement SubmitBtn;
	
	public CreateNewProjectPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public WebElement getCustDrpDwn() 
	{
		return CustDrpDwn;
	}

	public WebElement getProjNameTxt() 
	{
		return ProjNameTxt;
	}

	public WebElement getSubmitBtn() 
	{
		return SubmitBtn;
	}

	public WebElement getCreateProjectButton() 
	{
		return createProjectButton;
	}
	
	
}
