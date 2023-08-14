package practice.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import practice.testbase.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Reinstatement extends TestBase {

	public static SoftAssert st;

	public static long DueAmount = 0;
	public static String date, suspense;
	public static String SuspenseType = "";
	public static long AmountSuspense = 0;
	static String pattern = "yyyy-MM-dd";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	static String todaydate = simpleDateFormat.format(new Date());

	
	
	public static void acceptPolicy(String pno) throws Exception {
		TestBase.init();
		IssuanceQueue.getToken();
		String token = IssuanceQueue.token();
		RestAssured.baseURI = prop.getProperty("issuance");
		httpRequest=RestAssured.given();
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
		response = httpRequest.get("/internal/tasks/policy/" + pno);

		IssuanceQueue.waitSec(2);		
		
		String responeCode = response.getBody().asString();
		System.out.println(responeCode);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONArray js = (JSONArray) obj;

		int len = js.size() - 1;
		JSONObject taskId_response = (JSONObject) js.get(len);

		String taskId = (String) taskId_response.get("taskId");

		// System.out.println("taskId : " + taskId);

		RestAssured.baseURI = prop.getProperty("issuance");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
		httpRequest.body(
				"{\n\t\"comment\":\"Accept Request\",\n\t\"code\":\"ACCEPTREQUEST\",\n\t\"remarks\":\"\",\n\t\"policyNumber\":\""
						+ pno + "\"\n\t\n}");
		response = httpRequest.patch("/internal/tasks/" + taskId + "/action");
		System.out.println("Response is : " + response.getBody().asString());

	}


	public static Response initiateReinstatement(String pno) {

        TestBase.init();
		RestAssured.baseURI = prop.getProperty("renewal");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{\r\n" + 
				"	\"serviceRequestType\": \"REINSTATEMENT\",\r\n" + 
				"	\"policyNumber\": \""+pno+"\",\r\n" + 
				"	\"payAmount\": 0,\r\n" + 
				"	\"payDate\": \"2020-06-24T09:00:10.218Z\",\r\n" + 
				"	\"paymentMode\": \"string\",\r\n" + 
				"	\"coiQuestionnaire\": {\r\n" + 
				"		\"height\": 175,\r\n" + 
				"		\"weight\": \"72\",\r\n" + 
				"		\"questionnaire\": {\r\n" + 
				"			\"QUESTION1A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION2A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION3A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION4A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION4B\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION4C\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION4D\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION5A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION5B\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION5C\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION6A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION7A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION8A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION9A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION10A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION10B\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION10C\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION11A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason \": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION12A\": {\r\n" + 
				"				\"optedAnswer\": \"NO\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			},\r\n" + 
				"			\"QUESTION13A\": {\r\n" + 
				"				\"optedAnswer\": \"10000\",\r\n" + 
				"				\"reason\": null\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}");
		response = httpRequest.post("/reinstatement");
		return response;
	}


	public static void performPayment(String pno,String duedate,double amount) {

		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{\r\"amount\": \""+amount+"\",\r\"dueDate\": \""+duedate+"\",\r\"payer\": {\r\"businessId\": \"Test0QA53611108\","
				+ "\r\"name\": \"Satishagarval Annavarapu \","
				+ "\r\"mobileNumber\":\"8888888888\",\r\"email\":\"abc@an.d\"\r},"
				+ "\r\"paymentType\": \"Reinstatement\",\r\"policyNumber\": \""+pno+"\","
				+ "\r\"schemeCode\" : \"ARMP0000111\",\r\"vendorCode\": \"POLICYBAZAAR\",\r\"vendorPaymentId\" : \"Test12345\","
				+ "\r\"receiptInstrument\":{\r\"paymentMode\":\"cc\"\r},"
				+ "\r\"additionalInformation\": {\r\"productType\": \"Term\",\r\"ebppOpted\": false,\r\"nachOpted\": false,"
				+ "\r\"siOpted\": true,\r\"ecsOpted\": false\r},\r\"transactionDate\" : \""+todaydate+"\"\r}");
		response = httpRequest.post("/payments/register");
		//System.out.println("Response is : " + response.getBody().asString());
	}

	public static void Reject(String pno) throws Exception

	{

		TestBase.init();
		IssuanceQueue.getToken();
		String token = IssuanceQueue.token();
		RestAssured.baseURI = prop.getProperty("issuance");
		httpRequest=RestAssured.given();
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
		response = httpRequest.get("/internal/tasks/policy/" + pno);

		IssuanceQueue.waitSec(2);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONArray js = (JSONArray) obj;

		int len = js.size() - 1;
		JSONObject taskId_response = (JSONObject) js.get(len);

		String taskId = (String) taskId_response.get("taskId");

		RestAssured.baseURI = prop.getProperty("issuance");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("ali-application", "ISSUANCE");
		httpRequest.header("Authorization", token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
		httpRequest.body(
				"{\"comment\":\"Reject Request\","
				+ "\"code\":\"REJECTREQUEST\",\"remarks\":\"\","
				+ "\"policyNumber\":\""+pno+"\","
				+ "\"reason\":\"Test\"}");
		
		response = httpRequest.patch("/internal/tasks/" + taskId + "/action");
		System.out.println("Response is : " + response.getBody().asString());

	}

	public static void verifyStatus(String pno, String status, String reason) throws Exception {

		st = new SoftAssert();

		RestAssured.baseURI = "https://services.qa-aegonlife.com/policyv2";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		response = httpRequest.get("/group/policies/" + pno);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONObject policyStatus = (JSONObject) js.get("policyStatus");

		String PolicyStatus = (String) policyStatus.get("status");
		String policystatus_reason = (String) policyStatus.get("reason");

		System.out.println("Policy status : " + PolicyStatus);
		System.out.println("Status reason : " + policystatus_reason);
		st.assertEquals(PolicyStatus, status);
		st.assertEquals(policystatus_reason, reason);

		st.assertAll();

	}

	static String DueDate;

	public static void duePayment(String pno) throws Exception {
		System.out.println();
		System.out.println("\nDue Info : ");

		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		response = httpRequest.get("/v1/receivables?policyNumber=" + pno + "");
         IssuanceQueue.waitSec(2);
		String responeCode = response.getBody().asString();
		System.out.println(responeCode);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONArray dues = (JSONArray) js.get("dues");
		JSONObject duesdate = (JSONObject) dues.get(0);
		DueDate = (String) duesdate.get("dueDate");

		DueAmount = (long) duesdate.get("amount");

		// System.out.println("Due Date : " + DueDate);
		// System.out.println("Due Amount : " + DueAmount);

		JSONArray Suspense = (JSONArray) js.get("suspenseList");
		JSONObject amountSus = (JSONObject) Suspense.get(1);
		try {
			AmountSuspense = (long) amountSus.get("amount");
		} catch (Exception e) {
			AmountSuspense = (long) amountSus.get("amount");
		}
		SuspenseType = (String) amountSus.get("suspenseType");

	}

	public static String getDuedate() {
		return DueDate;
	}

	public static void refundInfo(String pno) {

		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{policyNumber: \"" + pno + "\"}");
		response = httpRequest.post("/payouts");

	}

	public static String mockProposal() throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("proposal");
		httpRequest = RestAssured.given();

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(ProposalRequest.Code());
		response = httpRequest.post("/mockproposals/submission?isSubstandard=substandard");
		Thread.sleep(2000);

		System.out.println("Policy Number : " + ProposalRequest.pno());

		return ProposalRequest.pno();
	}

	static String token;

	public static void ebaotoken() throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("ebao");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{\n \"username\": \"AEGONLIFE-FRONT.admin\",\n \"password\": \"C2iBDzkyJB\"\n}");
		response = httpRequest.post("/v1/json/tickets");
		Thread.sleep(2000);
		int code = response.getStatusCode();
		System.out.println("Respone code of ebao  : " + code);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		token = (String) js.get("access_token");

	}

	public static void ebaoLapse(String pno) throws Exception {
		Thread.sleep(5000);
		TestBase.init();
		RestAssured.baseURI = "https://sandbox.gw.in.ebaocloud.com/eBao/1.0";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.body("{\n\t  \"clientRequestId\": \"Lapsed812156992694\","
				+ "\n\t  \"clientRequestTime\": \"2020-05-05T05:20:14.399Z\","
				+ "\n\t  \"processDate\": \"2021-01-25T00:00:00.000+0530\"," + "\n\t  \"policyNumber\": \"" + pno
				+ "\"\n\t}");
		response = httpRequest.post("/aegon/policies/lapse");
		IssuanceQueue.waitSec(2);
		System.out.println("Policy Lapsed !!!");

	}

	public static void extraction(String pno) {
		//System.out.println(token);
		RestAssured.baseURI = "https://sandbox.gw.in.ebaocloud.com/eBao/1.0";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("X-ebao-tenant-id", "AEGONLIFE-FRONT");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.body("{\r\n  \"clientRequestId\": \"extraction272924837290\","
				+ "\r\n  \"clientRequestTime\": \"2020-05-07T05:09:32.176Z\"," + "\r\n  \"policyNumber\": \"" + pno
				+ "\"," + "\r\n  \"extractToDate\": \"2020-05-07T05:09:32.176Z\"\r\n}");
		response = httpRequest.post("/aegon/policies/renewals/extractions");

		System.out.println("Extraction Done : "+response.getStatusCode());
	}

	public static void renewalPayment(String pno, long Final_due, String date) throws Exception {

		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{\n\t\"amount\": \"" + Final_due + "\",\n\t\"dueDate\": \"" + date + "\",\n\t\"payer\": "
				+ "{\n\t\"businessId\": \"" + pno
				+ "\",\n\t\"name\": \"Satishagarval Annavarapu \",\n\t\"mobileNumber\":\"8888888888\",\n\t\"email\":\"abc@an.d\"\n\t},"
				+ "\n\t\"paymentType\": \"Renewal Payment\",\n\t\"policyNumber\": \"" + pno
				+ "\",\n\t\"schemeCode\" : \"ARMP0000111\",\n\t\"vendorCode\": \"POLICYBAZAAR\",\n\t\"vendorPaymentId\" : \"Test12345\",\n\t\"receiptInstrument\":"
				+ "{\n\t\"paymentMode\":\"cc\"\n\t},\n\t\"additionalInformation\": "
				+ "{\n\t\"productType\": \"Term\",\n\t\"ebppOpted\": false,\n\t\"nachOpted\": false,\n\t\"siOpted\": true,"
				+ "\n\t\"ecsOpted\": false\n\t},\n\t\"transactionDate\" : \"" + date + "\"\n\t}");
		response = httpRequest.post("/payments/register");
		System.out.println("Payment Response : " + response.getBody().asString());
		IssuanceQueue.waitSec(4);
	}
	
	
	
	static String actualDate,duedate;
	static double totalReinstatementAmount;
	public static Response getReinstatementquotation(String pno) throws Exception {

		TestBase.init();	
		RestAssured.baseURI = prop.getProperty("renewal");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{\"policyNumber\":\""+pno+"\","
				+ "\"paymentFreq\":\"MONTHLY\","
				+ "\"requestedOn\":\""+todaydate+"\"}");
		response = httpRequest.post("/policies/reinstatement/quotation");
	
		IssuanceQueue.waitSec(2);		

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONObject reinstatementResult=(JSONObject) js.get("reinstatementResult");
		JSONArray details=(JSONArray) reinstatementResult.get("details");
		
		JSONObject arrObj = (JSONObject) details.get(0);		
		 duedate = (String) arrObj.get("duedate");
		String actualDate1=duedate.replace("T00:00:00.000+0000", "");
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateObj1 = simpleDateFormat.parse(actualDate1);
		Date dateBefore = DateUtils.addDays(dateObj1, 0);
		actualDate = simpleDateFormat.format(dateBefore);		
		totalReinstatementAmount = (double) arrObj.get("totalReinstatementAmount");
		return response;
	}
	
	public static String dueDate() {
		return actualDate;
		
	}
	
	public static double totalReinstatementAmount() {
		return totalReinstatementAmount;
		
	}


}
