package practice.utility;

import practice.testbase.*;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class RestAPIUtils {

	/**
	 *
	 * @param baseUrl Set Base URL
	 */
	public static void setBaseUrl(String baseUrl) {
		RestAssured.baseURI = baseUrl;
	}

	/**
	 * @author Ashish
	 */
	public static void setAuthenticationToken() {
		TestBase.init();
		RestAssured.baseURI = TestBase.prop.getProperty("AuthenticationURL");

		List<Header> list = new LinkedList<Header>();
		Header header1 = new Header("Content-Type", "application/x-www-form-urlencoded");
		Header header2 = new Header("grant_type", TestBase.prop.getProperty("GrantType"));

		list.add(header1);
		list.add(header2);

		Headers headers = new Headers(list);
		Response response = RestAssured.given().auth().preemptive()
				.basic(TestBase.prop.getProperty("ClientUser"), TestBase.prop.getProperty("ClientPassword"))
				.headers(headers).formParam("grant_type", TestBase.prop.getProperty("GrantType"))
				.post(TestBase.prop.getProperty("SubURI"));
		String token = response.jsonPath().get("access_token");
		// TestBase.updateValueInPropertyFile("token","Bearer "+token);
	}

	/**
	 * @author Ashish
	 * @param baseUrl
	 * @param tokenNumber
	 * @param uriPath
	 * @param jsonBody
	 * @return
	 */
	public static Response postRequest(String baseUrl, String tokenNumber, String uriPath, String jsonBody) {
		Header header1 = new Header("Authorization", tokenNumber);
		Header header2 = new Header("Content-Type", "application/json");
		List<Header> list = new LinkedList<Header>();
		list.add(header1);
		list.add(header2);
		Headers headers = new Headers(list);
		RestAssured.baseURI = baseUrl;
		Response responsePost = given().headers(headers).body(jsonBody).when().post(uriPath);
		System.out.println(responsePost.statusCode());
		return responsePost;
	}

	/**
	 *
	 * @param baseUri
	 * @param uriPath
	 * @return
	 */
	public static Response getResponse(String baseUri, String uriPath) {
		Header header = new Header("Content-Type", "application/json");
		RestAssured.baseURI = baseUri;
		Response responseGet = (Response) given().header(header).when().get(uriPath);
		return responseGet;
	}

	/**
	 *
	 * @param baseUri
	 * @param uriPath
	 * @param headers
	 * @return
	 */
	public static Response getResponse(String baseUri, String uriPath, Headers headers) {
		RestAssured.baseURI = baseUri;
		Response responseGet = (Response) given().headers(headers).when().get(uriPath);
		return responseGet;
	}

	/**
	 * Handle Patch Request
	 * 
	 * @param baseUri
	 * @param uriPath
	 * @param headers
	 * @param bodyText
	 * @return
	 */
	public static Response patchResponse(String baseUri, String uriPath, Headers headers, String bodyText) {
		RestAssured.baseURI = baseUri;
		Response responsePatch = (Response) given().headers(headers).body(bodyText).when().patch(uriPath);
		return responsePatch;
	}

	/**
	 * Handle Post or Put Request
	 * 
	 * @param uriPath
	 * @param headers
	 * @param bodyParams
	 * @param bodyText
	 * @param requestType
	 * @return
	 */
	public static Response postOrPutResponse(String uriPath, Headers headers, HashMap<String, String> bodyParams,
			String bodyText, String requestType) {
		Response response = null;
		RequestSpecBuilder builder = new RequestSpecBuilder();
		for (Header header : headers) {
			builder.addHeader(header.getName(), header.getValue());
		}

		if (bodyText != null) {
			builder.setBody(bodyText);
		}
		if (bodyParams.size() > 0) {
			for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
				builder.addFormParam(entry.getKey(), entry.getValue());
			}
		}
		RequestSpecification requestSpec = builder.build();
		switch (requestType) {
		case "Post":
			response = (Response) given().spec(requestSpec).when().post(uriPath);
			break;
		case "Put":
			response = (Response) given().spec(requestSpec).when().put(uriPath);
			// response=RestAssured.given().headers(headers).body(bodyText).put(uriPath);
			break;
		default:
			new Exception("Please provide either Post or Put for request Type");
		}
		response.prettyPrint();
		System.out.println(response.getStatusCode());
		return response;
	}

	/**
	 * Get response using Url path and header
	 * 
	 * @param uriPath : URI path
	 * @param headers : headers values
	 * @return
	 */
	public static Response getResponse(String uriPath, Headers headers, HashMap<String, String> bodyParams) {
		System.out.println(uriPath);
		RequestSpecBuilder builder = new RequestSpecBuilder();
		for (Header header : headers) {
			builder.addHeader(header.getName(), header.getValue());
		}
		if (bodyParams.size() > 0) {
			for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
				builder.addFormParam(entry.getKey(), entry.getValue());
				System.out.println(entry.getValue());
			}
		}
		RequestSpecification requestSpec = builder.build();
		Response responseGet = (Response) given().spec(requestSpec).when().get(uriPath);
		return responseGet;
	}

	/**
	 * Delete request
	 * 
	 * @param uriPath
	 * @param headers
	 * @param bodyParams
	 * @return
	 */
	public static Response deleteResponse(String uriPath, Headers headers, HashMap<String, String> bodyParams) {

		System.out.println(uriPath);
		RequestSpecBuilder builder = new RequestSpecBuilder();
		for (Header header : headers) {
			builder.addHeader(header.getName(), header.getValue());
		}
		if (bodyParams.size() > 0) {
			for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
				builder.addFormParam(entry.getKey(), entry.getValue());
				System.out.println(entry.getValue());
			}
		}
		RequestSpecification requestSpec = builder.build();
		Response responseGet = (Response) given().spec(requestSpec).when().delete(uriPath);
		System.out.println(responseGet.getStatusCode());
		return responseGet;
	}

}
