package practice.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.asserts.SoftAssert;

import practice.reports.Logger;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
/**
 * 
 * @author ANKIT
 *
 */
public class ClaimPayoutExecution extends TestBase {
	
	public static RequestSpecification httpRequest;
	public static Response response;
	public static String jsonfile;
	public static JSONParser parser = new JSONParser();
	public static String payoutMode="";

	static SoftAssert st;
	static String pattern = "yyyy-MM-dd";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	static String todaydate = simpleDateFormat.format(new Date());
	static String claimNumber = "";
	static Long lumpSumBenefit, incomeBenefit;
	static Double grossClaimBenefitPayable = 0.00;
	static Double incomeBenefitAmt = 0.00;
	
	public static Response suspensePayoutForNEFTTrue(String suspensetypeRn, double amount, String token, String pno) {
		Logger.log("In suspense payout API>>>>>>");
		init();
		
		try {
			RestAssured.baseURI = prop.getProperty("suspenseAPI");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", "cP9ie2et5a1p9F7DN5K147VWXhT5IMmBgEXLSmT7");
			httpRequest.body("{\r\n" + 
					"   \"amount\": "+amount+",   				\r\n" + 
					"   \"approvalRequired\": false, 		\r\n" + 
					"   \"partialPayout\": true,    		\r\n" + 
					"   \"payoutMode\": \"neft\",      		\r\n" + 
					"   \"policyNumber\": \""+pno+"\",		\r\n" + 
					"   \"reason\": \"Payout\",				\r\n" + 
					"   \"refundDate\": \"2020-06-20\",    		\r\n" + 
					"   \"requestedOn\": \"2020-06-20T09:55:37.478Z\",\r\n" + 
					"   \"type\": \"SU\", \r\n" + 
					"   \"suspenseType\":\"RENEWAL\",            		\r\n" + 
					"   \"proposerName\":\"Akshay More321\",\r\n" + 
					"   \"payee\":{\r\n" + 
					"	\r\n" + 
					"		\"name\" : \"Harshit Agarwal321\",		\r\n" + 
					"		\"accountNumber\":\"1234567007\", \r\n" + 
					"		\"ifscCode\":\"ICIC0000915\" 	\r\n" + 
					"	}\r\n" + 
					"}");
			//https://service-api.qa-aegonlife.com/payment/internal/payouts/initiate							
			response = httpRequest.post("/internal/payouts/initiate");
			Logger.log("Suspense Payout Initiate API:"+response.getStatusCode());
			
			JSONObject jsonObject = (JSONObject) parser.parse(response.getBody().asString());
			payoutMode = (String) jsonObject.get("payoutMode");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
		
	}
	
//=======================================================================================================================	
	
	public static Response suspensePayoutForNEFTFalse(String suspensetypenb, double amount, String token, String pno) {
		// TODO Auto-generated method stub
		Logger.log("In suspense payout API>>>>>>");
		init();
		
		try {
			RestAssured.baseURI = prop.getProperty("suspenseAPI");
			httpRequest = RestAssured.given();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", "Bearer " + token);
			httpRequest.header("x-api-key", "cP9ie2et5a1p9F7DN5K147VWXhT5IMmBgEXLSmT7");
			httpRequest.body("{\r\n" + 
					"   \"amount\": 0,   				\r\n" + 
					"   \"approvalRequired\": false, 		\r\n" + 
					"   \"partialPayout\": false,    		\r\n" + 
					"   \"payoutMode\": \"neft\",      		\r\n" + 
					"   \"policyNumber\": \""+pno+"\",		\r\n" + 
					"   \"reason\": \"Payout\",				\r\n" + 
					"   \"refundDate\": \"2020-06-20\",    		\r\n" + 
					"   \"requestedOn\": \"2020-06-20T09:55:37.478Z\",\r\n" + 
					"   \"type\": \"SU\", \r\n" + 
					"   \"suspenseType\":\"RENEWAL\",            		\r\n" + 
					"   \"proposerName\":\"Akshay More321\",\r\n" + 
					"   \"payee\":{\r\n" + 
					"	\r\n" + 
					"		\"name\" : \"Harshit Agarwal321\",		\r\n" + 
					"		\"accountNumber\":\"1234567007\", \r\n" + 
					"		\"ifscCode\":\"ICIC0000915\" 	\r\n" + 
					"	}\r\n" + 
					"}");
			//https://service-api.qa-aegonlife.com/payment/internal/payouts/initiate							
			response = httpRequest.post("/internal/payouts/initiate");
			Logger.log("Suspense Payout Initiate API:"+response.getStatusCode());
			JSONObject jsonObject = (JSONObject) parser.parse(response.getBody().asString());
			payoutMode = (String) jsonObject.get("payoutMode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
		
	}
	
	public static Response getClaimNumber(String policyNumber) throws Exception{
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("claim");
		httpRequest = RestAssured.given();
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		response = httpRequest.get("/claims?policyNumber="+policyNumber);
		staticWait(3);
		String responseBody = "";
		if(response.getStatusCode()==200) {
			responseBody = response.getBody().asString();
			claimNumber = responseBody;
			//System.out.println("Response:::"+responseBody);			
		}else {
			System.out.println("Error:::"+responseBody);
		}	
		return response;
	}
	public static Response getClaimDetails(String claimNo) throws Exception {
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("claim");
		httpRequest = RestAssured.given();
		//httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		response = httpRequest.get("/claims/info?claimNumber="+claimNo);
		staticWait(3);
		if(response.getStatusCode()==200) {
			String responeBody = response.getBody().asString();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(responeBody);
			JSONObject jsObj = (JSONObject) obj;
			JSONObject claimDetailObj =  (JSONObject) jsObj.get("claimDetail");
			lumpSumBenefit = (long) claimDetailObj.get("lumpSumBenefit");
			incomeBenefit = (long) claimDetailObj.get("incomeBenefit");
			System.out.println("lum sum benefit::: "+lumpSumBenefit);
			System.out.println("income benefit::: "+incomeBenefit);
			JSONObject reviewDetailObj =  (JSONObject) jsObj.get("reviewDetail");
			JSONObject benefitsCalculationDetailArr =  (JSONObject) reviewDetailObj.get("benefitsCalculationDetail");
			grossClaimBenefitPayable = (double) benefitsCalculationDetailArr.get("grossClaimBenefitPayable");
			incomeBenefitAmt = (double) benefitsCalculationDetailArr.get("incomeBenefit");
			//System.out.println("gross claim benefit payable amount::: "+grossClaimBenefitPayable);
			//System.out.println("income benefit payable amount::: "+incomeBenefitAmt);
		} else {}
		return response;
	}
	
	public static String claimNumber() {
		return claimNumber;
	}
	
	public static double grossClaimBenefitPayable() {
		return grossClaimBenefitPayable;
	}
	public static long lumpSumBenefit() {
		return lumpSumBenefit;
	}
	
	public static long incomeBenefit() {
		return incomeBenefit;
	}
	public static double incomeBenefitAmount() {
		return incomeBenefitAmt;
	}
	
	public static void claimInitiatePayout(Double amount, Boolean partialPayout, String policyNo, String reason, String type, String proposerName) throws Exception {
		int strPayoutId;
		String strBusinessId = "";
		String strPolicyNumber = "";
		double Amount = 0.00;
		String strReason = "";
		String strStatus = "";
		String strType = "";
		boolean PartialPayout;
		double AmountPaid = 0.00;
		String strPayoutMode = "";
		st = new SoftAssert();
		TestBase.init();
		RestAssured.baseURI = prop.getProperty("payout");	//"https://services.qa-aegonlife.com";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.headers("X-AEGON-AUTH-CID", "16398");
		httpRequest.headers("X-AEGON-AUTH-SCOPE", "16398");
		httpRequest.header("Authorization", "Bearer " + IssuanceQueue.opstoken());
		httpRequest.header("x-api-key", prop.getProperty("x_api_key_ops"));
		httpRequest.body("{ \"amount\": "+amount+","
				+ "\n   \"approvalRequired\": false,"
				+ "\n	\"partialPayout\": "+partialPayout +","
				+ "\n	\"policyNumber\": \""+ policyNo +"\","
				+ "\n	\"payoutMode\": \"neft\","
				+ "\n	\"reason\": \""+ reason +"\","
				+ "\n	\"refundDate\": \""+ InitiatePayment.getFormatedDate("yyyy-MM-dd") +"\","
				+ "\n	\"requestedOn\": \""+ InitiatePayment.getFormatedDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") +"\","
				+ "\n	\"type\": \""+ type +"\","
				+ "\n	\"proposerName\": \""+ proposerName +"\","
				+ "\n	\"payee\": {\r\n	\"name\": \"Ankit Kesharwani\","
				+ "\n	\"accountNumber\": \"123456\","
				+ "\n	\"ifscCode\": \"HDFC0123456\" \n }"
				+ "\n 	}");				
		Response response = httpRequest.post("/initiate");
		staticWait(5);
		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		if (statusCode == 200) {
			System.out.println("response Body is:::"+responseBody);
			System.out.println("\n" + policyNo + " : Payout Initiated." + "\n");
			strPayoutId = response.jsonPath().getInt("id");
			strBusinessId = response.jsonPath().getString("businessId");
			System.out.println("Payout Id::: "+strPayoutId);
			System.out.println("BusinessId::: "+strBusinessId);
			strPolicyNumber = response.jsonPath().get("policyNumber");
			st.assertEquals(strPolicyNumber, policyNo);
			Amount = response.jsonPath().getDouble("amount");
			st.assertEquals(Amount, amount);	
			strReason = response.jsonPath().get("reason");
			st.assertEquals(strReason, reason);
			strStatus = response.jsonPath().getString("status");
			st.assertEquals(strStatus, "INITIATED");
			strType = response.jsonPath().getString("payoutType");
			st.assertEquals(strType, type);
			PartialPayout = response.jsonPath().getBoolean("partialPayout");
			st.assertEquals(PartialPayout, partialPayout);
			AmountPaid = response.jsonPath().getDouble("amountPaid");
			st.assertEquals(AmountPaid, amount);
			strPayoutMode = response.jsonPath().getString("payoutMode");
			st.assertEquals(strPayoutMode, "neft");
		} else if (statusCode == 400) {
			Logger.log("Payout not initiated. "+statusCode);
			Logger.log(responseBody);
		} else {
			Logger.log("Payout not initiated. "+statusCode);
			Logger.log(responseBody);
		}
		st.assertAll();
	}
	public static void getRefundInfo(String policyNo, String payoutTypeSU, String payoutTypePRC, String payoutTypeLSC, String payoutTypeIBC, String payoutMode, String reason, String vendorCode) throws Exception {
		st = new SoftAssert();
		RestAssured.baseURI = prop.getProperty("payment");	//"https://services.qa-aegonlife.com/";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.headers("Content-Type", "application/json");
		httpRequest.body("{ \n	\"policyNumber\": \""+ policyNo +"\" \n }");
		Response response = httpRequest.post("/payouts");
		Thread.sleep(5000);
		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		//System.out.println("Claim Policy response body: "+ responseBody);
		JSONParser jsonparser = new JSONParser();
		JSONArray payoutArr = (JSONArray) jsonparser.parse(responseBody);
		JSONObject jsonObj = null;
		if (statusCode == 200) {
			Logger.log("Payout status is ok.");
			Logger.log(responseBody);
			int size = payoutArr.size();
			int count =0;
			System.out.println("array size is::: "+ size);
			for (int i = 0; i< size; i++) {
				jsonObj = (JSONObject) payoutArr.get(i);
				String PayoutType = (String) jsonObj.get("payoutType");
				String PayoutMode = (String) jsonObj.get("payoutMode");
				long ArnNo =  (long) jsonObj.get("id");
				double amount = (double) jsonObj.get("amount");
				String Payoutreason = (String) jsonObj.get("reason");
				String status = (String) jsonObj.get("status");
				String policyNum = (String) jsonObj.get("policyNumber");			
				String SuspenseType = (String) jsonObj.get("suspenseType");			
				double amountPaid = (double) jsonObj.get("amountPaid");
				st.assertEquals(policyNum, policyNo);
				st.assertEquals(status, "REFUNDED");
				if(i== 0 && size == 1) {
					if(PayoutType.equalsIgnoreCase(payoutTypeSU)) {
						st.assertEquals(PayoutType, payoutTypeSU);
						st.assertEquals(PayoutMode, payoutMode);
						st.assertEquals(Payoutreason, reason+" "+payoutTypeSU);
					}else {
						st.assertEquals(PayoutType, payoutTypePRC);
						st.assertEquals(PayoutMode, payoutMode);
						st.assertEquals(Payoutreason, reason+" "+payoutTypePRC);
					}
				System.out.println("Payout type::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
				}
				if(i== 0 && size == 2) {
					st.assertEquals(PayoutType, payoutTypeSU);
					st.assertEquals(PayoutMode, payoutMode);
					st.assertEquals(Payoutreason, reason+" "+payoutTypeSU);
					System.out.println("Payout type::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
				} 
				if(i == 1 && size == 2) {
					if(PayoutType.equalsIgnoreCase(payoutTypeLSC)) {
						st.assertEquals(PayoutType, payoutTypeLSC);
						st.assertEquals(PayoutMode, payoutMode);
						st.assertEquals(Payoutreason, reason+" "+payoutTypeLSC);
					}else {
						st.assertEquals(PayoutType, payoutTypePRC);
						st.assertEquals(PayoutMode, payoutMode);
						st.assertEquals(Payoutreason, reason+" "+payoutTypePRC);
					}
					System.out.println("Payout type::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
				}
				if(i == 0 && size ==  3) {
					st.assertEquals(PayoutType, payoutTypeSU);
					st.assertEquals(PayoutMode, payoutMode);
					st.assertEquals(Payoutreason, reason+" "+payoutTypeSU);
					System.out.println("Payout type"+i+"::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
				} 
				if(i == 1 && size == 3) {
					st.assertEquals(PayoutType, payoutTypeLSC);
					st.assertEquals(PayoutMode, payoutMode);
					st.assertEquals(Payoutreason, reason+" "+payoutTypeLSC);
					System.out.println("Payout type"+i+"::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
				}
				if(i == 2 && size == 3) {
					st.assertEquals(PayoutType, payoutTypeIBC);
					st.assertEquals(PayoutMode, payoutMode);
					st.assertEquals(Payoutreason, reason+" "+payoutTypeIBC);
					System.out.println("Payout type"+i+"::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
				}
				if(i >= 0 && size > 3) {
					if(i == 0) {
						st.assertEquals(PayoutType, payoutTypeSU);
						st.assertEquals(PayoutMode, payoutMode);
						st.assertEquals(Payoutreason, reason+" "+payoutTypeSU);
						System.out.println("Payout type"+i+"::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
					}
					if(i == 1) {
						st.assertEquals(PayoutType, payoutTypeLSC);
						st.assertEquals(PayoutMode, payoutMode);
						st.assertEquals(Payoutreason, reason+" "+payoutTypeLSC);
						System.out.println("Payout type"+i+"::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
					}
					if(i == 2) {
						st.assertEquals(PayoutType, payoutTypeIBC);
						st.assertEquals(PayoutMode, payoutMode);
						st.assertEquals(Payoutreason, reason+" "+payoutTypeIBC);
						System.out.println("Payout type"+i+"::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
					}
					if(i > 2) {
						st.assertEquals(PayoutType, payoutTypeIBC);
						st.assertEquals(PayoutMode, payoutMode);
						st.assertEquals(Payoutreason, reason+" "+payoutTypeIBC);
						System.out.println("Payout type"+i+"::: "+ PayoutType +" ARN No::: "+ "TEST00"+ArnNo +" amount::: "+ amount +" amountPaid::: "+ amountPaid);
					}
				}				
				JSONObject vendorrefobj = (JSONObject) jsonObj.get("vendorRefund");
				String VendorCode = (String) vendorrefobj.get("vendorCode");
				st.assertEquals(VendorCode, vendorCode);
				count = i;
				if(count == size-1) {
					//System.out.println(count+"=>"+(size-1)); 
					break;
				}
			}
		} else {
			System.out.println("Refund Info is not OK.");
			System.out.println(response.getBody().asString());
		}
		st.assertAll();
	}
}
