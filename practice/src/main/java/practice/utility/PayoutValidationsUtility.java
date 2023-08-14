package practice.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
/**
 * @author SHREYAS
 *
 */
public class PayoutValidationsUtility extends TestBase {

	
	public static String responseMessage="";
	public static JSONParser parser = new JSONParser();
	public static JSONObject jsonObject = new JSONObject();
	public static String system_TodaysDate="";
	public static String emptySuspenseTypeErrorMessage="";
	
	
	public static String getTodaysDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		system_TodaysDate = formatter.format(date).trim();
		return system_TodaysDate;
	}
	
	
	public static Response validationForEmptySuspenseType(String policyNumber) throws Exception
	{
		init();
		IssuanceQueue.waitSec(1);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("X-AEGON-AUTH-CID", "16398");
			httpRequest.header("X-AEGON-AUTH-SCOPE", "16398");

			httpRequest.body("{\r\n" + 
					"  \"amount\": 0,\r\n" + 
					"  \"approvalRequired\": false,\r\n" + 
					"  \"partialPayout\": true,\r\n" + 
					"  \"policyNumber\": \""+policyNumber+"\",\r\n" + 
					"  \"reason\": \"TEST\",\r\n" + 
					"  \"refundDate\": \"2020-04-20T14:07:08.929Z\",\r\n" + 
					"  \"requestedOn\": \"2020-04-20T14:07:08.929Z\",\r\n" + 
					"  \"type\": \"SU\",\r\n" + 
					" \"suspenseType\":null\r\n" + 
					"}");
			
			response = httpRequest.post("/payouts/initiate");
			IssuanceQueue.waitSec(4);
			jsonObject = (JSONObject) parser.parse(response.getBody().asString());
			JSONArray jsonArray = new  JSONArray();
			jsonArray = (JSONArray) jsonObject.get("errors");
			
			jsonObject = (JSONObject) jsonArray.get(0);
			emptySuspenseTypeErrorMessage = (String) jsonObject.get("message");
	
			if(!emptySuspenseTypeErrorMessage.equals("Partial Payout Suspense Type not specified")) {
				System.out.println("In If loop");
				jsonObject = (JSONObject) jsonArray.get(1);
				emptySuspenseTypeErrorMessage=(String) jsonObject.get("message");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}	
	
	
	public static Response validationForDifferentPayoutTypes(String policyNumber,String payoutType) throws Exception
	{
		init();
		IssuanceQueue.waitSec(1);
		try {
			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("X-AEGON-AUTH-CID", "16398");
			httpRequest.header("X-AEGON-AUTH-SCOPE", "16398");

			httpRequest.body("{\r\n" + 
					"  \"amount\": 1000,\r\n" + 
					"  \"approvalRequired\": false,\r\n" + 
					"  \"partialPayout\": true,\r\n" + 
					"  \"policyNumber\": \""+policyNumber+"\",\r\n" + 
					"  \"reason\": \"TEST\",\r\n" + 
					"  \"refundDate\": \"2020-04-20T14:07:08.929Z\",\r\n" + 
					"  \"requestedOn\": \"2020-04-20T14:07:08.929Z\",\r\n" + 
					"  \"type\": \""+payoutType+"\",\r\n" + 
					" \"suspenseType\":\"RENEWAL\"\r\n" + 
					"}");
			
			response = httpRequest.post("/payouts/initiate");
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return response;
		
	}
	
	
}
