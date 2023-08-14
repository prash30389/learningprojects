package practice.testbase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import practice.testbase.TestBase;
import practice.utility.CustomException;
import practice.utility.Payment_UtilityV1;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProposalRequest_Backdate extends TestBase {

	static String projectPath, pno;
	static String jsonfile;
	
	public static void offline_payment(String pno, double paymentAmt, String vendorCode) throws Exception {
		String vendorPaymentID = Payment_UtilityV1.getVengorPaymentID();
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");

		httpRequest.body("{\n    \"amount\": \"" + paymentAmt + "\","
				+ "\n    \"dueDate\": \"2019-11-30\",\n    \"payer\": {\n        \"businessId\": \"ALIFGG469206489\","
				+ "\n        \"name\": \"Jones Smith\",\n        \"mobileNumber\": \"8888888888\","
				+ "\n        \"email\": \"abc@an.d\"\n    },\n    \"paymentType\": \"New Business\","
				+ "\n    \"policyNumber\": \"" + pno + "\",\n    \"schemeCode\": \"ARMP0000111\","
				+ "\n    \"vendorCode\": \"" + vendorCode + "\",\n    \"vendorPaymentId\": \"" + vendorPaymentID + "\","
				+ "\n    \"receiptInstrument\": {\n        \"paymentMode\": \"cc\"\n    },"
				+ "\n    \"additionalInformation\": {\n        \"productType\": \"Term\","
				+ "\n        \"ebppOpted\": false,\n        \"nachOpted\": false,"
				+ "\n        \"siOpted\": true,\n        \"ecsOpted\": false\n    },"
				+ "\n    \"transactionDate\": \"2020-04-05\"\n}");

		Response response = httpRequest.post("/payments/register");
		Thread.sleep(4000);
		int statusCode = response.getStatusCode();
		if (statusCode == 200) {
			System.out.println("Payment Done : "+pno);
		} else {
			System.out.println("Payment not done ");
			System.out.println(response.getBody().asString());

		}
		
	}
	public static String BackdatedCode() throws Exception {
		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/ProposalRequest.json";
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(jsonfile));

		JSONObject js = (JSONObject) obj;
		JSONObject js1 = (JSONObject) obj;

		int aNumber1 = 0, aNumber2 = 0;
		aNumber1 = (int) ((Math.random() * 9000000) + 1000000);
		aNumber2 = (int) ((Math.random() * 9000000) + 1000000);

		Random random = new Random();
		int minDay = (int) LocalDate.of(1990, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(2001, 1, 1).toEpochDay();
		long randomDay1 = minDay + random.nextInt(maxDay - minDay);

		LocalDate randomBirthDate1 = LocalDate.ofEpochDay(randomDay1);

		JSONObject policyInfo = (JSONObject) js.get("policyInfo");

		policyInfo.replace("policyNumber", "ALI5QAV1" + Integer.toString(aNumber1));

		// insuranceHistories

		/*
		 * JSONArray contacts = (JSONArray) party.get("contacts");
		 * 
		 * JSONObject contacts_Email = (JSONObject) contacts.get(1);
		 * contacts_Email.replace("contactInfo", "email");
		 */

		Faker faker = new Faker();

		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();

		int PAN = 0;
		PAN = (int) ((Math.random() * 9000) + 1000);

		long randomDay2 = minDay + random.nextInt(maxDay - minDay);
		LocalDate randomBirthDate2 = LocalDate.ofEpochDay(randomDay2);

		String firstName1 = faker.name().firstName();
		String lastName1 = faker.name().lastName();

		JSONObject insured = (JSONObject) js.get("insured");
		insured.replace("firstName", firstName);
		insured.replace("lastName", lastName);
		insured.replace("partyrefid", firstName + "@10");
		insured.replace("mobile", "777" + Integer.toString(aNumber2)); // randomBirthDate1 //dateOfBirth
		insured.replace("dateOfBirth", randomBirthDate1.toString());
		insured.replace("emailId", firstName + lastName + "@test.com");

		JSONArray identityInfo = (JSONArray) insured.get("identityInfo");

		JSONObject value = (JSONObject) identityInfo.get(0);
		value.replace("value", "AAAPP" + PAN + "C");

		JSONObject policyHolder = (JSONObject) js1.get("policyHolder");
		policyHolder.replace("firstName", firstName1);
		policyHolder.replace("lastName", lastName1);
		policyHolder.replace("partyrefid", firstName + "@10");
		policyHolder.replace("mobile", "888" + Integer.toString(aNumber2));
		policyHolder.replace("dateOfBirth", randomBirthDate2.toString());

		JSONArray identityInfo_policyHolder = (JSONArray) policyHolder.get("identityInfo");

		JSONObject value_policyHolder = (JSONObject) identityInfo_policyHolder.get(0);
		value_policyHolder.replace("value", "AABPP" + PAN + "P");

		policyInfo.replace("policyNumber", "ALI0QAP8" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -48);
		Date dateBefore1 = DateUtils.addDays(new Date(), -48);

		String purchaseDateTime = simpleDateFormat.format(dateBefore);
		String startDateTime = simpleDateFormat.format(dateBefore1);
		String dispatchDate = simpleDateFormat.format(dateBefore);

		policy.replace("purchaseDateTime", purchaseDateTime);
		policy.replace("startDate", startDateTime);
		policy.replace("coiDispatchDateTime", dispatchDate);

		JSONArray coverages = (JSONArray) policy.get("coverages");
		JSONObject coveragesValue0 = (JSONObject) coverages.get(0);

		coveragesValue0.replace("inceptionDate", startDateTime);

		return js.toJSONString();

	}
	
	public static String pno() {
		return pno;
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
		Thread.sleep(3000);
		System.out.println("Quotation Code:"+response1.getStatusCode());
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
			
			Double pay=installmentPremium+200;
			
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
				System.out.println("Payment Done : "+pno);
			} else {
				System.out.println("Payment not done ");
				System.out.println(response.getBody().asString());
	
			}
	
		}

}
	
