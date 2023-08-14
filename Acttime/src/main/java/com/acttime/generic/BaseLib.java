package com.acttime.generic;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseLib 
{
public static WebDriver driver;

@BeforeMethod
@Parameters({"browser" , "baseurl"})
public void PreCondition(String BrowserName, String url) throws IOException
{
	driver = BrowserFactory.launchbrowser(BrowserName);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	driver.get(url);
	Reporter.log(BrowserName + "launched and url navigated", true);
}

@AfterMethod
public void postCondition()
{
	driver.close();
	Reporter.log("browser closed");
}



}
