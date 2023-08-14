package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ActivProjCustPage extends BasePage
	{
		@FindBy(css="input[value='Create New Customer']")
		private WebElement createNewCustBtn;
		
		@FindBy(xpath ="//table[@id='SuccessMessages']//span")
		private WebElement sucessMsgEle; 
		
		@FindBy(name="selectedCustomer")
		private WebElement SelectCustdrpdwn;
		
		@FindBy(css="input[value*='Show']")
		private WebElement showBtn;
		
		@FindBy(xpath="//td[starts-with(@id,'customerNameCell')]//a[contains(@href,'customer')]")
		private WebElement CustLink;
		
		
	public ActivProjCustPage(WebDriver driver)
			{
				super(driver);
				PageFactory.initElements(driver, this);
			}
		
	public WebElement getCreateNewCustBtn() 
			{
				return createNewCustBtn;
			}
	
	
	

	public WebElement getCustLink() 
	{
		return CustLink;
	}

	public WebElement getSelectCustdrpdwn() {
		return SelectCustdrpdwn;
	}


	public WebElement getShowBtn() {
		return showBtn;
	}

	public WebElement getSucessMsgEle() 
			{
				return sucessMsgEle;
			}
	
	}
