package StandardPayinPolicies;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import practice.dynamodb.CommonUtility;
import practice.testbase.IssuanceQueue;
import practice.testbase.*;
import practice.utility.InitiatePayment;
import practice.utility.ProposalRequest;
import practice.utility.ReadPaymentJSON;
import practice.utility.JSONUtils;
import practice.reports.Logger;
public class PosPolicies extends TestBase {
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
			
	@BeforeTest
	public void BeforeTest() {
		TestBase.init();
		try {
			CommonUtility.loadConfigFile();
			ReadPaymentJSON.getExpectedDetailsFromJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * Initiate Payment validation for NB Policy using RAZORPAY
	 */
	@Test(priority = 1, enabled = true, description = "Create Surrender Policy For POS type", groups = {"Policy - POS"})
	public void NewBusinessPolicyForPOStypeUsingRazorPay() throws Exception {
		
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.posPolicy_Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		System.out.println(pno);
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");
		//pno			= "ALI0QAP19501064";
		paymentAmt		= InitiatePayment.getNBQuotation(pno)+10000.00; //10000.00;
		dueDate 		= "";
		vendorCode 		= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 		= CommonUtility.prop.getProperty("mobileNumber");
		emailID 		= CommonUtility.prop.getProperty("emailID");
		paymentType		= CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType 	= CommonUtility.prop.getProperty("mandateTypeCCSI"); 
		paymentOption	= CommonUtility.prop.getProperty("paymentOptionCard");
		suspenseType	= JSONUtils.getValueFromConstant("constantValueFile","suspenseType.suspenseNB");
		
 
		Logger.log("*********************Payment initiate.**********************************");
		List<String> inipayRes = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
		custId 		= inipayRes.get(0);
		policyNumber= inipayRes.get(1);
		transactionId= inipayRes.get(2);
		orderId		= inipayRes.get(3);

		Logger.log("*********Get Mandate Status for Policy Number.*******************");
		InitiatePayment.getMandates(pno);
		
		Logger.log("*********************Payment checkout for New Business (NB).**********************************");
		InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
		
		Logger.log("*********************Verify Payment Info for New Business (NB).**********************************");
		InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
		System.out.println(pno);
		
		Logger.log("*********Get Mandate Status for Policy Number after payment done.*******************");
		InitiatePayment.getMandates(pno);
		
		Logger.log("verify Policy is in 'INFORCE' Status for Standard decision=>"+policyNumber);
		staticWait(20);
		IssuanceQueue.verifyStatus(pno);
		
		Logger.log("***********Check Suspense entry for NB type.************");
		InitiatePayment.getAllSuspenses(pno);
		InitiatePayment.getSuspenseEntryForPayment(pno, 0.00, suspenseType);
		
		Logger.log("***********Save Policy Number in Config file.************");
		TestBase.updatePropertiesFile("initiatePayoutFLNV", pno);
	}
	
	@Test(priority = 2, enabled = false, description = "Create Surrender Policy For POS type", groups = {"Policy - POS"})
	public void SurrenderPolicyForPOStypeUsingRazorPay() throws Exception {
		Logger.log("\n******** Test Scenario  :  Create Add Rider Policy in Inforce State ******\n");
		st = new SoftAssert(); 
		
	
		//***********************************POS Policy**********************************/
			
		//For POS Surrender Policy
		IssuanceQueue.mockBackdatedProposal(ProposalRequest.SurrenderPOSCode());
		
		IssuanceQueue.waitSec(1);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		System.out.println(pno);
		IssuanceQueue.waitSec(5);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");
		//pno 		= "ALI0QAP16042150";
		paymentAmt	= InitiatePayment.getNBQuotation(pno); //0.0;
		dueDate 	= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType	= CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
		paymentOption= CommonUtility.prop.getProperty("paymentOptionCard");
		suspenseType= JSONUtils.getValueFromConstant("constantValueFile","suspenseType.suspenseNB"); 
 
		Logger.log("*********************Payment initiate.**********************************");
		List<String> inipayRes = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
		custId 		= inipayRes.get(0);
		policyNumber= inipayRes.get(1);
		transactionId= inipayRes.get(2);
		orderId		= inipayRes.get(3);
		System.out.println(custId);
		System.out.println(policyNumber);
		System.out.println(transactionId);
		System.out.println(orderId);
		Logger.log("*********Get Mandate Status for Policy Number.*******************");
		InitiatePayment.getMandates(pno);
		
		Logger.log("*********************Payment checkout for New Business (NB).**********************************");
		InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
		
		Logger.log("*********************Verify Payment Info for New Business (NB).**********************************");
		InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
		
		//Logger.log("*********Get Mandate Status for Policy Number after payment done.*******************");
		InitiatePayment.getMandates(pno);
		
		Logger.log("verify Policy is in 'INFORCE' Status for Standard decision=>"+policyNumber);
		staticWait(20);
		IssuanceQueue.verifyStatus(pno);
		
		Logger.log("***********Check Suspense entry for NB type.************");
		InitiatePayment.getAllSuspenses(pno);
		}
}


