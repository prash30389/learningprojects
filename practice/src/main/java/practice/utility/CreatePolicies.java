package practice.utility;

import java.util.List;

import org.testng.asserts.SoftAssert;

import practice.dynamodb.CommonUtility;
import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.PageObj;
import practice.testbase.TestBase;

public class CreatePolicies extends TestBase {

	public static String policyNumber=""; 
	public static String lapsedPolicy() throws Exception
	{
		
		TestBase.init();
		PageObj.mockBackdatedPolicyStd();
		policyNumber = PageObj.pnoMock();
		Reinstatement.ebaotoken();
		Reinstatement.ebaoLapse(policyNumber);
		Logger.log("Lapsed Policy:"+policyNumber);
		return policyNumber;
	}
	
	
	public static String gracePolicy() throws Exception
	{
		TestBase.init();
		PageObj.mockgracePolicy();
		policyNumber = PageObj.pnoMock();
		Reinstatement.ebaotoken();
		Reinstatement.extraction(policyNumber);
		Logger.log("Grace Policy:"+policyNumber);
		return policyNumber;
		
	}
	
	public static String gracePolicy_Claim() throws Exception
	{
		TestBase.init();
		PageObj.mockgracePolicy_claim();
		policyNumber = PageObj.pnoMock();
		System.out.println("policy for extraction:"+policyNumber);
		Reinstatement.ebaotoken();
		Reinstatement.extraction(policyNumber);
		Logger.log("Grace Policy:"+policyNumber);
		return policyNumber;
		
	}
	
	public static String CounterOfferPolicy() throws Exception
	{
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		policyNumber = ProposalRequest.pno();
		System.out.println("Counter Offer Policy:"+policyNumber);
		return policyNumber;
	}
	
	public static String razorpayInforcePolicy(String date) throws Exception
	{
		 String vendorCode = null;
		 String custId = null;
		 String transactionId = null;
		 String orderId = null;
		 Double paymentAmt = 0.0;
		 String mandateType = "";
		 String mobileNum = "";
		 String emailID = "";
		 String paymentOption = "";
		 String reason = null; 
		 String suspenseType = null; 
		 String payoutType = null;
		 String cancellationType = null;
		
		 String paymentType = "";
		 String dueDate = "";
		 CommonUtility.loadConfigFile();
		 if(date.equals("backdated")) {
			 IssuanceQueue.mockProposal(ProposalRequest.surrenderCode());
		 }else {
			 System.out.println("In Else");
			 IssuanceQueue.mockProposal(ProposalRequest.Code());
		 }
		 IssuanceQueue.waitSec(4);
			Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
			Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
			pno = ProposalRequest.pno();
			staticWait(20);
			paymentAmt 	= InitiatePayment.getNBQuotation(pno)+100.00;
			dueDate 	= "";
			vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
			mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
			emailID 	= CommonUtility.prop.getProperty("emailID");
			paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
			mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
			paymentOption	= CommonUtility.prop.getProperty("paymentOptionCard");
			reason 		= JSONUtils.getValueFromConstant("constantValueFile", "nullandvoidReqReason.medicalHistory");//"Policy Purchased by mistake";
			suspenseType= JSONUtils.getValueFromConstant("constantValueFile","suspenseType.suspenseNB");//"NB";
			cancellationType = JSONUtils.getValueFromConstant("constantValueFile", "cancellationType.nullandVoid");
			List<String> inipayRes = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
			custId = inipayRes.get(0);
			policyNumber = inipayRes.get(1);
			transactionId = inipayRes.get(2);
			orderId = inipayRes.get(3);
			InitiatePayment.getMandates(pno);
			InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
			return pno;
	}
	
	public static String razorpayLapsedPolicy() throws Exception
	{	
		policyNumber=razorpayInforcePolicy("");
		Reinstatement.ebaotoken();
		Reinstatement.ebaoLapse(policyNumber);
		return policyNumber;
		
	}
	
	public static String razorpayDraftPolicy() throws Exception
	{
		 String vendorCode = null;
		 String custId = null;
		 String policyNumber = null;
		 String transactionId = null;
		 String orderId = null;
		 String reason = null; 
		 Double amount = 0.00;
		 Double paymentAmt = 0.0; 
		 String paymentType = "";
		 String dueDate = "";
		 String mandateType = "";
		 String mobileNum = "";
		 String emailID = "";
		 Boolean partialPayout = false;
		 String paymentOption = "";
		 String suspenseType = null; 
		 String payoutType = null;
		 CommonUtility.loadConfigFile();
		Logger.log("\n******** Test Scenario  :  Payout => For Reject Policy ******\n");
		st = new SoftAssert();
		
		Logger.log("*********************Get Quote for NB.**********************************");
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		pno = ProposalRequest.pno();
		staticWait(20);
		
		Logger.log("*********************Generate Token******************************");
		IssuanceQueue.getToken_ops();
		
		Logger.log("*********************Get Quote for NB.**********************************");
		amount = InitiatePayment.getNBQuotation(pno);
		paymentAmt 	= amount-100.00;		
		dueDate 	= "";
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
		paymentOption	= CommonUtility.prop.getProperty("paymentOptionCard");
		
		partialPayout= false;
		reason 		= JSONUtils.getValueFromConstant("constantValueFile","rejectPayoutReason.paymentReject"); //"Payment Reject";
		suspenseType= JSONUtils.getValueFromConstant("constantValueFile","suspenseType.suspenseNB"); 
		payoutType 	= JSONUtils.getValueFromConstant("constantValueFile", "payoutType.payoutTypeRJ");
		
		Logger.log("*********************Payment initiate.**********************************");
		List<String> inipayRes = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
		Logger.log("response values=>"+ inipayRes);
		custId 			= inipayRes.get(0);
		policyNumber 	= inipayRes.get(1);
		transactionId 	= inipayRes.get(2);
		orderId 		= inipayRes.get(3);
		Logger.log("\n Customer Id=>" + custId + "\n Policy Number =>" + policyNumber + "\n Transactiond=>" + transactionId + "\n Order Id =>" +orderId);
		
		Logger.log("*********Get Mandate Status for Policy Number.*******************");
		InitiatePayment.getMandates(pno);
		
		Logger.log("*************Payment Checkout for Policy.*******************");
		InitiatePayment.makepayment(custId,pno,transactionId, orderId, paymentAmt, paymentOption);
		return policyNumber;
		
	}
}

