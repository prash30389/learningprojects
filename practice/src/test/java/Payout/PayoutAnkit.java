package Payout;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import practice.dynamodb.CommonUtility;
import practice.dynamodb.Events;
import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.ProposalRequest_Backdate;
import practice.testbase.TestBase;
import practice.utility.InitiatePayment;
import practice.utility.JSONUtils;
import practice.utility.PayoutExecution;
import practice.utility.ProposalRequest;

public class PayoutAnkit extends TestBase {
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
	public String refundType = null;
	public String FRRefundType = "STANDARD";
	
	
	@DataProvider (name = "Configuration")
    public static Object[][] ReadVariant() throws IOException
    {
		System.out.println("Data pull from the Configuration file one by one");
		FileInputStream fileInputStream= new FileInputStream("C:/Users/Prashant.s/Desktop/Data.xlsx"); //Excel sheet file location get mentioned here
		@SuppressWarnings("resource")
		XSSFWorkbook  workbook = new XSSFWorkbook (fileInputStream); //get my workbook 
		XSSFSheet  worksheet=workbook.getSheet("policyInput");// get my sheet from workbook
        XSSFRow Row=worksheet.getRow(0);     //get my Row which start from 0   
     
        int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
        int ColNum= Row.getLastCellNum(); // get last ColNum 
         
        Object Data[][]= new Object[RowNum][ColNum]; // pass my  count data in array
        DataFormatter formatter = new DataFormatter();  
            for(int i=0; i<RowNum; i++) //Loop work for Rows
            {  
                XSSFRow row= worksheet.getRow(i);
                 
                for (int j=0; j<ColNum; j++) //Loop work for colNum
                {
                    if(row==null)
                        Data[i][j]= "";
                    else
                    {
                        XSSFCell cell= row.getCell(j);
                        if(cell==null)
                            Data[i][j]= ""; //if it get Null value it pass no data 
                        else
                        {
                            String value=formatter.formatCellValue(cell);
                            Data[i][j]=value; //This formatter get my all values as string i.e integer, float all type data value
                            System.out.println("Data Values::"+Data[i][j]);
                        }
                    }
                }
            }
			return Data;
    }
	
