package com.acttime.scripts;

import org.testng.annotations.Test;

import com.acttime.features.LoginFeatures;
import com.acttime.features.TimeTrackFeatures;
import com.acttime.generic.BaseLib;
import com.acttime.generic.ExcelLib;

public class LoginTest extends BaseLib
{
@Test(priority = 1)
public void validLogin()
{
	ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
	String username = elib.readData("Sheet1", 1, 1);
	String password = elib.readData("Sheet1", 1, 2);
	LoginFeatures lf = new LoginFeatures(driver);
	lf.login(username, password);
	
	TimeTrackFeatures ttf = new TimeTrackFeatures(driver);
	ttf.verifyEnterTimeTrackPage();
}

@Test(priority = 2)
public void invalidLogin()
{
	ExcelLib elib = new ExcelLib("./testdata/testdata.xlsx");
	String username = elib.readData("Sheet1", 2, 1);
	String password = elib.readData("Sheet1", 2, 2);
	LoginFeatures lf = new LoginFeatures(driver);
	lf.login(username, password);
	lf.verifyinvalidlogin();
}
}
