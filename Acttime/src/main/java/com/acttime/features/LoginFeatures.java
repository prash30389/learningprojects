package com.acttime.features;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.acttime.pageobjects.LoginPage;

public class LoginFeatures {
	WebDriver driver;
	LoginPage lp;

	public LoginFeatures(WebDriver driver) {
		this.driver = driver;
		lp = new LoginPage(driver);
	}

	public void login(String username, String password) {
		lp.getUnameTxt().sendKeys(username);
		lp.getPwdtxt().sendKeys(password);
		lp.getLoginBtn().click();
	}

	public void verifyinvalidLogin() {
		String ExpTxt = "Username or Password is invalid. Please try again.";
		String actualTxt = lp.getErrorMsg().getText();

		Assert.assertEquals(actualTxt, ExpTxt);

		Reporter.log("invalidlogin is verified", true);

	}
}