	public void BeforeTest(String venCode, String canType ) throws Exception {
		TestBase.init();
		CommonUtility.loadConfigFile();
		st = new SoftAssert(); 
		System.out.println("*******************Policy going to create.**************************");
		Logger.log("Policy created using Mock API.");
		
		if(canType.equalsIgnoreCase("SURRENDER")) {
			IssuanceQueue.mockBackdatedProposal(ProposalRequest_Backdate.BackdatedCode());
		} 
		    /*
			 * else if (canType.equalsIgnoreCase("RIDER_FREELOOK")) {
			 * IssuanceQueue.mockAddRiderProposal(ProposalRequest.riderCode()); } else if
			 * (canType.equalsIgnoreCase("RIDER_SURRENDER")) {
			 * IssuanceQueue.mockAddRiderProposal(ProposalRequest.SurrenderRiderCode()); }
			 */ 
		else {
			IssuanceQueue.mockProposal(ProposalRequest.Code());
		}
		IssuanceQueue.waitSec(4);
		
		Logger.log("Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("Response   : " + IssuanceQueue.response.getBody().asString());
		st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
		 
		if(canType.equalsIgnoreCase("SURRENDER")) {
			pno = ProposalRequest_Backdate.pno();
		} else {
			pno = ProposalRequest.pno();
		}
		Logger.log("Policy No is:"+pno); 
		staticWait(20);	
		
		Logger.log("Get Quotation amount for NB.");
		paymentAmt 			= InitiatePayment.getNBQuotation(pno)+500.00;
		dueDate 			= "";
		vendorCode 			= venCode;
		mobileNum 			= CommonUtility.prop.getProperty("mobileNumber");
		emailID 			= CommonUtility.prop.getProperty("emailID");
		paymentType 		= CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType 		= CommonUtility.prop.getProperty("mandateTypeCCSI");
		paymentOption		= CommonUtility.prop.getProperty("paymentOptionCard");
		
		switch(canType) {
		case "NULL_AND_VOID_NO_REFUND":
			cancellationType	= canType.substring(0, 13);
			System.out.println("cancellationType::"+cancellationType);
			break;
		case "NULL_AND_VOID_FULL_REFUND":
			cancellationType	= canType.substring(0,13);
			System.out.println("cancellationType::"+cancellationType);
			break;
		default:
			cancellationType	= canType;
			System.out.println("cancellationType::"+cancellationType);
			break;
		}

		if(canType.equalsIgnoreCase("FREELOOK")) {
			refundType		= JSONUtils.getValueFromConstant("constantValueFile", "refundType.standard");
		} else if(canType.equalsIgnoreCase("NULL_AND_VOID_NO_REFUND")) {
			refundType		= JSONUtils.getValueFromConstant("constantValueFile", "nullandvoidRefundType.noRefund");
		} else if(canType.equalsIgnoreCase("NULL_AND_VOID_FULL_REFUND")) {
			refundType		= JSONUtils.getValueFromConstant("constantValueFile", "nullandvoidRefundType.fullRefund");
		} else if(canType.equalsIgnoreCase("CANCEL_FULL_REFUND")) {
			refundType		= JSONUtils.getValueFromConstant("constantValueFile", "refundType.fullRefund");
		} else if(canType.equalsIgnoreCase("SURRENDER")) {
			refundType		= JSONUtils.getValueFromConstant("constantValueFile", "refundType.standard");
		} else {}

		if(vendorCode.equalsIgnoreCase("RAZORPAY")) {
			Logger.log("Payment process initiated using initiate payment.");
			List<String> inipayRes = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
			custId = inipayRes.get(0);
			policyNumber = inipayRes.get(1);
			transactionId = inipayRes.get(2);
			orderId = inipayRes.get(3);
			System.out.println("\n Customer Id=>" + custId + "\n Policy Number =>"   + policyNumber + "\n Transactiond=>" + transactionId + "\n Order Id =>" +orderId);
			
			Logger.log("Get Mandate Status for Policy Number.");
			InitiatePayment.getMandates(pno);
			
			Logger.log("Make payment using Card payment option");
			InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
			
			Logger.log("Verify Payment Info for New Business (NB).");
			InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
			
			Logger.log("Get Mandate Status for Policy Number after payment done.");
			InitiatePayment.getMandates(pno);
		} else {
			Logger.log("Payment process initiated using register payment.");
			ProposalRequest_Backdate.offline_payment(pno, paymentAmt, vendorCode);
			Logger.log("Verify Payment Info for New Business (NB).");
			InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
		}

		Logger.log("Get All Suspences Entry.");
		InitiatePayment.getAllSuspenses(pno);
		//InitiatePayment.getSuspenseEntryForPayment(pno, 0.0, suspenseType);
		
		Logger.log("Verify Policy is in 'INFORCE' Status for Standard decision=>"+policyNumber);
		staticWait(20);
		IssuanceQueue.verifyStatus(pno);
		 
		Logger.log("Generate Token");
		IssuanceQueue.getToken_ops();
		
		System.out.println("*******************Going to cancel policy*********************");
		Logger.log("Cancel Policy");
		/*
		 * if (cancellationType.equalsIgnoreCase("RIDER_FREELOOK")) {
		 * InitiatePayment.CancellationRiderPolicy(pno, vendorCode, cancellationType,
		 * FRRefundType); } else if
		 * (cancellationType.equalsIgnoreCase("RIDER_SURRENDER")) {
		 * InitiatePayment.CancellationRiderPolicy(pno, vendorCode, cancellationType,
		 * FRRefundType); } else {
		 */
		InitiatePayment.CacellationPolicy(pno, vendorCode, cancellationType, refundType);
		staticWait(30); 
	}

	@Test(priority = 1, dataProvider = "Configuration", enabled= true, description = "Testcase to check functionality of Initiate Payout for Freelook Policy",  groups= {"Payout"})
	public void verifyPayoutforCancellPolicy(String VendorCode, String CancellationType) throws Exception {
		Logger.log("\n******** Test Scenario  :  Payout => For Cancelled Policy*********\n");
		vendorCode 	= 		VendorCode;//CommonUtility.prop.getProperty("vendorRazorpay");
		cancellationType = 	CancellationType;
		System.out.println("vendorCode::"+vendorCode+" cancellationType::"+ cancellationType);
		
		PayoutAnkit payout = new PayoutAnkit();
		payout.BeforeTest(vendorCode, cancellationType);
				
		Logger.log("Cancelled Policy Number is:::"+ pno);
		if(CancellationType.equalsIgnoreCase("FREELOOK")) {
			reason 		= JSONUtils.getValueFromConstant("constantValueFile", "freelookPayoutForInitiatePayment.reason");
			suspenseType= JSONUtils.getValueFromConstant("constantValueFile", "suspenseType.suspensePayout");  
			payoutType	= JSONUtils.getValueFromConstant("constantValueFile", "payoutType.payoutTypeFL");
		} else if(CancellationType.equalsIgnoreCase("NULL_AND_VOID_NO_REFUND")) {
			reason 		= JSONUtils.getValueFromConstant("constantValueFile", "nullandvoidReqReason.medicalHistory");
			suspenseType= JSONUtils.getValueFromConstant("constantValueFile", "suspenseType.suspensePayout");//"PAYOUT";  
			payoutType	= JSONUtils.getValueFromConstant("constantValueFile", "payoutType.payoutTypeSU");//payoutTypeNV
		} else if(CancellationType.equalsIgnoreCase("NULL_AND_VOID_FULL_REFUND")) {
			reason 		= JSONUtils.getValueFromConstant("constantValueFile", "nullandvoidReqReason.medicalHistory");
			suspenseType= JSONUtils.getValueFromConstant("constantValueFile", "suspenseType.suspensePayout");//"PAYOUT";   
			payoutType	= JSONUtils.getValueFromConstant("constantValueFile", "payoutType.payoutTypeSU");//payoutTypeNV
		} else if(CancellationType.equalsIgnoreCase("CANCEL_FULL_REFUND")) {
			reason 		= JSONUtils.getValueFromConstant("constantValueFile", "requestReason.benefitsInadequate");
			suspenseType= JSONUtils.getValueFromConstant("constantValueFile", "suspenseType.suspensePayout");//"PAYOUT";  
			payoutType	= JSONUtils.getValueFromConstant("constantValueFile", "payoutType.payoutTypeSU");//payoutTypeNV
		} else if(CancellationType.equalsIgnoreCase("SURRENDER")) {
			reason 		= JSONUtils.getValueFromConstant("constantValueFile", "requestReason.financialRequirement");
		} else if (CancellationType.equalsIgnoreCase("RIDER_FREELOOK")) {
			reason 		= JSONUtils.getValueFromConstant("constantValueFile", "requestReason.benefitsInadequate");
			suspenseType= JSONUtils.getValueFromConstant("constantValueFile", "suspenseType.suspensePayout");//"PAYOUT";  
			payoutType	= JSONUtils.getValueFromConstant("constantValueFile", "payoutType.payoutTypeFLAR");//payoutTypeNV
		}else if(CancellationType.equalsIgnoreCase("RIDER_SURRENDER")) {
			reason 		= JSONUtils.getValueFromConstant("constantValueFile", "requestReason.benefitsInadequate");
			suspenseType= JSONUtils.getValueFromConstant("constantValueFile", "suspenseType.suspensePayout");//"PAYOUT";  
			payoutType	= JSONUtils.getValueFromConstant("constantValueFile", "payoutType.Financialproblem");//payoutTypeNV
		}else {}

		Logger.log("Get Payout Status after Freelook Cancellation.");
		String susType = null;
		if(vendorCode.equalsIgnoreCase("POLICYBAZAAR")) {
			PayoutExecution.freelookPayout(pno);
		}
		InitiatePayment.getPayoutStatus(pno, reason, susType, payoutType, vendorCode);
		
		Logger.log("Get Suspence Entry After Payout Initiated."); 
		InitiatePayment.getAllSuspenses(pno);
		//InitiatePayment.getSuspenseEntryForPayment(pno, 0.0, suspenseType);		
	}
	
	@Test(priority = 2, enabled= true, dependsOnMethods={"verifyPayoutforCancellPolicy"}, description = "TC - To verify dynamodb events for FL Cancellation Payout", groups = {"Payout"})
	public void verify_PayoutEventsForCancellation() throws Exception
	{
		Logger.log("*************Check events in dynamodb.************");
		IssuanceQueue.waitSec(4);
		Events.checkEvents_Payout(pno);
	}
}

