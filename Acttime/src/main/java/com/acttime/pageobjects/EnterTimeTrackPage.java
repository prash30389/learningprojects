package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EnterTimeTrackPage 
{
@FindBy(xpath = "//td[contains(text(),'Enter Time-Track')]")
private WebElement timetrackTitle;

public EnterTimeTrackPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

public WebElement getTimetrackTitle() 
{
	return timetrackTitle;
}



}
