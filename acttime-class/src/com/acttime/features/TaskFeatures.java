package com.acttime.features;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import com.acttime.pageobjects.ActivProjCustPage;
import com.acttime.pageobjects.CreateNewCustPage;
import com.acttime.pageobjects.EditCustInformationPage;
import com.acttime.pageobjects.EnterTimeTrackPage;
import com.acttime.pageobjects.OpenTaskPage;

public class TaskFeatures 
{
WebDriver driver;
EnterTimeTrackPage etp;
OpenTaskPage otp;
ActivProjCustPage apcp;
CreateNewCustPage cncp;
EditCustInformationPage ecip;

public TaskFeatures(WebDriver driver) 
{
	this.driver= driver;
	etp = new EnterTimeTrackPage(driver);
	otp = new OpenTaskPage(driver);
	apcp = new ActivProjCustPage(driver);
	cncp= new CreateNewCustPage(driver);
	ecip = new EditCustInformationPage(driver);
}

public void createcustomer(String customerName)
{
	etp.getTaskEle().click();
	otp.getProjectsNCustomersEle().click();
	apcp.getCreateNewCustBtn().click();
	cncp.getCustTxtBx().sendKeys(customerName);
	cncp.getCreateCustomerBtn().click();
}
public void verifyCreateCust(String customerName)
{
	String ExpMsg= "Customer \""+customerName+"\" has been successfully created.";
	String ActualMsg = apcp.getSucessMsgEle().getText();
	
	Assert.assertEquals(ActualMsg, ExpMsg);
	Reporter.log(ExpMsg, true);
}
	
public void logout()
	{
	apcp.getLogoutBtn().click();
	}

public void deleteCustomer(String customerName)
{
	etp.getTaskEle().click();
	otp.getProjectsNCustomersEle().click();
	Select sel = new Select(apcp.getSelectCustdrpdwn());
	sel.selectByVisibleText(customerName);
	apcp.getShowBtn().click();
	apcp.getCustLink().click();
	ecip.getDelThisCustEle().click();
	ecip.getDeletePopupEle().click();
	
}
public void verifydeleteCust(String ExpCustomer)
	{
		String ExpTxt="Customer has been successfully deleted.";
		String actualTxt=apcp.getSucessMsgEle().getText();
		Assert.assertEquals(actualTxt, ExpTxt);
		
		Select sel = new Select(apcp.getSelectCustdrpdwn());
		List<WebElement> options = sel.getOptions();
		for (int i = 0; i < options.size(); i++) 
		{
			String actualCustomer = options.get(i).getText();
			Assert.assertNotEquals(actualCustomer, ExpCustomer );
		}
		
		Reporter.log(ExpTxt, true );
		
	}
	
}




