package practice.utility;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import practice.dynamodb.CommonUtility;
import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import practice.utility.CustomException.GetDueAPIResponseException;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Payment_UtilityV1 extends TestBase {

	public static String policyNumber="";
	public static Response response=null;
	
	public static String paymentStatus ="";
	public static String vendorCode ="";
	public static String paymentModeType="";
	
	public static long ReinstatementdueAmount=0;
	public static double renewalDueAmount=0;
	public static double counterOfferAmount=0;
	public static long dueAmount=0;
	public static String dueDate="";
	
	public static JSONObject jsonObject =new JSONObject();
	public static JSONParser parser = new JSONParser();
	
	public static String createPolicyNumber()
	{
		long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
		policyNumber = String.valueOf(number);
		policyNumber= "PAYM1"+policyNumber;
		return policyNumber;
	}
	
	
	public static String getVengorPaymentID()
	{
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
	    String numbers = "0123456789";
	    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    int length = 10;

	    for(int i = 0; i < length; i++) {
	    	int index = random.nextInt(alphaNumeric.length());
	    	char randomChar = alphaNumeric.charAt(index);
	    	sb.append(randomChar);
	    }
	    String randomString = sb.toString();
	return randomString;
	}
	
	public static Response makeRegisterPayment(String policyNumber,String vendorPaymentID, String vendorCode, String paymentMode, String paymentType) throws Exception
	{
//		SoftAssert softAssert = new SoftAssert();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String system_TodaysDate = formatter.format(date).trim();
		IssuanceQueue.waitSec(5);
		
		
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");

			httpRequest.body("{\r\n" + 
					"    \"amount\": \"100\",\r\n" + 
					"    \"dueDate\": \""+system_TodaysDate+"\",\r\n" + 
					"    \"payer\": {\r\n" + 
					"        \"businessId\": \""+policyNumber+"\",\r\n" + 
					"        \"name\": \"Satishagarval Annavarapu \",\r\n" + 
					"        \"mobileNumber\": \"8888888888\",\r\n" + 
					"        \"email\": \"abc@an.d\"\r\n" + 
					"    },\r\n" + 
					"    \"paymentType\": \""+paymentType+"\",\r\n" + 
					"    \"policyNumber\": \""+policyNumber+"\",\r\n" + 
					"    \"schemeCode\": \"ARMP0000111\",\r\n" + 
					"    \"vendorCode\": \""+vendorCode+"\",\r\n" + 
					"    \"vendorPaymentId\": \""+vendorPaymentID+"\",\r\n" + 
					"    \"receiptInstrument\": {\r\n" + 
					"        \"paymentMode\": \""+paymentMode+"\"\r\n" + 
					"    },\r\n" + 
					"    \"additionalInformation\": {\r\n" + 
					"        \"productType\": \"Term\",\r\n" + 
					"        \"ebppOpted\": false,\r\n" + 
					"        \"nachOpted\": false,\r\n" + 
					"        \"siOpted\": true,\r\n" + 
					"        \"ecsOpted\": false\r\n" + 
					"    },\r\n" + 
					"    \"transactionDate\": \""+system_TodaysDate+"\"\r\n" + 
					"}");
			
			response = httpRequest.post("/payments/register");
			System.out.println("Payment Code:"+response.getStatusCode());
		
			if(response.getStatusCode()!=200) {
				Logger.log(policyNumber+" - "+paymentMode);
				Logger.log(response.getBody().asString());
			}
//			softAssert.assertEquals(response.getStatusCode(), 200, policyNumber);
			
			IssuanceQueue.waitSec(4);
//			Logger.log("Response Code Payment:"+response.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		softAssert.assertAll();
		return response;
		
	}
	
	public static void getFinalPaymentStatus(String policyNumber,String vendorName,String paymentMode,String paymentType) throws ParseException, IOException
	{
		SoftAssert softAssert = new SoftAssert();
		
		String paymentModeValid ="";
		String getPaymentType="";
		
		if(paymentType.equals("New Business"))
			getPaymentType="NB";
		else if(paymentType.equals("Counter Offer"))
			getPaymentType="CO";
		else if(paymentType.equals("Reinstatement"))
			getPaymentType="CR";
		else if(paymentType.equals("Renewal Payment"))
			getPaymentType="RP";
		
		
		if(response.getStatusCode()==200)
		{
			paymentModeValid = "Valid";
			JSONObject object = (JSONObject) parser.parse(response.getBody().asString());
			
			
			jsonObject = (JSONObject) object.get("payment");
			paymentStatus = (String) jsonObject.get("status");
			Logger.log("Payment Status:"+paymentStatus);
			Logger.log("");
			
			String modeOfPayment = (String) jsonObject.get("paymentMode");
			
			jsonObject = (JSONObject) jsonObject.get("vendorPayment");
			vendorCode = (String) jsonObject.get("vendorCode");
		
			String paymentId = (String) jsonObject.get("paymentId");
			
			jsonObject = (JSONObject) object.get("bill");
			paymentModeType = (String)jsonObject.get("type");
			
			String getPolicyNumber = (String)jsonObject.get("policyNumber");
			
			System.out.println("paymentStatus:"+paymentStatus+ " vendorCode:"+vendorCode+" paymentModeType:"+paymentModeType+ " paymentId:"+paymentId+ " paymentMode:"+modeOfPayment);
			System.out.println("=========================================");
			
			softAssert.assertEquals(getPolicyNumber,policyNumber,policyNumber);
			softAssert.assertEquals(paymentModeType,getPaymentType,policyNumber);
			softAssert.assertEquals(modeOfPayment,paymentMode,policyNumber);
			softAssert.assertEquals(vendorCode,vendorName,policyNumber);
			softAssert.assertEquals(paymentStatus,"SUCCESSFUL",policyNumber);
			
			
			
			
		}else if (response.getStatusCode()==400) {
			
			System.out.println(response.getBody().asString());
			String responseMessage=PaymentValidationsUtility.getResponseMessage(response);
			if(responseMessage.equals("Invalid payment mode")) {
				
				CommonUtility.loadConfigFile();
				String invalid = CommonUtility.prop.getProperty("invlidPaymentModes");
				System.out.println(invalid);
			
				for (String invalidPaymentMode: invalid.split(",")) {
			         System.out.println(invalidPaymentMode);
			         if(paymentMode.equals(invalidPaymentMode)) {
			        	 System.out.println("Invalid");
			        	 paymentModeValid = "Invalid";
			        	 break;
			         }
			      }
				
					System.out.println("Execute Initiate Payment For: "+policyNumber);
					/**
					 * Proceed With Initiate Payment
					 */
					
					try {
						razorpayPayment(policyNumber,paymentType);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		softAssert.assertAll();
	}
	
//==============================================================================
	public static Response registerPayment_RP_CR_AR(String pno, String vendorPaymentID, String vendorName, String paymentMode, String paymentType) throws Exception
	{
		
		IssuanceQueue.waitSec(2);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			
			if(paymentType.equals("Reinstatement"))
				dueAmount =	ReinstatementdueAmount;
			else if(paymentType.contains("Renewal Payment"))
				dueAmount =(long) renewalDueAmount;
			else if (paymentType.contains("Counter Offer"))
				dueAmount =(long) counterOfferAmount;
			
			httpRequest.body("{\r\n" + 
					"    \"amount\": \""+dueAmount+"\",\r\n" + 
					"    \"dueDate\": \""+dueDate+"\",\r\n" + 
					"    \"payer\": {\r\n" + 
					"        \"businessId\": \""+pno+"\",\r\n" + 
					"        \"name\": \"Satishagarval Annavarapu \",\r\n" + 
					"        \"mobileNumber\": \"8888888888\",\r\n" + 
					"        \"email\": \"abc@an.d\"\r\n" + 
					"    },\r\n" + 
					"    \"paymentType\": \""+paymentType+"\",\r\n" + 
					"    \"policyNumber\": \""+pno+"\",\r\n" + 
					"    \"schemeCode\": \"ARMP0000111\",\r\n" + 
					"    \"vendorCode\": \""+vendorName+"\",\r\n" + 
					"    \"vendorPaymentId\": \""+vendorPaymentID+"\",\r\n" + 
					"    \"receiptInstrument\": {\r\n" + 
					"        \"paymentMode\": \""+paymentMode+"\"\r\n" + 
					"    },\r\n" + 
					"    \"additionalInformation\": {\r\n" + 
					"        \"productType\": \"Term\",\r\n" + 
					"        \"ebppOpted\": false,\r\n" + 
					"        \"nachOpted\": false,\r\n" + 
					"        \"siOpted\": true,\r\n" + 
					"        \"ecsOpted\": false\r\n" + 
					"    },\r\n" + 
					"    \"transactionDate\": \""+dueDate+"\"\r\n" + 
					"}");
			
			response = httpRequest.post("/payments/register");
			Logger.log("Status Code:"+response.getStatusCode());
			IssuanceQueue.waitSec(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return response;
	}
//==============================================================================
	
	public static Response getDueAmountAndDueDate(String pno, String paymentType) throws Exception
	{
		if(paymentType.equals("Reinstatement")||paymentType.equals("Renewal Payment")) {
				RestAssured.baseURI = prop.getProperty("payment");
				httpRequest = RestAssured.given();
				httpRequest.header("Content-Type", "application/json");
				response = httpRequest.get("/receivables/"+pno);
				if(response.getStatusCode()!=200)
				{
					throw new CustomException.GetDueAPIResponseException("Get Due API response not found:"+pno+ "Response Code:"+response.getStatusCode());
				}else {
					jsonObject =(JSONObject) parser.parse(response.getBody().asString());
					JSONArray jsonArray = new JSONArray();
					jsonArray = (JSONArray) jsonObject.get("bills");
					
					jsonObject = (JSONObject) jsonArray.get(0);
					dueDate    = (String) jsonObject.get("dueDate");
					System.out.println("Due Date:"+dueDate);
					
					if(paymentType.equals(PaymentConstants.paymentTypeReinstatement))
					ReinstatementdueAmount  = (long) jsonObject.get("amount");
					else if(paymentType.equals(PaymentConstants.paymentTypeRenewal))
					renewalDueAmount=(double) jsonObject.get("amount");
					
					System.out.println("Reinstatement Amount:"+ReinstatementdueAmount);
				}	
		}else if (paymentType.equals("Counter Offer")) {
			System.out.println("In Co:"+pno);
			InitiatePayment.createDue(pno, "CO");
			InitiatePayment.getDues(pno);
			 counterOfferAmount	= InitiatePayment.amountPayable();
			 dueDate 	= InitiatePayment.dueDate();
		}
		return response;
	}
//==============================================================================
	
	public static List getSuspenseDetails(String pno)
	{
		ArrayList list = new ArrayList<>();
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			response= httpRequest.get("/suspenses/search?policyNumber="+pno+"&suspenseType=NB");
			JSONArray jsonArray  = (JSONArray) parser.parse(response.getBody().asString());
			jsonObject = (JSONObject) jsonArray.get(0);
			String suspenseType = (String) jsonObject.get("suspenseType");
			String transactionType = (String) jsonObject.get("transactionType");
			double amount = (double) jsonObject.get("amount");
			list.add(suspenseType);
			list.add(transactionType);
			list.add(amount);
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return list;
	}
//==============================================================================

	
	public static void razorpayPayment(String pno,String paymentType) throws Exception
	{
		
		 String vendorCode = "";
		 String custId = "";
		 String transactionId = "";
		 String orderId = "";
		 double paymentAmt = 0.0; 
//		 String paymentType = "";
		 String mandateType = "";
		 String mobileNum = "";
		 String emailID = "";
		 String dueDate = "";
		 String suspenseType = "";
		 String paymentOption = "";
		 String policyNumber = "";
	
		 
		Logger.log("Policy Number is:::"+ pno);
		System.out.println("pno:"+pno);
		IssuanceQueue.waitSec(20);
		
		Logger.log("Get Policy Payment amt for NB.");
		if(paymentType.equals("New Business"))
		{	paymentAmt	= 100.00; //10000.00;
			dueDate 	= "";
		}
		else if (paymentType.equals("Reinstatement"))
		{
			paymentAmt	= ReinstatementdueAmount;
			dueDate= Payment_UtilityV1.dueDate;
		}
		else if (paymentType.equals("Renewal Payment"))
		{
			paymentAmt	= renewalDueAmount;
			dueDate= Payment_UtilityV1.dueDate;

		}else if (paymentType.equals("Counter Offer"))
		{
			paymentAmt	= counterOfferAmount;
			dueDate= Payment_UtilityV1.dueDate;
		}
		
		vendorCode 	= CommonUtility.prop.getProperty("vendorRazorpay");
		mobileNum 	= CommonUtility.prop.getProperty("mobileNumber");
		emailID 	= CommonUtility.prop.getProperty("emailID");
//		paymentType	= CommonUtility.prop.getProperty("paymentTypeNB");
		mandateType = CommonUtility.prop.getProperty("mandateTypeCCSI");
		paymentOption= CommonUtility.prop.getProperty("paymentOptionCard");
		suspenseType= JSONUtils.getValueFromConstant("constantValueFile","suspenseType.suspenseNB"); 

 
		Logger.log("Payment process initiated using initiate payment.");
		List<String> inipayRes = InitiatePayment.initiatePayment(paymentAmt, dueDate, pno, mobileNum, emailID, paymentType, vendorCode, mandateType);
		custId 		= inipayRes.get(0);
		policyNumber= inipayRes.get(1);
		transactionId= inipayRes.get(2);
		orderId		= inipayRes.get(3);

		System.out.println(custId+ " - "+policyNumber+" - "+transactionId+" - "+orderId);
		
		Logger.log("Get Mandate Status for Policy Number before payment.");
		InitiatePayment.getMandates(pno);
		
		
		Logger.log("Payment checkout for New Business (NB) Policy.");
		InitiatePayment.makepayment(custId, pno, transactionId, orderId, paymentAmt, paymentOption);
		
		Logger.log("Verify Payment Info for New Business (NB).**********************************");
		InitiatePayment.getPaymentInfo(pno, paymentType, paymentAmt, vendorCode, "SUCCESSFUL", paymentOption);
		System.out.println(pno);
		
		Logger.log("Get Mandate Status for Policy Number after payment done.");
		InitiatePayment.getMandates(pno);
		
		//Logger.log("verify Policy is in 'INFORCE' Status for Standard decision=>"+policyNumber);
		staticWait(20);
		//IssuanceQueue.verifyStatus(pno);
		
		Logger.log("Check Suspense entry for NB type.");
	
	}
	
	
	
}
