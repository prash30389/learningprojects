package Payout;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;



import practice.utility.JSONUtils;
import practice.dynamodb.CommonUtility;
import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import practice.utility.*;

public class PayoutPolicy extends TestBase {
	public String vendorCode = null;
	public String custId = null;
	public String policyNumber = null;
	public String transactionId = null;
	public String orderId = null;
	public String reason = null;
	public double paymentAmt = 0.0;
	public String paymentType = "";
	public String dueDate = "";
	public String mandateType = "";
	public String mobileNum = "";
	public String emailID = "";
	public String paymentOption = "";
	public String suspenseType = null;
	public String payoutType = null;
	public String cancellationType = null;
	public String nullandvoidRefundType = "No Refund";
	public String refundType = "";
	public String FRRefundType = "STANDARD";
	public String FilePath = "C:/Users/Prashant.s/Desktop/Data.xlsx";
	
	@Test(dataProvider = "Configuration", groups = {
			"Payment - Initiate Payout" }, enabled = true)
	public void PolicyTest(String vendorCode, String cancellationType) throws Exception {

		TestBase.init();
		CommonUtility.loadConfigFile();
		st = new SoftAssert();
		Logger.log("*******************Policy created using Mock API.***********************");
		if (cancellationType.equalsIgnoreCase("SURRENDER")) {
			IssuanceQueue.mockBackdatedProposal(ProposalRequest.SurrenderPolicyCode());
		}else if (cancellationType.equalsIgnoreCase("RIDER_FREELOOK")) {
			IssuanceQueue.mockAddRiderProposal(ProposalRequest.riderCode());
		} else if (cancellationType.equalsIgnoreCase("RIDER_SURRENDER")) {
			IssuanceQueue.mockAddRiderProposal(ProposalRequest.SurrenderRiderCode());  
		} else {
			IssuanceQueue.mockProposal(ProposalRequest.Code());
		}
		IssuanceQueue.waitSec(4);

		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);

		pno = ProposalRequest.pno();
		Logger.log("Policy No:" + pno);
		staticWait(20);

		Logger.log("*********************Get Quote for NB.**********************************");
		paymentAmt = InitiatePayment.getNBQuotation(pno) + 100.00;
		dueDate = "";
		mobileNum = CommonUtility.prop.getProperty("mobileNumber");
		emailID = CommonUtility.prop.getProperty("emailID");
		paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
		paymentOption = CommonUtility.prop.getProperty("paymentOptionCard");
		reason = JSONUtils.getValueFromConstant("constantValueFile", "freelookPayoutForInitiatePayment.reason");// "Policy
																												// Purchased
																												// by
																												// mistake";
		suspenseType = JSONUtils.getValueFromConstant("constantValueFile", "suspenseType.suspenseNB");
		Logger.log("*********************Payment initiate.**********************************");
		List<String> inipayRes = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID,
				paymentType, vendorCode, mandateType);
		custId = inipayRes.get(0);
		policyNumber = inipayRes.get(1);
		transactionId = inipayRes.get(2);
		orderId = inipayRes.get(3);
		Logger.log("\n Customer Id=>" + custId + "\n Policy Number =>" + policyNumber + "\n Transactiond=>"
				+ transactionId + "\n Order Id =>" + orderId);

		Logger.log("*********Get Mandate Status for Policy Number.***********");
		InitiatePayment.getMandates(pno);

		Logger.log("*********Make payment for NB.***********");
		InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);

		Logger.log("*********************Verify Payment Info for New Business (NB).**********************************");
		InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);

		Logger.log("*********Get Mandate Status for Policy Number after payment done.***********");
		InitiatePayment.getMandates(pno);

		Logger.log("*********Get Suspence Entry.**********");
		InitiatePayment.getAllSuspenses(pno);
		// InitiatePayment.getSuspenseEntryForPayment(pno, 0.0, suspenseType);

		Logger.log("Verify Policy is in 'INFORCE' Status for Standard decision=>" + policyNumber);
		staticWait(10);
		IssuanceQueue.verifyStatus(pno);

		Logger.log("***********Save Policy Number in Config file.************");
		TestBase.updatePropertiesFile("initiatePayoutFL", pno);
		Logger.log("Saved Policy Number is   : " + pno);

		Logger.log("*********************Generate Token******************************");
		IssuanceQueue.getToken_ops();

		Logger.log("********************* Freelook Cancellation******************************");
		if (cancellationType.equalsIgnoreCase("NULL_AND_VOID")
				&& (nullandvoidRefundType.equalsIgnoreCase("No Refund"))) {
			InitiatePayment.CacellationPolicy(pno, vendorCode, cancellationType, refundType);
			nullandvoidRefundType = JSONUtils.getValueFromConstant("constantValueFile",
					"nullandvoidRefundType.fullRefund");
			if (cancellationType.equalsIgnoreCase("NULL_AND_VOID")
					&& (nullandvoidRefundType.equalsIgnoreCase("Full Refund"))) {
				TestBase.init();
				CommonUtility.loadConfigFile();
				st = new SoftAssert();
				IssuanceQueue.mockProposal(ProposalRequest.Code());
				IssuanceQueue.waitSec(4);
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				pno = ProposalRequest.pno();
				Logger.log("Policy No:" + pno);
				staticWait(20);
				paymentAmt = InitiatePayment.getNBQuotation(pno) + 100.00;
				dueDate = "";
				mobileNum = CommonUtility.prop.getProperty("mobileNumber");
				emailID = CommonUtility.prop.getProperty("emailID");
				paymentType = CommonUtility.prop.getProperty("paymentTypeNB");
				mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
				paymentOption = CommonUtility.prop.getProperty("paymentOptionCard");
				reason = JSONUtils.getValueFromConstant("constantValueFile", "freelookPayoutForInitiatePayment.reason"); // mistake";
				suspenseType = JSONUtils.getValueFromConstant("constantValueFile", "suspenseType.suspenseNB");
				List<String> inipayRes1 = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID,
						paymentType, vendorCode, mandateType);
				custId = inipayRes1.get(0);
				policyNumber = inipayRes1.get(1);
				transactionId = inipayRes1.get(2);
				orderId = inipayRes1.get(3);
				InitiatePayment.getMandates(pno);
				InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
				InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
				InitiatePayment.getMandates(pno);
				InitiatePayment.getAllSuspenses(pno);
				staticWait(10);
				IssuanceQueue.verifyStatus(pno);
				IssuanceQueue.getToken_ops();
				InitiatePayment.CacellationPolicy(pno, vendorCode, cancellationType, refundType);
			} }
		else if (cancellationType.equalsIgnoreCase("RIDER_FREELOOK")) {
				InitiatePayment.CancellationRiderPolicy(pno, vendorCode, cancellationType, FRRefundType);
			} else if (cancellationType.equalsIgnoreCase("RIDER_SURRENDER")) {
				InitiatePayment.CancellationRiderPolicy(pno, vendorCode, cancellationType, FRRefundType);
			} else {
				InitiatePayment.CacellationPolicy(pno, vendorCode, cancellationType, refundType);
			}
			staticWait(10);
		}
	}

