package practice.utility;

import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import practice.utility.FreelookTest;
import practice.utility.JSONUtils;

import practice.testbase.TestBase;

import practice.pageobjects.*;
import practice.reports.Logger;
import practice.testbase.*;
import practice.utility.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class InitiatePayment extends TestBase {
	static JSONObject reqjsonObj = null;
	static String pattern = "yyyy-MM-dd";
	public static double amountNB, amountRenewal,amountPayout, amountReinstatement;
	public static long amountAR;
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	static Date dateBefore = DateUtils.addDays(new Date(), -2);
	static Date dateAfter = DateUtils.addDays(new Date(), 10);
	static String backdate = simpleDateFormat.format(dateBefore);
	static String todaydate = simpleDateFormat.format(new Date());
	static String futuredate = simpleDateFormat.format(dateAfter);
	
	public static Double getNBQuotation(String pno) throws Exception {
		Logger.log("\n***** To get installment Premium from getNBQuotation API method Started *****\n");
		Response response=null;
		double installmentPremium=0.0;
		String quotationStatus="";
		int i=0;
		while(i<=10) {
			RestAssured.baseURI = prop.getProperty("quotation");
			RequestSpecification httpRequest = RestAssured.given();
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			response = httpRequest.get("/quotation?policyNumber=" + pno);
			staticWait(3);
			if(response.getStatusCode()!=200) {
				i++;
				staticWait(5);
			}
			if(response.getStatusCode()==200) {
				quotationStatus= "Pass";
				break;
			}
		}
		if(quotationStatus.equals("Pass")) {
			String responseBody = response.getBody().asString();
			JSONParser jsonparser = new JSONParser();
			Object obj_payment = jsonparser.parse(responseBody);
			JSONObject js_payment = (JSONObject) obj_payment;
			JSONObject policyInfo = (JSONObject) js_payment.get("policyInfo");
			JSONObject premium = (JSONObject) policyInfo.get("premium");
			installmentPremium = (Double) premium.get("installmentPremium");		
			Logger.log("Amount to pay : "+installmentPremium);	
			System.out.println("Amount:"+installmentPremium);
			staticWait(3);
		}
		Logger.log("\n installment Premium Returned *****\n");
		return installmentPremium;
	}
	
	static String actualDate,duedate,paymentType, suspenseTypeNB,suspenseTypeRN, suspenseTypeRE;
	static double amountPayable;
	static double totalRenewalAmount, totalReinstatementAmount;
	public static Response getRenewalQuotation(String pno) throws Exception {
		TestBase.init();	
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		response = httpRequest.get("/v1/receivables?policyNumber="+pno);
		Thread.sleep(2000);		
		String responseBody = response.getBody().asString();
		JSONParser jsonparser = new JSONParser();
		Object obj = jsonparser.parse(responseBody);
		JSONObject js = (JSONObject) obj;
		JSONArray duesArr = (JSONArray) js.get("dues");
		JSONObject dues = null;
		for (int i =0; i< duesArr.size(); i++) {
			dues = (JSONObject) duesArr.get(i);
		}
		duedate = (String) dues.get("dueDate");
		actualDate = duedate;
		totalRenewalAmount = (double) dues.get("amountPayable");
		paymentType = (String) dues.get("type");
		Logger.log(duedate +" || "+ totalRenewalAmount +" || "+ paymentType);
		return response;
	}
	
	public static Response getReinstatementQuotation(String pno) throws Exception {
		TestBase.init();	
		RestAssured.baseURI = prop.getProperty("renewal");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{\"policyNumber\":\""+pno+"\","
				+ "\"paymentFreq\":\"MONTHLY\","
				+ "\"requestedOn\":\""+backdate+"\"}");
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
		Logger.log("actualdate"+ actualDate);
		totalReinstatementAmount = (double) arrObj.get("totalReinstatementAmount");
		return response;
	}
	
	public static Response createDue(String policyNo, String type) throws Exception {
		TestBase.init();	
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body("{ \"amount\": 624.0, \"dueDate\": \"" + futuredate + "\", \"policyNumber\": \"" + policyNo + "\", \"productType\": \"Term\", \"type\": \"" + type + "\"}");
		response = httpRequest.post("/receivables");
		Thread.sleep(2000);	
		int responseCode = response.getStatusCode();
		Logger.log(response.getBody().asString());
		if(responseCode == 200) {
			String billID = response.jsonPath().getString("billId");
			Logger.log(billID);
		}
		return response;
	}
	
	public static Response cancelDue(String policyNo) throws Exception {
		
		TestBase.init();	
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("accept", "*/*");
		response = httpRequest.post("/receivables/cancel/"+policyNo);
		Thread.sleep(2000);	
		int responseCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		if(responseCode == 200) {
			Logger.log(responseBody);
			String billID = response.jsonPath().getString("billId");
			Logger.log(billID);
		} else if (responseCode == 400) {
			Logger.log(responseBody);
			String Code = response.jsonPath().getString("code");
			String Desc = response.jsonPath().getString("desc");
			String errMsg = response.jsonPath().getString("errorMessage");
			st.assertEquals(Code, "SPG415");
			st.assertEquals(Desc, null);
			st.assertEquals(errMsg, "Bill not found for policy number "+policyNo);
		}
		return response;
	}
	
	public static Response getCOQuotation(String pno) throws Exception {
		TestBase.init();	
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		response = httpRequest.get("/v1/receivables?policyNumber="+pno);
		Thread.sleep(2000);		
		String responseBody = response.getBody().asString();
		JSONParser jsonparser = new JSONParser();
		Object obj = jsonparser.parse(responseBody);
		JSONObject js = (JSONObject) obj;
		JSONArray duesArr = (JSONArray) js.get("dues");
		JSONObject dues = null;
		for (int i =0; i< duesArr.size(); i++) {
			dues = (JSONObject) duesArr.get(i);
		}
		duedate = (String) dues.get("dueDate");
		actualDate = duedate;
		amountPayable = (double) dues.get("amount");
		paymentType = (String) dues.get("type");
		Logger.log(duedate + " || " + amountPayable + " || " + paymentType);
		return response;
	}
	
	public static Response getDues(String pno) throws Exception {
		TestBase.init();	
		RestAssured.baseURI = prop.getProperty("payment");
		httpRequest = RestAssured.given();
		httpRequest.headers("Accept", "*/*");
		response = httpRequest.get("/receivables/"+pno);
		IssuanceQueue.waitSec(6);		
		
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		int responseCode = response.getStatusCode();		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responseBody);
		JSONObject jsObj = (JSONObject) obj;
		if(responseCode == 200) {
			Logger.log("response body for get dues : "+responseBody);
			JSONArray bills = (JSONArray) jsObj.get("bills");
			JSONObject arrbillsObj = (JSONObject) bills.get(0);
			duedate = (String) arrbillsObj.get("dueDate");
			actualDate = duedate;
			Long amt;
			try {
				// if amout is long type
				amt = (Long) arrbillsObj.get("amount");
				amountPayable = amt.doubleValue();
			} catch (Exception e) {
				//if amount is double type
				amountPayable = (double) arrbillsObj.get("amount");
			}
			paymentType = (String) arrbillsObj.get("type");
			JSONArray suspenses = (JSONArray) jsObj.get("suspenses");
			int size = suspenses.size();
			JSONObject arrsusObj = (JSONObject) suspenses.get(0);
			suspenseTypeNB = (String) arrsusObj.get("suspenseType");
			arrsusObj = (JSONObject) suspenses.get(1);
			suspenseTypeRN = (String) arrsusObj.get("suspenseType");
			arrsusObj = (JSONObject) suspenses.get(2);
			suspenseTypeRE = (String) arrsusObj.get("suspenseType");

		} else {
			Logger.log("response code for get dues : "+responseCode);
			Logger.log("response body for get dues : "+responseBody);
		}
		
		return response;
	}
	public static void CancellationRiderPolicy(String policyNo, String vendorCode, String cancellationType, String FRRefundType) throws Exception {
		String responseBody = null;
		st = new SoftAssert();
		if(cancellationType.equalsIgnoreCase("RIDER_FREELOOK")) {
			responseBody = FreelookTest.getQuoteFR(policyNo).getBody().asString();
		}else if(cancellationType.equalsIgnoreCase("RIDER_SURRENDER")) {
			responseBody = FreelookTest.getQuoteSR(policyNo).getBody().asString();
		}
		staticWait(15);
		Logger.log("Response Body of "+cancellationType+" Quote=>"+ responseBody);
		String strPolicyNumber = response.jsonPath().getString("policyNumber");
		st.assertEquals(strPolicyNumber	, policyNo);
		String strType = response.jsonPath().getString("type");
		String strTotalRefundAmount = response.jsonPath().getString("quotationInfo.totalRefundAmount");
		String strTotalSuspenseAmount = response.jsonPath().getString("quotationInfo.totalSuspenseAmount");
		Logger.log("Actual Can Type=>"+strType+" Expected Can Type=>"+cancellationType);
		if(cancellationType.equalsIgnoreCase("RIDER_FREELOOK")) {
			st.assertEquals(strType, cancellationType);
		}
		else if(cancellationType.equalsIgnoreCase("RIDER_SURRENDER")) {
			st.assertEquals(strType, cancellationType);
		}
		
		String strStatus = response.jsonPath().getString("status");
		st.assertEquals(strStatus, "ACTIVE");
		int responseCode = response.getStatusCode();
		st.assertEquals(responseCode, 200);
		Logger.log("Quote Response Code:" + responseCode);
		Logger.log("Quote Response Body is:" + responseBody);
		//FL
		if(cancellationType.equalsIgnoreCase("RIDER_FREELOOK")) {
			FreelookTest.freelookriderCancellation(pno);
		}else if(cancellationType.equalsIgnoreCase("RIDER_SURRENDER")) {
			FreelookTest.surrenderriderCancellation(pno);
		} 
		responseBody = FreelookTest.response.getBody().asString();
		Logger.log("Response Body of "+cancellationType+" =>"+ responseBody);
		String strCode = response.jsonPath().getString("code");
		String strDesc = response.jsonPath().getString("desc");
		String errMsg = response.jsonPath().getString("errorMessage");
		if(cancellationType.equalsIgnoreCase("RIDER_FREELOOK")) {
			st.assertEquals(strCode, "POL100");
			st.assertTrue(strDesc.contains("Refund Initiated For Policy Number: " + pno), "Actual message:" + strDesc);		
			st.assertNull(response.jsonPath().getString("errorMessage"));
		}else if(cancellationType.equalsIgnoreCase("RIDER_SURRENDER")) {
			st.assertEquals(strCode, "SER100");
			st.assertTrue(strDesc.contains("Surrender Initiated For Policy Number: " + pno), "Actual message:" + strDesc);		
			st.assertNull(response.jsonPath().getString("errorMessage"));
		}
		// response code
		responseCode = response.getStatusCode();
		st.assertEquals(responseCode, 200);
		st.assertAll();
		Logger.log("\nPerformed "+cancellationType+" Response Body is:" + responseBody);
		Logger.log("Performed "+cancellationType+" Response Code is:" + responseCode);
		System.out.println("//Wait for Status Changed into Terminated"); 
		//staticWait(200);
		/*
		 * FreelookTest.getPolicy(pno); responseBody =
		 * FreelookTest.getPolicy(pno).getBody().asString();
		 * Logger.log("Response Body of Desired Policy =>"+ responseBody); String
		 * strPolicyNo = response.jsonPath().getString("policyNumber");
		 * Assert.assertEquals(strPolicyNo, pno); strStatus =
		 * response.jsonPath().getString("coverages.status"); String strReason =
		 * response.jsonPath().getString("coverages.reason"); String statusDesc =
		 * response.jsonPath().getString("coverages.statusDesc"); String reasonDesc =
		 * response.jsonPath().getString("coverages.reasonDesc");
		 * Logger.log("Policy Status after performing "+cancellationType+" : " +
		 * strReason + "| " + strStatus); String statusInforce =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationStatus.statusInforce"); String statusfreelookinitiated
		 * =JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationStatus.statusfreelookinitiated"); String
		 * statusridersurrenderinitiated =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationStatus.statusridersurrenderinitiated"); String statusTerminated
		 * = JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationStatus.statusTerminated"); String freelookInitiated =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationReason.freelookInitiated"); String surrenderinitiated =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationReason.Surrenderinitiated"); String freelookCompleted =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationReason.freelookCompleted"); String surrenderCompleted =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationReason.SURRENDERCOMPLETED"); String statusDescInForce =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationStatusDesc.statusDescInForce"); String statusDescTerminated =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationStatusDesc.statusDescTerminated"); String
		 * freelookReasonInitiated = JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationReasonDesc.freelookInitiated"); String surrenderReasonInitiated
		 * = JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationReasonDesc.Surrendered"); String rescinded =
		 * JSONUtils.getValueFromConstant("constantValueFile",
		 * "cancellationReasonDesc.rescinded"); if
		 * (strStatus.equalsIgnoreCase("INFORCE") &&
		 * strReason.equalsIgnoreCase(freelookInitiated)) { st.assertEquals(strStatus,
		 * statusInforce); st.assertEquals(strReason, freelookInitiated);
		 * st.assertEquals(statusDesc, statusDescInForce); st.assertEquals(reasonDesc,
		 * freelookReasonInitiated); } else if (strStatus.equalsIgnoreCase("TERMINATED")
		 * && strReason.equalsIgnoreCase(freelookCompleted)) {
		 * st.assertEquals(strStatus, statusTerminated); st.assertEquals(strReason,
		 * freelookCompleted); st.assertEquals(statusDesc, statusDescTerminated);
		 * st.assertEquals(reasonDesc, rescinded);
		 * 
		 * } else if (strStatus.equalsIgnoreCase("INFORCE") &&
		 * strReason.equalsIgnoreCase(surrenderinitiated)) { st.assertEquals(strStatus,
		 * statusInforce); st.assertEquals(strReason, surrenderinitiated);
		 * st.assertEquals(statusDesc, statusDescInForce); st.assertEquals(reasonDesc,
		 * freelookReasonInitiated); } else if (strStatus.equalsIgnoreCase("TERMINATED")
		 * && strReason.equalsIgnoreCase(surrenderCompleted)) {
		 * st.assertEquals(strStatus, statusTerminated); st.assertEquals(strReason,
		 * surrenderCompleted); st.assertEquals(statusDesc, statusDescTerminated);
		 * st.assertEquals(reasonDesc, surrenderReasonInitiated); } else { //
		 * LOG.info("something wrong!"); } responseCode = response.getStatusCode();
		 * st.assertEquals(responseCode, 200);
		 */
		st.assertAll();
	}

	public static String getDOB(String policyNo) throws Exception {
		TestBase.init();	
		RestAssured.baseURI = prop.getProperty("policy");
		httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		response = httpRequest.get("/group/policies/"+pno);
		IssuanceQueue.waitSec(2);		
		int responseCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		String policyHolderId = "";
		if(responseCode == 200) {
			System.out.println("response body is : "+responseBody);
			JSONParser jsonparser = new JSONParser();
			Object obj = jsonparser.parse(responseBody);
			JSONObject js = (JSONObject) obj;
			JSONObject policyHolderObj =  (JSONObject) js.get("policyHolder");
			policyHolderId = (String) policyHolderObj.get("id");
		}
		
		RestAssured.baseURI = prop.getProperty("parties");
		httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		response = httpRequest.get("/members/"+policyHolderId);
		IssuanceQueue.waitSec(2);		
		int resCode = response.getStatusCode();
		String resBody = response.getBody().asString();
		String dob = "";
		if(resCode == 200) {
			System.out.println("response body is : "+resBody);
			JSONParser jsonparser = new JSONParser();
			Object obj = jsonparser.parse(resBody);
			JSONObject jsonObj = (JSONObject) obj;
			String dateofBirth = (String) jsonObj.get("dateOfBirth");
			String dt = dateofBirth.replace("T00:00:00.000+0000","");
			String arr[] = dt.split("-");
			String yyyy = arr[0];
			String mm = arr[1];
			String dd = arr[2];
			dob = dd+"-"+mm+"-"+yyyy;
		}
		return dob;
	}
	
	public static String dueDate() {
		return actualDate;
	}
	
	public static double amountPayable() {
		return amountPayable;
	}
	
	public static String paymentType() {
		return paymentType;
	}
	
	public static double totalRenewalAmount() {
		return totalRenewalAmount;
	}
	
	public static double totalReinstatementAmount() {
		return totalReinstatementAmount;
	}
	public static String suspenseTypeNB() {
		return suspenseTypeNB;
	}
	public static String suspenseTypeRN() {
		return suspenseTypeRN;
	}
	public static String suspenseTypeRE() {
		return suspenseTypeRE;
	}
	
	public static List<String> initiatePayment(double paymentAmt, String dueDate, String pno, String mobileNum, String emailID, String paymentType, String vendorCode, String mandateType) throws Exception {
		List<String> arrPayment = new ArrayList<>();
		String strCustomerId = "";
		String strPolicyNumber = "";
		String strTransactionId = "";
		String strOrderId = "";
		TestBase.init();
		if(dueDate.isEmpty()) {
			dueDate = futuredate;
		}
		if(paymentType.isEmpty()) {
			paymentType = "New Business";
		}
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");		
		httpRequest.body(" {\n	\"amount\": \""+ paymentAmt +"\"," 
				+ "\n	\"dueDate\": \""+ dueDate +"\","
				+ "\n	\"payer\": {\r\n	\"businessId\": \""+ pno +"\","
				+ "\n	\"name\": \"Test User\","
				+ "\n	\"mobileNumber\": \""+ mobileNum +"\","
				+ "\n	\"email\": \""+ emailID +"\"\n },"
				+ "\n	\"paymentType\": \""+ paymentType +"\","
				+ "\n	\"policyNumber\": \""+ pno +"\","
				+ "\n	\"schemeCode\": \"ARMP0000111\","
				+ "\n	\"vendorCode\": \""+ vendorCode +"\"," 
				+ "\n	\"mandateType\": \""+ mandateType +"\","
				+ "\n	\"additionalInformation\": {\r\n	\"productType\": \"Term\","
				+ "\n	\"ebppOpted\": false\r\n }"
				+ "\n	}");
		Response response = httpRequest.post("/payments");
		Thread.sleep(10000);
		int statusCode = response.getStatusCode();
		Logger.log("status code   :"+statusCode);
		String responseBody = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responseBody);
		JSONObject jsObj = (JSONObject) obj;
		if (statusCode == 200) {
			Logger.log("Initiate Payment response body	: "+responseBody);
			Logger.log("Status Code						: "+statusCode);
			strCustomerId = response.jsonPath().getString("payer.vendorPayer.payerId");
			strPolicyNumber = response.jsonPath().getString("payer.policyNumber");
			strTransactionId = response.jsonPath().getString("payment.businessId"); 
			strOrderId = response.jsonPath().getString("payment.vendorPayment.vendorOrderId");
			arrPayment.add(0, strCustomerId);
			arrPayment.add(1, strPolicyNumber);
			arrPayment.add(2, strTransactionId);
			arrPayment.add(3, strOrderId);
			Logger.log("Payment initiated.");
		} else if (statusCode == 400) {
			Logger.log("Initiate Payment response body	: "+responseBody);
			JSONArray errorsJson = (JSONArray) jsObj.get("errors");
			if(errorsJson != null) {
				JSONObject arrErrObj = (JSONObject) errorsJson.get(0);
				String errCode = (String) arrErrObj.get("code");
				String errMsg = (String) arrErrObj.get("message");
				if(vendorCode.isEmpty()) {
					st.assertEquals(errCode, "SPG402");
					st.assertEquals(errMsg, "Provided vendor code is not available");
				} else if(paymentAmt == 0.0) {
					st.assertEquals(errCode, "SPG402");
					st.assertEquals(errMsg, "Payment amount should be greater than INR 0.");
				} else if(mobileNum == "" && emailID == "") {
					st.assertEquals(errCode, "SPG402");
					st.assertEquals(errMsg, "Email or MobileNumber can not be empty");
				} else if(paymentAmt > 0.0) {
					st.assertEquals(errCode, null);
					st.assertEquals(errMsg, "E-mandate amount should be INR 0.");
				} else {}
			} else {
				String Code = response.jsonPath().getString("code");
				String Desc = response.jsonPath().getString("desc");
				st.assertEquals(Code, "SPG415");
				st.assertEquals(Desc, "Due not found");
			}
			st.assertAll();
		} else {
			Logger.log("Payment not initiated.");
			Logger.log("Initiate Payment response body	: "+responseBody);
		}
		return arrPayment;
	}
	
	public static List<String> initiatePaymentForCO(double paymentAmt, String dueDate, String pno, String mobileNum, String emailID, String paymentType, String vendorCode) throws Exception {
		List<String> arrPayment = new ArrayList<>();
		String strCustomerId = "";
		String strPolicyNumber = "";
		String strTransactionId = "";
		String strOrderId = "";
		TestBase.init();
		if(dueDate.isEmpty()) {
			dueDate = futuredate;
		}
		if(paymentType.isEmpty()) {
			paymentType = "New Business";
		}
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com";	
		RequestSpecification httpRequest = RestAssured.given();		
		httpRequest.headers("Content-Type", "application/json");
		httpRequest.body(" {\n	\"amount\": \""+ paymentAmt +"\"," 
				+ "\n	\"dueDate\": \""+ dueDate +"\","
				+ "\n	\"payer\": {\r\n	\"businessId\": \""+ pno +"\","
				+ "\n	\"name\": \"Test User\","
				+ "\n	\"mobileNumber\": \""+ mobileNum +"\","
				+ "\n	\"email\": \""+ emailID +"\"\n },"
				+ "\n	\"paymentType\": \""+ paymentType +"\","
				+ "\n	\"policyNumber\": \""+ pno +"\","
				+ "\n	\"schemeCode\": \"ARMP0000111\","
				+ "\n	\"vendorCode\": \""+ vendorCode +"\"," 
				+ "\n	\"additionalInformation\": {\r\n	\"productType\": \"Term\","
				+ "\n	\"ebppOpted\": false\r\n }"
				+ "\n	}");
		Response response = httpRequest.post("/payments");
		Thread.sleep(10000);
		int statusCode = response.getStatusCode();
		Logger.log("status code   :"+statusCode);
		String responseBody = response.getBody().asString();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responseBody);
		JSONObject jsObj = (JSONObject) obj;
		if (statusCode == 200) {
			Logger.log("Status Code						: "+statusCode);
			Logger.log("Initiate Payment response body	: "+responseBody);
			//Get Response Body Value
			strCustomerId = response.jsonPath().getString("payer.vendorPayer.payerId");
			strPolicyNumber = response.jsonPath().getString("payer.policyNumber");
			strTransactionId = response.jsonPath().getString("payment.businessId"); 
			strOrderId = response.jsonPath().getString("payment.vendorPayment.vendorOrderId");
			arrPayment.add(0, strCustomerId);
			arrPayment.add(1, strPolicyNumber);
			arrPayment.add(2, strTransactionId);
			arrPayment.add(3, strOrderId);
			Logger.log("Payment initiated.");
		} else if (statusCode == 400) {
			Logger.log("Status Code						: "+statusCode);
			Logger.log("Initiate Payment response body	: "+responseBody);
		} else {
			Logger.log("Payment not initiated.");
			Logger.log("Initiate Payment response body	: "+responseBody);
		}
		return arrPayment;
	}
	
	public static String getFormatedDate(String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void getMandates(String policyNo) throws Exception {
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		Response response = httpRequest.get("/mandates?contractNumber="+policyNo);
		Thread.sleep(5000);
		int statusCode = response.getStatusCode();
		if (statusCode == 200) {
			Logger.log("Manadate done.");
			Logger.log(response.getBody().asString());	
			String status = response.jsonPath().getString("status");
			Logger.log("Policy Manadte status is : "+status);			
		} else {
			Logger.log("Manadate not done.");
			Logger.log(response.getBody().asString());
		}
	}
	
	public static void makepayment(String custId, String policyNo, String transactionId, String orderId, double paymentAmt, String paymentOption) throws Exception {	
		TestBase.setup();
		checkoutPayment(custId, policyNo, transactionId, orderId, paymentAmt, paymentOption);
		//TestBase.tearDown();
	}	
	public static void checkoutPayment(String custId, String policyNo, String transactionId, String orderId, double paymentAmt, String paymentOption) throws Exception {
		TestBase.enterValueInTextBox(CheckoutPageLocators.CustomerID, custId);
		System.out.println("Customer Id Entered =>"+ custId);
		TestBase.enterValueInTextBox(CheckoutPageLocators.PolicyNumber, policyNo);
		System.out.println("Policy Number Entered =>"+ policyNo);
		TestBase.enterValueInTextBox(CheckoutPageLocators.AgeonTransactionID, transactionId);
		System.out.println("Aegon Transaction Id Entered =>"+ transactionId);
		TestBase.enterValueInTextBox(CheckoutPageLocators.OrderID, orderId);
		System.out.println("Order Id Entered =>"+ orderId);
		TestBase.clickButton(CheckoutPageLocators.PayBtn);
		System.out.println("Clicked On Pay Button");
		Thread.sleep(7000);
		TestBase.switchToiFrame();
		InitiatePayment.addCustomerDetails();
		if(paymentOption.equalsIgnoreCase("Card")) {
			InitiatePayment.addPaymentTypeDetails(paymentOption, paymentAmt);
		} else if(paymentOption.equalsIgnoreCase("Internet Banking")) {
			InitiatePayment.addPaymentTypeDetails(paymentOption, paymentAmt);
		} else if (paymentOption.equalsIgnoreCase("Wallet")) {
			InitiatePayment.addPaymentTypeDetails(paymentOption, paymentAmt);
		} else {}
	}
	
	public static void addCustomerDetails() throws Exception {
		try {
			if(w.findElements(By.xpath(CheckoutPageLocators.userDetailPanel)).size() != 0) {
				TestBase.clickButton(CheckoutPageLocators.userDetailPanel);
				System.out.println("Clicked on User Detail Panel."); 
			}
		} catch (Exception e) {
			System.out.println("User Detail Panel not available.");
		}
		TestBase.enterValueInTextBox(CheckoutPageLocators.contactCode, PaymentConstants.contactCode);
		System.out.println("Enter Contact Code.");
		TestBase.enterValueInTextBox(CheckoutPageLocators.emailId, PaymentConstants.emailId);
		System.out.println("Enter Email Id.");
		TestBase.clickButton(CheckoutPageLocators.proceedBtn);
		System.out.println("Clicked On Proceed Button");
		Thread.sleep(2000);
	}
	
	public static void addPaymentTypeDetails(String paymentType, double paymentAmt) throws Exception {
		String parentwinHandle = "";
		switch(paymentType) {
		case "Card":
			Logger.log("Selected Payment Type is Card.");
			/*
			Logger.log("Selected Payment Type is Card.");
			if(w.findElement(By.xpath(cardDetailPanel)).isDisplayed()) {
				TestBase.clickButton(cardDetailPanel);
				Logger.log("Clicked on Card Detail Panel");
			} else {
				JavascriptExecutor jse = (JavascriptExecutor)w;
				jse.executeScript("document.getElementById('card').click();");
				Logger.log("Clicked on Card Detail Panel using java script");
			}*/
			if(w.findElements(By.xpath(CheckoutPageLocators.cardDetailPanel)).size() != 0) {
				TestBase.clickButton(CheckoutPageLocators.cardDetailPanel);
				System.out.println("Clicked on Card Detail Panel"); 
			}
			if(w.findElements(By.xpath(CheckoutPageLocators.addNewCardBtn)).size() != 0) {
				TestBase.clickButton(CheckoutPageLocators.addNewCardBtn);
				System.out.println("Clicked on Add Another Card + Button"); 
			}
			TestBase.enterValueInTextBox(CheckoutPageLocators.cardNumber, PaymentConstants.cardNumber);
			TestBase.enterValueInTextBox(CheckoutPageLocators.cardExpiry, PaymentConstants.cardExpiry);
			TestBase.enterValueInTextBox(CheckoutPageLocators.cardHolderName, PaymentConstants.cardHolderName);
			TestBase.enterValueInTextBox(CheckoutPageLocators.cardCVV, PaymentConstants.cvv);
			String currency = TestBase.currencyFormat(paymentAmt);
			//Assert.assertEquals(TestBase.getText(CheckoutPageLocators.payAmt), "PAY ? "+currency);
			TestBase.clickButton(CheckoutPageLocators.PaymentBtn);
			System.out.println("Clicked on Payment Button."); 
			Thread.sleep(5000);
			parentwinHandle = TestBase.getparentwindowHandle();
			TestBase.switchtochildWindow();
			TestBase.clickButton(CheckoutPageLocators.successBtn);
			System.out.println("Clicked on Success Button."); 
			//TestBase.switchbacktoparentWindow(parentwinHandle);
			//TestBase.handleWindowPopup();
			break;
		case "Internet Banking":
			Logger.log("Selected Payment Type is Netbanking.");
			TestBase.clickButton(CheckoutPageLocators.selectHDFC);
			TestBase.clickButton(CheckoutPageLocators.selectNetbanking);
			TestBase.enterValueInTextBox(CheckoutPageLocators.textboxBAN, PaymentConstants.bankAccountNumber);
			TestBase.enterValueInTextBox(CheckoutPageLocators.textboxIFSC, PaymentConstants.ifscCode);
			TestBase.enterValueInTextBox(CheckoutPageLocators.textboxAHN, PaymentConstants.accountHolderName);
			TestBase.selectValueFromDropDown(CheckoutPageLocators.dropdownTOBA,PaymentConstants.typeOfBankAcc);
			TestBase.clickButton(CheckoutPageLocators.AuthenticateBtn);
			System.out.println("Clicked on Authenticate Button."); 
			Thread.sleep(5000);
			parentwinHandle = TestBase.getparentwindowHandle();
			TestBase.switchtochildWindow();
			TestBase.clickButton(CheckoutPageLocators.successBtn);
			System.out.println("Clicked on Success Button."); 
			//TestBase.switchbacktoparentWindow(parentwinHandle);
			//TestBase.handleWindowPopup();
			break;
		case "Wallet":
			Logger.log("Selected Payment Type is Wallet.");
			break;
		case "UPI / QR":
			Logger.log("Selected Payment Type is UPI / QR.");
			break;
		case "EMI":
			Logger.log("Selected Payment Type is EMI.");
			break;
		default:
			Logger.log("Selected Payment Type is Invalid Type");
			break;
		}
	}
	
	public static void mandateRegistration(String policyNo, String dob, double paymentAmt, String paymentOption) throws Exception {
		TestBase.init();	
		String URL = prop.getProperty("renewalURL");
		TestBase.openURL(URL);
		renewPolicyOnline(policyNo, dob, paymentAmt, paymentOption);
		//TestBase.tearDown();
	}
	
	public static void renewPolicyOnline(String policyNo, String dob, double paymentAmt, String paymentOption) throws Exception {
		TestBase.enterValueInTextBox(CheckoutPageLocators.textboxPolicyNumber, policyNo);
		TestBase.enterValueInTextBox(CheckoutPageLocators.textboxDOB, dob);
		TestBase.clickButton(CheckoutPageLocators.submitBtn);
		//Logger.log("Clicked On Pay Button");
		staticWait(7);
		if(w.findElements(By.xpath(CheckoutPageLocators.cbAllowPayment)).size() != 0) {
			TestBase.clickButton(CheckoutPageLocators.cbAllowPayment);
			staticWait(1);
		}
		JavascriptExecutor jse = ((JavascriptExecutor) w);
		el = w.findElement(By.xpath(CheckoutPageLocators.paymentOptionNB));
		jse.executeScript("arguments[0].scrollIntoView();", el);
		staticWait(2);
		TestBase.clickButton(CheckoutPageLocators.paymentOptionNB);
		TestBase.clickButton(CheckoutPageLocators.cbTermsCond);
		TestBase.clickButton(CheckoutPageLocators.cbStandingInstructions);
		TestBase.clickButton(CheckoutPageLocators.proceedtopaymentBtn);
		staticWait(10);
		TestBase.switchToiFrame();
		InitiatePayment.addCustomerDetails();
		if(paymentOption.equalsIgnoreCase("Card")) {
			InitiatePayment.addPaymentTypeDetails(paymentOption, paymentAmt);
		} else if(paymentOption.equalsIgnoreCase("Internet Banking")) {
			InitiatePayment.addPaymentTypeDetails(paymentOption, paymentAmt);
		} else {}
	}
	
	public static void getPaymentInfo(String policyNumber, String paymentType, double paymentAmt, String vendorCode, String status, String paymentMode) throws Exception {
		staticWait(10);
		st = new SoftAssert();
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		httpRequest.body("{ \"billBusinessId\": null, \"billType\": null, \"businessId\": null, \"pageNo\": 1, \"paymentDateTime\": null, \"policyNumber\": \""+policyNumber+"\", \"rowCount\": 10, \"status\": null, \"vendorOrderId\": null, \"vendorTransactionId\": null}");
		response = httpRequest.post("/payments/search");
		staticWait(25);
		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();		
		JSONParser jsonparser = new JSONParser();
		JSONArray paymentArr = (JSONArray) jsonparser.parse(responseBody);
		JSONObject jsonObj = null;
		if (statusCode == 200) {
			Logger.log("Final Payment done.");
			if(paymentType.equalsIgnoreCase("New Business")) {
				jsonObj = (JSONObject) paymentArr.get(0);
			} else if(paymentType.equalsIgnoreCase("Renewal Payment")) {
				jsonObj = (JSONObject) paymentArr.get(1);
			} else if(paymentType.equalsIgnoreCase("Reinstatement")) {
				jsonObj = (JSONObject) paymentArr.get(1);
			} else if(paymentType.equalsIgnoreCase("Counter Offer")) {
				jsonObj = (JSONObject) paymentArr.get(0);
			} else if(paymentType.equalsIgnoreCase("Mandate Registration")) {
				jsonObj = (JSONObject) paymentArr.get(2);
			}
			Logger.log("response of payment info	: "+jsonObj);
			String policyNum = (String) jsonObj.get("policyNumber");
			String PaymentType = (String) jsonObj.get("paymentType");
			String dueDate = (String) jsonObj.get("dueDate");
			Long amt;
			Double amount;
			try {
				// if amout is long type
				amt = (Long) jsonObj.get("amount");
				amount = amt.doubleValue();
			} catch (Exception e) {
				//if amount is double type
				amount = (Double) jsonObj.get("amount");			
			}
			//Double amount =  (Double) jsonObj.get("amount");
			String VendorCode =  (String) jsonObj.get("vendorCode");
			String paymentStatus =  (String) jsonObj.get("status");
			String PaymentMode = (String) jsonObj.get("paymentModeDescription");
			String pM = (String) jsonObj.get("paymentMode");
			if(PaymentMode != null) {
				if(PaymentMode.equalsIgnoreCase("Internet Banking")) {
					String BankName = (String) jsonObj.get("bankName");
					Boolean MandateRegistered = (Boolean) jsonObj.get("isMandateRegistered");
					Logger.log(BankName + " || " + MandateRegistered);
				} else if (PaymentMode.equalsIgnoreCase("Card")){
					Boolean MandateRegistered = (Boolean) jsonObj.get("isMandateRegistered");
					Logger.log("Manadte Registration is : "+MandateRegistered);
				}
				Logger.log("Payment Mode, Due Date and Amount values are	: " + PaymentMode + " || " + dueDate + " || "+ amount);
			} else {
				Logger.log("Payment Status and Payment Mode values are	: "+paymentStatus+ " || "+PaymentMode+ " || "+pM); 
			}
			st.assertEquals(policyNum, policyNumber);
			st.assertEquals(PaymentType, paymentType);
			st.assertEquals(amount, paymentAmt);
			st.assertEquals(VendorCode, vendorCode);
			st.assertEquals(paymentStatus, status);
			st.assertEquals(PaymentMode, paymentMode);
			st.assertAll();	
		} else { 
			Logger.log("Suspense not done ");
			Logger.log(response.getBody().asString());
		}
	}
	
	public static void getAllSuspenses(String policyNo) throws Exception{
		st = new SoftAssert();
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		Response response = httpRequest.get("/suspenses?policyNumber="+policyNo);
		Thread.sleep(5000);
		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		JSONParser jsonparser = new JSONParser();
		JSONArray suspensesArr = (JSONArray) jsonparser.parse(responseBody);
		JSONObject jsonObj = null;
		if (statusCode == 200) {
			Logger.log("Suspense done.");
			Logger.log(response.getBody().asString());
			jsonObj = (JSONObject) suspensesArr.get(0);
			String policyNum, suspenseType;
			try {
				policyNum = (String) jsonObj.get("policyNumber");
				amountNB = (Double) jsonObj.get("amount");
				suspenseType = (String) jsonObj.get("suspenseType");
				st.assertEquals(policyNum, policyNo);		
				st.assertEquals(suspenseType, "NB");
			} catch (Exception e) {}
			jsonObj = (JSONObject) suspensesArr.get(1);
			try {
				policyNum = (String) jsonObj.get("policyNumber");
				amountRenewal = (Double) jsonObj.get("amount");
				suspenseType = (String) jsonObj.get("suspenseType");
				st.assertEquals(policyNum, policyNo);		
				st.assertEquals(suspenseType, "RENEWAL");
			} catch (Exception e) {}
			jsonObj = (JSONObject) suspensesArr.get(2);
			try {
				policyNum = (String) jsonObj.get("policyNumber");
				amountPayout = (Double) jsonObj.get("amount");
				suspenseType = (String) jsonObj.get("suspenseType");
				st.assertEquals(policyNum, policyNo);		
				st.assertEquals(suspenseType, "PAYOUT");
			} catch (Exception e) {}
			jsonObj = (JSONObject) suspensesArr.get(3);
			try {
				policyNum = (String) jsonObj.get("policyNumber");
				amountReinstatement = (Double) jsonObj.get("amount");
				suspenseType = (String) jsonObj.get("suspenseType");
				st.assertEquals(policyNum, policyNo);		
				st.assertEquals(suspenseType, "REINSTATEMENT");
			} catch (Exception e) {}
			jsonObj = (JSONObject) suspensesArr.get(4);
			try {
				policyNum = (String) jsonObj.get("policyNumber");
				amountAR = (Long) jsonObj.get("amount");
				suspenseType = (String) jsonObj.get("suspenseType");
				st.assertEquals(policyNum, policyNo);		
				st.assertEquals(suspenseType, "AR");
			} catch (Exception e) {}
			Logger.log("Suspense Status : "+amountNB+" || "+amountRenewal+" || "+amountPayout+" || "+amountReinstatement+" || "+amountAR);
		} else {
			Logger.log("Suspense not done ");
			Logger.log(response.getBody().asString());
		}
		st.assertAll();	
	}
	
	public static void getSuspenseEntryForPayment(String policyNo, double amt, String suspenseType) throws Exception {
		st = new SoftAssert();
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		Response response = httpRequest.get("/suspenses?policyNumber="+policyNo+"&suspenseType="+suspenseType);
		Thread.sleep(5000);
		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		JSONParser jsonparser = new JSONParser();
		JSONArray suspenseArr = (JSONArray) jsonparser.parse(responseBody);
		JSONObject jsonObj = null;
		if (statusCode == 200) {
			Logger.log("Suspense done.");
			Logger.log(response.getBody().asString());
			jsonObj = (JSONObject) suspenseArr.get(0);
			String policyNum = (String) jsonObj.get("policyNumber");
			st.assertEquals(policyNum, policyNo);
			double amount = (double) jsonObj.get("amount");			
			st.assertEquals(amount, amt);
			String susType =  (String) jsonObj.get("suspenseType");
			st.assertEquals(susType, suspenseType);
		} else {
			Logger.log("Suspense not done ");
			Logger.log(response.getBody().asString());
		}
		st.assertAll();
	}
	
	public static void makeInitiatepayout(Boolean partialPayout, String policyNo, String reason, String suspenseType, String type, String vendorCode, double amount) throws Exception {
		Boolean PartialPayout;
		String strPolicyNumber = "";
		String strReason = "";
		String strSuspenceType = "";
		String strType = "";
		double Amount = 0.00;
		String strVendorCode = "";
		String strStatus = "";
		st = new SoftAssert();
		TestBase.init();
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
		String responseBody = response.getBody().asString();
		if (statusCode == 200) {
			Logger.log(responseBody);
			Logger.log("\n" + pno + " : Payout Initiated." + "\n");
			String strPayoutId = response.jsonPath().getString("id");
			strPolicyNumber = response.jsonPath().get("policyNumber");
			st.assertEquals(strPolicyNumber, policyNo);
			strReason = response.jsonPath().get("reason");
			st.assertEquals(strReason, reason);
			strStatus = response.jsonPath().getString("status");
			st.assertEquals(strStatus, "INITIATED");
			strVendorCode = response.jsonPath().getString("vendorRefund.vendorCode");
			st.assertEquals(strVendorCode, vendorCode);
			strType = response.jsonPath().getString("payoutType");
			st.assertEquals(strType, type);
			PartialPayout = response.jsonPath().getBoolean("partialPayout");
			st.assertEquals(PartialPayout, partialPayout);
			Amount = response.jsonPath().getDouble("amount");
			st.assertEquals(Amount, amount);
			strSuspenceType = response.jsonPath().getString("suspenseType");
			st.assertEquals(strSuspenceType, suspenseType);
		} else if (statusCode == 400) {
			Logger.log("Payout not initiated.");
			Logger.log(responseBody);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(responseBody);
			if(partialPayout == true && suspenseType == null && type != "") {
				JSONArray arrErrObj = (JSONArray) obj;
				JSONObject jsObj =  (JSONObject) arrErrObj.get(0);
				String errCode = (String) jsObj.get("code");
				String errMsg = (String) jsObj.get("errorMessage");
				String errDesc = (String) jsObj.get("desc");
				st.assertEquals(errCode, "SYS001");
				st.assertEquals(errDesc, "System Error");
			} else if (partialPayout == true && suspenseType != null && type != "SU") {
				JSONObject jsObj = (JSONObject) obj;
				String errCode = (String) jsObj.get("code");
				String errMsg = (String) jsObj.get("errorMessage");
				st.assertEquals(errCode, "SPG701");
				Logger.log(errMsg);
				//JSONArray errorsJson = (JSONArray) jsObj.get("errors");
				//JSONObject arrErrObj = (JSONObject) errorsJson.get(0);
				//arrErrObj = (JSONObject) errorsJson.get(1);
				//errCode = (String) arrErrObj.get("code");
				//errMsg = (String) arrErrObj.get("message");
				//Logger.log(errMsg);
				//st.assertEquals(errCode, "SPG1001");
			} else if(partialPayout == false && suspenseType == null && type == "SU") {
				JSONArray arrErrObj = (JSONArray) obj;
				JSONObject jsObj =  (JSONObject) arrErrObj.get(0);
				Logger.log(jsObj.toJSONString());
				String errCode = (String) jsObj.get("code");
				String errMsg = (String) jsObj.get("errorMessage");
				String errDesc = (String) jsObj.get("desc");
				st.assertEquals(errCode, "SYS001");
				st.assertEquals(errDesc, "System Error");
			} else {}
		} else {
			Logger.log("Payout not initiated.");
			Logger.log(response.getBody().asString());
		}
		st.assertAll();
	}
	
	public static void getPayoutStatus(String policyNo, String reason, String susType, String payoutType, String vendorCode) throws Exception {
		st = new SoftAssert();
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com/";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		httpRequest.body("{ \n	\"policyNumber\": \""+ policyNo +"\" \n }");
		Response response = httpRequest.post("/payouts");
		Thread.sleep(5000);
		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		System.out.println("freelook policy response body: "+ responseBody);
		JSONParser jsonparser = new JSONParser();
		System.out.println(jsonparser.parse(responseBody));
		JSONArray payoutArr = (JSONArray) jsonparser.parse(responseBody);
		JSONObject jsonObj = null;
		if (statusCode == 200) {
			Logger.log("Payout status is ok.");
			Logger.log(responseBody);
			int size = payoutArr.size();
			Logger.log("array size is::"+ size);
			for (int i = 0; i< size; i++) {
				jsonObj = (JSONObject) payoutArr.get(i);
				String policyNum = (String) jsonObj.get("policyNumber");
				String Canreason = (String) jsonObj.get("reason");
				String status = (String) jsonObj.get("status");
				String PayouType = (String) jsonObj.get("payoutType");
				String SuspenseType = (String) jsonObj.get("suspenseType");
				double amount = (double) jsonObj.get("amount");
				double amountPaid = (double) jsonObj.get("amountPaid");
				st.assertEquals(policyNum, policyNo);
				st.assertEquals(Canreason, reason);
				st.assertEquals(status, "REFUNDED");
				if(i== 0 && size == 1) {
					if(PayouType.equalsIgnoreCase("RJ")) {
						st.assertEquals(PayouType, "RJ");
						Logger.log("Payout type::: "+ PayouType +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
					} else {
						st.assertEquals(PayouType, "SU");
						Logger.log("Payout type::: "+ PayouType +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
					}
				}
				if(i== 0 && size == 2) {
					st.assertEquals(PayouType, payoutType);
					Logger.log("Payout type::: "+ PayouType +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
				} 
				if(i == 1 && size == 2) {
					st.assertEquals(PayouType, "SU");
					Logger.log("Actual Payout type::: "+ PayouType +" amount::: "+ amount +" amountPaid::: "+amountPaid);
				}
				if(SuspenseType != null){
					st.assertEquals(SuspenseType, susType);
				} else {
					st.assertNull(SuspenseType);
				}
				JSONObject vendorrefobj = (JSONObject) jsonObj.get("vendorRefund");
				String VendorCode = (String) vendorrefobj.get("vendorCode");
				st.assertEquals(VendorCode, vendorCode);
			}
		} else {
			Logger.log("Payment Status is not OK.");
			Logger.log(response.getBody().asString());
		}
		st.assertAll();
	}
		
	public static void CacellationPolicy(String policyNo, String vendorCode, String cancellationType, String refundType) throws Exception {
		String responseBody = null;
		st = new SoftAssert();
		if(cancellationType.equalsIgnoreCase("FREELOOK")) {
			responseBody = FreelookTest.getQuote(policyNo).getBody().asString();
		} else if (cancellationType.equalsIgnoreCase("NULL_AND_VOID")) {
			responseBody = FreelookTest.getQuoteNullVoid(policyNo, refundType).getBody().asString();
		} else if (cancellationType.equalsIgnoreCase("CANCEL_FULL_REFUND")) {
			responseBody = FreelookTest.getQuoteCancelFullRefund(policyNo, refundType).getBody().asString();
		} else if (cancellationType.equalsIgnoreCase("SURRENDER")) {
			responseBody = FreelookTest.getQuoteSurrender(policyNo).getBody().asString();
		}
		staticWait(5);
		Logger.log("Response Body of "+cancellationType+" Quote=>"+ responseBody);
		String strPolicyNumber = response.jsonPath().getString("policyNumber");
		st.assertEquals(strPolicyNumber	, policyNo);
		String strType = response.jsonPath().getString("type");
		String strTotalRefundAmount = response.jsonPath().getString("quotationInfo.totalRefundAmount");
		String strTotalSuspenseAmount = response.jsonPath().getString("quotationInfo.totalSuspenseAmount");
		Logger.log("Actual Can Type=>"+strType+" Expected Can Type=>"+cancellationType);
		if(cancellationType.equalsIgnoreCase("FREELOOK")) {
			st.assertEquals(strType, cancellationType);
		} else if(cancellationType.equalsIgnoreCase("NULL_AND_VOID")) {
			if(strType.equalsIgnoreCase(cancellationType+"_NO_REFUND")) {
				st.assertEquals(strType, cancellationType+"_NO_REFUND");
			} else if(strType.equalsIgnoreCase(cancellationType+"_FULL_REFUND")) {
				st.assertEquals(strType, cancellationType+"_FULL_REFUND");	
			}
		} else if(cancellationType.equalsIgnoreCase("CANCEL_FULL_REFUND")) {
			st.assertEquals(strType, cancellationType);
		} else if(cancellationType.equalsIgnoreCase("SURRENDER")) {
			st.assertEquals(strType, cancellationType);
			//Logger.log("Total Refund Amount=>"+strTotalRefundAmount);
			//Logger.log("Total Suspense Amount=>"+strTotalSuspenseAmount);
		}
		String strStatus = response.jsonPath().getString("status");
		st.assertEquals(strStatus, "ACTIVE");
		int responseCode = response.getStatusCode();
		st.assertEquals(responseCode, 200);
		System.out.println("Quote Response Code:" + responseCode);
		System.out.println("Quote Response Body is:" + responseBody);
		//FL
		if(cancellationType.equalsIgnoreCase("FREELOOK")) {
			FreelookTest.freelookCancellation(pno);
		} else if (cancellationType.equalsIgnoreCase("NULL_AND_VOID")) {
			FreelookTest.null_void_Cancellation(pno, refundType);
		} else if (cancellationType.equalsIgnoreCase("CANCEL_FULL_REFUND")) {
			FreelookTest.cancel_full_refund_Cancellation(pno, refundType);
		} else if (cancellationType.equalsIgnoreCase("SURRENDER")) {
			FreelookTest.surrenderCancellation(pno);
		}
		responseBody = FreelookTest.response.getBody().asString();
		Logger.log("Response Body of "+cancellationType+" =>"+ responseBody);
		String strCode = response.jsonPath().getString("code");
		String strDesc = response.jsonPath().getString("desc");
		String errMsg = response.jsonPath().getString("errorMessage");
		String serviceReqId = response.jsonPath().getString("addInfos.serviceRequestId");
		if(cancellationType.equalsIgnoreCase("FREELOOK")) {
			st.assertEquals(strCode, "POL100");
			st.assertTrue(strDesc.contains("Refund Initiated For Policy Number: " + pno), "Actual message:" + strDesc);		
			st.assertNull(response.jsonPath().getString("errorMessage"));
			st.assertTrue(serviceReqId.contains("SR0000"));
		} else if (cancellationType.equalsIgnoreCase("NULL_AND_VOID")) {
			st.assertEquals(strCode, "NAV200");
			st.assertTrue(strDesc.contains("Null And Void Successful for policy number: " + pno), "Actual message:" + strDesc);		
			st.assertNull(response.jsonPath().getString("errorMessage"));
		} else if (cancellationType.equalsIgnoreCase("CANCEL_FULL_REFUND")) {
			st.assertEquals(strCode, "CFR100");
			st.assertTrue(strDesc.contains("Cancel Full Refund Successful for policy number: " + pno), "Actual message:" + strDesc);		
			st.assertNull(response.jsonPath().getString("errorMessage"));
		} else if (cancellationType.equalsIgnoreCase("SURRENDER")) {
			st.assertEquals(strCode, "SER100");
			st.assertTrue(strDesc.contains("Surrender Initiated For Policy Number: " + pno), "Actual message:" + strDesc);		
			st.assertNull(response.jsonPath().getString("errorMessage"));
		} 
		// response code
		responseCode = response.getStatusCode();
		st.assertEquals(responseCode, 200);
		st.assertAll();
		Logger.log("\nPerformed "+cancellationType+" Response Body is:" + responseBody);
		Logger.log("Performed "+cancellationType+" Response Code is:" + responseCode);
		System.out.println("//Wait for Status Changed into Terminated.");
		if(cancellationType.equalsIgnoreCase("NULL_AND_VOID")|| cancellationType.equalsIgnoreCase("CANCEL_FULL_REFUND")) {
			staticWait(40);
		} else {
			staticWait(270);
		}
		FreelookTest.getPolicy(pno);
		responseBody = FreelookTest.getPolicy(pno).getBody().asString();
		System.out.println("Response Body of Desired Policy =>"+ responseBody);
		String strPolicyNo = response.jsonPath().getString("policyNumber");
		Assert.assertEquals(strPolicyNo, pno);
		strStatus = response.jsonPath().getString("policyStatus.status");
		String strReason = response.jsonPath().getString("policyStatus.reason");
		String statusDesc = response.jsonPath().getString("policyStatus.statusDescription");
		String reasonDesc = response.jsonPath().getString("policyStatus.reasonDescription");
		Logger.log("Policy Status after performing "+cancellationType+" : " + strReason + "| " + strStatus);
		String statusInforce = JSONUtils.getValueFromConstant("constantValueFile", "cancellationStatus.statusInforce");
		String statusNullAndVoidInitiated =JSONUtils.getValueFromConstant("constantValueFile", "cancellationStatus.statusNullAndVoidInitiated");
		String statusTerminated = JSONUtils.getValueFromConstant("constantValueFile", "cancellationStatus.statusTerminated");
		String freelookInitiated = JSONUtils.getValueFromConstant("constantValueFile", "cancellationReason.freelookInitiated");
		String freelookCompleted = JSONUtils.getValueFromConstant("constantValueFile", "cancellationReason.freelookCompleted");
		String statusDescInForce = JSONUtils.getValueFromConstant("constantValueFile", "cancellationStatusDesc.statusDescInForce");
		String statusDescTerminated = JSONUtils.getValueFromConstant("constantValueFile", "cancellationStatusDesc.statusDescTerminated");
		String freelookReasonInitiated = JSONUtils.getValueFromConstant("constantValueFile", "cancellationReasonDesc.freelookInitiated");
		String freelookNullandVoidInitiated = JSONUtils.getValueFromConstant("constantValueFile", "cancellationReasonDesc.freelookNullandVoidInitiated");
		String rescinded = JSONUtils.getValueFromConstant("constantValueFile", "cancellationReasonDesc.rescinded");
		if (strStatus.equalsIgnoreCase("INFORCE") && strReason.equalsIgnoreCase(freelookInitiated)) {
			st.assertEquals(strStatus, statusInforce);
			st.assertEquals(strReason, freelookInitiated);
			st.assertEquals(statusDesc, statusDescInForce);
			st.assertEquals(reasonDesc, freelookReasonInitiated);
		} else if (strStatus.equalsIgnoreCase("INFORCE") && strReason.equalsIgnoreCase(statusNullAndVoidInitiated)) {
			st.assertEquals(strStatus, statusInforce);
			st.assertEquals(strReason, statusNullAndVoidInitiated);
			st.assertEquals(statusDesc, statusDescInForce);
			st.assertEquals(reasonDesc, freelookNullandVoidInitiated);
		} else if (strStatus.equalsIgnoreCase("TERMINATED") && strReason.equalsIgnoreCase(freelookCompleted)) {
			st.assertEquals(strStatus, statusTerminated);
			st.assertEquals(strReason, freelookCompleted);
			st.assertEquals(statusDesc, statusDescTerminated);
			st.assertEquals(reasonDesc, rescinded);
		} else if (strStatus.equalsIgnoreCase("TERMINATED") && strReason.equalsIgnoreCase("NULL_AND_VOID_NO_REFUND")) {
			st.assertEquals(strStatus, "TERMINATED");
			st.assertEquals(strReason, "NULL_AND_VOID_NO_REFUND");
			st.assertEquals(statusDesc, statusDescTerminated);
			st.assertEquals(reasonDesc, "Null & Void - No Refund");
		} else if (strStatus.equalsIgnoreCase("TERMINATED") && strReason.equalsIgnoreCase("NULL_AND_VOID_FULL_REFUND")) {
			st.assertEquals(strStatus, "TERMINATED");
			st.assertEquals(strReason, "NULL_AND_VOID_FULL_REFUND");
			st.assertEquals(statusDesc, statusDescTerminated);
			st.assertEquals(reasonDesc, "Null & Void - Full Refund");
		} else if (strStatus.equalsIgnoreCase("TERMINATED") && strReason.equalsIgnoreCase("CANCEL_FULL_REFUND")) {
			st.assertEquals(strStatus, "TERMINATED");
			st.assertEquals(strReason, "CANCEL_FULL_REFUND");
			st.assertEquals(statusDesc, statusDescTerminated);
			st.assertEquals(reasonDesc, "Cancellation-Full Refund");
		} else if (strStatus.equalsIgnoreCase("TERMINATED") && strReason.equalsIgnoreCase("SURRENDER_COMPLETED")) {
			st.assertEquals(strStatus, "TERMINATED");
			st.assertEquals(strReason, "SURRENDER_COMPLETED");
			st.assertEquals(statusDesc, statusDescTerminated);
			st.assertEquals(reasonDesc, "Surrendered");
		} else {
			// LOG.info("something wrong!");
		}
		responseCode = response.getStatusCode();
		st.assertEquals(responseCode, 200);
		st.assertAll();
	}
}