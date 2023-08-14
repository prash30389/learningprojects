package practice.claim;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.JSONParser;

import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class Endorsement_Nom_Contact extends TestBase {

	public static String bearerToken, bearerTokenUnivers;
	public static String api_key;
	public static String api_key_univers;
	public static Response response;
	public static Headers headers, headersUNIVERS;
	public static String endorsementbaseUrl;
	public static String postBody;
	public static HashMap<String, String> bodyParams = new HashMap<String, String>();
	public String projectPath = System.getProperty("user.dir");
	JSONParser parser = new JSONParser();

	public static void Utility() throws Exception {
		IssuanceQueue.getToken();
		IssuanceQueue.getToken_ops();

		TestBase.init();
		endorsementbaseUrl = prop.getProperty("endorsement");
		bearerToken = IssuanceQueue.opstoken();
		api_key = prop.getProperty("x_api_key_ops");
		bearerTokenUnivers = IssuanceQueue.token();
		api_key_univers = prop.getProperty("x_api_key");

		List<Header> list = new LinkedList<Header>();
		Header header1 = new Header("content-type", "application/json");
		Header header2 = new Header("Authorization", "Bearer "+bearerToken);
		Header header3 = new Header("x-api-key", api_key);
		list.add(header1);
		list.add(header2);
		list.add(header3);
		headers = new Headers(list);

		List<Header> listUni = new LinkedList<Header>();
		Header header4 = new Header("Authorization", "Bearer "+bearerTokenUnivers);
		Header header5 = new Header("x-api-key", api_key_univers);
		listUni.add(header1);
		listUni.add(header4);
		listUni.add(header5);
		headersUNIVERS = new Headers(listUni);

	}

}