//=======================================================================
	
	public static String BackDatedPolicy() throws FileNotFoundException, IOException, ParseException
	{

		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/ProposalRequest2.json";

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(jsonfile));

		JSONObject js = (JSONObject) obj;
		JSONObject js1 = (JSONObject) obj;

		int aNumber1 = 0, aNumber2 = 0;
		aNumber1 = (int) ((Math.random() * 9000000) + 1000000);
		aNumber2 = (int) ((Math.random() * 9000000) + 1000000);

		Random random = new Random();
		int minDay = (int) LocalDate.of(1990, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(2001, 1, 1).toEpochDay();
		long randomDay1 = minDay + random.nextInt(maxDay - minDay);

		LocalDate randomBirthDate1 = LocalDate.ofEpochDay(randomDay1);

		JSONObject policyInfo = (JSONObject) js.get("policyInfo");

		policyInfo.replace("policyNumber", "ALI5QAV1" + Integer.toString(aNumber1));

		// insuranceHistories

		/*
		 * JSONArray contacts = (JSONArray) party.get("contacts");
		 * 
		 * JSONObject contacts_Email = (JSONObject) contacts.get(1);
		 * contacts_Email.replace("contactInfo", "email");
		 */

		Faker faker = new Faker();

		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();

		int PAN = 0;
		PAN = (int) ((Math.random() * 9000) + 1000);

		long randomDay2 = minDay + random.nextInt(maxDay - minDay);
		LocalDate randomBirthDate2 = LocalDate.ofEpochDay(randomDay2);

		String firstName1 = faker.name().firstName();
		String lastName1 = faker.name().lastName();

		JSONObject insured = (JSONObject) js.get("insured");
		insured.replace("firstName", firstName);
		insured.replace("lastName", lastName);
		insured.replace("partyrefid", firstName + "@10");
		insured.replace("mobile", "777" + Integer.toString(aNumber2)); // randomBirthDate1 //dateOfBirth
		insured.replace("dateOfBirth", randomBirthDate1.toString());
		insured.replace("emailId", firstName + lastName + "@test.com");

		JSONArray identityInfo = (JSONArray) insured.get("identityInfo");

		JSONObject value = (JSONObject) identityInfo.get(0);
		value.replace("value", "AAAPP" + PAN + "C");

		JSONObject policyHolder = (JSONObject) js1.get("policyHolder");
		policyHolder.replace("firstName", firstName1);
		policyHolder.replace("lastName", lastName1);
		policyHolder.replace("partyrefid", firstName + "@10");
		policyHolder.replace("mobile", "888" + Integer.toString(aNumber2));
		policyHolder.replace("dateOfBirth", randomBirthDate2.toString());

		JSONArray identityInfo_policyHolder = (JSONArray) policyHolder.get("identityInfo");

		JSONObject value_policyHolder = (JSONObject) identityInfo_policyHolder.get(0);
		value_policyHolder.replace("value", "AABPP" + PAN + "P");

		policyInfo.replace("policyNumber", "ALI0QAP8" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -48);
		Date dateBefore1 = DateUtils.addDays(new Date(), -48);

		String purchaseDateTime = simpleDateFormat.format(dateBefore);
		String startDateTime = simpleDateFormat.format(dateBefore1);
		String dispatchDate = simpleDateFormat.format(dateBefore);

		policy.replace("purchaseDateTime", purchaseDateTime);
		policy.replace("startDate", startDateTime);
		policy.replace("coiDispatchDateTime", dispatchDate);

		JSONArray coverages = (JSONArray) policy.get("coverages");
		JSONObject coveragesValue0 = (JSONObject) coverages.get(0);

		coveragesValue0.replace("inceptionDate", startDateTime);

		return js.toJSONString();

	
	}
	

}
