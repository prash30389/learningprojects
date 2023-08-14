package practice.utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.asserts.SoftAssert;

import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SuspenseEntries extends TestBase {
	public static String projectPath = System.getProperty("user.dir");

	public static RequestSpecification httpRequest;
	public static Response response;
	public static String jsonfile;
	public static JSONParser parser = new JSONParser();
	public static Object obj;
	
	public static long amountPAYOUT, amountAR, patchId;
	public static String suspenseType="";;
	public static double amountNB, amountRENEWAL,suspenseAmountPayout, amountReinstatement;

	public static Response getSuspense(String mockProposalNo) throws InterruptedException
	{
		init();
		Logger.log("Policy Number:"+mockProposalNo);
		try {
			IssuanceQueue.waitSec(10);
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
	
	
	
	
	public static void getAllSuspenseDetails(String suspenseType) throws Exception
	{
		SoftAssert softAssert = new  SoftAssert();
		
		Logger.log("==========Get All suspense Details=====");
		IssuanceQueue.waitSec(2);

		org.json.simple.JSONArray  jarray = null;
		JSONObject suspenses=null;
		jarray = (org.json.simple.JSONArray) parser.parse(response.getBody().asString());
		
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
		System.out.println(amountNB+" * "+amountRENEWAL+" * "+suspenseAmountPayout+" * "+amountReinstatement+" * "+amountAR);
		Logger.log("================================================================");
		System.out.println("paymentAmount:"+paymentAmount+" >amountNB:"+amountNB);
		
		if(suspenseType.equals("NB")) {
			softAssert.assertEquals(amountNB, paymentAmount);
			softAssert.assertEquals(amountRENEWAL, 0.0);
			softAssert.assertEquals(suspenseAmountPayout, 0.0);
			softAssert.assertEquals(amountReinstatement, 0.0);
			softAssert.assertEquals(amountAR, 0.0);
			
		}else if(suspenseType.equals("Renewal")) {
			softAssert.assertEquals(amountNB, 0.0);
			softAssert.assertEquals(amountRENEWAL, 100.0);
			softAssert.assertEquals(suspenseAmountPayout, 0.0);
			softAssert.assertEquals(amountReinstatement, 0.0);
			softAssert.assertEquals(amountAR, 0.0);
			
		}else if(suspenseType.equals("Reinstatement")) {
			softAssert.assertEquals(amountNB, 0.0);
			softAssert.assertEquals(amountRENEWAL, 0.0);
			softAssert.assertEquals(suspenseAmountPayout, 0.0);
			softAssert.assertEquals(amountReinstatement, 100.0);
			softAssert.assertEquals(amountAR, 0.0);
		}
		softAssert.assertAll();
	}
	
	public static Double paymentAmount=0.0;
	public static void actual_Quotation(String pno) throws Exception {

		int amount;
		RestAssured.baseURI = prop.getProperty("quotation");
		RequestSpecification httpRequest, httpRequest1;

		httpRequest1 = RestAssured.given();
		httpRequest1.header("Content-Type", "application/json");

		Response response1 = httpRequest1.get("/quotation?policyNumber=" + pno);
		Thread.sleep(3000);
		System.out.println("Quotation Code:"+response1.getStatusCode());
		if(response1.getStatusCode()!=200)
		{
			throw new CustomException.QuotationNotFoundException("Quotation not found:"+pno);
		}else {
		String responeCode_payment = response1.getBody().asString();
		JSONParser payment = new JSONParser();
		Object obj_payment = payment.parse(responeCode_payment);

		JSONObject js_payment = (JSONObject) obj_payment;

		JSONObject policyInfo = (JSONObject) js_payment.get("policyInfo");
		JSONObject premium = (JSONObject) policyInfo.get("premium");
		Double installmentPremium = (Double) premium.get("installmentPremium");		
		System.out.println("Amount to pay : "+installmentPremium);	

		 Thread.sleep(3200);
		 paymentAmount=installmentPremium-100;
		}

}
	
	/**
	 * Method to pay more 100 Rs. than Quotation, So that Amount will transfer to Renewal
	 */
	public static void addMorePaymentAmount()
	{
		paymentAmount = 200.0;
	}
	
	public static void doPayment(String pno) throws InterruptedException
	{
		System.out.println("Payment AMount:>>>:"+paymentAmount);
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");

		httpRequest.body("{\n    \"amount\": \"" + paymentAmount + "\","
				+ "\n    \"dueDate\": \"2019-11-30\",\n    \"payer\": {\n        \"businessId\": \"ALIFGG469206489\","
				+ "\n        \"name\": \"Jones Smith\",\n        \"mobileNumber\": \"8888888888\","
				+ "\n        \"email\": \"abc@an.d\"\n    },\n    \"paymentType\": \"New Business\","
				+ "\n    \"policyNumber\": \"" + pno + "\",\n    \"schemeCode\": \"ARMP0000111\","
				+ "\n    \"vendorCode\": \"POLICYBAZAAR\",\n    \"vendorPaymentId\": \"Test12345\","
				+ "\n    \"receiptInstrument\": {\n        \"paymentMode\": \"cc\"\n    },"
				+ "\n    \"additionalInformation\": {\n        \"productType\": \"Term\","
				+ "\n        \"ebppOpted\": false,\n        \"nachOpted\": false,"
				+ "\n        \"siOpted\": true,\n        \"ecsOpted\": false\n    },"
				+ "\n    \"transactionDate\": \"2020-04-05\"\n}");

		Response response = httpRequest.post("/payments/register");
		Thread.sleep(4000);
		int statusCode = response.getStatusCode();
		if (statusCode == 200) {
			System.out.println("Payment Done : "+pno);
		} else {
			System.out.println("Payment not done ");
			System.out.println(response.getBody().asString());

		}
	}
	
	
	public static void main(String[] args) {
		try {
			getSuspense("ALI444000113057");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
	//		getAllSuspenseDetails();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
