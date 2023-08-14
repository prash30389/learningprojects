package AssignmentQspiders;


import java.util.Date;


import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class MyTestListener  implements ITestListener {
	
public static int executionCount, passCount, failCount, skipCount =0; 
	
	public void onTestStart(ITestResult result) 
	{
		executionCount++;
		Reporter.log(result.getName() + " Script Execution starts" + new Date(), true); 
		
	}

	public void onTestSuccess(ITestResult result) 
	{
		passCount++;
		Reporter.log(result.getName() + " Script Execution success", true);
		
	}

	public void onTestFailure(ITestResult result, WebDriver driver, String fileName) throws Exception
	{
		failCount++;
		
		Reporter.log(result.getName() + " Script Execution Failed", true);
//		TakesScreenshot ts = (TakesScreenshot) BaseLib.driver;
//		 
//		File srcFile= ts.getScreenshotAs(OutputType.FILE);
//		File destFile = new File("./screenshots/" + result.getName() + ".png");
//		
		
//		try 
//		{
//			
//			FileHandler.copy(srcFile, destFile);
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
//		
//		Reporter.log("screenshot has been taken", true);
	}
		
	

	public void onTestSkipped(ITestResult result) 
	{
		skipCount ++;
		Reporter.log(result.getName() + " Script Execution skipped", true);
		
	}

	public void onStart(ITestContext context) 
	{
		Reporter.log("Suite Execution starts" + new Date(), true);
		
	}

	public void onFinish(ITestContext context) 
	{
		Reporter.log(" Suite Execution finish" + new Date(), true);
		Reporter.log("Total Scripts Executed : " + executionCount, true);
		Reporter.log("Total Scripts passed : " + passCount, true);
		Reporter.log("Total Scripts failed : " + failCount, true);
		Reporter.log("Total Scripts skipped : " + skipCount, true);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	

}
