package com.acttime.features;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.acttime.pageobjects.LoginPage;

public class LoginFeatures 
{
	WebDriver driver;
	LoginPage lp;
	
	public LoginFeatures(WebDriver driver)
	{
		this.driver = driver;
		lp = new LoginPage(driver);
	}
	
	//feature
	public void login(String username, String password)
	{
		lp.getUnTxtBx().sendKeys(username);
		lp.getPwdTxtBx().sendKeys(password);
		lp.getLoginButton().click();
	}
	
	public void verifyinvalidlogin()
	{
		String ExpMsg = "Username or Password is invalid. Please try again.";
		String actualMsg = lp.getErrmsgEle().getText();
		Assert.assertEquals(actualMsg, ExpMsg);
		
		Reporter.log("invalid login is verified", true);
	}
	
	
	
}
