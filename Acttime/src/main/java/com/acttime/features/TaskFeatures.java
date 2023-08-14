package com.acttime.features;

import java.util.List;

import org.openqa.selenium.By;
/**
 * @author prashant
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import com.acttime.pageobjects.ActProjNCustPage;
import com.acttime.pageobjects.BasePage;
import com.acttime.pageobjects.CreateNewCustPage;
import com.acttime.pageobjects.CreateNewProjectPage;
import com.acttime.pageobjects.CreateNewTasksPage;
import com.acttime.pageobjects.EditCustInfoPage;
import com.acttime.pageobjects.OpenTaskPage;
import com.acttime.pageobjects.TaskPage;
import com.acttime.pageobjects.ViewOpenTaskPage;


public class TaskFeatures 
{
WebDriver driver;
BasePage bp;
OpenTaskPage otp;
ActProjNCustPage apcp;
CreateNewCustPage cncp;
EditCustInfoPage ecip;
CreateNewProjectPage cnpp;
CreateNewTasksPage cntp;
ViewOpenTaskPage votp;
TaskPage tp;

	public TaskFeatures(WebDriver driver)
		{
			this.driver=driver;
			bp = new BasePage(driver);
			otp = new OpenTaskPage(driver);
			apcp = new ActProjNCustPage(driver);
			cncp= new CreateNewCustPage(driver);
			ecip = new EditCustInfoPage(driver);
			cnpp= new CreateNewProjectPage(driver);
			cntp = new CreateNewTasksPage(driver);
			votp = new ViewOpenTaskPage(driver);
			tp = new TaskPage(driver);
		}

	public void createCustomer(String CustomerName)
		{
			bp.getTaskEle().click();
			otp.getProjectsNCustomersEle().click();
			apcp.getCreateNewCustomerButton().click();
			cncp.getCustTxtBx().sendKeys(CustomerName);
			cncp.getCreateCustomerBtn().click();
			
			
		}
	public void verifycreateCust (String customerName)
		{
		String ExpTxt = "Customer \""+customerName+"\" has been successfully created.";
		String actualTxt = apcp.getSucessMsgEle().getText();
		Assert.assertEquals(actualTxt, ExpTxt);
		
		Reporter.log(ExpTxt, true);
		}
	public void logout()
		{
			apcp.getLogoutBtn().click();
		}
	
	public void DelCustomer(String CustomerName)
	{
		
		bp.getTaskEle().click();
		otp.getProjectsNCustomersEle().click();
		Select sel = new Select(apcp.getSelectCustdrpdwn());
		sel.selectByVisibleText(CustomerName);
		apcp.getShowBtn().click();
		apcp.getCustLink().click();
		ecip.getDelcustEle().click();
		ecip.getDeletePopupEle().click();
		
		
	}
	
	public void VerifyDelCustomer(String ExpCustomer)
	{
		String ExpTxt="Customer has been successfully deleted.";
		String ActualTxt = apcp.getSucessMsgEle().getText();
		Assert.assertEquals(ActualTxt, ExpTxt);
		
		Select sel = new Select(apcp.getSelectCustdrpdwn());
		List<WebElement> options = sel.getOptions();
		
		for (int i = 0; i < options.size(); i++) 
		{
			String actualCustomer= options.get(i).getText();
			Assert.assertNotEquals(actualCustomer, ExpCustomer);
		}
		
		Reporter.log(ExpTxt, true);
	}
	
	public void CreateProject(String CustomerName, String ProjectName)
	{
		bp.getTaskEle().click();
		otp.getProjectsNCustomersEle().click();
		/*apcp.getCreateNewCustomerButton().click();
		cncp.getCustTxtBx().sendKeys(CustomerName);
		cncp.getCreateCustomerBtn().click();*/
		cnpp.getCreateProjectButton().click();
		Select sel = new Select(cnpp.getCustDrpDwn());
		sel.selectByVisibleText(CustomerName);
		cnpp.getProjNameTxt().sendKeys(ProjectName);
		cnpp.getSubmitBtn().click();
		
		
	}
	
	public void VerifyCreateProject(String ProjectName)
	{
		String ExpTxt = "Project \""+ProjectName+"\" has been successfully created.";
		String actualTxt = apcp.getSucessMsgEle().getText();
		Assert.assertEquals(actualTxt, ExpTxt);
		
		Reporter.log(ExpTxt, true);
	}
	
	public void DeleteProject(String ProjectName)
	{
		bp.getTaskEle().click();
		otp.getProjectsNCustomersEle().click();
		apcp.getProjLink().click();
		ecip.getDelProjEle().click();
		ecip.getDelProjPopupEle().click();
		
		
	}
	
	public void VerifyDeleteProject()
	{
		String ExpText="Project has been successfully deleted.";
		String ActualText = apcp.getSucessmsgProj().getText();
		Assert.assertEquals(ActualText, ExpText);
		
		Reporter.log(ExpText, true);
	}
	public void CreateTask(String customerName, String ProjectName, String TaskName, String Date )
	{
		bp.getTaskEle().click();
		otp.getCreateNewTasksBtn().click();
		WebElement selectCustomerToAddTask = cntp.getCustNameDrpDwn();
		Select sel = new Select(selectCustomerToAddTask);
		sel.selectByVisibleText(customerName);
		WebElement selectProjectToAddTask = cntp.getProjNameDrpDwn();
		Select sel2 = new Select(selectProjectToAddTask);
		sel2.selectByVisibleText(ProjectName);
		String firstXpath = "//input[@name='task[";
		String secondXpath = "].name']";
		List<WebElement> taskNameElement = cntp.getTasktxt();
		int taskElementSize = taskNameElement.size();
		for (int i = 0; i < taskElementSize - 4; i++) {
			String finalXpath = firstXpath + i + secondXpath;
			WebElement createTaskInputField = driver.findElement(By.xpath(finalXpath));
			createTaskInputField.sendKeys(TaskName);
			cntp.getDeadlineDateTxt().sendKeys(Date);
			cntp.getselectTaskDeadLineDateElm().click();
			cntp.getBilltypeSelect().click();
			cntp.getCreatedTaskAddToMyTimeTrackElm().click();
			cntp.getCreateTaskBtn().click();
		}
	}
	
	public void VerifyCreateTask(String customerName, String ProjectName)
	{
		String ExpText = "1 new task was added to the customer \""+customerName+"\", project \""+ProjectName+"\".";
		String actualText= cntp.getSucessMsgTask().getText();
		Assert.assertEquals(actualText, ExpText);
		
		Reporter.log(ExpText, true);
	}
	
	
	
	public void completeTask() {
		bp.getTaskEle().click();
		tp.getSelectTaskElm().click();
		tp.getCompletedSelectedTaskButton().click();		
		
	}
	
	
	public void verifyCompleteTask() {
		
		String ExpMsg ="Selected tasks have been successfully completed.";
		String actualMsg = bp.getSucessMsgEle().getText();
		Assert.assertEquals(actualMsg, ExpMsg);
		
		
	
		
	}
	
	public void DeleteTask(String TaskName)
	{
		bp.getTaskEle().click();
		otp.getTaskEleLink().click();
		votp.getDeleteTaskBtn().click();
		votp.getDelConfirmBtn().click();	
	}
	
	public void VerifyDeleteTask()
	{
		String ExpText="Task has been successfully deleted.";
		String ActualText = otp.getVerifyMsgEle().getText();
		Assert.assertEquals(ActualText, ExpText);
		
		Reporter.log(ExpText, true);
	}
}


