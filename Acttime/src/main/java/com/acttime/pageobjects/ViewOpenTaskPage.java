package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewOpenTaskPage 
	{
	@FindBy(css="input[value='Delete This Task']")
	private WebElement DeleteTaskBtn;
	
	@FindBy(id="deleteButton")
	private WebElement DelConfirmBtn;
	
	public ViewOpenTaskPage(WebDriver driver)
		{
			PageFactory.initElements(driver, this);
		}
	
	
	public WebElement getDeleteTaskBtn() 
		{
			return DeleteTaskBtn;
		}
	
	public WebElement getDelConfirmBtn() 
		{
			return DelConfirmBtn;
		}
	}
