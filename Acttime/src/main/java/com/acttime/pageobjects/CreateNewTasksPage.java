package com.acttime.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewTasksPage 
{
	@FindBy(name="customerId")
	private WebElement CustNameDrpDwn;
	
	@FindBy(name="projectId")
	private WebElement ProjNameDrpDwn;
	
	@FindBy(xpath="//input[@class='text']")
	private List<WebElement> tasktxt;
	
	@FindBy(xpath="//img[@id='ext-gen7']")
	private WebElement DeadlineDateTxt;
	
	@FindBy(xpath = "//table[contains(@class,'x-date-inner')]//tr[5]//span[text()='27']")
	private WebElement selectTaskDeadLineDateElm;
	
	@FindBy(xpath="//option[@value='1']")
	private WebElement BilltypeSelect;

	@FindBy(xpath = "//input[contains(@name,'task[0].markedToBeAddedToUserTasks')]")
	private WebElement createdTaskAddToMyTimeTrackElm;
	
	@FindBy(xpath="//input[@value='Create Tasks']")
	private WebElement CreateTaskBtn;
	
	@FindBy(xpath= "//table[@id='SuccessMessages']//span")
	private WebElement SucessMsgTask;
	
	public CreateNewTasksPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public WebElement getCustNameDrpDwn() 
	{
		return CustNameDrpDwn;
	}

	public WebElement getProjNameDrpDwn() 
	{
		return ProjNameDrpDwn;
	}

	public List<WebElement> getTasktxt() 
	{
		return tasktxt;
	}

	public WebElement getDeadlineDateTxt() 
	{
		return DeadlineDateTxt;
	}

	public WebElement getBilltypeSelect() 
	{
		return BilltypeSelect;
	}

	

	public WebElement getselectTaskDeadLineDateElm() {
		
		return selectTaskDeadLineDateElm;
	}

	public WebElement getCreatedTaskAddToMyTimeTrackElm() {
		return createdTaskAddToMyTimeTrackElm;
	}

	public WebElement getCreateTaskBtn() 
	{
		return CreateTaskBtn;
	}

	public WebElement getSucessMsgTask() {
		return SucessMsgTask;
	}
	
	
	
	
	
	
}
