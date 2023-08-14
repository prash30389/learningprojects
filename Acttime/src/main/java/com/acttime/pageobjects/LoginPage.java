package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 

{
	@FindBy(xpath="//input[@name='username']")
	private WebElement UnameTxt;
	
	@FindBy(xpath = "//input[@name='pwd']")
	private WebElement Pwdtxt;
	
	@FindBy(xpath = "//a[@id='loginButton']")
	private WebElement LoginBtn;
	
	@FindBy(xpath ="//span[contains(text() , 'Username or Password is invalid. Please try again.')]")
	private WebElement ErrorMsg;
	
	public LoginPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public WebElement getErrorMsg() {
		return ErrorMsg;
	}

	public WebElement getUnameTxt() 
	{
		return UnameTxt;
	}

	public WebElement getPwdtxt() 
	{
		return Pwdtxt;
	}

	public WebElement getLoginBtn() 
	{
		return LoginBtn;
	}
	
	
}
