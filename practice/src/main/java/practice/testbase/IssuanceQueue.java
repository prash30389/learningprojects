package practice.testbase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import practice.reports.Logger;
import practice.utility.ProposalRequest;
import practice.utility.ProposalRequest_grace;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class IssuanceQueue extends TestBase {

	static SoftAssert st;
	static PageObj p;
	public static String policyStatus_CO="";

	public static Response submitProposal_Api(String body) throws Exception {
		String access_token = TestBase.getToken();
		RestAssured.baseURI = prop.getProperty("api_gateway");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + access_token);
     	httpRequest.header("x-api-key", prop.getProperty("api_key"));
		httpRequest.body(ProposalRequest.Code());
		response = httpRequest.post("/policy/retail");
		return response;
	}
	public static Response mockAddRiderProposal(String body) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("proposal");
		httpRequest = RestAssured.given();

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(body);
		response = httpRequest.post("/mockproposals/submission?isSubstandard=standard");
		Thread.sleep(2000);

		System.out.println("Policy Number : " + ProposalRequest_Backdate.pno());
		return response;

	}
	public static Response submitProposal(String body) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("proposal");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(ProposalRequest.Code());
		response = httpRequest.post("/retail/submission");
		Thread.sleep(2000);
		System.out.println("Policy Number : " + ProposalRequest.pno());
		return response;
	}

	public static Response mockgraceProposal(String body) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("proposal");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(body);
		response = httpRequest.post("/mockproposals/submission?isSubstandard=substandard");
		Thread.sleep(2000);
		System.out.println("Policy Number : " + ProposalRequest_grace.pno());
		return response;
	}
	
	public static Response mockBackdatedProposal(String body) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("proposal");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(body);
		response = httpRequest.post("/mockproposals/submission?isSubstandard=standard");
		Thread.sleep(2000);
		return response;
	}
	public static Response mockProposal(String body) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("proposal");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(body);
		response = httpRequest.post("/mockproposals/submission?isSubstandard=standard");
		Thread.sleep(2000);
		return response;
	}
	/**
	 * Ankit
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static Response mockeMandateProposal(String body) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("proposal");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(body);
		response = httpRequest.post("/mockproposals/submission?isSubstandard=standard");
		Thread.sleep(2000);
		System.out.println("Policy Number : " + ProposalRequest.pno());
		return response;
	}

	public static void pendingQueue(String pno) throws Exception {
		IssuanceQueue.waitSec(2);
		TestBase.init();
		String queueName = IssuanceQueue.verifyQueue(pno);
		System.out.println(queueName);
		IssuanceQueue.waitSec(2);
		if (queueName.contains("Pending")) {
			st = new SoftAssert();
			String taskId = IssuanceQueue.taskId(pno);
			IssuanceQueue.waitSec(2);
			// System.out.println(taskId);
			TestBase.init();
			String token = IssuanceQueue.token();
			TestBase.restAssured("issuance");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			httpRequest.body("{\"code\":\"ACCEPT\"," + "\"comment\":\"Comment from Esales \"," + "\"policyNumber\":\""
					+ pno + "\"}");
			response = httpRequest.patch("/internal/tasks/" + taskId + "/action");
			Thread.sleep(2000);
			int code1 = response.getStatusCode();
			System.out.println("Status code for Esales Actions is : " + code1);
			System.out.println("Response ESales Actions : " + response.getBody().asString());
			st.assertEquals(code1, 200);
			String desc = response.jsonPath().getString("desc");
			st.assertTrue(desc.contains("successfully"),
					"Desciption should contains expected : successfully  Actual Code : " + desc);
			System.out.println("");
			// Thread.sleep(10000);
			// QueueName.verifyQueueName(pno, 1, "CMO");
			st.assertAll();
			System.out.println();
		} else {
			throw new SkipException("QueueName is different found is : " + queueName);
		}
	}

	public static void cmoQueue(String pno) throws Exception {
		IssuanceQueue.waitSec(2);
		TestBase.init();
		String queueName = IssuanceQueue.verifyQueue(pno);
		IssuanceQueue.waitSec(2);
		if (queueName.contains("CMO")) {
			st = new SoftAssert();
			String taskId = IssuanceQueue.taskId(pno);
			IssuanceQueue.waitSec(2);
//			System.out.println(taskId);
			TestBase.init();
			String token = IssuanceQueue.token();
			TestBase.restAssured("issuance");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			httpRequest.body(
					"{\"code\":\"REFERTOSOURCE\",\"comment\":\"comment from CMO\",\"policyNumber\":\"" + pno + "\"}");
			response = httpRequest.patch("/internal/tasks/" + taskId + "/action");
			IssuanceQueue.waitSec(2);
			int code = response.getStatusCode();
			System.out.println("Status code for CMO Actions is : " + code);
			System.out.println("Response CMO Actions : " + response.getBody().asString());
			st.assertEquals(code, 200);
			String desc = response.jsonPath().getString("desc");
			st.assertTrue(desc.contains("successfully"),
					"Desciption should contains expected : successfully  Actual Code : " + desc);
			System.out.println("");
		} else {
			throw new SkipException("QueueName is different found is : " + queueName);
		}
	}

	public static void uwQueueStandard(String pno) throws Exception {
		TestBase.init();
		IssuanceQueue.waitSec(2);
		String queueName = IssuanceQueue.verifyQueue(pno);
		IssuanceQueue.waitSec(2);
		if (queueName.contains("Underwriter")) {
			st = new SoftAssert();
			String taskId = IssuanceQueue.taskId(pno);
			IssuanceQueue.waitSec(2);
			// System.out.println(taskId);
			System.out.println("UW - > Standard Decision");
			TestBase.init();
			String token = IssuanceQueue.token();
			TestBase.restAssured("issuance");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			httpRequest.body(
					"{\"comment\":\"comment from UW user\",\"code\":\"STANDARD\",\"remarks\":\"\",\"policyNumber\":\""
							+ pno + "\",\"feedback\":\"Esales FTR\"}");
			response = httpRequest.patch("/internal/tasks/" + taskId + "/action");
			IssuanceQueue.waitSec(2);
			int code = response.getStatusCode();
			System.out.println("Status code for UW Actions is : " + code);
			System.out.println("Response UW Actions : " + response.getBody().asString());
			st.assertEquals(code, 200);
			String desc = response.jsonPath().getString("desc");
			st.assertTrue(desc.contains("successfully"),
					"Desciption should contains expected : successfully  Actual Code : " + desc);
			System.out.println("");
			st.assertAll();
		} else {
			throw new SkipException("QueueName is different found is : " + queueName);
		}

	}

	public static void approverQueue(String pno) throws Exception {
		TestBase.init();
		String queueName = IssuanceQueue.verifyQueue(pno);
		IssuanceQueue.waitSec(2);
		if (queueName.contains("Approver")) {
			st = new SoftAssert();
			String taskId = IssuanceQueue.taskId(pno);
			IssuanceQueue.waitSec(2);
			System.out.println("Approver - > Sub -  Standard Decision");
			TestBase.init();
//			String token = IssuanceQueue.token();
			TestBase.restAssured("issuance");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			httpRequest.body(
					"{\"comment\":\"comment from Approver user\",\"code\":\"SUBSTANDARD\",\"remarks\":\"\",\"policyNumber\":\""
							+ pno + "\",\"feedback\":\"Esales FTR\"}");
			response = httpRequest.patch("/internal/tasks/" + taskId + "/action");
			IssuanceQueue.waitSec(10);
			int code = response.getStatusCode();
			System.out.println("Status code for UW Actions is : " + code);
			System.out.println("Response UW Actions : " + response.getBody().asString());
			st = new SoftAssert();
			st.assertEquals(code, 200);
			String desc = response.jsonPath().getString("desc");
			st.assertTrue(desc.contains("successfully"),
					"Desciption should contains expected : successfully  Actual Code : " + desc);
			System.out.println("");
			IssuanceQueue.waitSec(4);
			// IssuanceQueue.verifyQueueName(pno, 5, "Issued");
			st.assertAll();
		} else {
			throw new SkipException("QueueName is different found is : " + queueName);
		}

	}

	public static void uwQueueSubStandard(String pno) throws Exception {
		IssuanceQueue.waitSec(2);
		TestBase.init();
		String queueName = IssuanceQueue.verifyQueue(pno);
		IssuanceQueue.waitSec(2);
		if (queueName.contains("Underwriter")) {
			st = new SoftAssert();
			String taskId = IssuanceQueue.taskId(pno);
			IssuanceQueue.waitSec(2);
			TestBase.init();
			String token = IssuanceQueue.token();
			TestBase.restAssured("issuance");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			httpRequest.body(
					"{\"comment\":\"test comment from UW\",\"code\":\"APPROVER\",\"remarks\":\"\",\"policyNumber\":\""
							+ pno + "\",\"assignedToRole\":\"APPROVER\"}");
			response = httpRequest.patch("/internal/tasks/" + taskId + "/action");
			Thread.sleep(2000);
			int code = response.getStatusCode();
			System.out.println("Status code for UW Actions is : " + code);
			System.out.println("Response UW Actions : " + response.getBody().asString());
			st.assertEquals(code, 200);
			String desc = response.jsonPath().getString("desc");
			st.assertTrue(desc.contains("successfully"),
					"Desciption should contains expected : successfully  Actual Code : " + desc);
			Thread.sleep(10000);
			IssuanceQueue.verifyQueueName(pno, 3, "Approver");
			System.out.println("");
			// st.assertAll();
		} else {
			throw new SkipException("QueueName is different found is : " + queueName);
		}

	}

	public static void uwCO(String pno) throws Exception {
		TestBase.init();
		IssuanceQueue.waitSec(2);
		String queueName = IssuanceQueue.verifyQueue(pno);
		IssuanceQueue.waitSec(2);
		if (queueName.contains("Underwriter")) {
			st = new SoftAssert();
			String taskId = IssuanceQueue.taskId(pno);
			IssuanceQueue.waitSec(2);
			System.out.println("UW - > CounterOffer Decision");
			TestBase.init();
			String token = IssuanceQueue.token();
			TestBase.restAssured("issuance");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			httpRequest.body("{\"comment\":\"Test Counter Offer\","
					+ "\"taskId\":\""+taskId+"\","
					+ "\"agree\":true,\"policyNumber\":\""+pno+"\","
					+ "\"coverageEntries\":[{\"sumAssured\":150000000,\"term\":\"40\",\"coverageCode\":\"ARMP0000111\","
					+ "\"premium\":41713,\"type\":\"BASE\",\"emrReason\":\"build\",\"emrType\":\"amt_form\",\"emrValue\":0.25,"
					+ "\"perMileReason\":\"build\",\"perMileType\":\"perthsnd\",\"perMileValue\":\"2\"},"
					+ "{\"sumAssured\":250000,\"term\":\"40\",\"coverageCode\":\"138B006V05\",\"premium\":12,\"type\":\"RIDER\"}],\"reason\":\"Abnormal findings in blood report\",\"remarks\":\"Abnormal findings in blood report\",\"premium\":41725,\"smoker\":\"NON_SMOKER\","
					+ "\"action\":\"ISSUECOUNTEROFFER\",\"policyTerm\":\"40\",\"premiumPayingTerm\":\"40\","
					+ "\"quotationResponse\":\"{\\\"policyInfo\\\":{\\\"product\\\":{\\\"schemeCode\\\":\\\"ARMP0000111\\\"},"
					+ "\\\"policyTerm\\\":40,\\\"premiumPayingTerm\\\":40,\\\"coverages\\\":[{\\\"code\\\":\\\"138B006V05\\\","
					+ "\\\"sumAssured\\\":250000,\\\"premium\\\":{\\\"value\\\":10,\\\"sgst\\\":1,\\\"cgst\\\":1,\\\"igst\\\":0,\\\"ugst\\\":0,\\\"totalgst\\\":2}},{\\\"code\\\":\\\"ARMP0000111\\\",\\\"sumAssured\\\":150000000,\\\"premium\\\":{\\\"value\\\":35350,\\\"sgst\\\":706,\\\"cgst\\\":706,\\\"igst\\\":0,\\\"ugst\\\":0,\\\"totalgst\\\":1412},\\\"loading\\\":[{\\\"type\\\":\\\"PER_MILE\\\",\\\"amount\\\":26100,\\\"sgst\\\":2349,\\\"cgst\\\":2349,\\\"igst\\\":0,\\\"ugst\\\":0,\\\"totalgst\\\":4698,\\\"totalAmount\\\":null},{\\\"type\\\":\\\"EMR\\\",\\\"amount\\\":1407,\\\"sgst\\\":126.5,\\\"cgst\\\":126.5,\\\"igst\\\":0,\\\"ugst\\\":0,\\\"totalgst\\\":253,\\\"totalAmount\\\":null}]}],\\\"premium\\\":{\\\"frequency\\\":\\\"MONTHLY\\\",\\\"installmentPremium\\\":41725,\\\"sgst\\\":3182.5,\\\"cgst\\\":3182.5,\\\"igst\\\":0,\\\"ugst\\\":0,\\\"totalgst\\\":6365}},\\\"result\\\":\\\"SUCCESS\\\"}\"}");
			response = httpRequest.post("/internal/counterOffer");
			IssuanceQueue.waitSec(2);
			int code = response.getStatusCode();
			System.out.println("Status code for UW Actions is : " + code);
			System.out.println("Response UW Actions : " + response.getBody().asString());
			st.assertEquals(code, 200);
			String desc = response.jsonPath().getString("desc");
			st.assertTrue(desc.contains("successfully"),
					"Desciption should contains expected : successfully  Actual Code : " + desc);
			System.out.println("");
			// st.assertAll();
		} else {
			throw new SkipException("QueueName is different found is : " + queueName);
		}

	}

	public static void verifyQueueName(String pno, int i, String queuename) throws Exception {
		waitSec(4);
		st = new SoftAssert();
		TestBase.init();
		String token = IssuanceQueue.getToken();
		System.out.println(token);
		TestBase.restAssured("issuance");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
		response = httpRequest.get("/internal/tasks/policy/" + pno + "");
		waitSec(4);
		String responeCode = response.getBody().asString();
		System.out.println("responeCode of task : " + responeCode);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONArray js = (JSONArray) obj;
		JSONObject taskDesc_Info = (JSONObject) js.get(i);
		String taskDesc = (String) taskDesc_Info.get("taskDesc");
		st.assertTrue(taskDesc.contains(queuename),
				"Expected Queue : " + queuename + " but Queue found  :  " + taskDesc + "   ");
		System.out.println("\nPolicy moved to queue : " + taskDesc + "\n");
		// st.assertAll();
	}

	public static void manualmerge(String pno, String queueName) throws Exception {
		waitSec(8);
		if (queueName.contains("Merge")) {
			RequestSpecification httpRequest;
			Response response;
			String uniqueId;
			// String policyNumber = "ALI0RQA14962473";
			String partyId;
			TestBase.init();
			RestAssured.baseURI = prop.getProperty("policy");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			response = httpRequest.get("/group/policies/" + pno);
			int statusCode = response.getStatusCode();
			System.out.println("Merge -> Policy Number : The status code is: " + statusCode);
			String responeCode = response.getBody().asString();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(responeCode);
			JSONObject js = (JSONObject) obj;
			JSONObject insured = (JSONObject) js.get("insured");
			partyId = (String) insured.get("id");
			// System.out.println(partyId);
			RestAssured.baseURI = prop.getProperty("parties");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			response = httpRequest.get("/members/" + partyId);
			uniqueId = response.jsonPath().getString("uniqueId");
			// System.out.println(uniqueId);
			RestAssured.baseURI = prop.getProperty("parties");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			httpRequest.body("{\"dedupStatus\":\"ACCEPT\",\"policyNumber\":\"" + pno + "\",\"uniqueId\":\"" + uniqueId
					+ "\"}\r\n");
			response = httpRequest.patch("/members/" + partyId + "/");
			int code;
			code = response.getStatusCode();
			System.out.println("Response ESales Actions : " + response.getBody().asString());
			// System.out.println(code);
			waitSec(3);
			RestAssured.baseURI = prop.getProperty("issuance");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
			httpRequest.body("{\"actionCode\":\"MERGELA\",\"lifeAssuaredUniqueId\":\"" + uniqueId
					+ "\",\"proposerUniqueId\":\"" + uniqueId + "\"}");
			response = httpRequest.patch("/internal/policies/" + pno + "/manual-merge");
			int code1;
			code1 = response.getStatusCode();
			// System.out.println(code1);
			System.out.println("Merge : " + response.getBody().asString() + " | Code : " + code1);
		} else {
			throw new SkipException("Policies is not in merge queue");
		}

	}

	public static void docStore(String pno) throws Exception {
		waitSec(4);
		st = new SoftAssert();
		TestBase.init();
		// String token = QueueName.getToken(pno);
		String token = IssuanceQueue.token();
		// System.out.println(token);
		TestBase.restAssured("issuance");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
		response = httpRequest.get("internal/requirements/" + pno + "?docStatus=NAVLBL");
		int code = response.getStatusCode();
		// System.out.println("Status code for Create Proposal is : " + code);
		System.out.println("\nPolicy is assigned to Esales User !!! \n");
		waitSec(2);
		// QueueName.verifyQueueName(pno, 0, "Pending");
		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONArray js = (JSONArray) obj;
		System.out.println("Number of document to upload : " + js.size());
		for (int i = 0; i < js.size(); i++) {
			waitSec(2);
			JSONObject documentName = (JSONObject) js.get(i);
			String fileName = (String) documentName.get("fileName");
			String taskId = (String) documentName.get("taskId");
			String requirementType = (String) documentName.get("requirementType");
//			System.out.println(fileName + "  " + requirementType);
//			System.out.println("" + js.get(i));
			// https://services.dt-aegonlife.com/docstore/documents/
			waitSec(2);
			String f = System.getProperty("user.dir") + "\\Test.pdf";
			HttpResponse<String> response_post = Unirest
					.patch(prop.getProperty("docstore") + "/" + requirementType + "/policy/" + pno + "/task/" + taskId
							+ "?operation=add")
					.header("Authorization", "Bearer " + token).header("x-api-key", prop.getProperty("x_api_key"))
					.field("file", new File(f)).asString();
			waitSec(2);
			RestAssured.baseURI = prop.getProperty("docstore_meta");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("X-DS-DOCPOLICY", "DEFAULT");
			httpRequest.body("{\"policyNumber\":\"" + pno + "\",\"filename\":\"" + fileName + "\",\"fileType\":\""
					+ requirementType + "\"}\r\n");
			response = httpRequest.put("/documents/" + fileName);
			int code1;
			code1 = response.getStatusCode();
			// System.out.println(code1);
			System.out.println("Docstore : " + response.getBody().asString() + " | Code : " + code1);
			waitSec(2);
		}
	}

	public static String verifyQueue(String pno) throws Exception {
		waitSec(8);
		st = new SoftAssert();
		TestBase.init();
		// String token = QueueName.getToken(pno);
		String token = IssuanceQueue.token();
		// System.out.println(token);
		TestBase.restAssured("issuance");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
		response = httpRequest.get("/internal/tasks/policy/" + pno + "");
		waitSec(2);
		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONArray js = (JSONArray) obj;
		int len = js.size() - 1;
		JSONObject taskId_response = (JSONObject) js.get(len);
		String taskDesc = (String) taskId_response.get("taskDesc");
		return taskDesc;
	}

	public static String taskId(String pno) throws Exception {
		TestBase.init();
		String token = IssuanceQueue.token();
		// System.out.println(token);
		TestBase.restAssured("issuance");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key"));
		response = httpRequest.get("/internal/tasks/policy/" + pno + "");
		waitSec(2);
		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONArray js = (JSONArray) obj;
		int len = js.size() - 1;
		JSONObject taskId_response = (JSONObject) js.get(len);
		String taskId = (String) taskId_response.get("taskId");	
	//	System.out.println("taskId : "+taskId);
		return taskId;
	}

	public static void verifyStatus(String pno) throws Exception {
		waitSec(4);
		TestBase.init();
		st = new SoftAssert();
		String QuotationStatus ="";
		int i =0;
		while(i<=10) {
		String access_token = TestBase.getToken();
		RestAssured.baseURI = prop.getProperty("policy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + access_token);
		httpRequest.header("x-api-key", prop.getProperty("api_key"));
		response = httpRequest.get("/group/policies/" + pno);
		IssuanceQueue.waitSec(3);
		if(response.getStatusCode()!=200) {
			i++;
			staticWait(5);
		}
		else if (response.getStatusCode()==200) {
			System.out.println("loop count is	: "+i);
			QuotationStatus= "Pass";	
			break;
		}
		
	}
		if (QuotationStatus.equals("Pass")) {
		int statusCode = response.getStatusCode();
		Logger.log("GET -> Policy Number : The status code is: " + statusCode);
		st.assertEquals(statusCode, 200);
		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONObject policyStatus = (JSONObject) js.get("policyStatus");
		String PolicyStatus = (String) policyStatus.get("status");
		Logger.log("Policy status : " + PolicyStatus);
		st.assertEquals(PolicyStatus, "INFORCE");
		st.assertAll();
	} }
	
	
	public static String getStatus(String pno) throws Exception {
		waitSec(2);
		TestBase.init();
		String access_token = TestBase.getToken();
		RestAssured.baseURI = prop.getProperty("policy");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + access_token);
		httpRequest.header("x-api-key", prop.getProperty("api_key"));
		response = httpRequest.get("/group/policies/" + pno);
		IssuanceQueue.waitSec(2);
		int statusCode = response.getStatusCode();
		System.out.println("Policy Number : The status code is: " + statusCode);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONObject policyStatus = (JSONObject) js.get("policyStatus");
		String PolicyStatus = (String) policyStatus.get("status");
		System.out.println("Policy status : " + PolicyStatus);
		return PolicyStatus;
	}

	
	public static void verifyStatusAPI(String pno) throws Exception {
		waitSec(2);
		TestBase.init();
		st = new SoftAssert();
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
		st.assertEquals(statusCode, 200);

		String responeCode = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responeCode);
		JSONObject js = (JSONObject) obj;

		JSONObject policyStatus = (JSONObject) js.get("policyStatus");
		String PolicyStatus = (String) policyStatus.get("status");
		System.out.println("Policy status : " + PolicyStatus);
		st.assertEquals(PolicyStatus, "INFORCE");
		st.assertAll();
	}


	public static void databaseCode(String pno) throws Exception {
		try {
			TestBase.init();
			String dbURL = prop.getProperty("database");
			String username = prop.getProperty("db_username");
			String password = prop.getProperty("db_password");
			Connection con = DriverManager.getConnection(dbURL, username, password);
			Statement st = con.createStatement();
			String selectquery = "update policy_task_requirements set status='AVLBL' where policy_number ='" + pno
					+ "'";
			PreparedStatement preparedStmt = con.prepareStatement(selectquery);
			preparedStmt.executeUpdate();
			System.out.println("Done");
			con.close();
		} catch (Exception e) {
			System.out.println("No datacase connection :  " + e.getMessage());
		}
	}

	static String token;

	public static String getToken() throws Exception {
		p = new PageObj();
		p.openBrowser_universe();
		p.initDriver();
		waitSec(1);
		p.login("8509", "password");
		waitSec(1);
		token = p.getItemFromLocalStorage();
		p.logout();
		return token;
	}

	static String token_ops;

	public static String getToken_ops() throws Exception {
		try {
			p = new PageObj();
			p.openBrowser_ops_console();
			p.initDriver();
			waitSec(3);
			p.login("13853", "password");
			waitSec(3);
			token_ops = p.getItemFromLocalStorage();
			p.logout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Token:"+token_ops);
		return token_ops;
	}

	public static String token() {
		String token_no;
		return token_no = token;
	}

	public static String opstoken() {
		String token_no;
		return token_no = token_ops;
	}

	public static void waitSec(int sec) throws Exception {
		long timeSec = sec * 1000;
		Thread.sleep(timeSec);
	}
}
