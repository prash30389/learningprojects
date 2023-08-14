package com.acttime.features;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.acttime.pageobjects.EnterTimeTrackPage;

public class TimeTrackFeatures //feature name should start with process name/module name
{
WebDriver driver;
EnterTimeTrackPage etp;

public TimeTrackFeatures(WebDriver driver)
	{
		this.driver=driver;
		etp = new EnterTimeTrackPage(driver);
	}

public void VerifyEnterTimeTrackPage()
{
	String expTxt= "Enter Time-Track";
	String actualTxt = etp.getTimetrackTitle().getText();
	Assert.assertEquals(expTxt, actualTxt);
	
	String expTitle = "actiTIME - Enter Time-Track";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(expTitle, actualTitle);
	
	Reporter.log("EnterTimeTrackPage is verified", true);
	
	
	
}
	
}
