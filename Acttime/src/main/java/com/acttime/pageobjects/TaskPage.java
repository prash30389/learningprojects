package com.acttime.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TaskPage {

	@FindBy(xpath = "//a[text()='Projects & Customers']")
	private WebElement projectsNCustomersEle;

	@FindBy(xpath = "//input[@value='Create New Tasks']")
	private WebElement createNewTaskElm;
	
	@FindBy(xpath = "//table[contains(@class,'secondLevel firstActive ')]//a[text()='Open Tasks']")
	private WebElement openTaskElm;
	
	@FindBy(xpath = "//td[contains(@class,'listtblcell selectCell')]/input")
	private WebElement selectTaskElm;
	
	@FindBy(xpath = "//input[@value='Complete Selected Tasks']")
	private WebElement completedSelectedTaskButton;
	
	@FindBy(xpath = "//table[contains(@class,'secondLevel  ')]//td[4]//a")
	private WebElement completedTasksButton;
	
	

	
	public TaskPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	
	public WebElement getProjectsNCustomersEle() {
		return projectsNCustomersEle;
	}

	
	public WebElement getCreateNewTaskElm() {
		return createNewTaskElm;
	}


	public WebElement getOpenTaskElm() {
		return openTaskElm;
	}


	public WebElement getSelectTaskElm() {
		return selectTaskElm;
	}


	public WebElement getCompletedSelectedTaskButton() {
		return completedSelectedTaskButton;
	}


	public WebElement getCompletedTasksButton() {
		return completedTasksButton;
	}
	
	

}
