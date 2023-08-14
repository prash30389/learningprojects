package practice.utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import practice.dynamodb.CommonUtility;
import practice.reports.Logger;
import practice.testbase.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestReject extends TestBase{
	
	public static Response response;
	public static String jsonfile;
	public static JSONParser parser = new JSONParser();
	
	public static String getFormatedDate(String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void getSuspenseSummary() throws ParseException
	{
			RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com";
			RequestSpecification httpRequest = RestAssured.given();
			httpRequest.headers("Content-Type", "application/json");
			
			Response response =httpRequest.get("/suspenses/search?policyNumber="+767+"&suspenseType=PAYOUT");
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody().asString());
			
			org.json.simple.JSONArray  jarray = null;
			JSONObject suspenses=null;
			jarray = (org.json.simple.JSONArray) parser.parse(response.getBody().asString());
			
			System.out.println(jarray.size());
			suspenses = (JSONObject) jarray.get(0);
			try {
			double	amount = (double) suspenses.get("amount");
				System.out.println(amount);
			} catch (Exception e2) {e2.printStackTrace();}
			
	}
	
	

	public static void rejectPayout(String pno) throws IOException, InterruptedException
	{
		String amount ="0.0";
		String partialPayout ="false";
		String policyNo=pno;
		String reason ="Payment Reject";
		String suspenseType ="NB";
		String type ="RJ";
		String vendorCode="POLICYBAZAAR";
		
		TestBase.init();
		Logger.log("Policy Number:"+pno);
		RestAssured.baseURI = prop.getProperty("payment");
		
//		CommonUtility.loadConfigFile();
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		httpRequest.headers("X-AEGON-AUTH-CID", "16398");
		httpRequest.headers("X-AEGON-AUTH-SCOPE", "16398");
		httpRequest.body("{ \"amount\": "+amount+","
				+ "\n   \"approvalRequired\": false,"
				+ "\n	\"partialPayout\": "+partialPayout +","
				+ "\n	\"policyNumber\": \""+ policyNo +"\","
				+ "\n	\"reason\": \""+ reason +"\","
				+ "\n	\"mobileNumber\": \"6660060198\","
				+ "\n	\"refundDate\": \""+ getFormatedDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") +"\","
				+ "\n	\"requestedOn\": \""+ getFormatedDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") +"\","
				+ "\n	\"suspenseType\": \""+ suspenseType +"\","
				+ "\n	\"type\": \""+ type +"\","
				+ "\n	\"vendorCode\": \""+ vendorCode +"\" \n }");
				
		Response response = httpRequest.post("/payouts/initiate");
		Thread.sleep(10000);
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
	}
	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		// TODO Auto-generated method stub
		rejectPayout("ALI0QAP18309235");
	//	getSuspenseSummary();
	}

}
