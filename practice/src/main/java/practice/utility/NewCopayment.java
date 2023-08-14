package practice.utility;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import practice.reports.Logger;
import practice.testbase.TestBase;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class NewCopayment extends TestBase
{
	
	public static String bill="";
	public static  String status="";
	public static String paymentStatus ="";
	public static String vendorCode ="";
	public static void registerPayment_CO(String pno) throws Exception
	{
		init();
		Logger.log("Policy Number:"+pno);
		InitiatePayment.createDue(pno, "CO");
		InitiatePayment.getDues(pno);
		double paymentAmt	= InitiatePayment.amountPayable();
		String dueDate 	= InitiatePayment.dueDate();
		Logger.log("CO paymentAmt:"+paymentAmt+">"+dueDate);
		RestAssured.baseURI="https://services.qa-aegonlife.com/payment";
		String response=given().header("Content-Type", "application/json").body("{\r\n" + 
			"  \"amount\": \""+paymentAmt+"\",\r\n" + 
			"  \"dueDate\": \""+dueDate+"\",\r\n" + 
			"  \"payer\": {\r\n" + 
			"    \"businessId\": \"ALIQA1291301231\",\r\n" + 
			"    \"name\": \"Satishagarval Annavarapu\",\r\n" + 
			"    \"mobileNumber\":\"8888888888\",\r\n" + 
			"    \"email\":\"abc@an.d\"\r\n" + 
			"  },\r\n" + 
			"  \"paymentType\": \"Counter Offer\",\r\n" + 
			"  \"policyNumber\": \""+pno+"\",\r\n" + 
			"  \"schemeCode\" : \"ARMP0000111\",\r\n" + 
			"  \"vendorCode\": \"POLICYBAZAAR\",\r\n" + 
			"  \"vendorPaymentId\" : \"Test12345\",\r\n" + 
			"  \"receiptInstrument\":{\r\n" + 
			"  	\"paymentMode\":\"cc\"\r\n" + 
			"  },\r\n" + 
			"  \"additionalInformation\": {\r\n" + 
			"    \"productType\": \"Term\",\r\n" + 
			"    \"ebppOpted\": false,\r\n" + 
			"    \"nachOpted\": false,\r\n" + 
			"    \"siOpted\": true,\r\n" + 
			"    \"ecsOpted\": false\r\n" + 
			"  },\r\n" + 
			"  \"transactionDate\" : \"2020-05-19\"\r\n" + 
			"}").when().post("/payments/register")
	            .then().assertThat().statusCode(200).extract().response().asString();
	
	         Logger.log(response);
	         
	         JsonPath js=new JsonPath(response);
	         bill=js.getString("bill.type");
	         Logger.log("\n The Bill type is:"+bill);
	         
	         
	         JsonPath js1=new JsonPath(response);
	         status =js1.getString("bill.status");
	         Logger.log("\n The bill status is:"+status);
	         JSONParser parser = new JSONParser();
	         JSONObject jsonObject =new JSONObject();
	         
	         JSONObject object = (JSONObject) parser.parse(response);
				
				jsonObject = (JSONObject) object.get("payment");
				paymentStatus = (String) jsonObject.get("status");
				Logger.log("Status"+paymentStatus);
				
				jsonObject = (JSONObject) jsonObject.get("vendorPayment");
				vendorCode = (String) jsonObject.get("vendorCode");
				Logger.log("Payer Coder:"+vendorCode);
	        
	        }
}
	