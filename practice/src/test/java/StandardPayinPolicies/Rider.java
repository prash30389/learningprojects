package StandardPayinPolicies;

import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import practice.dynamodb.CommonUtility;
import practice.dynamodb.Events;
import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import practice.utility.InitiatePayment;
import practice.utility.JSONUtils;
import practice.utility.ProposalRequest;

public class Rider extends TestBase {
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
			//ReadPaymentJSON.getExpectedDetailsFromJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * Initiate Payment validation for NB Policy using RAZORPAY
	 */
	@Test(priority = 1, enabled = true, description = "Create Add Rider Policy", groups = {"Payment - Initiate Payin Rider"})
	public void createAddRiderPolicyForNewBusinessUsingRazorPay() throws Exception {
		Logger.log("\n******** Test Scenario  :  Create Add Rider Policy in Inforce State ******\n");
		st = new SoftAssert(); 
		//***************************************Iterm7 Policy**********************************/
		//For Rider Freelook
		IssuanceQueue.mockAddRiderProposal(ProposalRequest.riderCode());  
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		System.out.println(pno);
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");
		//pno 		= "ALI0QAP16042150";
		paymentAmt	= InitiatePayment.getNBQuotation(pno)+500.0; //0.0;
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
		//InitiatePayment.getMandates(pno);
		
		Logger.log("*********************Payment checkout for New Business (NB).**********************************");
		InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
		
		Logger.log("*********************Verify Payment Info for New Business (NB).**********************************");
		InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
		
		//Logger.log("*********Get Mandate Status for Policy Number after payment done.*******************");
		//InitiatePayment.getMandates(pno);
		
		Logger.log("verify Policy is in 'INFORCE' Status for Standard decision=>"+policyNumber);
		staticWait(20);
		IssuanceQueue.verifyStatus(pno);
		
		Logger.log("***********Check Suspense entry for NB type.************");
		InitiatePayment.getAllSuspenses(pno);
		
		Logger.log("verify Policy is in 'INFORCE' Status for Standard decision=>" + policyNumber);
		staticWait(30);
		IssuanceQueue.verifyStatus(pno);
		Logger.log("***********Save Policy Number in Config file.************");
		TestBase.updatePropertiesFile("initiatePayoutFR", pno);
		}
	@Test(priority = 2, enabled = false, dependsOnMethods={"createAddRiderPolicyForNewBusinessUsingRazorPay"}, description = "TC - To verify dynamodb events for Razorpay Payin", groups = {"Payment - Initiate Payin Rider"})
	public void verifyPaymentEventsRazorpay() throws Exception
	{
		Logger.log("*************Check events in dynamodb.************"); 
		IssuanceQueue.waitSec(4);
		Events.checkEvents_InitiatePaymentPayin(pno);
	}
	
