package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage 
{
	@FindBy(xpath="//div[contains(text(),'Tasks')]")
	private WebElement TaskEle;
	
	@FindBy(id="logoutLink")
	private WebElement LogoutBtn;
	
	@FindBy(xpath = "//table[@id='SuccessMessages']//span")
	private WebElement sucessMsgEle;	
	
	
	@FindBy(xpath = "//form[@name='TaskListForm']/table/tbody/tr[5]//table[2]//tr[2]/td[3]")
	private WebElement verifyMessage;

	public BasePage(WebDriver driver)
		{
			PageFactory.initElements(driver, this);
		}

	public WebElement getTaskEle() 
		{
			return TaskEle;
		}

	public WebElement getLogoutBtn() 
	{
		return LogoutBtn;
	}

	public WebElement getSucessMsgEle() {
		return sucessMsgEle;
	}

	public WebElement getVerifyMessage() {
		return verifyMessage;
	}
	
	
}
