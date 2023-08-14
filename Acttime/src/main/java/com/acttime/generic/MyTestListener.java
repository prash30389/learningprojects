package com.acttime.generic;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class MyTestListener implements ITestListener


{
	WebDriver driver;
public static int ExecutionCount, PassCount, failCount, SkipCount = 0;

public void onTestStart(ITestResult result) 
{
	ExecutionCount ++;
	Reporter.log(result.getName() + "Execution of test script start" + new Date(), true);

}


public void onTestSuccess(ITestResult result) 
{
	PassCount ++;
	Reporter.log(result.getName() + "Execution of test script passed" + new Date(), true);
}


public void onTestFailure(ITestResult result) 
{
	failCount ++;
	Reporter.log(result.getName() + "Execution of test script failed" + new Date(), true);
	TakesScreenshot ts =(TakesScreenshot) driver;
	
	File srcfile = ts.getScreenshotAs(OutputType.FILE);
	File DestFile = new File("./screenshots/" +result.getName() + ".png");
	
	try 
	{
		FileHandler.copy(srcfile, DestFile);
	} 
	catch (IOException e) 
	{
		
		e.printStackTrace();
	}
}


public void onTestSkipped(ITestResult result) 
{
SkipCount ++;
Reporter.log(result.getName() + "Test Script skipped" + new Date(), true);
}


public void onStart(ITestContext context) 
{
	Reporter.log("Execution Starts " + new Date(), true);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	boolean status = js.executeScript("return document.readyState").toString().equalsIgnoreCase("complete");
	if(status) 
	{
		Reporter.log("page is loaded completely");
	} 
	else{
		Reporter.log("Waiting for the page");
	}
	
	/*for(int i =0; i<30; i++)
	{
		try
		{
			Thread.sleep(1000);
		}
	catch(Exception e)
		{
	
	if(js.executeScript("return document.readyState", 3).toString().equalsIgnoreCase("complete")) 
	{
		Reporter.log("page is loaded completely");
		break;
	} 
	else{
		Reporter.log("Waiting for the page");
	}
	}*/
}


public void onFinish(ITestContext context) 
{
	Reporter.log("Execution End " + new Date(), true);
	
	Reporter.log("Total Scripts Executed : " + ExecutionCount, true);
	Reporter.log("Total Scripts passed : " + PassCount, true);
	Reporter.log("Total Scripts failed : " + failCount, true);
	Reporter.log("Total Scripts skipped : " + SkipCount, true);

}


public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) 
{
	
}

	

}