	/*
	 * Validation Vendor Code Not Empty
	 */
	@Test(priority = 3, enabled = false, description = "TC to verify Error throws when Vendor Code is Empty.", groups = {"Payment - Initiate Payin Rider"})
	public void verifyVendorCodeNotEmpty() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when Vendor Code is Empty.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");		
		paymentAmt 	= InitiatePayment.getNBQuotation(pno);
		dueDate 	= "";
		vendorCode 	= "";
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Mandatory Fields in Payment Request
	 */
	@Test(priority = 4, enabled = false, description = "TC to verify mobileNumber and email mandatory in payment request if vendor code is RAZORPAY", groups = {"Payment - Initiate Payin Rider"})
	public void verifyMandatoryFieldsInPaymentRequest() throws Exception {
		Logger.log("\n******** Test Scenario : Verify mobile Number and email mandatory in payment request if vendor code is RAZORPAY.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");		
		paymentAmt 	= InitiatePayment.getNBQuotation(pno);
		dueDate		= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= "";
		emailID 	= "";
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Request Amt can't be zero other than eManadte Payment
	 */
	@Test(priority = 5, enabled = false, groups = {"Payment - Initiate Payin Rider"}, description = "TC to verify request amount can not be zero other than eMandate Payment, error thrown.")
	public void verifyRequestAmountCannotZeroForCCSIMandateType() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when mandate type other than eMandate Payment and payment amount is zero.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Set Policy Payment amt Zero.**********************************");		
		paymentAmt	= 0.0;
		dueDate 	= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Request Amount can be greater than zero for eMandate Payment
	 */
	@Test(priority = 6, enabled = false, groups = {"Payment - Initiate Payin Rider"}, description = "TC to verify Error throws when mandate type eMandate Payment and payment amount is greater than zero.")
	public void verifyRequestAmountCannotGreaterThanZeroForeMandateType() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when mandate type eMandate Payment and payment amount is greater than zero.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Set Policy Payment amt Zero.**********************************");		
		paymentAmt	= 424.0;
		dueDate 	= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeeMandate");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	@Test(priority = 7, enabled = true, description = "Create Add Rider Policy", groups = {"Payment - Initiate Payin Rider"})
	public void SurrenderRiderPolicyForNewBusinessUsingRazorPay() throws Exception {
		Logger.log("\n******** Test Scenario  :  Create Add Rider Policy in Inforce State ******\n");
		st = new SoftAssert(); 
		//***************************************Iterm7 Policy**********************************/
	 
		
		//For Rider Surrender	
		IssuanceQueue.mockAddRiderProposal(ProposalRequest.SurrenderRiderCode());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		System.out.println(pno);
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");
		//pno 		= "ALI0QAP16042150";
		paymentAmt	= InitiatePayment.getNBQuotation(pno)+500.0; //0.0;
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
		//InitiatePayment.getMandates(pno);
		
		Logger.log("*********************Payment checkout for New Business (NB).**********************************");
		InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
		
		Logger.log("*********************Verify Payment Info for New Business (NB).**********************************");
		InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
		
		//Logger.log("*********Get Mandate Status for Policy Number after payment done.*******************");
		//InitiatePayment.getMandates(pno);
		
		Logger.log("verify Policy is in 'INFORCE' Status for Standard decision=>"+policyNumber);
		staticWait(20);
		IssuanceQueue.verifyStatus(pno);
		
		Logger.log("***********Check Suspense entry for NB type.************");
		InitiatePayment.getAllSuspenses(pno);
		}
	@Test(priority = 8, enabled = true, dependsOnMethods={"SurrenderRiderPolicyForNewBusinessUsingRazorPay"}, description = "TC - To verify dynamodb events for Razorpay Payin", groups = {"Payment - Initiate Payin Rider"})
	public void verifyPaymentEventsRazorpaySR() throws Exception
	{
		Logger.log("*************Check events in dynamodb.************"); 
		IssuanceQueue.waitSec(4);
		Events.checkEvents_InitiatePaymentPayin(pno);
	}
	
	/*
	 * Validation Vendor Code Not Empty
	 */
	@Test(priority = 9, enabled = true, description = "TC to verify Error throws when Vendor Code is Empty.", groups = {"Payment - Initiate Payin Rider"})
	public void verifyVendorCodeNotEmptySR() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when Vendor Code is Empty.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");		
		paymentAmt 	= InitiatePayment.getNBQuotation(pno);
		dueDate 	= "";
		vendorCode 	= "";
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Mandatory Fields in Payment Request
	 */
	@Test(priority = 10, enabled = true, description = "TC to verify mobileNumber and email mandatory in payment request if vendor code is RAZORPAY", groups = {"Payment - Initiate Payin Rider"})
	public void verifyMandatoryFieldsInPaymentRequestSR() throws Exception {
		Logger.log("\n******** Test Scenario : Verify mobile Number and email mandatory in payment request if vendor code is RAZORPAY.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");		
		paymentAmt 	= InitiatePayment.getNBQuotation(pno);
		dueDate		= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= "";
		emailID 	= "";
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Request Amt can't be zero other than eManadte Payment
	 */
	@Test(priority =11, enabled = true, groups = {"Payment - Initiate Payin Rider"}, description = "TC to verify request amount can not be zero other than eMandate Payment, error thrown.")
	public void verifyRequestAmountCannotZeroForCCSIMandateTypeSR() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when mandate type other than eMandate Payment and payment amount is zero.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Set Policy Payment amt Zero.**********************************");		
		paymentAmt	= 0.0;
		dueDate 	= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Request Amount can be greater than zero for eMandate Payment
	 */
	@Test(priority = 12, enabled = true, groups = {"Payment - Initiate Payin Rider"}, description = "TC to verify Error throws when mandate type eMandate Payment and payment amount is greater than zero.")
	public void verifyRequestAmountCannotGreaterThanZeroForeMandateTypeSR() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when mandate type eMandate Payment and payment amount is greater than zero.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Set Policy Payment amt Zero.**********************************");		
		paymentAmt	= 424.0;
		dueDate 	= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeeMandate");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	@Test(priority = 13, enabled = true, description = "Create Add Rider Policy", groups = {"Payment - Initiate Payin Rider"})
	public void SurrenderPolicyForNewBusinessUsingRazorPay() throws Exception {
		Logger.log("\n******** Test Scenario  :  Create Add Rider Policy in Inforce State ******\n");
		st = new SoftAssert(); 
		//***************************************Iterm7 Policy**********************************/
	 
		
		//For Surrender Policy 
		IssuanceQueue.mockBackdatedProposal(ProposalRequest.SurrenderPolicyCode());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		System.out.println(pno);
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");
		//pno 		= "ALI0QAP16042150";
		paymentAmt	= InitiatePayment.getNBQuotation(pno)+500.0; //0.0;
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
		//InitiatePayment.getMandates(pno);
		
		Logger.log("*********************Payment checkout for New Business (NB).**********************************");
		InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
		
		Logger.log("*********************Verify Payment Info for New Business (NB).**********************************");
		InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
		
		//Logger.log("*********Get Mandate Status for Policy Number after payment done.*******************");
		//InitiatePayment.getMandates(pno);
		
		Logger.log("verify Policy is in 'INFORCE' Status for Standard decision=>"+policyNumber);
		staticWait(20);
		IssuanceQueue.verifyStatus(pno);
		
		Logger.log("***********Check Suspense entry for NB type.************");
		InitiatePayment.getAllSuspenses(pno);
		}
	@Test(priority = 14, enabled = true, dependsOnMethods={"SurrenderPolicyForNewBusinessUsingRazorPay"}, description = "TC - To verify dynamodb events for Razorpay Payin", groups = {"Payment - Initiate Payin Rider"})
	public void verifyPaymentEventsRazorpaySP() throws Exception
	{
		Logger.log("*************Check events in dynamodb.************"); 
		IssuanceQueue.waitSec(4);
		Events.checkEvents_InitiatePaymentPayin(pno);
	}
	
	/*
	 * Validation Vendor Code Not Empty
	 */
	@Test(priority = 15, enabled = true, description = "TC to verify Error throws when Vendor Code is Empty.", groups = {"Payment - Initiate Payin Rider"})
	public void verifyVendorCodeNotEmptySP() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when Vendor Code is Empty.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");		
		paymentAmt 	= InitiatePayment.getNBQuotation(pno);
		dueDate 	= "";
		vendorCode 	= "";
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Mandatory Fields in Payment Request
	 */
	@Test(priority = 16, enabled = true, description = "TC to verify mobileNumber and email mandatory in payment request if vendor code is RAZORPAY", groups = {"Payment - Initiate Payin Rider"})
	public void verifyMandatoryFieldsInPaymentRequestSP() throws Exception {
		Logger.log("\n******** Test Scenario : Verify mobile Number and email mandatory in payment request if vendor code is RAZORPAY.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Get Policy Payment amt for NB.**********************************");		
		paymentAmt 	= InitiatePayment.getNBQuotation(pno);
		dueDate		= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= "";
		emailID 	= "";
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Request Amt can't be zero other than eManadte Payment
	 */
	@Test(priority =17, enabled = true, groups = {"Payment - Initiate Payin Rider"}, description = "TC to verify request amount can not be zero other than eMandate Payment, error thrown.")
	public void verifyRequestAmountCannotZeroForCCSIMandateTypeSP() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when mandate type other than eMandate Payment and payment amount is zero.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Set Policy Payment amt Zero.**********************************");		
		paymentAmt	= 0.0;
		dueDate 	= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}
	
	/*
	 * Verify Request Amount can be greater than zero for eMandate Payment
	 */
	@Test(priority = 18, enabled = true, groups = {"Payment - Initiate Payin Rider"}, description = "TC to verify Error throws when mandate type eMandate Payment and payment amount is greater than zero.")
	public void verifyRequestAmountCannotGreaterThanZeroForeMandateTypeSP() throws Exception {
		Logger.log("\n******** Test Scenario  :  Verify Error throws when mandate type eMandate Payment and payment amount is greater than zero.**********\n");
		st = new SoftAssert(); 
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		IssuanceQueue.waitSec(20);
		
		Logger.log("*********************Set Policy Payment amt Zero.**********************************");		
		paymentAmt	= 424.0;
		dueDate 	= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeeMandate");
 
		Logger.log("*********************Payment initiate.**********************************");
		InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
	}	
}
