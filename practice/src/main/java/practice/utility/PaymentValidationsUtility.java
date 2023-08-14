package practice.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
/**
 * @author SHREYAS
 *
 */
public class PaymentValidationsUtility extends TestBase {
	
	public static String responseMessage="";
	public static JSONParser parser = new JSONParser();
	public static JSONObject jsonObject = new JSONObject();
	public static String system_TodaysDate="";
	
	public static String getTodaysDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		system_TodaysDate = formatter.format(date).trim();
		return system_TodaysDate;
	}
	
	
	
	public static Response checkEmptyPaymentMode(String policyNumber,String vendor, String paymentType) throws Exception {
		// TODO Auto-generated method stub

		init();
		String system_TodaysDate = getTodaysDate();
		String payType = "";
		if(paymentType.equals(PaymentConstants.paymentTypeNewBusiness))
			payType = PaymentConstants.paymentTypeNewBusiness;
		else 
			payType = paymentType;
			
		IssuanceQueue.waitSec(1);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");

			httpRequest.body("{\r\n" + 
					"    \"amount\": \"20000\",\r\n" + 
					"    \"dueDate\": \""+system_TodaysDate+"\",\r\n" + 
					"    \"payer\": {\r\n" + 
					"        \"businessId\": \""+policyNumber+"\",\r\n" + 
					"        \"name\": \"Satishagarval Annavarapu \",\r\n" + 
					"        \"mobileNumber\": \"8888888888\",\r\n" + 
					"        \"email\": \"abc@an.d\"\r\n" + 
					"    },\r\n" + 
					"    \"paymentType\": \""+payType+"\",\r\n" + 
					"    \"policyNumber\": \""+policyNumber+"\",\r\n" + 
					"    \"schemeCode\": \"ARMP0000111\",\r\n" + 
					"    \"vendorCode\": \""+vendor+"\",\r\n" + 
					"    \"vendorPaymentId\": \"Test12345\",\r\n" + 
					"    \"receiptInstrument\": {\r\n" + 
					"        \"paymentMode\": null\r\n" + 
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
			IssuanceQueue.waitSec(4);
			jsonObject = (JSONObject) parser.parse(response.getBody().asString());
			responseMessage = (String) jsonObject.get("desc");
			Logger.log("Response Code Payment:"+response.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	public static String getResponseMessage(Response response) throws ParseException
	{
		jsonObject = (JSONObject) parser.parse(response.getBody().asString());
		JSONArray jsonArray = new  JSONArray();
		jsonArray = (JSONArray) jsonObject.get("errors");
		if(jsonArray!=null) {
			jsonObject = (JSONObject) jsonArray.get(0);
		}
		responseMessage = (String) jsonObject.get("message");
		if(responseMessage==null){
			responseMessage = (String) jsonObject.get("desc");
		}
		return responseMessage;
	}
	
	public static Response amountZeroValidation(String policyNumber, String paymentType) throws Exception {
		// TODO Auto-generated method stub

		init();
		IssuanceQueue.waitSec(1);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.body("{\r\n" + 
					"    \"amount\": \"0\",\r\n" + 
					"    \"dueDate\": \"2020-05-13\",\r\n" + 
					"    \"payer\": {\r\n" + 
					"        \"businessId\": \""+policyNumber+"\",\r\n" + 
					"        \"name\": \"Satishagarval Annavarapu \",\r\n" + 
					"        \"mobileNumber\": \"8888888888\",\r\n" + 
					"        \"email\": \"abc@an.d\"\r\n" + 
					"    },\r\n" + 
					"    \"paymentType\": \""+paymentType+"\",\r\n" + 
					"    \"policyNumber\": \""+policyNumber+"\",\r\n" + 
					"    \"schemeCode\": \"ARMP0000111\",\r\n" + 
					"    \"vendorCode\": \"POLICYBAZAAR\",\r\n" + 
					"    \"vendorPaymentId\": \"Test12345\",\r\n" + 
					"    \"receiptInstrument\": {\r\n" + 
					"        \"paymentMode\": \"cc\"\r\n" + 
					"    },\r\n" + 
					"    \"additionalInformation\": {\r\n" + 
					"        \"productType\": \"Term\",\r\n" + 
					"        \"ebppOpted\": false,\r\n" + 
					"        \"nachOpted\": false,\r\n" + 
					"        \"siOpted\": true,\r\n" + 
					"        \"ecsOpted\": false\r\n" + 
					"    },\r\n" + 
					"    \"transactionDate\": \"2020-04-27\"\r\n" + 
					"}");
			
			response = httpRequest.post("/payments/register");
		}catch(Exception e) {
			
		}
		return response;
	}
	
//=======================================================================================================================
	
	public static Response validateMismatchInPaymentType(String policyNumber, String dueDate,String paymentType, String setPaymentType) 
	{
		long dueAmount=0;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = null;
		try {
			date = (Date)formatter.parse(dueDate);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date dateBefore = DateUtils.addDays(date, -5); 
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
		String finalDueDate = newFormat.format(dateBefore);   // Actual Due date - 5
		
		if(paymentType.equals("Reinstatement"))
			dueAmount =	Payment_Utility.ReinstatementdueAmount;
		else if(paymentType.equals(PaymentConstants.paymentTypeRenewal))
			dueAmount =(long) Payment_Utility.renewalDueAmount;
		
		if(setPaymentType.equals("CR"))
			setPaymentType = "Reinstatement";
		else if (setPaymentType.equals("RP"))
			setPaymentType = "Renewal Payment";
		else
			setPaymentType = "New Business";
		
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");

			httpRequest.body("{\r\n" + 
					"    \"amount\": \""+dueAmount+"\",\r\n" + 
					"    \"dueDate\": \""+finalDueDate+"\",\r\n" + 
					"    \"payer\": {\r\n" + 
					"        \"businessId\": \""+policyNumber+"\",\r\n" + 
					"        \"name\": \"Satishagarval Annavarapu \",\r\n" + 
					"        \"mobileNumber\": \"8888888888\",\r\n" + 
					"        \"email\": \"abc@an.d\"\r\n" + 
					"    },\r\n" + 
					"    \"paymentType\": \""+setPaymentType+"\",\r\n" + 
					"    \"policyNumber\": \""+policyNumber+"\",\r\n" + 
					"    \"schemeCode\": \"ARMP0000111\",\r\n" + 
					"    \"vendorCode\": \"POLICYBAZAAR\",\r\n" + 
					"    \"vendorPaymentId\": \"Test12345\",\r\n" + 
					"    \"receiptInstrument\": {\r\n" + 
					"        \"paymentMode\": \"cc\"\r\n" + 
					"    },\r\n" + 
					"    \"additionalInformation\": {\r\n" + 
					"        \"productType\": \"Term\",\r\n" + 
					"        \"ebppOpted\": false,\r\n" + 
					"        \"nachOpted\": false,\r\n" + 
					"        \"siOpted\": true,\r\n" + 
					"        \"ecsOpted\": false\r\n" + 
					"    },\r\n" + 
					"    \"transactionDate\": \""+finalDueDate+"\"\r\n" + 
					"}");
			
			response = httpRequest.post("/payments/register");
		}catch(Exception e) {
			
		}
		return response;
		
	}
//==========================================================================================================================
	
	
	public static void cancelBill(String policyNumber)
	{
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			response = httpRequest.post("/receivables/cancel/"+policyNumber);
		//	System.out.println("Response Body:"+response.getBody().asString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static Response validateDueNotFoundForPayment(String policyNumber,String paymentType)
	{
		String system_TodaysDate = getTodaysDate();
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");

			httpRequest.body("{\r\n" + 
					"    \"amount\": \"5000\",\r\n" + 
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
					"    \"vendorCode\": \"POLICYBAZAAR\",\r\n" + 
					"    \"vendorPaymentId\": \"Test12345\",\r\n" + 
					"    \"receiptInstrument\": {\r\n" + 
					"        \"paymentMode\": \"cc\"\r\n" + 
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
		}catch(Exception e) {
			
		}
		return response;
	}
//=====================================================================================================================
	
	
	public static Response validateEmptyMobileAndEmailid(String policyNumber, String emptyString, String vendorCode, String paymentType)
	{
		String strMobile ="";
		String strEmail="";
		String system_TodaysDate = getTodaysDate();
		
		if(emptyString.equals("mobile")) 
			strMobile="";
		else 
			strMobile="8888888888";
		if(emptyString.equals("email"))
			strEmail="";
		else 
			strEmail="abc@an.d";
		
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");

			httpRequest.body("{\r\n" + 
					"    \"amount\": \"1000\",\r\n" + 
					"    \"dueDate\": \""+system_TodaysDate+"\",\r\n" + 
					"    \"payer\": {\r\n" + 
					"        \"businessId\": \""+policyNumber+"\",\r\n" + 
					"        \"name\": \"Satishagarval Annavarapu \",\r\n" + 
					"        \"mobileNumber\": \""+strMobile+"\",\r\n" + 
					"        \"email\": \""+strEmail+"\"\r\n" + 
					"    },\r\n" + 
					"    \"paymentType\": \""+paymentType+"\",\r\n" + 
					"    \"policyNumber\": \""+policyNumber+"\",\r\n" + 
					"    \"schemeCode\": \"ARMP0000111\",\r\n" + 
					"    \"vendorCode\": \""+vendorCode+"\",\r\n" + 
					"    \"vendorPaymentId\": \"Test12345\",\r\n" + 
					"    \"receiptInstrument\": {\r\n" + 
					"        \"paymentMode\": \"cc\"\r\n" + 
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
		
		}catch(Exception e) {
			
		}
		return response;
		
	}
	
	
	
}
