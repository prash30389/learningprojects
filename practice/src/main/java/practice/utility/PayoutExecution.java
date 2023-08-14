package practice.utility;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import practice.dynamodb.CommonUtility;
import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author SHREYAS
 *
 */
public class PayoutExecution extends TestBase {

	public static WebDriver w;
	public static String projectPath = System.getProperty("user.dir");

	public static RequestSpecification httpRequest;
	public static Response response;
	public static String jsonfile;
	public static JSONParser parser = new JSONParser();
	public static Object obj;
	public static JSONObject policy, insured, js, paymentInfo , payoutStatus;
	SoftAssert st;
	
	public static double paidAmount_policyIssue, paidAmountRefund;
	public static double amountNB, amountRENEWAL,suspenseAmountPayout, amountReinstatement;
	public static long amountPAYOUT, amountAR, patchId;
	
	public static String offlinePatch="";
	public static String suspenseType="";;
	
	public static String paymentType ="";
	public static String paymentStatus ="";
	public static String suspenseTypeAfterPayout="";
	public static String mockProposalNo =""; 
	public static String policyNumber="";
	public static String vendorCode="";
	public static String finalPayoutStatus="";
	public static String payoutType="";
	
	public static void waitSec(int sec) throws Exception {
		long timeSec = sec * 1000;
		Thread.sleep(timeSec);

	}
	public static void createMockProposal() throws Exception
	{
		IssuanceQueue.mockProposal(ProposalRequest.Code());
		IssuanceQueue.waitSec(4);
		Logger.log("Mock Proposal Status     : " + IssuanceQueue.response.getStatusCode());
		Logger.log("");
		mockProposalNo = ProposalRequest.pno();
		System.out.println("Policy : "+mockProposalNo);
		IssuanceQueue.waitSec(20);
		PerformPayment.actual_payment(mockProposalNo);
		IssuanceQueue.waitSec(8);
		IssuanceQueue.verifyStatus(mockProposalNo);
	}
	
	public static void searchPaymentInfo(String pno) throws Exception
	{
		init();
		Logger.log("In Search Payment Info>>>>"+policyNumber);
		IssuanceQueue.waitSec(1);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.body("{\r\n" + 
					"	\"policyNumber\": \""+pno+"\"\r\n" + 
					"}");
			response = httpRequest.post("/payments/search");
			Logger.log("Payment Info:"+response.getStatusCode());
			JSONArray  jarray = null;
			JSONObject paym=null;
			jarray = (JSONArray) parser.parse(response.getBody().asString());
			
			paym = (JSONObject) jarray.get(0);
			paidAmount_policyIssue = (double) paym.get("amount");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static Response checkPayoutStatus(String policyNumber) throws Exception
	{
		init();
		Logger.log("Policy Number:"+policyNumber);
		IssuanceQueue.waitSec(5);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.body("{\r\n" + 
					"    \"policyNumber\": \""+policyNumber+"\"\r\n" + 
					"}");
			response = httpRequest.post("/payouts");
			
			if(response.getStatusCode()!=200 )
			{
				System.out.println("Payout Status Code:"+response.getStatusCode());
				throw new CustomException.PayoutResponseException("Response not found in payout. Response code is not 200");
			}
			
			Logger.log("Payout Status code : " + response.getStatusCode());
			System.out.println("Payout Status Code:"+response.getStatusCode());
			Logger.log("checkPayoutStatus>>>"+response.getBody().asString());
//			System.out.println("Check Payout status Response *** : " + response.getBody().asString());
			JSONParser jparser = new JSONParser();
			JSONArray object=null;

			object= (JSONArray) jparser.parse(response.getBody().asString());
			JSONObject getStatus = (JSONObject) object.get(0);
			patchId = (Long) getStatus.get("id");
			finalPayoutStatus = (String) getStatus.get("status");
			JSONObject VCode = (JSONObject) getStatus.get("vendorRefund");
			vendorCode = (String) VCode.get("vendorCode");
			payoutType = (String)getStatus.get("payoutType");
			paidAmountRefund = (double) getStatus.get("amountPaid");
			System.out.println("id:>"+patchId+ "-->PayoutStatus:>"+finalPayoutStatus+ "-->Vendor Code:>"+vendorCode+ "-->PayoutType:>"+payoutType+ "-->Refund Amount:>"+paidAmountRefund);
		} catch (ParseException e) {
			System.out.println("In Catch");
			e.printStackTrace();
		}
		return response;
	}
	
