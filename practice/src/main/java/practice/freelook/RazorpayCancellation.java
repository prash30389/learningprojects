package practice.freelook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.asserts.SoftAssert;

import practice.dynamodb.CommonUtility;
import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import practice.utility.CreatePolicies;
import practice.utility.InitiatePayment;
import practice.utility.JSONUtils;
import practice.utility.ProposalRequest;
import practice.utility.Reinstatement;

import io.restassured.RestAssured;

public class RazorpayCancellation extends TestBase {

	
	public static String razorpaySurrender(String token) throws Exception
	{
		init();
		pno=CreatePolicies.razorpayInforcePolicy("backdated");
		System.out.println("Razor Surren:"+pno);
		Logger.log("Surrender Policy:"+pno);
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String todaydate = simpleDateFormat.format(new Date());
		Thread.sleep(30000);
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno + "\","
				+ "\"comment\":\"Surrender this policy\",\"medicalFee\":0," + "\"reason\":\"OTHER\",\"requestedOn\":\""
				+ todaydate + "\"," + "\"refundType\":\"STANDARD\",\"cancellationType\":\"SURRENDER\","
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"204e95bc-8b47-4a46-83a3-08daed45b661\","
				+ "\"policyNumber\":\"" + pno + "\",\"docType\":\"Request_Letter\","
				+ "\"isUploaded\":true}],\"coverageNo\":[]}");
		response = httpRequest.post("/internal/surrender");
		Thread.sleep(5000);
		System.out.println("Response : "+response.getStatusCode()+" "+response.getBody().asString());

		IssuanceQueue.waitSec(280);
		return pno;
		
		
	}
	
	public static String reinstatementRejectForRezorpay() throws Exception
	{
		pno=CreatePolicies.razorpayInforcePolicy("backdated");
		System.out.println("Policy in Reinstatement Rejec:"+pno);
		Logger.log("Reinstatement Reject:"+pno);
		Reinstatement.ebaotoken();
		Reinstatement.ebaoLapse(pno);
		IssuanceQueue.waitSec(10);
		IssuanceQueue.waitSec(4);
		st = new SoftAssert();
		Reinstatement.initiateReinstatement(pno);
		IssuanceQueue.waitSec(10);
		Reinstatement.Reject(pno);
		IssuanceQueue.waitSec(10);

		return pno;
		
	}
	
	public static String backDatedInforcePolicy() throws Exception
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
		 IssuanceQueue.mockProposal(ProposalRequest.surrenderCode());
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
			pno = inipayRes.get(1);
			transactionId = inipayRes.get(2);
			orderId = inipayRes.get(3);
			InitiatePayment.getMandates(pno);
			InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
			return pno;
	}
	
//====================================================================================================
	
	
	
}
