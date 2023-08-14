package practice.freelook;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import practice.testbase.IssuanceQueue;
import practice.testbase.PageObj;
import practice.testbase.TestBase;
import practice.utility.CustomException;
import practice.utility.Reinstatement;

import io.restassured.RestAssured;

public class FreeLook extends TestBase {

	public static String freelookComplete(String token) throws Exception {

		TestBase.init();
		PageObj.mockPolicyFreelook();
		String pno = PageObj.pnoMock();
		IssuanceQueue.waitSec(4);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String todaydate = simpleDateFormat.format(new Date());
		                                                    
		RestAssured.baseURI = prop.getProperty("cancelpolicy");//"https://service-api.qa-aegonlife.com/cancelpolicy";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		                                 
		httpRequest.body("{\"policyNumber\":\"" + pno
				+ "\",\"comment\":\"Cancel \",\"medicalFee\":\"10\",\"reason\":\"PURCHASED_BY_MISTAKE\","
				+ "\"requestedOn\":\"" + todaydate + "\","
				+ "\"cancellationType\":\"FREELOOK\",\"refundType\":\"STANDARD\"}");
		response = httpRequest.post("/internal/policies/cancellation");
		IssuanceQueue.waitSec(2);
		System.out.println("Response : "+response.getStatusCode()+" "+response.getBody().asString());
		if(response.getStatusCode()!=200)
		{
			throw new CustomException.FreelookFailedException("Freelook failed:"+pno+"-"+response.getStatusCode()+"-"+response.getBody().asString());
		}else if(response.getStatusCode()==200) {
			IssuanceQueue.waitSec(280);
		}
		//IssuanceQueue.waitSec(280);
		return pno;
	}

	public static String NullVoid(String token) throws Exception {

		TestBase.init();
		PageObj.mockPolicyFreelook();
		String pno = PageObj.pnoMock();
		IssuanceQueue.waitSec(4);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String todaydate = simpleDateFormat.format(new Date());

		RestAssured.baseURI = "https://service-api.qa-aegonlife.com/cancelpolicy";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", "cP9ie2et5a1p9F7DN5K147VWXhT5IMmBgEXLSmT7");
		httpRequest.body("{\r\n    \"policyNumber\": \"" + pno + "\"," + "\r\n    \"comment\": \"Test\","
				+ "\r\n    \"medicalFee\": 0,\r\n    \"cancellationType\": \"NULL_AND_VOID\","
				+ "\r\n    \"reason\": \"OTHER\",\r\n    \"refundType\": \"NO_REFUND\"," + "\r\n    \"requestedOn\": \""
				+ todaydate + "\",\r\n    \"coverageCode\": [],"
				+ "\r\n    \"endorsementDocumentReqList\": [\r\n        {\r\n            \"docId\": \"bab799c1-6e29-44e1-9bc0-8ec9d999ba93\","
				+ "\r\n            \"policyNumber\": \"" + pno + "\","
				+ "\r\n            \"docType\": \"Legal_Letter\","
				+ "\r\n            \"isUploaded\": true\r\n        },"
				+ "\r\n        {\r\n            \"docId\": \"f94265f5-d0fa-470b-87e0-2170e097eb78\","
				+ "\r\n            \"policyNumber\": \"" + pno
				+ "\",\r\n            \"docType\": \"Approval_Email\",\r\n            \"isUploaded\": true\r\n        }\r\n    ]\r\n}");
		response = httpRequest.post("/internal/null-and-void");
		Thread.sleep(2000);
		System.out.println("Response : "+response.getStatusCode()+" "+response.getBody().asString());

		IssuanceQueue.waitSec(280);
		return pno;

	}
	
	
	public static String surrender(String token) throws Exception {

		TestBase.init();
		PageObj.mockPolicySurrender();
		String pno = PageObj.pnoMock();
		IssuanceQueue.waitSec(4);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String todaydate = simpleDateFormat.format(new Date());

		RestAssured.baseURI = "https://service-api.qa-aegonlife.com/cancelpolicy";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", "cP9ie2et5a1p9F7DN5K147VWXhT5IMmBgEXLSmT7");
		httpRequest.body("{\"policyNumber\":\"" + pno + "\","
				+ "\"comment\":\"Surrender this policy\",\"medicalFee\":0," + "\"reason\":\"OTHER\",\"requestedOn\":\""
				+ todaydate + "\"," + "\"refundType\":\"STANDARD\",\"cancellationType\":\"SURRENDER\","
				+ "\"endorsementDocumentReqList\":[{\"docId\":\"204e95bc-8b47-4a46-83a3-08daed45b661\","
				+ "\"policyNumber\":\"" + pno + "\",\"docType\":\"Request_Letter\","
				+ "\"isUploaded\":true}],\"coverageNo\":[]}");
		response = httpRequest.post("/internal/surrender");
		Thread.sleep(2000);
		System.out.println("Response : "+response.getStatusCode()+" "+response.getBody().asString());

		IssuanceQueue.waitSec(280);
		return pno;

	}
	
	public static String  LapsedPolicy() throws Exception {
		TestBase.init();
		PageObj.mockBackdatedPolicy();
		String pno = PageObj.pnoMock();
		Reinstatement.ebaotoken();
		Reinstatement.ebaoLapse(pno);
		IssuanceQueue.waitSec(10);
		return pno;
	}

}
