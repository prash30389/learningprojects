package TestNGExercises;

// it is used to monitor the events and runtime events


import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener
{

	public void onTestStart(ITestResult result) 
	{
		System.out.println(result.getName() + " Script Execution starts" + new Date());
		
	}

	public void onTestSuccess(ITestResult result) 
	{
		System.out.println(result.getName() + " Script Execution success");
		
	}

	public void onTestFailure(ITestResult result) 
	{
		System.out.println(result.getName() + " Script Execution Failed");
		
	}

	public void onTestSkipped(ITestResult result) 
	{
		System.out.println(result.getName() + " Script Execution skipped");
		
	}

	public void onStart(ITestContext context) 
	{
		System.out.println(" Suite Execution starts" + new Date());
		
	}

	public void onFinish(ITestContext context) 
	{
		System.out.println(" Suite Execution finish" + new Date());
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{
		// TODO Auto-generated method stub
		
	}

}
