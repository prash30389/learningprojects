package com.acttime.features;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.acttime.pageobjects.EnterTimeTrackPage;

public class TimeTrackFeatures 
{
WebDriver driver;
EnterTimeTrackPage etp;

public TimeTrackFeatures(WebDriver driver)
{
	this.driver= driver;
	etp = new EnterTimeTrackPage(driver);
}

public void verifyEnterTimeTrackPage()
{
	String ExpText="Enter Time-Track";
	String actualTxt = etp.getEnterTimeTrackTitle().getText();
	Assert.assertEquals(actualTxt, ExpText);
	
	String expTitle = "actiTIME - Enter Time-Track";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expTitle);
	
	
	
	
	Reporter.log("EnterTimeTrackPage is verified", true);
}





}
