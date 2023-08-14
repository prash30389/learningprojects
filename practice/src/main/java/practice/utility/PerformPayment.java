package practice.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PerformPayment extends TestBase {

	public static void registerPayment(String pno, int amount) throws Exception {

		TestBase.init();
		Logger.log("Policy Number:"+pno);
		RequestSpecification httpRequest;

		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");

		httpRequest.body("{\n    \"amount\": \"" + amount + "\","
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
			System.out.println("\n" + pno + " : Payment Done" + "\n");
		} else {
			System.out.println("Payment not done ");
			System.out.println(response.getBody().asString());

		}

	}

	public static void registerPayment_CO(String pno) throws Exception {
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);		
		Date dateBefore = DateUtils.addDays(new Date(), 0);	

		
		String billDay = simpleDateFormat.format(dateBefore);


		RestAssured.baseURI = prop.getProperty("quotation");
		RequestSpecification httpRequest, httpRequest1;

		httpRequest1 = RestAssured.given();
		httpRequest1.header("Content-Type", "application/json");

		Response response1 = httpRequest1.get("/quotation?policyNumber=" + pno);
		Thread.sleep(3000);
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
		
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");

		httpRequest.body("{\n    \"amount\": \"" + installmentPremium + "\","
				+ "\n    \"dueDate\": \""+billDay+"\",\n    \"payer\": {\n        \"businessId\": \"ALIFGG469206489\","
				+ "\n        \"name\": \"Jones Smith\",\n        \"mobileNumber\": \"8888888888\","
				+ "\n        \"email\": \"abc@an.d\"\n    },\n    \"paymentType\": \"Counter Offer\","
				+ "\n    \"policyNumber\": \"" + pno + "\",\n    \"schemeCode\": \"ARMP0000111\","
				+ "\n    \"vendorCode\": \"POLICYBAZAAR\",\n    \"vendorPaymentId\": \"Test12345\","
				+ "\n    \"receiptInstrument\": {\n        \"paymentMode\": \"cc\"\n    },"
				+ "\n    \"additionalInformation\": {\n        \"productType\": \"Term\","
				+ "\n        \"ebppOpted\": false,\n        \"nachOpted\": false,"
				+ "\n        \"siOpted\": true,\n        \"ecsOpted\": false\n    },"
				+ "\n    \"transactionDate\": \"2020-04-15\"\n}");

		Response response = httpRequest.post("/payments/register");
		Thread.sleep(4000);
		int statusCode = response.getStatusCode();
		if (statusCode == 200) {
			System.out.println("\n" + pno + " : Payment Done" + "\n");
		} else {
			System.out.println("Payment not done ");
			System.out.println(response.getBody().asString());

		}
	}
  }

	

	public static void actual_payment(String pno) throws Exception {

		int amount;
		int i=1;
		Response response1=null;
		String quotationStatus="";
		while(i<=10) {
		RestAssured.baseURI = prop.getProperty("quotation");
		RequestSpecification httpRequest, httpRequest1;

		httpRequest1 = RestAssured.given();
		httpRequest1.header("Content-Type", "application/json");
		response1 = httpRequest1.get("/quotation?policyNumber=" + pno);
		System.out.println("Quotation ResponseCode:"+response1.getStatusCode());
		Thread.sleep(3000);
		if(response1.getStatusCode()!=200)
		{
			i++;
			IssuanceQueue.waitSec(5);
		//	throw new CustomException.QuotationNotFoundException("Quotation not found:"+pno);
		}
		if(response1.getStatusCode()==200) {
			quotationStatus= "Pass";
			break;
		}
	}
	if(quotationStatus.equals("Pass")) {
		String responeCode_payment = response1.getBody().asString();
		JSONParser payment = new JSONParser();
		Object obj_payment = payment.parse(responeCode_payment);

		JSONObject js_payment = (JSONObject) obj_payment;

		JSONObject policyInfo = (JSONObject) js_payment.get("policyInfo");
		JSONObject premium = (JSONObject) policyInfo.get("premium");
		Double installmentPremium = (Double) premium.get("installmentPremium");		
		System.out.println("Amount to pay : "+installmentPremium);	

		Thread.sleep(3200);
		
		Double pay=installmentPremium+100;
		
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");

		httpRequest.body("{\n    \"amount\": \"" + pay + "\","
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
			Logger.log("Payment Done : "+pno);
		} else {
			Logger.log("Payment not done ");
			Logger.log(response.getBody().asString());
		}

	}
	
	}
	

}
