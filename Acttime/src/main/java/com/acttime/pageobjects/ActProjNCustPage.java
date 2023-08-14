package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ActProjNCustPage extends BasePage
{
	@FindBy(xpath ="//input[@value='Create New Customer']")
	private WebElement CreateNewCustomerButton;
	
	@FindBy(xpath ="//table[starts-with(@id,'SuccessMessages')]//span[contains(text(),'Customer')]")
	private WebElement sucessMsgEle; 
	
	@FindBy(name="selectedCustomer")
	private WebElement SelectCustdrpdwn;
	
	@FindBy(css="input[value*='Show']")
	private WebElement showBtn;
	
	@FindBy(xpath ="//td[starts-with(@id,'customerNameCell')]//a[contains(@href,'customer')]")
	private WebElement CustLink;
	//---------------------------------------------------------------------. project webelements
	@FindBy(css="input[value='Create New Project']")
	private WebElement CreateNewProjectButton;
	
	@FindBy(xpath="//td[starts-with(@id,'projectNameCell')]//a[contains(@href,'project')]")
	private WebElement projLink;
	
	@FindBy(xpath="//table[starts-with(@id,'SuccessMessages')]//span[contains(text(),'Project has been successfully deleted.')]")
	private WebElement sucessmsgProj;
	
	
	public ActProjNCustPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public WebElement getCreateNewCustomerButton() 
	{
		return CreateNewCustomerButton;
	}

	public WebElement getSucessMsgEle() 
	{
		return sucessMsgEle;
	}

	public WebElement getSelectCustdrpdwn() 
	{
		return SelectCustdrpdwn;
	}

	public WebElement getShowBtn() 
	{
		return showBtn;
	}

	public WebElement getCustLink() 
	{
		return CustLink;
	}

	public WebElement getCreateNewProjectButton() 
	{
		return CreateNewProjectButton;
	}

	public WebElement getProjLink() 
	{
		return projLink;
	}

	public WebElement getSucessmsgProj() 
	{
		return sucessmsgProj;
	}

	
	}

	
	
	

	
	
	

