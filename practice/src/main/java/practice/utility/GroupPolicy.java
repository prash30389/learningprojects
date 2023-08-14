package practice.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

import practice.testbase.TestBase;
import practice.updateRequest.*;

import io.restassured.RestAssured;

public class GroupPolicy extends TestBase {

	static String policyNumber;

	public static String vendorCode(String vendorCode, String Code, String pno, String token) throws Exception {

		RestAssured.baseURI = "https://services.qa-aegonlife.com/proposal";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("tenantId", vendorCode);
		httpRequest.body(Code);
		response = httpRequest.post("/proposals/submission");
		policyNumber = pno;
		Thread.sleep(20000);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), 0);
		String date = simpleDateFormat.format(dateBefore);

		RestAssured.baseURI = "https://service-api.qa-aegonlife.com/cancelpolicy/internal/policies";
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + token);
		httpRequest.header("x-api-key", "cP9ie2et5a1p9F7DN5K147VWXhT5IMmBgEXLSmT7");

		httpRequest.body("{\"policyNumber\":\"" + pno + "\",\"comment\":\"Cancel this policy\","
				+ "\"medicalFee\":0,\"reason\":\"PURCHASED_BY_MISTAKE\"," + "\"requestedOn\":\"" + date + "\"}\r\n");
		response = httpRequest.post("/cancellation");
		Thread.sleep(4000);

		System.out.println(
				"Policy No : " + vendorCode + " : " + policyNumber + " Freelook - " + response.getStatusCode());

		return policyNumber;

	}

}
