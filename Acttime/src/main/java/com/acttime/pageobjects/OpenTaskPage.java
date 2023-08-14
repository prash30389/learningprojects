package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpenTaskPage 
{
	@FindBy(xpath ="//a[text()='Projects & Customers']")
	private WebElement projectsNCustomersEle;
	
	@FindBy(xpath="//input[contains(@value,'Create New Tasks')]")
	private WebElement CreateNewTasksBtn;

	@FindBy(xpath="//td[contains(@id,'taskNameCell')]/a")
	private WebElement TaskEleLink;
	
	@FindBy(xpath="//span[contains(text(),'Task has been successfully deleted.')]")
	private WebElement verifyMsgEle;
	
	public OpenTaskPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public WebElement getProjectsNCustomersEle() 
	{
		return projectsNCustomersEle;
	}

	public WebElement getCreateNewTasksBtn() 
	{
		return CreateNewTasksBtn;
	}

	public WebElement getTaskEleLink() 
	{
		return TaskEleLink;
	}

	public WebElement getVerifyMsgEle() 
	{
		return verifyMsgEle;
	}
	
	
}
