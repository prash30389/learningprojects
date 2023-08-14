package com.acttime.scripts;

import org.testng.annotations.Test;

import com.acttime.features.LoginFeatures;
import com.acttime.features.TaskFeatures;
import com.acttime.generic.BaseLib;
import com.acttime.generic.ExcelLib;

public class TaskTest extends BaseLib
{

	@Test
	public void createCustomer()
	{
		ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
		String username = elib.readData("Sheet1", 3, 1);
		String password = elib.readData("Sheet1", 3, 2);
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		
		String customerName =elib.readData("Sheet1", 3, 3);
		TaskFeatures tf = new TaskFeatures(driver);
		tf.createcustomer(customerName);
		tf.verifyCreateCust(customerName);
		tf.logout();
	}
	
	@Test(dependsOnMethods={"createCustomer"})
	public void deleteCustomer()
	{
		ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
		String username = elib.readData("Sheet1", 4, 1);
		String password = elib.readData("Sheet1", 4, 2);
		LoginFeatures lf = new LoginFeatures(driver);
		lf.login(username, password);
		
		String customerName= elib.readData("Sheet1", 4, 3);
		TaskFeatures tf= new TaskFeatures(driver);
		tf.deleteCustomer(customerName);
		tf.verifydeleteCust(customerName);
		tf.logout();
		
		
		
	}
	
	
	
	
}
