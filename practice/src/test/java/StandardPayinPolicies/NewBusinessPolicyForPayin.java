package StandardPayinPolicies;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import practice.utility.InitiatePayment;
import practice.utility.JSONUtils;

import practice.dynamodb.CommonUtility;
import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import practice.utility.ProposalRequest;

/**
 * @author prashant.s
 *
 */
public class NewBusinessPolicyForPayin extends TestBase {
	public String vendorCode = "";
	public String custId = "";
	public String policyNumber = "";
	public String transactionId = "";
	public String orderId = "";
	public double paymentAmt = 0.0; 
	public String paymentType = "";
	public String mandateType = "";
	public String mobileNum = "";
	public String emailID = "";
	public String dueDate = "";
	public String suspenseType = "";
	public String paymentOption = "";
	public String dob = "";
	public String lapsedPolicy="";
	public String lapsedPolicyeMandate="";
	public String gracePolicy="";
	public String gracePolicyeMandate="";
	public String cancellationType = null;
	
	@BeforeTest(alwaysRun = true)
	public void BeforeTest()
	{
		TestBase.init();
		try {
			CommonUtility.loadConfigFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(priority=1, enabled=true, description ="TestCase For Payin-New Business Policy using Razorpay Vendor", groups="Payin")
	@Parameters("vendor")
	public void PayinNewBusinesspolicy(String vendor) throws Exception
	{
		Logger.log("++++++++++++++++++++++++++++++++++Payin_NewBusinessPolicy Method Started++++++++++++++++++++++++++++++++++");
		Logger.log("***** Start Creating Procedures for NB Policy *****");
		Logger.log("\n Test Scenario  :  Payin => Initiate Payment for NB using RAZORPAY \n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(2);
		Logger.log("Get Status code of API : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		System.out.println(pno);
		IssuanceQueue.waitSec(2);	
		Logger.log("*********************Procedure for Get Policy Payment amt of NB Policy**********************************");
		//pno			= "ALI0QAP19501064";
		paymentAmt		= InitiatePayment.getNBQuotation(pno)+5000.00; //10000.00;
		Logger.log("Policy Payment amount fetched");
		
		dueDate 		= "";
		mobileNum 		= CommonUtility.prop.getProperty("mobileNumber");
		Logger.log("mobile number fetched from 'config.properties' File ");
		emailID 		= CommonUtility.prop.getProperty("emailID");
		Logger.log("emailID fetched from 'config.properties' File ");
		paymentType		= CommonUtility.prop.getProperty("paymentTypeNB");
		Logger.log("paymentType fetched from 'config.properties' File ");
		mandateType 	= CommonUtility.prop.getProperty("mandateTypeCCSI");
		Logger.log("mandateType fetched from 'config.properties' File ");
		paymentOption	= CommonUtility.prop.getProperty("paymentOptionCard");
		Logger.log("paymentOption fetched from 'config.properties' File ");
		suspenseType	= JSONUtils.getValueFromConstant("constantValueFile","suspenseType.suspenseNB");
		Logger.log("get suspenseType From Constant File ' constantValueFile ' ");
		cancellationType= JSONUtils.getValueFromConstant("constantValueFile", "cancellationType.freelook");
		Logger.log("get cancellationType From Constant File ' constantValueFile ' ");
		Logger.log("*********************Payment initiate.**********************************");
		List<String> inipayRes = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendor, mandateType);
		Logger.log("response of initiatePayment method saved in reference variable inipayRes [ Type : List<String> ]  ");
		custId 		= inipayRes.get(0);
		Logger.log("custId fetched from inipayRes  ");
		policyNumber= inipayRes.get(1);
		Logger.log("policyNumber fetched from inipayRes  ");
		transactionId= inipayRes.get(2);
		Logger.log("transactionId fetched from inipayRes  ");
		orderId		= inipayRes.get(3);
		Logger.log("orderId fetched from inipayRes  ");

		Logger.log("*********Get Mandate Status for Policy Number*******************");
		InitiatePayment.getMandates(pno);
		
		Logger.log("*********************Payment checkout for New Business (NB).**********************************");
		InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
		
		Logger.log("*********************Verify Payment Info for New Business (NB).**********************************");
		InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendor, "SUCCESSFUL", paymentOption);
		System.out.println(pno);
		Logger.log("*********Get Mandate Status for Policy Number after payment done.*******************");
		InitiatePayment.getMandates(pno);
		
		Logger.log("verify Policy is in 'INFORCE' Status for Standard decision=>"+policyNumber);
		staticWait(20);
		IssuanceQueue.verifyStatus(pno);
		
		Logger.log("***********Check Suspense entry for NB type.************");
		InitiatePayment.getAllSuspenses(pno);
		InitiatePayment.getSuspenseEntryForPayment(pno, 0.00, suspenseType);
		
		Logger.log("************************* Token Generation ********************");
		IssuanceQueue.getToken_ops();
		
		Logger.log("***********Save Policy Number in Config file.************");
		TestBase.updatePropertiesFile("initiatePayoutFLNV", pno);
	
		Logger.log("***** NB Policy Created *****");
		Logger.log("++++++++++++++++++++++++++++++++++Payin_NewBusinessPolicy Method Completed++++++++++++++++++++++++++++++++++");
	}
}