	static int arraySize=0;
	public static int getPayoutArraySize(String policyNumber)
	{
		init();
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.body("{\r\n" + 
					"    \"policyNumber\": \""+policyNumber+"\"\r\n" + 
					"}");
			response = httpRequest.post("/payouts");
			
			Logger.log("Payout Status code : " + response.getStatusCode());
			JSONParser jparser = new JSONParser();
			JSONArray object=null;
			
			object= (JSONArray) jparser.parse(response.getBody().asString());
			Logger.log("Size:>>>>>"+object.size());
			arraySize = object.size();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return arraySize;
		
	}
	
	public static String payoutTypNormal="";
	public static String payoutTypSuspense="";
	public static Response freelookPayout(String policyNumber) throws Exception
	{
		SoftAssert st = new SoftAssert();
		
		init();
		Logger.log("Policy Number:"+policyNumber);
		IssuanceQueue.waitSec(5);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.body("{\r\n" + 
					"    \"policyNumber\": \""+policyNumber+"\"\r\n" + 
					"}");
			response = httpRequest.post("/payouts");
			Logger.log("Payout Status code : " + response.getStatusCode());
			System.out.println("Payout Status Code:"+response.getStatusCode());
			Logger.log("Payout body:"+response.getBody().asString());
			JSONParser jparser = new JSONParser();
			JSONArray object=null;
			
//			String payoutType1="";
///			String payoutType2="";
			
			object= (JSONArray) jparser.parse(response.getBody().asString());
			for (int i = 0; i <= object.size()-1; i++) {
				
					JSONObject getStatus = (JSONObject) object.get(i);
					patchId = (Long) getStatus.get("id");
					finalPayoutStatus = (String) getStatus.get("status");
					JSONObject VCode = (JSONObject) getStatus.get("vendorRefund");
					vendorCode = (String) VCode.get("vendorCode");
					if(i==0)
					payoutTypNormal = (String)getStatus.get("payoutType");
					if(i==1)
					payoutTypSuspense = (String)getStatus.get("payoutType");	
					paidAmountRefund = (double) getStatus.get("amountPaid");
					System.out.println("id:>"+patchId+ "-->PayoutStatus:>"+finalPayoutStatus+ "-->Vendor Code:>"+vendorCode+ "-->PayoutType:>"+payoutTypNormal+ "-->Refund Amount:>"+paidAmountRefund);
					doOfflinePatch();
					IssuanceQueue.waitSec(3);
					checkPayoutStatus(policyNumber);
			}
	//		st.assertEquals(payoutType1, "FL");
	//		st.assertEquals(payoutType2, "SU");
	//		st.assertAll();
		} catch (ParseException e) {
			System.out.println("In Catch");
			e.printStackTrace();
		}
		return response;
	}
	
	
	public static Response doOfflinePatch() throws ParseException
	{
		init();
		Logger.log("====Start Patch====");
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.body("{\r\n" + 
					"  \"payoutId\": "+patchId+"\r\n" + 
					"}");
			
			response = httpRequest.patch("/payouts/offline");
			Logger.log("Payout patch Status code : " + response.getStatusCode());
			Logger.log("Payout patch Response  : " + response.getBody().asString());
			offlinePatch = response.getBody().asString();
			Logger.log("=====End of PAtch Method====");
			Logger.log("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
		
	}
//=========================================================================================================================	
	public static Response getSuspense(String mockProposalNo) throws InterruptedException
	{
		init();
		Logger.log("Policy Number:"+mockProposalNo);
		try {
			IssuanceQueue.waitSec(8);
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			//https://services.qa-aegonlife.com/payment/suspenses?policyNumber="+mockProposalNo
			response = httpRequest.get("/suspenses?policyNumber="+mockProposalNo);
			Logger.log("Response Code- Get Suspense:"+response.getStatusCode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return response;
	}
	
	public static void getAllSuspenseDetails() throws Exception
	{
		Logger.log("==========Get All suspense Details=====");
		IssuanceQueue.waitSec(2);

		JSONArray  jarray = null;
		JSONObject suspenses=null;
		jarray = (JSONArray) parser.parse(response.getBody().asString());
		
		suspenses = (JSONObject) jarray.get(0);
		try {
			amountNB = (double) suspenses.get("amount");
			suspenseType = (String) suspenses.get("suspenseType");
		} catch (Exception e2) {}
		
		suspenses = (JSONObject) jarray.get(1);
		try {
			amountRENEWAL = (double) suspenses.get("amount");
		System.out.println("Renewal:"+amountRENEWAL);
			suspenseType = (String) suspenses.get("suspenseType");
		} catch (Exception e1) {
		
		}
		
		suspenses = (JSONObject) jarray.get(2);
		try {
			suspenseAmountPayout = (double) suspenses.get("amount");
			suspenseType = (String) suspenses.get("suspenseType");
		} catch (Exception e) {}
		try {
			suspenses = (JSONObject) jarray.get(3);
			amountReinstatement = (double) suspenses.get("amount");
		} catch (Exception e) {}
		suspenseType = (String) suspenses.get("suspenseType");
		try {
			suspenses = (JSONObject) jarray.get(4);
			amountAR = (long) suspenses.get("amount");
			suspenseType = (String) suspenses.get("suspenseType");
		} catch (Exception e) {}
		Logger.log(amountNB+" * "+amountRENEWAL+" * "+suspenseAmountPayout+" * "+amountReinstatement+" * "+amountAR);
		Logger.log("================================================================");
	}
	
	
	public static Response suspensePayoutAPI(String getSuspenseType, double amount, String token, String pno) throws Exception
	{
		Logger.log("In suspense payout API>>>>>>");
		init();
		try {
			RestAssured.baseURI = prop.getProperty("suspenseAPI");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", "cP9ie2et5a1p9F7DN5K147VWXhT5IMmBgEXLSmT7");
			httpRequest.body("{\r\n" + 
					"    \"policyNumber\": \""+pno+"\",\r\n" + 
					"    \"amount\": \""+amount+"\",\r\n" + 
					"    \"type\": \"SU\",\r\n" + 
					"    \"suspenseType\": \""+getSuspenseType+"\",\r\n" + 
					"    \"refundDate\": \"2020-05-27T18:05:57.696Z\",\r\n" + 
					"    \"requestedOn\": \"2020-05-27T18:05:57.696Z\",\r\n" + 
					"    \"reason\": \"Branch Payout\",\r\n" + 
					"    \"partialPayout\": true\r\n" + 
					"}");
			//https://service-api.qa-aegonlife.com/payment/internal/payouts/initiate							
			response = httpRequest.post("/internal/payouts/initiate");
			Logger.log("Suspense Payout Initiate API:"+response.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
		
	}
	
	//Getting Details after hitting suspensePayoutAPI
	public static void  getSuspenseRefundInfo() throws Exception
	{
			IssuanceQueue.waitSec(1);
			JSONObject suspenses=null;
			suspenses = (JSONObject) parser.parse(response.getBody().asString());
			try {
				patchId  = (long) suspenses.get("id");
				payoutType = (String) suspenses.get("payoutType");
				suspenseTypeAfterPayout = (String) suspenses.get("suspenseType");
				finalPayoutStatus =(String) suspenses.get("status");
			} catch (Exception e) {
				
			}
			Logger.log(patchId +" "+payoutType+" "+suspenseTypeAfterPayout+" "+finalPayoutStatus);
	}
	
	public static void checkSuspenseAmount(String type) throws Exception
	{
		SoftAssert softAssert = new  SoftAssert();
		
	//	Logger.log("==========Get All suspense Details====="+suspenseType);
		IssuanceQueue.waitSec(4);
		Logger.log(response.getBody().asString());
		JSONArray  jarray = null;
		JSONObject suspenses=null;
		jarray = (JSONArray) parser.parse(response.getBody().asString());
		
		suspenses = (JSONObject) jarray.get(0);
		try {
			amountNB = (double) suspenses.get("amount");
			suspenseType = (String) suspenses.get("suspenseType");
		} catch (Exception e2) {}
		
		suspenses = (JSONObject) jarray.get(1);
		try {
			amountRENEWAL = (double) suspenses.get("amount");
			suspenseType = (String) suspenses.get("suspenseType");
		} catch (Exception e1) {
		
		}
		
		suspenses = (JSONObject) jarray.get(2);
		try {
			suspenseAmountPayout = (double) suspenses.get("amount");
			suspenseType = (String) suspenses.get("suspenseType");
		} catch (Exception e) {}
		try {
			suspenses = (JSONObject) jarray.get(3);
			amountReinstatement = (double) suspenses.get("amount");
		} catch (Exception e) {}
		suspenseType = (String) suspenses.get("suspenseType");
		try {
			suspenses = (JSONObject) jarray.get(4);
			amountAR = (long) suspenses.get("amount");
			suspenseType = (String) suspenses.get("suspenseType");
		} catch (Exception e) {}
		Logger.log(amountNB+" * "+amountRENEWAL+" * "+suspenseAmountPayout+" * "+amountReinstatement+" * "+amountAR);
		Logger.log("================================================================");
		
		
		if(type.equals("RP")) {
			//System.out.println("In if loop>> Renewal");
			softAssert.assertEquals(amountNB, 0.0);
			softAssert.assertEquals(amountRENEWAL, 200.0);
			softAssert.assertEquals(suspenseAmountPayout, 0.0);
			softAssert.assertEquals(amountReinstatement, 0.0);
			softAssert.assertEquals(amountAR, 0);
			
		}else if(type.equals("CR")) {
			//System.out.println("Reinstatement >>");
			softAssert.assertEquals(amountNB, 0.0);
			softAssert.assertEquals(amountRENEWAL, 0.0);
			softAssert.assertEquals(suspenseAmountPayout, 0.0);
			softAssert.assertEquals(amountReinstatement, 200.0);
			softAssert.assertEquals(amountAR, 0);
		}
		softAssert.assertAll();
	}
	
	
	public static String getPolicyNumbers(String code) throws IOException
	{
		String policy ="";
		if(code.equals(ReadPaymentJSON.mobikwik))
			policy=CommonUtility.prop.getProperty("mobikwik");
		else if(code.equals(ReadPaymentJSON.paytm))
			policy=CommonUtility.prop.getProperty("paytm");
		else if(code.equals(ReadPaymentJSON.cointab))
			policy=CommonUtility.prop.getProperty("cointab");
		else if(code.equals(ReadPaymentJSON.onemg))
			policy=CommonUtility.prop.getProperty("onemg");
		else if(code.equals(ReadPaymentJSON.flipkart))
			policy=CommonUtility.prop.getProperty("flipkart");
		else if(code.equals(ReadPaymentJSON.firstcry))	
			policy=CommonUtility.prop.getProperty("firstcry");
		else if(code.equals(ReadPaymentJSON.indialends))	
			policy=CommonUtility.prop.getProperty("indialends");
	
		return policy;
		
	}
//=======================================================================================================================	
	
	public static double calculations() {
		
		amountRENEWAL = amountRENEWAL- 50;
		return amountRENEWAL;
		// TODO Auto-generated method stub
		
	}
}
