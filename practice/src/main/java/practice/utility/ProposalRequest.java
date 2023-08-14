package practice.utility;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import practice.testbase.TestBase;

import practice.reports.Logger;
import com.github.javafaker.Faker;


public class ProposalRequest extends TestBase {

	static String projectPath, pno;
	static String jsonfile;

	@SuppressWarnings("unchecked")
	public static String Code() throws Exception {
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

		policyInfo.replace("policyNumber", "ALI0QAP1" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -6);
		Date dateBefore1 = DateUtils.addDays(new Date(), -6);

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
	
//============
	@SuppressWarnings("unchecked")
	public static String surrenderCode() throws Exception {
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

		policyInfo.replace("policyNumber", "ALI0QAP1" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -35);
		Date dateBefore1 = DateUtils.addDays(new Date(), -35);

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
//============	
	
	
	@SuppressWarnings("unchecked")
	public static String posPolicy_Code() throws Exception {
		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/posProposalRequest.json";

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

		policyInfo.replace("policyNumber", "ALI0QAPOS1" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -4);
		Date dateBefore1 = DateUtils.addDays(new Date(), -4);

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
	@SuppressWarnings("unchecked")
	public static String SurrenderPOSCode() throws Exception {
		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/posProposalRequest.json";

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

		policyInfo.replace("policyNumber", "ALI0QAPOS2" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -34);
		Date dateBefore1 = DateUtils.addDays(new Date(), -34);

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
//=============================================================================================
	
	/**
	 * Ankit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String eMandate_Code() throws Exception {
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

		policyInfo.replace("policyNumber", "ALI0QAP1" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");
		paymentInfo.replace("mandateType", "eMandate");
		System.out.println(paymentInfo);

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -4);
		Date dateBefore1 = DateUtils.addDays(new Date(), -4);

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
	
	/**
	 * Ankit
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String CO_Code() throws Exception {
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

		policyInfo.replace("policyNumber", "ALI0QAP1" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -4);
		Date dateBefore1 = DateUtils.addDays(new Date(), -4);

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
	
	//For Claim
	@SuppressWarnings("unchecked")
	public static String Code(String sumAssured) throws Exception {
		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/ProposalRequest.json";

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(jsonfile));

		JSONObject js = (JSONObject) obj;
		JSONObject js1 = (JSONObject) obj;

		int aNumber1 = 0, aNumber2 = 0;
		aNumber1 = (int) ((Math.random() * 900000) + 100000);
		aNumber2 = (int) ((Math.random() * 900000) + 100000);

		Random random = new Random();
		int minDay = (int) LocalDate.of(1990, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(2001, 1, 1).toEpochDay();
		long randomDay1 = minDay + random.nextInt(maxDay - minDay);

		LocalDate randomBirthDate1 = LocalDate.ofEpochDay(randomDay1);

		JSONObject policyInfo = (JSONObject) js.get("policyInfo");


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
		//insured.replace("mobile", "777" + Integer.toString(aNumber2)); // randomBirthDate1 //dateOfBirth
		insured.replace("mobile", "6666600009");
		insured.replace("dateOfBirth", randomBirthDate1.toString());
		insured.replace("emailId", firstName + lastName + "@test.com");

		JSONArray identityInfo = (JSONArray) insured.get("identityInfo");

		JSONObject value = (JSONObject) identityInfo.get(0);
		value.replace("value", "AAAPP" + PAN + "C");

		JSONObject policyHolder = (JSONObject) js1.get("policyHolder");
		policyHolder.replace("firstName", firstName1);
		policyHolder.replace("lastName", lastName1);
		policyHolder.replace("partyrefid", firstName + "@10");
		//policyHolder.replace("mobile", "888" + Integer.toString(aNumber2));
		policyHolder.replace("mobile", "6666600009");
		policyHolder.replace("dateOfBirth", randomBirthDate2.toString());

		JSONArray identityInfo_policyHolder = (JSONArray) policyHolder.get("identityInfo");

		JSONObject value_policyHolder = (JSONObject) identityInfo_policyHolder.get(0);
		value_policyHolder.replace("value", "AABPP" + PAN + "P");

		policyInfo.replace("policyNumber", "ALI0QAP1" + Integer.toString(aNumber1)+2);

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -14);
		Date dateBefore1 = DateUtils.addDays(new Date(), -14);

		String purchaseDateTime = simpleDateFormat.format(dateBefore);
		String startDateTime = simpleDateFormat.format(dateBefore1);
		String dispatchDate = simpleDateFormat.format(dateBefore);

		policy.replace("purchaseDateTime", purchaseDateTime);
		policy.replace("startDate", startDateTime);
		policy.replace("coiDispatchDateTime", dispatchDate);

		JSONArray coverages = (JSONArray) policy.get("coverages");
		JSONObject coveragesValue0 = (JSONObject) coverages.get(0);

		coveragesValue0.replace("inceptionDate", startDateTime);
		coveragesValue0.replace("sumAssured", sumAssured);

		return js.toJSONString();

	}
	@SuppressWarnings("unchecked")
	public static String SurrenderRiderCode() throws Exception {
		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/SchemeJson_Retail/rider.json";

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

		policyInfo.replace("policyNumber", "ALI0QAAD" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -34);
		Date dateBefore1 = DateUtils.addDays(new Date(), -34);

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
	@SuppressWarnings("unchecked")
	public static String riderCode() throws Exception {
		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/SchemeJson_Retail/rider.json";

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

		policyInfo.replace("policyNumber", "ALI0QAAD" + Integer.toString(aNumber1));

		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));

		// "insured"

		// Date Code

		JSONObject policy = (JSONObject) js.get("policyInfo");

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -4);
		Date dateBefore1 = DateUtils.addDays(new Date(), -4);

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
	@SuppressWarnings("unchecked")
	public static String SurrenderPolicyCode() throws Exception {
		Logger.log("\n++++++++++ CODE method of Create Sample data for policy  ++++++++++\n");

		init();
		projectPath = System.getProperty("user.dir");
		jsonfile = projectPath + "/ProposalRequest.json";

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(jsonfile));
		JSONObject js = (JSONObject) obj;
		JSONObject js1 = (JSONObject) obj;
		
		Logger.log("\n***** Sample data for USER & POLICY HOLDER Started *****\n");
		int aNumber1 = 0, aNumber2 = 0;
		aNumber1 = (int) ((Math.random() * 9000000) + 1000000);
		aNumber2 = (int) ((Math.random() * 9000000) + 1000000);
		Random random = new Random();
		int minDay = (int) LocalDate.of(1990, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(2001, 1, 1).toEpochDay();
		
		long randomDay1 = minDay + random.nextInt(maxDay - minDay);
		LocalDate randomBirthDate1 = LocalDate.ofEpochDay(randomDay1);
		
		JSONObject policyInfo = (JSONObject) js.get("policyInfo");
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
		Logger.log("\n Sample user name created for user \n");
		
		insured.replace("partyrefid", firstName + "@10");
		Logger.log("\n Sample partyrefid created for user \n");
		
		insured.replace("mobile", "777" + Integer.toString(aNumber2)); 
		Logger.log("\n Sample mobile No created for user \n");
		
		insured.replace("dateOfBirth", randomBirthDate1.toString());
		Logger.log("\n Sample DOB created for user \n");
		
		insured.replace("emailId", firstName + lastName + "@test.com");
		Logger.log("\n Sample email ID created for user \n");
		
		JSONArray identityInfo = (JSONArray) insured.get("identityInfo");
		JSONObject value = (JSONObject) identityInfo.get(0);
		
		value.replace("value", "AAAPP" + PAN + "C");
		Logger.log("\n Sample PAN card created for user \n");
		
		JSONObject policyHolder = (JSONObject) js1.get("policyHolder");
		
		policyHolder.replace("firstName", firstName1);
		policyHolder.replace("lastName", lastName1);
		Logger.log("\n Sample user name created for policy holder \n");
		
		policyHolder.replace("partyrefid", firstName + "@10");
		Logger.log("\n Sample partyrefid created for policy Holder \n");

		policyHolder.replace("mobile", "888" + Integer.toString(aNumber2));
		Logger.log("\n Sample mobile number created for policy holder \n");
		
		policyHolder.replace("dateOfBirth", randomBirthDate2.toString());
		Logger.log("\n Sample DOB created for policy holder \n");
		
		JSONArray identityInfo_policyHolder = (JSONArray) policyHolder.get("identityInfo");
		JSONObject value_policyHolder = (JSONObject) identityInfo_policyHolder.get(0);
		Logger.log("\n Sample policy holder identityInfo created \n");
		
		value_policyHolder.replace("value", "AABPP" + PAN + "P");
		Logger.log("\n Sample PAN card created for policy holder \n");
		
		policyInfo.replace("policyNumber", "ALI0QAP1" + Integer.toString(aNumber1));
		String policyNumber = policyInfo.get("policyNumber").toString();
		pno = policyNumber;

		JSONObject paymentInfo = (JSONObject) js.get("paymentInfo");

		paymentInfo.replace("transactionId", "41015" + Integer.toString(aNumber2));
		Logger.log("\n policy number created \n");

		JSONObject policy = (JSONObject) js.get("policyInfo");
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dateBefore = DateUtils.addDays(new Date(), -36);
		Date dateBefore1 = DateUtils.addDays(new Date(), -36);

		String purchaseDateTime = simpleDateFormat.format(dateBefore);
		String startDateTime = simpleDateFormat.format(dateBefore1);
		String dispatchDate = simpleDateFormat.format(dateBefore);

		policy.replace("purchaseDateTime", purchaseDateTime);
		Logger.log("\n policy purchaseDateTime created \n");
		policy.replace("startDate", startDateTime);
		Logger.log("\n policy startDateTime created \n");
		policy.replace("coiDispatchDateTime", dispatchDate);
		Logger.log("\n policy dispatchDateTime created \n");

		JSONArray coverages = (JSONArray) policy.get("coverages");
		JSONObject coveragesValue0 = (JSONObject) coverages.get(0);

		coveragesValue0.replace("inceptionDate", startDateTime);
		Logger.log("\n***** Sample Data for policy created *****\n");
		// insuranceHistories
		/*
		 * JSONArray contacts = (JSONArray) party.get("contacts");
		 * 
		 * JSONObject contacts_Email = (JSONObject) contacts.get(1);
		 * contacts_Email.replace("contactInfo", "email");
		 */
		return js.toJSONString();

	
	}
//================================================================================================================

	public static String pno() {
		return pno;
	}
}
