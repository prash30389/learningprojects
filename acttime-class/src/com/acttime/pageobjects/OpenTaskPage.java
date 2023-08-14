package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpenTaskPage 
{
@FindBy(xpath ="//a[text()='Projects & Customers']")
private WebElement projectsNCustomersEle;

public OpenTaskPage(WebDriver driver)
{
	PageFactory.initElements(driver, this);
}

public WebElement getProjectsNCustomersEle() {
	return projectsNCustomersEle;
}


}
