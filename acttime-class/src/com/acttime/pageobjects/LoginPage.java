package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 
{
@FindBy(name="username")
private WebElement unTxtBx;

@FindBy(name="pwd")
private WebElement pwdTxtBx;

@FindBy(id="loginButton")
private WebElement LoginButton;

@FindBy(xpath="//div[@id='ServerSideErrorMessage']//span")
private WebElement errmsgEle;

public LoginPage(WebDriver driver)
{
	PageFactory.initElements(driver, this);
}

public WebElement getUnTxtBx() 
	{
		return unTxtBx;
	}

public WebElement getPwdTxtBx() 
	{
		return pwdTxtBx;
	}

public WebElement getLoginButton() 
	{
		return LoginButton;
	}

public WebElement getErrmsgEle() 
{
	return errmsgEle;
}

}
