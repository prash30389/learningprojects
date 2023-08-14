/**
 * 
 */
package practice.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import practice.utility.CustomException.GetDueAPIResponseException;
import practice.utility.CustomException.QuotationNotFoundException;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * @author SHREYAS
 *
 */
public class Payment_Utility extends TestBase {

	public static String policyNumber="";
	public static Response response=null;
	
	public static String paymentStatus ="";
	public static String vendorCode ="";
	public static String paymentModeType="";
	
	public static long ReinstatementdueAmount=0;
	public static double renewalDueAmount=0;
	public static long dueAmount=0;
	public static String dueDate="";
	
	public static JSONObject jsonObject =new JSONObject();
	public static JSONParser parser = new JSONParser();

	
	public static String createPolicyNumber()
	{
		long number = (long) Math.floor(Math.random() * 90000000000L) + 10000000000L;
		policyNumber = String.valueOf(number);
		policyNumber= "PAY1"+policyNumber;
		Logger.log("Policy Number:"+policyNumber);
		return policyNumber;
	}
	
	
	public static Response makeProposalRegisterPayment(String policyNumber, String payType,String vendor) throws Exception
	{
		init();
		Logger.log("Vendor:"+vendor);
		Logger.log("Policy Number:"+policyNumber); 
		Logger.log("PolicyNumber>>Vendor: "+policyNumber+" "+ vendor);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String system_TodaysDate = formatter.format(date).trim();
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
			IssuanceQueue.waitSec(4);
	//		Logger.log("Response Code Payment:"+response.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
		
	}
//======================================================================================================================================

	public static Response getDueAmountAndDueDate(String pno, String paymentType) throws ParseException, GetDueAPIResponseException
	{
			TestBase.init();
			Logger.log("Policy Number:"+pno);
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
			
			if(paymentType.equals(PaymentConstants.paymentTypeReinstatement))
			ReinstatementdueAmount  = (long) jsonObject.get("amount");
			else if(paymentType.equals(PaymentConstants.paymentTypeRenewal))
			renewalDueAmount=(double) jsonObject.get("amount");
		}
//			System.out.println("ReinstatementdueAmount"+ReinstatementdueAmount+">>renewalDueAmount"+renewalDueAmount);
			
		return response;
	}
	
	public static Response registerPayment_RP_CR_AR(String pno, String paymentType, String vendrName) throws Exception
	{
		IssuanceQueue.waitSec(2);
		init();
		Logger.log("Policy Number:"+pno);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			
			if(paymentType.equals("Reinstatement"))
				dueAmount =	ReinstatementdueAmount;
			else if(paymentType.contains("Renewal"))
				dueAmount =(long) renewalDueAmount;
			
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
					"    \"vendorCode\": \""+vendrName+"\",\r\n" + 
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
					"    \"transactionDate\": \""+dueDate+"\"\r\n" + 
					"}");
			
			response = httpRequest.post("/payments/register");
			IssuanceQueue.waitSec(3);
			Logger.log("Code:"+response.getStatusCode());
			Logger.log("Response:"+response.getBody().asString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	public static void getFinalPaymentStatus() throws ParseException
	{
		try {
			JSONObject object = (JSONObject) parser.parse(response.getBody().asString());
			
			jsonObject = (JSONObject) object.get("payment");
			paymentStatus = (String) jsonObject.get("status");
			Logger.log("Status"+paymentStatus);
			
			jsonObject = (JSONObject) jsonObject.get("vendorPayment");
			vendorCode = (String) jsonObject.get("vendorCode");
			Logger.log("Payer Coder:"+vendorCode);
			
			jsonObject = (JSONObject) object.get("bill");
			paymentModeType = (String)jsonObject.get("type");
			Logger.log("Mode:"+paymentModeType);
			Logger.log("============================================================================");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
