package practice.utility;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FreelookTest extends TestBase {

	static SoftAssert st;
	static String pattern = "yyyy-MM-dd";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	static String todaydate = simpleDateFormat.format(new Date());
	static Double totalFreelookCharges;
	static Double totalRefundAmount;
	static Double netSV;
	
	public static Response freelookriderCancellation(String pno) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\""+ pno +"\",\"comment\":\"\",\"medicalFee\":0,"
				+ "\"cancellationType\":\"RIDER_FREELOOK\",\"reason\":\"INADEQUATE_BENEFITS\","
				+ "\"refundType\":\"STANDARD\",\"requestedOn\":\"" + todaydate + "\",\"coverageCode\":[\"138B006V05\"],"
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"4a370000-9de6-4bcc-9dae-fd2c959f9f38\","
				+ "\"policyNumber\":\"" + pno +"\",\"docType\":\"Customer_consent\",\"isUploaded\":true}]}");
		response = httpRequest.post("/internal/coverage/freelook");
		Thread.sleep(2000);

		return response;

	}
	public static Response surrenderriderCancellation(String pno) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\""+ pno +"\",\"comment\":\"\",\"medicalFee\":0,"
				+ "\"cancellationType\":\"RIDER_SURRENDER\",\"reason\":\"FINANCIAL_PROBLEM\",\"refundType\":\"STANDARD\","
				+ "\"requestedOn\":\""+ todaydate +"\",\"coverageCode\":[\"138B006V05\"],"
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"19eb37da-3566-4a9e-bdc9-94050449715e\","
				+ "\"policyNumber\":\"" + pno +"\","
				+ "\"docType\":\"Customer_Consent\",\"isUploaded\":true}]}");
		response = httpRequest.post("/internal/surrender/rider");
		Thread.sleep(2000);

		return response;

	}
	public static Response getQuoteFR(String pno) throws Exception {
		TestBase.init();
		String quotationStatus="";
		int i=0;
		while(i<=10) {
			RestAssured.baseURI = prop.getProperty("cancelpolicy");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
			httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
			httpRequest.body("{\"policyNumber\":\""+ pno +"\",\"comment\":\"\",\"medicalFee\":0,"
					+ "\"cancellationType\":\"RIDER_FREELOOK\",\"reason\":\"INADEQUATE_BENEFITS\","
					+ "\"refundType\":\"STANDARD\",\"requestedOn\":\"" + todaydate + "\",\"coverageCode\":[\"138B006V05\"]}");
			response = httpRequest.post("/internal/policies/rider/freelook/quote");
			staticWait(3);
			if(response.getStatusCode()!=200) {
				i++;
				staticWait(5);
			}
			if(response.getStatusCode()==200) {
				System.out.println("loop count is	: "+i);
				quotationStatus= "Pass";	
				break;
			}
			return response;
		}
	if(quotationStatus.equals("Pass")) {
		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;
		JSONObject quotationInfo = (JSONObject) js.get("quotationInfo");
		totalFreelookCharges = (Double) quotationInfo.get("totalFreelookCharges");
		totalRefundAmount = (Double) quotationInfo.get("totalRefundAmount");
		st.assertEquals(totalFreelookCharges, totalRefundAmount);
	}
	return response;
}
	public static Response getQuoteSR(String pno) throws Exception {
		TestBase.init();
		String quotationStatus="";
		int i=0;
		while(i<=10) {
			RestAssured.baseURI = prop.getProperty("cancelpolicy");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
			httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
			httpRequest.body("{\"policyNumber\":\"" + pno +"\",\"comment\":\"\",\"medicalFee\":0,"
					+ "\"cancellationType\":\"RIDER_SURRENDER\",\"reason\":\"FINANCIAL_PROBLEM\","
					+ "\"refundType\":\"STANDARD\",\"requestedOn\":\"" + todaydate + "\",\"coverageCode\":[\"138B006V05\"]}");
			response = httpRequest.post("/internal/surrender/quotations");
			staticWait(3);
			if(response.getStatusCode()!=200) {
				i++;
				staticWait(5);
			}
			if(response.getStatusCode()==200) {
				System.out.println("loop count is	: "+i);
				quotationStatus= "Pass";	
				break;
			}
			return response;
		}
	if(quotationStatus.equals("Pass")) {
		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;
		JSONObject quotationInfo = (JSONObject) js.get("quotationInfo");
		JSONObject surrenderQuotationResponse = (JSONObject) js.get("surrenderQuotationResponse");
		netSV = (Double) surrenderQuotationResponse.get("netSV");
		totalRefundAmount = (Double) quotationInfo.get("totalRefundAmount");
		st.assertEquals(netSV, totalRefundAmount);
	}
	return response;
}
	public static Response getQuote(String pno) throws Exception {
		TestBase.init();
		String quotationStatus="";
		int i=0;
		while(i<=10) {
			RestAssured.baseURI = prop.getProperty("cancelpolicy");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
			httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
			httpRequest.body("{\"policyNumber\":\"" + pno + "\",\"comment\":\"Cancel \","
					+ "\"medicalFee\":\"10\",\"reason\":\"PURCHASED_BY_MISTAKE\"," + "\"requestedOn\":\"" + todaydate
					+ "\",\"cancellationType\":\"FREELOOK\"," + "\"refundType\":\"STANDARD\"}");
			response = httpRequest.post("/internal/policies/quote");
			staticWait(3);
			if(response.getStatusCode()!=200) {
				i++;
				staticWait(5);
			}
			if(response.getStatusCode()==200) {
				System.out.println("loop count is	: "+i);
				quotationStatus= "Pass";	
				break;
			}
		}
		if(quotationStatus.equals("Pass")) {
			String responeCode = response.getBody().asString();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(responeCode);
			JSONObject js = (JSONObject) obj;
			JSONObject quotationInfo = (JSONObject) js.get("quotationInfo");
			totalFreelookCharges = (Double) quotationInfo.get("totalFreelookCharges");
		}
		return response;
	}

	public static Response getQuoteNullVoid(String pno, String refundType) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno + "\"," + "\"comment\":\"cancel policy\",\"medicalFee\":0,"
				+ "\"reason\":\"MEDICAL_HISTORY\",\"requestedOn\":\"" + todaydate + "\","
				+ "\"cancellationType\":\"NULL_AND_VOID\",\"refundType\":\"" +refundType + "\","
				+ "\"coverageNo\":[\"127606892\"]}");
		response = httpRequest.post("/internal/null-and-void/quotations");
		Thread.sleep(2000);
		Assert.assertEquals(response.getStatusCode(), 200);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONObject quotationInfo = (JSONObject) js.get("quotationInfo");
		totalFreelookCharges = (Double) quotationInfo.get("totalFreelookCharges");
		return response;
	}
	
	public static Response getQuoteCancelFullRefund(String pno, String refundType) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno + "\"," + "\"comment\":\"cancel full refund\",\"medicalFee\":0,"
				+ "\"reason\":\"INADEQUATE_BENEFITS\",\"requestedOn\":\"" + todaydate + "\","
				+ "\"cancellationType\":\"CANCEL_FULL_REFUND\",\"refundType\":\"" +refundType + "\","
				+ "\"coverageNo\":[\"\"]}");
		response = httpRequest.post("/internal/cancel-full-refund/quotation");
		Thread.sleep(2000);
		Assert.assertEquals(response.getStatusCode(), 200);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONObject quotationInfo = (JSONObject) js.get("quotationInfo");
		totalFreelookCharges = (Double) quotationInfo.get("totalRefundAmount");
		return response;
	}
	
	public static Double refundAmount() {
		return totalFreelookCharges;
	}

	public static Response getQuoteSurrender(String pno) throws Exception {
		TestBase.init();
		String quotationStatus="";
		int i=0;
		while(i<=10) {
			RestAssured.baseURI = prop.getProperty("cancelpolicy");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
			httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
			httpRequest.body("{\"policyNumber\":\"" + pno + "\"," 
					+ "\"comment\":\"surrender policy\",\"medicalFee\":0,"
					+ "\"reason\":\"FINANCIAL_REQUIREMENT\",\"requestedOn\":\"" + todaydate + "\","
					+ "\"cancellationType\":\"SURRENDER\",\"refundType\":\"STANDARD\","
					+ "\"coverageCode\":[\"\"]}");
			response = httpRequest.post("/internal/surrender/quotations");
			IssuanceQueue.waitSec(3);
			if(response.getStatusCode()!=200) {
				i++;
				staticWait(5);
			}
			if(response.getStatusCode()==200) {
				System.out.println("loop count is	: "+i);
				quotationStatus= "Pass";	
				break;
			}
		}
		if(quotationStatus.equals("Pass")) {
			Assert.assertEquals(response.getStatusCode(), 200);
			String responeCode = response.getBody().asString();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(responeCode);
			JSONObject js = (JSONObject) obj;
			JSONObject quotationInfo = (JSONObject) js.get("quotationInfo");
			totalFreelookCharges = (Double) quotationInfo.get("totalRefundAmount");
		}
		return response;
	}

	public static Response freelookCancellation(String pno) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno
				+ "\",\"comment\":\"Cancel \",\"medicalFee\":\"10\",\"reason\":\"PURCHASED_BY_MISTAKE\","
				+ "\"requestedOn\":\"" + todaydate + "\","
				+ "\"cancellationType\":\"FREELOOK\",\"refundType\":\"STANDARD\"}");
		response = httpRequest.post("/internal/policies/cancellation");
		Thread.sleep(2000);
		return response;
	}

	public static Response addRiderCancellation(String pno) throws Exception {
		TestBase.init();
		String access_token = TestBase.getToken();
		RestAssured.baseURI = prop.getProperty("api_gateway");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + access_token);
		httpRequest.header("x-api-key", prop.getProperty("api_key"));
		response = httpRequest.get("/policy/" + pno);
		IssuanceQueue.waitSec(2);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONArray coverages = (JSONArray) js.get("coverages");
		JSONObject coverages_Info;

		coverages_Info = (JSONObject) coverages.get(0);

		String coverageType = (String) coverages_Info.get("coverageType");

		String coverageNo;

		if (coverageType.equalsIgnoreCase("RIDER")) {
			coverageNo = (String) coverages_Info.get("coverageNo");
		} else {
			coverages_Info = (JSONObject) coverages.get(1);
			coverageNo = (String) coverages_Info.get("coverageNo");

		}

		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno + "\","
				+ "\"comment\":\"\",\"medicalFee\":0,\"reason\":\"PURCHASED_BY_MISTAKE\"," + "\"requestedOn\":\""
				+ todaydate + "\",\"refundType\":\"STANDARD\"," + "\"cancellationType\":\"RIDER_FREELOOK\","
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"10df79fa-63c4-4194-bd0d-a11b719471fe\","
				+ "\"policyNumber\":\"" + pno + "\",\"docType\":\"Customer_consent\",\"isUploaded\":true}],"
				+ "\"coverageNo\":[\"" + coverageNo + "\"]}");
		response = httpRequest.post("/internal/policies/rider/freelook");
		Thread.sleep(2000);

		System.out.println(coverageNo);

		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asString());
		return response;
	}
	
	public static void deleteRiderStatus(String pno, String verifyStatus, String verifyStatusDesc, String verifyreason,
			String verifyreasonDesc) throws Exception {

		st = new SoftAssert();
		TestBase.init();

		String access_token = TestBase.getToken();
		RestAssured.baseURI = prop.getProperty("api_gateway");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + access_token);
		httpRequest.header("x-api-key", prop.getProperty("api_key"));
		response = httpRequest.get("/policy/" + pno);
		IssuanceQueue.waitSec(2);
		int statusCode = response.getStatusCode();
		System.out.println("GET -> Policy Number : The status code is: " + statusCode);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONArray coverages = (JSONArray) js.get("coverages");
		JSONObject coverages_Info;

		coverages_Info = (JSONObject) coverages.get(0);

		String coverageType = (String) coverages_Info.get("coverageType");

		String coverageNo;

		if (coverageType.equalsIgnoreCase("RIDER")) {
			coverageNo = (String) coverages_Info.get("coverageNo");
		} else {
			coverages_Info = (JSONObject) coverages.get(1);
			coverageNo = (String) coverages_Info.get("coverageNo");

		}

		String status = (String) coverages_Info.get("status");
		String statusDesc = (String) coverages_Info.get("statusDesc");
		String reason = (String) coverages_Info.get("reason");
		String reasonDesc = (String) coverages_Info.get("reasonDesc");

		st.assertEquals(verifyStatus, status);
		st.assertEquals(verifyStatusDesc, statusDesc);
		st.assertEquals(verifyreason, reason);
		st.assertEquals(verifyreasonDesc, reasonDesc);

		st.assertAll();
	}

	public static Response null_void_Cancellation(String pno, String refundType) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno + "\",\"comment\":\"\","
				+ "\"medicalFee\":0,\"reason\":\"MEDICAL_HISTORY\"," + "\"requestedOn\":\"" + todaydate
				+ "\",\"refundType\":\"" + refundType + "\"," + "\"cancellationType\":\"NULL_AND_VOID\","
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"e797be2c-676a-4dd0-8313-99d156b4940b\","
				+ "\"policyNumber\":\"" + pno + "\"," + "\"docType\":\"Legal_Letter\",\"isUploaded\":true},"
				+ "{\"docId\":\"160b11d0-0689-417a-a069-efb5e9ed83b3\"," + "\"policyNumber\":\"" + pno
				+ "\",\"docType\":\"Approval_Email\",\"isUploaded\":true}],"
				+ "\"coverageNo\":[\"134063214\",\"134063219\"]}\r\n\r\n");
		response = httpRequest.post("/internal/null-and-void");
		Thread.sleep(2000);
		return response;
	}
	
	public static Response cancel_full_refund_Cancellation(String pno, String refundType) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno + "\",\"comment\":\"Can Full Refund\","
				+ "\"medicalFee\":0,\"reason\":\"INADEQUATE_BENEFITS\"," + "\"requestedOn\":\"" + todaydate
				+ "\",\"refundType\":\"" + refundType + "\"," + "\"cancellationType\":\"CANCEL_FULL_REFUND\","
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"a1b1fe88-9827-4344-9ab5-f31fe95018a7\","
				+ "\"policyNumber\":\"" + pno + "\"," + "\"docType\":\"Legal_Letter\",\"isUploaded\":true},"
				+ "{\"docId\":\"160b11d0-0689-417a-a069-efb5e9ed83b3\"," + "\"policyNumber\":\"" + pno
				+ "\",\"docType\":\"Approval_Email\",\"isUploaded\":true}],"
				+ "\"coverageNo\":[\"134063214\",\"134063219\"]}\r\n\r\n");
		response = httpRequest.post("/internal/cancel-full-refund");
		Thread.sleep(2000);
		return response;
	}

	public static Response surrenderCancellation(String pno) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno + "\","
				+ "\"comment\":\"Surrender this policy\",\"medicalFee\":0," 
				+ "\"reason\":\"FINANCIAL_REQUIREMENT\",\"requestedOn\":\""+ todaydate + "\"," 
				+ "\"refundType\":\"STANDARD\",\"cancellationType\":\"SURRENDER\","
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"eb131600-d18d-440d-b561-ed3d77c493ca\","
				+ "\"policyNumber\":\"" + pno + "\",\"docType\":\"Request_Letter\","
				+ "\"isUploaded\":true}],\"coverageCode\":[\"\"]}");
		response = httpRequest.post("/internal/surrender");
		IssuanceQueue.waitSec(2);
		return response;
	
		/*String access_token = TestBase.getToken();
		RestAssured.baseURI = prop.getProperty("api_gateway");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + access_token);
		httpRequest.header("x-api-key", prop.getProperty("api_key"));
		response = httpRequest.get("/policy/" + pno);
		IssuanceQueue.waitSec(2);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONArray coverages = (JSONArray) js.get("coverages");
		JSONObject coverages_Info;

		coverages_Info = (JSONObject) coverages.get(0);

		String coverageNo1, coverageNo2;
		coverageNo1 = (String) coverages_Info.get("coverageNo");
		
		RestAssured.baseURI = prop.getProperty("cancelpolicy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"policyNumber\":\"" + pno + "\","
				+ "\"comment\":\"Surrender this policy\",\"medicalFee\":0," + "\"reason\":\"FINANCIAL_REQUIREMENT\",\"requestedOn\":\""
				+ todaydate + "\"," + "\"refundType\":\"STANDARD\",\"cancellationType\":\"SURRENDER\","
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"204e95bc-8b47-4a46-83a3-08daed45b661\","
				+ "\"policyNumber\":\"" + pno + "\",\"docType\":\"Request_Letter\","
				+ "\"isUploaded\":true}],\"coverageNo\":[\"" + coverageNo1 + "\"]}");
		response = httpRequest.post("/internal/surrender");
		IssuanceQueue.waitSec(2);
		return response;*/
	}


	public static Response revokePolicy(String pno) throws Exception {
		TestBase.init();

		RestAssured.baseURI = prop.getProperty("policy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		response = httpRequest.get("/group/policies/servicerequests?policy_number=" + pno);
		IssuanceQueue.waitSec(2);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONArray serviceRequestList = (JSONArray) js.get("serviceRequestList");
		JSONObject serviceRequest = (JSONObject) serviceRequestList.get(0);

		String requestId = (String) serviceRequest.get("requestId");

		/**********************************************************************************************/

		RestAssured.baseURI = "https://services.qa-aegonlife.com/cancelpolicy";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{\"docId\":\"648ebd99-b4fb-43ed-9b82-5112bddebc29\"," + "\"policyNumber\":\"" + pno + "\","
				+ "\"docType\":\"CUSTOMER_EMAIL\"}");
		response = httpRequest.post("/policies/" + pno + "/sid/" + requestId + "/revoke");
		Thread.sleep(2000);
		return response;
	}

	public static Response getPolicy(String pno) throws Exception {

		st = new SoftAssert();
		RestAssured.baseURI = prop.getProperty("policy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		response = httpRequest.get("/group/policies/" + pno);
		Thread.sleep(2000);
		return response;
	}

	public static Response verifyServiceRequest(String pno) throws Exception {

		st = new SoftAssert();
		RestAssured.baseURI = prop.getProperty("policy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		response = httpRequest.get("/group/policies/servicerequests?policy_number=" + pno);
		Thread.sleep(2000);
		return response;
	}

	public static void suspenseAmount(double vamount, double vamountNB, double vamountRENEWAL, long vamountPAYOUT,
			long vamountREINSTATEMENT, long vamountAR) throws Exception {

		SoftAssert st = new SoftAssert();
		TestBase.init();

		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");

		response = httpRequest.get("/suspenses?policyNumber=" + pno);
		Thread.sleep(8000);

		String responeCode = response.getBody().asString();

		System.out.println(responeCode);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONArray js = (JSONArray) obj;

		JSONObject suspenses;

		double amount;
		double amountNB, amountRENEWAL;
		long amountPAYOUT, amountREINSTATEMENT, amountAR;
		String suspenseType, policyNumber;

		suspenses = (JSONObject) js.get(0);
		amount = (double) suspenses.get("amount");
		suspenseType = (String) suspenses.get("suspenseType");
		policyNumber = (String) suspenses.get("policyNumber");
		System.out.println(policyNumber);

		suspenses = (JSONObject) js.get(1);
		amountNB = (double) suspenses.get("amount");
		suspenseType = (String) suspenses.get("suspenseType");
		policyNumber = (String) suspenses.get("policyNumber");

		suspenses = (JSONObject) js.get(2);
		amountRENEWAL = (double) suspenses.get("amount");
		suspenseType = (String) suspenses.get("suspenseType");
		policyNumber = (String) suspenses.get("policyNumber");

		suspenses = (JSONObject) js.get(3);
		amountREINSTATEMENT = (long) suspenses.get("amount");
		suspenseType = (String) suspenses.get("suspenseType");
		policyNumber = (String) suspenses.get("policyNumber");

		suspenses = (JSONObject) js.get(4);
		amountAR = (long) suspenses.get("amount");
		suspenseType = (String) suspenses.get("suspenseType");
		policyNumber = (String) suspenses.get("policyNumber");

		st.assertEquals(amount, vamount);
		st.assertEquals(amountNB, vamountNB);
		st.assertEquals(amountRENEWAL, vamountRENEWAL);
		st.assertEquals(amountREINSTATEMENT, vamountREINSTATEMENT);
		st.assertEquals(amountAR, vamountAR);
		st.assertAll();
	}

	public static void refundInfoPayout(String pno, String vPolicyStatus) throws Exception {
		st = new SoftAssert();
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{\"policyNumber\":\"" + pno + "\"}");
		response = httpRequest.post("/payouts");
		IssuanceQueue.waitSec(2);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONArray values = (JSONArray) obj;

		JSONObject js = (JSONObject) values.get(0);

		long id = (long) js.get("id");
		String PolicyStatus = (String) js.get("status");
		Double RefundAmount = (Double) js.get("amountPaid");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String todaydate = simpleDateFormat.format(new Date());

		if (PolicyStatus.equals("INITIATED")) {
			HttpResponse<String> response1 = Unirest.patch("https://services.qa-aegonlife.com/payment/payouts/offline")
					  .header("Content-Type", "application/json")
					  .header("Cookie", "JSESSIONID=3BC67488B0BAB0775DFBF3A61F4E8FE3")
					  .body("{\r\n  \"instrumentNumber\": \"test\",\r\n  \"issuedOn\": \""+todaydate+"\",\r\n  \"payoutId\": "+id+"\r\n}")
					  .asString();
			IssuanceQueue.waitSec(4);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody().asString());

			RestAssured.baseURI = prop.getProperty("payment");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.body("{\"policyNumber\":\"" + pno + "\"}");
			response = httpRequest.post("/payouts");
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody().asString());

			IssuanceQueue.waitSec(2);
			String responeCode1 = response.getBody().asString();
			JSONParser parser1 = new JSONParser();
			Object obj1 = parser1.parse(responeCode1);
			JSONArray values1 = (JSONArray) obj1;

			JSONObject js1 = (JSONObject) values1.get(0);

			String PolicyStatus_verify = (String) js1.get("status");

			System.out.println("PolicyStatus_verify : "+PolicyStatus_verify);

			st.assertEquals(PolicyStatus_verify, vPolicyStatus);
		} else {
			st.assertEquals(PolicyStatus, vPolicyStatus);
		}
		st.assertAll();
	}
}