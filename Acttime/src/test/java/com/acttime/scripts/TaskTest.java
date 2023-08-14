package com.acttime.scripts;

import java.io.IOException;

import org.testng.annotations.Test;

import com.acttime.features.LoginFeatures;
import com.acttime.features.TaskFeatures;
import com.acttime.generic.BaseLib;
import com.acttime.generic.ExcelLib;

public class TaskTest extends BaseLib
{
	@Test(enabled = true)
	public void createCustomer()
	{
		ExcelLib el = new ExcelLib("./testdata/testdata.xlsx");
		  String username = el.GetData("Sheet1", 3, 1);
		  String password = el.GetData("Sheet1", 3, 2 );
		  
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		
		String CustomerName = el.GetData("Sheet1", 3, 3);
		TaskFeatures tf = new TaskFeatures(driver);
		tf.createCustomer(CustomerName);
		tf.verifycreateCust(CustomerName);
		tf.logout();
	}
	
	@Test(dependsOnMethods="createCustomer", enabled=false)
	public void DeleteCustomer()
	{
		ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
		String username = elib.GetData("Sheet1", 4, 1);
		String password = elib.GetData("Sheet1", 4, 2);
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		
		String customerName = elib.GetData("Sheet1", 4, 3);
		TaskFeatures tf = new TaskFeatures(driver);
		tf.DelCustomer(customerName);
		tf.VerifyDelCustomer(customerName);
		tf.logout();
	}
	
	@Test(dependsOnMethods = { "createCustomerTest" }, enabled = true)
	public void CreateProject()
	{
		ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
		String username = elib.GetData("Sheet1", 5, 1);
		String password = elib.GetData("Sheet1", 5, 2);
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		TaskFeatures tf = new TaskFeatures(driver);
		String customerName = elib.GetData("Sheet1", 5, 3);
		
		String ProjectName = elib.GetData("Sheet1", 5, 4);
		tf.CreateProject(customerName, ProjectName);
	}
	
	@Test(dependsOnMethods="CreateProject", enabled=false)
	public void DeleteProject()
	{
		ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
		String username = elib.GetData("Sheet1", 5, 1);
		String password = elib.GetData("Sheet1", 5, 2);
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		TaskFeatures tf = new TaskFeatures(driver);
		String ProjectName = elib.GetData("Sheet1", 5, 4);
		tf.DeleteProject(ProjectName);
		tf.VerifyDeleteProject();
		tf.logout();
	}
	
	@Test(dependsOnMethods="CreateProject", enabled=true)
	public void CreateTask()
	{
		ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
		String username = elib.GetData("Sheet1", 6, 1);
		String password = elib.GetData("Sheet1", 6, 2);
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		TaskFeatures tf = new TaskFeatures(driver);
		String customerName = elib.GetData("Sheet1", 6, 3);
		String ProjectName = elib.GetData("Sheet1", 6, 4);
		String TaskName = elib.GetData("Sheet1", 6, 5);
		String Date = elib.GetData("Sheet1", 6, 6);
		tf.CreateTask(customerName, ProjectName, TaskName, Date);
		tf.VerifyCreateTask(customerName, ProjectName);
		tf.logout();
	}
	
	@Test(dependsOnMethods = { "createNewTaskTest" },enabled=true)
	public void completeTaskTest() throws IOException
	{
		
		ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
		String username = elib.GetData("Sheet1", 1, 1);
		String password = elib.GetData("Sheet1", 2, 1);
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		TaskFeatures tf = new TaskFeatures(driver);
		tf.completeTask();
		tf.verifyCompleteTask();
		
	}
	@Test(priority =4, dependsOnMethods="CreateTask")
	public void DeleteTask()
	{
		ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
		String username = elib.GetData("Sheet1", 7, 1);
		String password = elib.GetData("Sheet1", 7, 2);
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		TaskFeatures tf = new TaskFeatures(driver);
		String TaskName =elib.GetData("Sheet1", 7, 3);
		tf.DeleteTask(TaskName);
		tf.VerifyDeleteTask();
		tf.logout();
		
	}
}
