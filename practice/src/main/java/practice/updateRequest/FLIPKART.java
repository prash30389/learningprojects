package practice.updateRequest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import practice.testbase.TestBase;



public class FLIPKART extends TestBase {

	static String projectPath;
	static String jsonfile;
	static String policyNumber;

	public static String Code() throws Exception {
		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/SchemeJson/FLIPKART.json";

		int aNumber1 = 0, aNumber2 = 0;
		aNumber1 = (int) ((Math.random() * 9000000) + 1000000);
		aNumber2 = (int) ((Math.random() * 9000000) + 1000000);
		// System.out.println((aNumber1));
		// System.out.println((aNumber2));

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(jsonfile));

		JSONObject js = (JSONObject) obj;

		// System.out.println(js);

		JSONObject policy = (JSONObject) js.get("policyInfo");

		policy.replace("policyNumber", "FL5GELC5" + Integer.toString(aNumber1));

		policyNumber = policy.get("policyNumber").toString();

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "33395" + Integer.toString(aNumber2));

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -8);
		Date dateBefore1 = DateUtils.addDays(new Date(), -8);
		Date dateBefore2 = DateUtils.addDays(new Date(), -8);

		String purchaseDateTime = simpleDateFormat.format(dateBefore);
		String startDateTime = simpleDateFormat.format(dateBefore1);
		String dispatchDate = simpleDateFormat.format(dateBefore2);

		policy.replace("purchaseDateTime", purchaseDateTime);
		policy.replace("startDate", startDateTime);
		policy.replace("coiDispatchDateTime", dispatchDate);

		return js.toJSONString();

	}

	public static String pno() {
		String pnumber;
		return pnumber = policyNumber;
	}
}
