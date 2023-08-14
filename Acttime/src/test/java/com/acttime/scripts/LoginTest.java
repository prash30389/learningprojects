package com.acttime.scripts;
/**
 *@author prashant
 * 
 */
import org.testng.annotations.Test;

import com.acttime.features.LoginFeatures;
import com.acttime.features.TimeTrackFeatures;
import com.acttime.generic.BaseLib;
import com.acttime.generic.ExcelLib;

public class LoginTest extends BaseLib
/**
 * 
 */
{
	
  @Test(priority = 1)
  public void validLogin() 
	  {
		  //fetch data from excel file
		  ExcelLib el = new ExcelLib("./testdata/testdata.xlsx");
		  String username = el.GetData("Sheet1", 1, 1);
		  String password = el.GetData("Sheet1", 1, 2 ); 
		  
		  //call feature
		  LoginFeatures lf = new LoginFeatures(driver);
		  lf.login(username, password);
		  
		  TimeTrackFeatures ttf = new TimeTrackFeatures(driver);
		  ttf.VerifyEnterTimeTrackPage();
	  }
	
  @Test(priority = 2)
  public void invalidLogin() 
	  {
		  //fetch data from excel file
		  ExcelLib el = new ExcelLib("./testdata/testdata.xlsx");
		  String username = el.GetData("Sheet1", 2, 1);
		  String password = el.GetData("Sheet1", 2, 2 ); 
		  
		  //call feature
		  LoginFeatures lf = new LoginFeatures(driver);
		  lf.login(username, password);
		  lf.verifyinvalidLogin();
	  }
	  
	  
	  
 }

