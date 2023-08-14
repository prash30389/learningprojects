package Concepts;


import io.restassured.RestAssured;
import io.restassured.authentication.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.*;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.internal.*;
import io.restassured.internal.assertion.AssertParameter;
import io.restassured.internal.log.LogRepository;
import io.restassured.mapper.ObjectMapper;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyStore;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.restassured.specification.ProxySpecification.host;

public class restAPILibrary {

	 private static final String SSL = "SSL";
	    private static ResponseParserRegistrar RESPONSE_PARSER_REGISTRAR = new ResponseParserRegistrar();

	    public static final String DEFAULT_URI = "http://localhost";
	    public static final String DEFAULT_BODY_ROOT_PATH = "";
	    public static final int DEFAULT_PORT = 8080;
	    public static final int UNDEFINED_PORT = -1;
	    public static final String DEFAULT_PATH = "";
	    public static final AuthenticationScheme DEFAULT_AUTH = new NoAuthScheme();
	    public static final boolean DEFAULT_URL_ENCODING_ENABLED = true;
	    public static final String DEFAULT_SESSION_ID_VALUE = null;

	    /**
	     * The base URI that's used by REST assured when making requests if a non-fully qualified URI is used in the request.
	     * Default value is {@value #DEFAULT_URI}.
	     */
	    public static String baseURI = DEFAULT_URI;

	    /**
	     * The port that's used by REST assured when it's left out of the specified URI when making a request.
	     * Default port will evaluate to {@value #DEFAULT_PORT}.
	     */
	    public static int port = UNDEFINED_PORT;

	    /**
	     * A base path that's added to the {@link #baseURI} by REST assured when making requests. E.g. let's say that
	     * the {@link #baseURI} is <code>http://localhost</code> and <code>basePath</code> is <code>/resource</code>
	     * then
	     * <p/>
	     * <pre>
	     * ..when().get("/something");
	     * </pre>
	     * <p/>
	     * will make a request to <code>http://localhost/resource</code>.
	     * Default <code>basePath</code> value is empty.
	     */
	    public static String basePath = DEFAULT_PATH;

	    /**
	     * Specifies if Rest Assured should url encode the URL automatically. Usually this is a recommended but in some cases
	     * e.g. the query parameters are already be encoded before you provide them to Rest Assured then it's useful to disable
	     * URL encoding. For example:
	     * <pre>
	     * RestAssured.baseURI = "https://jira.atlassian.com";
	     * RestAssured.port = 443;
	     * RestAssured.urlEncodingEnabled = false; // Because "query" is already url encoded
	     * String query = "project%20=%20BAM%20AND%20issuetype%20=%20Bug";
	     * String response = get("/rest/api/2.0.alpha1/search?jql={q}",query).andReturn().asString();
	     * ...
	     * </pre>
	     * The <code>query</code> is already url encoded so you need to disable Rest Assureds url encoding to prevent double encoding.
	     */
	    public static boolean urlEncodingEnabled = DEFAULT_URL_ENCODING_ENABLED;

	    /**
	     * Set an authentication scheme that should be used for each request. By default no authentication is used.
	     * If you have specified an authentication scheme and wish to override it for a single request then
	     * you can do this using:
	     * <p/>
	     * <pre>
	     *     given().auth().none()..
	     * </pre>
	     */
	    public static AuthenticationScheme authentication = DEFAULT_AUTH;

	    /**
	     * Define a configuration for e.g. redirection settings and http client parameters (default is <code>new RestAssuredConfig()</code>). E.g.
	     * <pre>
	     * RestAssured.config = config().redirect(redirectConfig().followRedirects(true).and().maxRedirects(0));
	     * </pre>
	     * <p/>
	     * <code>config()</code> can be statically imported from {@link RestAssuredConfig}.
	     * <p/>
	     * </pre>
	     */
	    public static RestAssuredConfig config = new RestAssuredConfig();

	    /**
	     * Set the default root path of the response body so that you don't need to write the entire path for each expectation.
	     * E.g. instead of writing:
	     * <p/>
	     * <pre>
	     * get(..).then().assertThat().
	     *          body("x.y.firstName", is(..)).
	     *          body("x.y.lastName", is(..)).
	     *          body("x.y.age", is(..)).
	     *          body("x.y.gender", is(..));
	     * </pre>
	     * <p/>
	     * you can use a root and do:
	     * <pre>
	     * RestAssured.rootPath = "x.y";
	     * get(..).then().assertThat().
	     *          body("firstName", is(..)).
	     *          body("lastName", is(..)).
	     *          body("age", is(..)).
	     *          body("gender", is(..)).
	     * </pre>
	     */
	    public static String rootPath = DEFAULT_BODY_ROOT_PATH;

	    /**
	     * Specify a default request specification that will be sent with each request. E,g.
	     * <pre>
	     * RestAssured.requestSpecification = new RequestSpecBuilder().addParameter("parameter1", "value1").build();
	     * </pre>
	     * <p/>
	     * means that for each request by Rest Assured "parameter1" will be equal to "value1".
	     */
	    public static RequestSpecification requestSpecification = null;

	    /**
	     * Specify a default parser. This parser will be used if the response content-type
	     * doesn't match any pre-registered or custom registered parsers. Also useful if the response
	     * doesn't contain a content-type at all.
	     */
	    public static Parser defaultParser = null;

	    /**
	     * Specify a default response specification that will be sent with each request. E,g.
	     * <pre>
	     * RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();
	     * </pre>
	     * <p/>
	     * means that for each response Rest Assured will assert that the status code is equal to 200.
	     */
	    public static ResponseSpecification responseSpecification = null;

	    /**
	     * Set the default session id value that'll be used for each request. This value will be set in the {@link SessionConfig} so it'll
	     * override the session id value previously defined there (if any). If you need to change the sessionId cookie name you need to configure and supply the {@link SessionConfig} to
	     * <code>RestAssured.config</code>.
	     */
	    public static String sessionId = DEFAULT_SESSION_ID_VALUE;

	    /**
	     * Specify a default proxy that REST Assured will use for all requests (unless overridden by individual tests). For example:
	     * <p/>
	     * <pre>
	     * RestAssured.proxy = host("127.0.0.1").withPort(8888);
	     * </pre>
	     * where <code>host</code> is statically imported from {@link ProxySpecification#host(String)}.
	     *
	     * @see #proxy(String)
	     * @see #proxy(String, int)
	     * @see #proxy(String, int, String)
	     * @see #proxy(java.net.URI)
	     * @see #proxy(ProxySpecification)
	     */
	    public static ProxySpecification proxy = null;

	    private static List<Filter> filters = new LinkedList<Filter>();


	    /**
	     * Add default filters that will be applied to each request.
	     *
	     * @param filters The filter list
	     */
	   

	    /**
	     * @return The current default filters
	     */
	    public static List<Filter> filters() {
	        return Collections.unmodifiableList(filters);
	    }

	    /**
	     * Set a object mapper that'll be used when serializing and deserializing Java objects to and from it's
	     * document representation (XML, JSON etc).
	     *
	     * @param objectMapper The object mapper to use.
	     */
	    public static void objectMapper(ObjectMapper objectMapper) {
	        Validate.notNull(objectMapper, "Default object mapper cannot be null");
	        config = config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapper(objectMapper));
	    }

	    /**
	     * Start building the response part of the test io.restassured.specification. E.g.
	     * <p/>
	     * <pre>
	     * expect().body("lotto.lottoId", equalTo(5)).when().get("/lotto");
	     * </pre>
	     * <p/>
	     * will expect that the response body for the GET request to "/lotto" should
	     * contain JSON or XML which has a lottoId equal to 5.
	     *
	     * @return A response io.restassured.specification.
	     */
	    public static ResponseSpecification expect() {
	        return createTestSpecification().getResponseSpecification();
	    }

	    /**
	     * Start building the request part of the test io.restassured.specification. E.g.
	     * <p/>
	     * <pre>
	     * with().parameters("firstName", "John", "lastName", "Doe").when().post("/greetXML").then().assertThat().body("greeting.firstName", equalTo("John"));
	     * </pre>
	     * <p/>
	     * will send a POST request to "/greetXML" with request parameters <tt>firstName=John</tt> and <tt>lastName=Doe</tt> and
	     * expect that the response body containing JSON or XML firstName equal to John.
	     * <p/>
	     * The only difference between {@link #with()} and {@link #given()} is syntactical.
	     *
	     * @return A request specification.
	     */
	    public static RequestSpecification with() {
	        return given();
	    }

	    /**
	     * Create a list of arguments that can be used to create parts of the path in a body/content expression.
	     * This is useful in situations where you have e.g. pre-defined variables that constitutes the key. For example:
	     * <pre>
	     * String someSubPath = "else";
	     * int index = 1;
	     * expect().body("something.%s[%d]", withArgs(someSubPath, index), equalTo("some value")). ..
	     * </pre>
	     * <p/>
	     * or if you have complex root paths and don't wish to duplicate the path for small variations:
	     * <pre>
	     * get("/x").then().assertThat().
	     *          root("filters.filterConfig[%d].filterConfigGroups.find { it.name == 'Gold' }.includes").
	     *          body(withArgs(0), hasItem("first")).
	     *          body(withArgs(1), hasItem("second")).
	     *          ..
	     * </pre>
	     * <p/>
	     * The key and arguments follows the standard <a href="http://download.oracle.com/javase/1,5.0/docs/api/java/util/Formatter.html#syntax">formatting syntax</a> of Java.
	     *
	     * @return A list of arguments that can be used to build up the response specification
	     * @deprecated Use {@link #withArgs(Object, Object...)} instead
	     */
	    @Deprecated
	    public static List<Argument> withArguments(Object firstArgument, Object... additionalArguments) {
	        return withArgs(firstArgument, additionalArguments);
	    }

	    /**
	     * Create a list of no arguments that can be used to create parts of the path in a response specification for JSON, XML or HTML validation.
	     * This is useful in situations where you have e.g. pre-defined variables that constitutes the key. For example:
	     * <pre>
	     * get("/jsonStore").then().
	     *          root("store.%s", withArgs("book")).
	     *          body("category.size()", equalTo(4)).
	     *          appendRoot("%s.%s", withArgs("author", "size()")).
	     *          body(withNoArguments(), equalTo(4));
	     * </pre>
	     * <p/>
	     *
	     * @return A list of no arguments that can be used to build up the response specification
	     * @deprecated Use {@link #withNoArgs()} instead
	     */
	    @Deprecated
	    public static List<Argument> withNoArguments() {
	        return withNoArgs();
	    }

	    /**
	     * Create a list of arguments that can be used to create parts of the path in a body/content expression.
	     * This is useful in situations where you have e.g. pre-defined variables that constitutes the key. For example:
	     * <pre>
	     * String someSubPath = "else";
	     * int index = 1;
	     * when().get().then().body("something.%s[%d]", withArgs(someSubPath, index), equalTo("some value")). ..
	     * </pre>
	     * <p/>
	     * or if you have complex root paths and don't wish to duplicate the path for small variations:
	     * <pre>
	     * get("/x").then().assertThat().
	     *          root("filters.filterConfig[%d].filterConfigGroups.find { it.name == 'Gold' }.includes").
	     *          body(withArgs(0), hasItem("first")).
	     *          body(withArgs(1), hasItem("second")).
	     *          ..
	     * </pre>
	     * <p/>
	     * The key and arguments follows the standard <a href="http://download.oracle.com/javase/1,5.0/docs/api/java/util/Formatter.html#syntax">formatting syntax</a> of Java.
	     *
	     * @return A list of arguments.
	     */
	    public static List<Argument> withArgs(Object firstArgument, Object... additionalArguments) {
	        Validate.notNull(firstArgument, "You need to supply at least one argument");
	        final List<Argument> arguments = new LinkedList<Argument>();
	        arguments.add(Argument.arg(firstArgument));
	        if (additionalArguments != null && additionalArguments.length > 0) {
	            for (Object additionalArgument : additionalArguments) {
	                arguments.add(Argument.arg(additionalArgument));
	            }
	        }
	        return Collections.unmodifiableList(arguments);
	    }

	    /**
	     * Create a list of no arguments that can be used to create parts of the path in a response specification for JSON, XML or HTML validation.
	     * This is useful in situations where you have e.g. pre-defined variables that constitutes the key. For example:
	     * <pre>
	     * get("/jsonStore").then().
	     *          root("store.%s", withArgs("book")).
	     *          body("category.size()", equalTo(4)).
	     *          appendRoot("%s.%s", withArgs("author", "size()")).
	     *          body(withNoArgs(), equalTo(4));
	     *
	     * @return A list of no arguments.
	     */
	    public static List<Argument> withNoArgs() {
	        return Collections.unmodifiableList(Collections.<Argument>emptyList());
	    }

	    /**
	     * Start building the request part of the test io.restassured.specification. E.g.
	     * <p/>
	     * <pre>
	     * given().parameters("firstName", "John", "lastName", "Doe").when().post("/greetXML").then().body("greeting.firstName", equalTo("John"));
	     * </pre>
	     * <p/>
	     * will send a POST request to "/greetXML" with request parameters <tt>firstName=John</tt> and <tt>lastName=Doe</tt> and
	     * expect that the response body containing JSON or XML firstName equal to John.
	     * <p/>
	     * The only difference between {@link #with()} and {@link #given()} is syntactical.
	     *
	     * @return A request specification.
	     */
	    public static RequestSpecification given() {
	        return createTestSpecification().getRequestSpecification();
	    }

	    /**
	     * Start building the DSL expression by sending a request without any parameters or headers etc. E.g.
	     * <p/>
	     * <pre>
	     * when().
	     *        get("/x").
	     * then().
	     *        body("x.y.z1", equalTo("Z1")).
	     *        body("x.y.z2", equalTo("Z2"));
	     * </pre>
	     * <p>
	     * Note that if you need to add parameters, headers, cookies or other request properties use the {@link #given()} method.
	     * </p>
	     *
	     * @return A request sender interface that let's you call resources on the server
	     */
	    public static RequestSender when() {
	        return createTestSpecification().getRequestSpecification();
	    }

	    /**
	     * When you have long specifications it can be better to split up the definition of response and request specifications in multiple lines.
	     * You can then pass the response and request specifications to this method. E.g.
	     * <p/>
	     * <pre>
	     * RequestSpecification requestSpecification = with().parameters("firstName", "John", "lastName", "Doe");
	     * ResponseSpecification responseSpecification = expect().body("greeting", equalTo("Greetings John Doe"));
	     * given(requestSpecification, responseSpecification).get("/greet");
	     * </pre>
	     * <p/>
	     * This will perform a GET request to "/greet" and verify it according to the <code>responseSpecification</code>.
	     *
	     * @return A test io.restassured.specification.
	     */
	    public static RequestSender given(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
	        return new TestSpecificationImpl(requestSpecification, responseSpecification);
	    }

	    /**
	     * When you're only interested in supplying a predefined request specification without a response specification then you can use this method.
	     * For example:
	     * <p/>
	     * <pre>
	     * RequestSpecification requestSpecification = with().parameters("firstName", "John", "lastName", "Doe");
	     * given(requestSpecification).get("/greet"). ..;
	     * </pre>
	     * <p/>
	     * This will perform a GET request to "/greet" and without any validation (only a static response specification has been configured).
	     *
	     * @return A RequestSender
	     */
	    public static RequestSpecification given(RequestSpecification requestSpecification) {
	        return given().spec(requestSpecification);
	    }

	    /**
	     * Perform a GET request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>get("/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the GET request.
	     */
	    public static Response get(String path, Object... pathParams) {
	        return given().get(path, pathParams);
	    }

	    /**
	     * Perform a GET request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters.
	     * @return The response of the GET request.
	     */
	    public static Response get(String path, Map<String, ?> pathParams) {
	        return given().get(path, pathParams);
	    }

	    /**
	     * Perform a POST request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>post("/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the request.
	     */
	    public static Response post(String path, Object... pathParams) {
	        return given().post(path, pathParams);
	    }

	    /**
	     * Perform a POST request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters.
	     * @return The response of the request.
	     */
	    public static Response post(String path, Map<String, ?> pathParams) {
	        return given().post(path, pathParams);
	    }

	    /**
	     * Perform a PUT request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>put("/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the request.
	     */
	    public static Response put(String path, Object... pathParams) {
	        return given().put(path, pathParams);
	    }

	    /**
	     * Perform a DELETE request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>delete("/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the request.
	     */
	    public static Response delete(String path, Object... pathParams) {
	        return given().delete(path, pathParams);
	    }

	    /**
	     * Perform a DELETE request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters.
	     * @return The response of the request.
	     */
	    public static Response delete(String path, Map<String, ?> pathParams) {
	        return given().delete(path, pathParams);
	    }

	    /**
	     * Perform a HEAD request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>head("/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the request.
	     */
	    public static Response head(String path, Object... pathParams) {
	        return given().head(path, pathParams);
	    }

	    /**
	     * Perform a HEAD request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters.
	     * @return The response of the request.
	     */
	    public static Response head(String path, Map<String, ?> pathParams) {
	        return given().head(path, pathParams);
	    }

	    /**
	     * Perform a PATCH request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>head("/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the request.
	     */
	    public static Response patch(String path, Object... pathParams) {
	        return given().patch(path, pathParams);
	    }

	    /**
	     * Perform a PATCH request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters.
	     * @return The response of the request.
	     */
	    public static Response patch(String path, Map<String, ?> pathParams) {
	        return given().patch(path, pathParams);
	    }

	    /**
	     * Perform a OPTIONS request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>head("/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the request.
	     */
	    public static Response options(String path, Object... pathParams) {
	        return given().options(path, pathParams);
	    }

	    /**
	     * Perform a OPTIONS request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters.
	     * @return The response of the request.
	     */
	    public static Response options(String path, Map<String, ?> pathParams) {
	        return given().options(path, pathParams);
	    }

	    /**
	     * Perform a GET request to a <code>uri</code>.
	     *
	     * @param uri The uri to send the request to.
	     * @return The response of the GET request.
	     */
	    public static Response get(URI uri) {
	        return given().get(uri);
	    }

	    /**
	     * Perform a POST request to a <code>uri</code>.
	     *
	     * @param uri The uri to send the request to.
	     * @return The response of the request.
	     */
	    public static Response post(URI uri) {
	        return given().post(uri);
	    }

	    /**
	     * Perform a PUT request to a <code>uri</code>.
	     *
	     * @param uri The uri to send the request to.
	     * @return The response of the request.
	     */
	    public static Response put(URI uri) {
	        return given().put(uri);
	    }

	    /**
	     * Perform a DELETE request to a <code>uri</code>.
	     *
	     * @param uri The uri to send the request to.
	     * @return The response of the request.
	     */
	    public static Response delete(URI uri) {
	        return given().delete(uri);
	    }

	    /**
	     * Perform a HEAD request to a <code>uri</code>.
	     *
	     * @param uri The uri to send the request to.
	     * @return The response of the request.
	     */
	    public static Response head(URI uri) {
	        return given().head(uri);
	    }

	    /**
	     * Perform a PATCH request to a <code>uri</code>.
	     *
	     * @param uri The uri to send the request to.
	     * @return The response of the request.
	     */
	    public static Response patch(URI uri) {
	        return given().patch(uri);
	    }

	    /**
	     * Perform a OPTIONS request to a <code>uri</code>.
	     *
	     * @param uri The uri to send the request to.
	     * @return The response of the request.
	     */
	    public static Response options(URI uri) {
	        return given().options(uri);
	    }

	    /**
	     * Perform a GET request to a <code>url</code>.
	     *
	     * @param url The url to send the request to.
	     * @return The response of the GET request.
	     */
	    public static Response get(URL url) {
	        return given().get(url);
	    }

	    /**
	     * Perform a POST request to a <code>url</code>.
	     *
	     * @param url The url to send the request to.
	     * @return The response of the request.
	     */
	    public static Response post(URL url) {
	        return given().post(url);
	    }

	    /**
	     * Perform a PUT request to a <code>url</code>.
	     *
	     * @param url The url to send the request to.
	     * @return The response of the request.
	     */
	    public static Response put(URL url) {
	        return given().put(url);
	    }

	    /**
	     * Perform a DELETE request to a <code>url</code>.
	     *
	     * @param url The url to send the request to.
	     * @return The response of the request.
	     */
	    public static Response delete(URL url) {
	        return given().delete(url);
	    }

	    /**
	     * Perform a HEAD request to a <code>url</code>.
	     *
	     * @param url The url to send the request to.
	     * @return The response of the request.
	     */
	    public static Response head(URL url) {
	        return given().head(url);
	    }

	    /**
	     * Perform a PATCH request to a <code>url</code>.
	     *
	     * @param url The url to send the request to.
	     * @return The response of the request.
	     */
	    public static Response patch(URL url) {
	        return given().patch(url);
	    }

	    /**
	     * Perform a OPTIONS request to a <code>url</code>.
	     *
	     * @param url The url to send the request to.
	     * @return The response of the request.
	     */
	    public static Response options(URL url) {
	        return given().options(url);
	    }

	    /**
	     * Perform a GET request to the statically configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @return The response of the GET request.
	     */
	    public static Response get() {
	        return given().get();
	    }

	    /**
	     * Perform a POST request to the statically configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @return The response of the request.
	     */
	    public static Response post() {
	        return given().post();
	    }

	    /**
	     * Perform a PUT request to the statically configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @return The response of the request.
	     */
	    public static Response put() {
	        return given().put();
	    }

	    /**
	     * Perform a DELETE request to the statically configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @return The response of the request.
	     */
	    public static Response delete() {
	        return given().delete();
	    }

	    /**
	     * Perform a HEAD request to the statically configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @return The response of the request.
	     */
	    public static Response head() {
	        return given().head();
	    }

	    /**
	     * Perform a PATCH request to the statically configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @return The response of the request.
	     */
	    public static Response patch() {
	        return given().patch();
	    }

	    /**
	     * Perform a OPTIONS request to the statically configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @return The response of the request.
	     */
	    public static Response options() {
	        return given().options();
	    }

	    /**
	     * Perform a request to the pre-configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @param method The HTTP method to use
	     * @return The response of the request.
	     */
	    public static Response request(Method method) {
	        return given().request(method);
	    }

	    /**
	     * Perform a custom HTTP request to the pre-configured path (by default <code>http://localhost:8080</code>).
	     *
	     * @param method The HTTP method to use
	     * @return The response of the request.
	     */
	    public static Response request(String method) {
	        return given().request(method);
	    }

	    /**
	     * Perform a HTTP request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param method     The HTTP method to use
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>request(Method.TRACE,"/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the request.
	     */
	    public static Response request(Method method, String path, Object... pathParams) {
	        return given().request(method, path, pathParams);
	    }

	    /**
	     * Perform a custom HTTP request to a <code>path</code>. Normally the path doesn't have to be fully-qualified e.g. you don't need to
	     * specify the path as <tt>http://localhost:8080/path</tt>. In this case it's enough to use <tt>/path</tt>.
	     *
	     * @param method     The HTTP method to use
	     * @param path       The path to send the request to.
	     * @param pathParams The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>request("method","/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
	     * @return The response of the request.
	     */
	    public static Response request(String method, String path, Object... pathParams) {
	        return given().request(method, path, pathParams);
	    }

	    /**
	     * Perform a request to a <code>uri</code>.
	     *
	     * @param method The HTTP method to use
	     * @param uri    The uri to send the request to.
	     * @return The response of the GET request.
	     */
	    public static Response request(Method method, URI uri) {
	        return given().request(method, uri);
	    }

	    /**
	     * Perform a request to a <code>url</code>.
	     *
	     * @param method The HTTP method to use
	     * @param url    The url to send the request to.
	     * @return The response of the GET request.
	     */
	    public static Response request(Method method, URL url) {
	        return given().request(method, url);
	    }

	    /**
	     * Perform a custom HTTP request to a <code>uri</code>.
	     *
	     * @param method The HTTP method to use
	     * @param uri    The uri to send the request to.
	     * @return The response of the GET request.
	     */
	    public static Response request(String method, URI uri) {
	        return given().request(method, uri);
	    }

	    /**
	     * Perform a custom HTTP request to a <code>url</code>.
	     *
	     * @param method The HTTP method to use
	     * @param url    The url to send the request to.
	     * @return The response of the GET request.
	     */
	    public static Response request(String method, URL url) {
	        return given().request(method, url);
	    }

	    /**
	     * Create a http basic authentication scheme.
	     *
	     * @param userName The user name.
	     * @param password The password.
	     * @return The authentication scheme
	     */
	    public static AuthenticationScheme basic(String userName, String password) {
	        final BasicAuthScheme scheme = new BasicAuthScheme();
	        scheme.setUserName(userName);
	        scheme.setPassword(password);
	        return scheme;
	    }

	    /**
	     * Create a NTLM authentication scheme.
	     *
	     * @param userName The user name.
	     * @param password The password.
	     * @param workstation The NTLM workstation.
	     * @param domain The NTLM workstation.
	     * @return The authentication scheme
	     */
	    public static AuthenticationScheme ntlm(String userName, String password, String workstation, String domain) {
	        final NTLMAuthScheme scheme = new NTLMAuthScheme();
	        scheme.setUserName(userName);
	        scheme.setPassword(password);
	        scheme.setWorkstation(workstation);
	        scheme.setDomain(domain);
	        return scheme;
	    }

	    /**
	     * Use form authentication. Rest Assured will try to parse the response
	     * login page and determine and try find the action, username and password input
	     * field automatically.
	     * <p>
	     * Note that the request will be much faster if you also supply a form auth configuration.
	     * </p>
	     *
	     * @param userName The user name.
	     * @param password The password.
	     * @return The authentication scheme
	     * @see #form(String, String, FormAuthConfig)
	     */
	    public static AuthenticationScheme form(String userName, String password) {
	        return form(userName, password, null);
	    }

	    /**
	     * Use form authentication with the supplied configuration.
	     *
	     * @param userName The user name.
	     * @param password The password.
	     * @param config   The form authentication config
	     * @return The authentication scheme
	     */
	    public static AuthenticationScheme form(String userName, String password, FormAuthConfig config) {
	        if (userName == null) {
	            throw new IllegalArgumentException("Username cannot be null");
	        }
	        if (password == null) {
	            throw new IllegalArgumentException("Password cannot be null");
	        }
	        final FormAuthScheme scheme = new FormAuthScheme();
	        scheme.setUserName(userName);
	        scheme.setPassword(password);
	        scheme.setConfig(config);
	        return scheme;
	    }

	    /**
	     * Return the http preemptive authentication specification for setting up preemptive authentication requests.
	     * This means that the authentication details are sent in the request header regardless if the server challenged
	     * for authentication or not.
	     *
	     * @return The authentication scheme
	     */
	    public static PreemptiveAuthProvider preemptive() {
	        return new PreemptiveAuthProvider();
	    }

	    /**
	     * Sets a certificate to be used for SSL authentication. See {@link java.lang.Class#getResource(String)}
	     * for how to get a URL from a resource on the classpath.
	     * <p>
	     * Uses SSL settings defined in {@link SSLConfig}.
	     * </p>
	     *
	     * @param certURL  URL to a JKS keystore where the certificate is stored.
	     * @param password The password for the keystore
	     * @return The request io.restassured.specification
	     */
	    public static AuthenticationScheme certificate(String certURL, String password) {
	        SSLConfig sslConfig = config().getSSLConfig();
	        return certificate(certURL, password, CertificateAuthSettings.certAuthSettings().keyStoreType(sslConfig.getKeyStoreType()).trustStore(sslConfig.getTrustStore()).
	                keyStore(sslConfig.getKeyStore()).trustStoreType(sslConfig.getTrustStoreType()).x509HostnameVerifier(sslConfig.getX509HostnameVerifier()).
	                port(sslConfig.getPort()).sslSocketFactory(sslConfig.getSSLSocketFactory()));
	    }

	    /**
	     * Sets a certificate to be used for SSL authentication. See {@link Class#getResource(String)} for how to get a URL from a resource
	     * on the classpath.
	     * <p/>
	     *
	     * @param certURL                 URL to a JKS keystore where the certificate is stored.
	     * @param password                The password for the keystore
	     * @param certificateAuthSettings More advanced settings for the certificate authentication
	     */
	    public static AuthenticationScheme certificate(String certURL, String password, CertificateAuthSettings certificateAuthSettings) {
	        return certificate(certURL, password, "", "", certificateAuthSettings);
	    }

	    /**
	     * Sets a certificate to be used for SSL authentication. See {@link Class#getResource(String)} for how to get a URL from a resource
	     * on the classpath.
	     * <p/>
	     *
	     * @param trustStorePath          URL to a JKS trust store where the certificate is stored.
	     * @param trustStorePassword      The password for the trust store
	     * @param keyStorePath            URL to a JKS keystore where the certificate is stored.
	     * @param keyStorePassword        The password for the keystore
	     * @param certificateAuthSettings More advanced settings for the certificate authentication
	     */
	    public static AuthenticationScheme certificate(String trustStorePath, String trustStorePassword,
	                                                   String keyStorePath, String keyStorePassword,
	                                                   CertificateAuthSettings certificateAuthSettings) {
	        AssertParameter.notNull(keyStorePath, "Keystore path");
	        AssertParameter.notNull(keyStorePassword, "Keystore password");
	        AssertParameter.notNull(trustStorePath, "Trust store path");
	        AssertParameter.notNull(trustStorePassword, "Keystore password");
	        AssertParameter.notNull(certificateAuthSettings, CertificateAuthSettings.class);
	        final CertAuthScheme scheme = new CertAuthScheme();
	        scheme.setPathToKeyStore(keyStorePath);
	        scheme.setKeyStorePassword(keyStorePassword);
	        scheme.setKeystoreType(certificateAuthSettings.getKeyStoreType());
	        scheme.setKeyStore(certificateAuthSettings.getKeyStore());
	        scheme.setPort(certificateAuthSettings.getPort());
	        scheme.setTrustStore(certificateAuthSettings.getTrustStore());
	        scheme.setTrustStoreType(certificateAuthSettings.getTrustStoreType());
	        scheme.setPathToTrustStore(trustStorePath);
	        scheme.setTrustStorePassword(trustStorePassword);
	        scheme.setX509HostnameVerifier(certificateAuthSettings.getX509HostnameVerifier());
	        scheme.setSslSocketFactory(certificateAuthSettings.getSSLSocketFactory());
	        return scheme;
	    }

	    /**
	     * Use http digest authentication. Note that you need to encode the password yourself.
	     *
	     * @param userName The user name.
	     * @param password The password.
	     * @return The authentication scheme
	     */
	    public static AuthenticationScheme digest(String userName, String password) {
	        return basic(userName, password);
	    }

	    /**
	     * Excerpt from the HttpBuilder docs:<br>
	     * OAuth sign the request. Note that this currently does not wait for a WWW-Authenticate challenge before sending the the OAuth header.
	     * All requests to all domains will be signed for this instance.
	     * This assumes you've already generated an accessToken and secretToken for the site you're targeting.
	     * For More information on how to achieve this, see the <a href="https://github.com/mttkay/signpost/blob/master/docs/GettingStarted.md#using-signpost">Signpost documentation</a>.
	     *
	     * @param consumerKey
	     * @param consumerSecret
	     * @param accessToken
	     * @param secretToken
	     * @return The authentication scheme
	     */
	    public static AuthenticationScheme oauth(String consumerKey, String consumerSecret, String accessToken, String secretToken) {
	        OAuthScheme scheme = new OAuthScheme();
	        scheme.setConsumerKey(consumerKey);
	        scheme.setConsumerSecret(consumerSecret);
	        scheme.setAccessToken(accessToken);
	        scheme.setSecretToken(secretToken);
	        return scheme;
	    }

	    /**
	     * Excerpt from the HttpBuilder docs:<br>
	     * OAuth sign the request. Note that this currently does not wait for a WWW-Authenticate challenge before sending the the OAuth header.
	     * All requests to all domains will be signed for this instance.
	     * This assumes you've already generated an accessToken and secretToken for the site you're targeting.
	     * For More information on how to achieve this, see the <a href="https://github.com/mttkay/signpost/blob/master/docs/GettingStarted.md#using-signpost">Signpost documentation</a>.
	     *
	     * @param consumerKey
	     * @param consumerSecret
	     * @param accessToken
	     * @param secretToken
	     * @param signature
	     * @return The authentication scheme
	     */
	    public static AuthenticationScheme oauth(String consumerKey, String consumerSecret, String accessToken, String secretToken, OAuthSignature signature) {
	        OAuthScheme scheme = new OAuthScheme();
	        scheme.setConsumerKey(consumerKey);
	        scheme.setConsumerSecret(consumerSecret);
	        scheme.setAccessToken(accessToken);
	        scheme.setSecretToken(secretToken);
	        scheme.setSignature(signature);
	        return scheme;
	    }

	    /**
	     * OAuth sign the request. Note that this currently does not wait for a WWW-Authenticate challenge before sending the the OAuth header.
	     * All requests to all domains will be signed for this instance.
	     *
	     * @param accessToken The access token to use
	     * @return The authentication scheme
	     */
	    public static AuthenticationScheme oauth2(String accessToken) {
	        PreemptiveOAuth2HeaderScheme myScheme = new PreemptiveOAuth2HeaderScheme();
	        myScheme.setAccessToken(accessToken);
	        return myScheme;
	    }

	    /**
	     * OAuth sign the request. Note that this currently does not wait for a WWW-Authenticate challenge before sending the the OAuth header.
	     * All requests to all domains will be signed for this instance.
	     *
	     * @param accessToken
	     * @param signature
	     * @return The authentication scheme
	     */
	    public static AuthenticationScheme oauth2(String accessToken, OAuthSignature signature) {
	        OAuth2Scheme scheme = new OAuth2Scheme();
	        scheme.setAccessToken(accessToken);
	        scheme.setSignature(signature);
	        return scheme;
	    }

	    /**
	     * Register a custom content-type to be parsed using a predefined parser. E.g. let's say you want parse
	     * content-type <tt>application/custom</tt> with the XML parser to be able to verify the response using the XML dot notations:
	     * <pre>
	     * get("/x").then().assertThat().body("document.child", equalsTo("something"))..
	     * </pre>
	     * Since <tt>application/custom</tt> is not registered to be processed by the XML parser by default you need to explicitly
	     * tell REST Assured to use this parser before making the request:
	     * <pre>
	     * RestAssured.registerParser("application/custom, Parser.XML");
	     * </pre>
	     *
	     * @param contentType The content-type to register
	     * @param parser      The parser to use when verifying the response.
	     */
	    public static void registerParser(String contentType, Parser parser) {
	        RESPONSE_PARSER_REGISTRAR.registerParser(contentType, parser);
	    }

	    /**
	     * Unregister the parser associated with the provided content-type
	     *
	     * @param contentType The content-type associated with the parser to unregister.
	     */
	    public static void unregisterParser(String contentType) {
	        RESPONSE_PARSER_REGISTRAR.unregisterParser(contentType);
	    }

	    /**
	     * Resets the {@link #baseURI}, {@link #basePath}, {@link #port}, {@link #authentication} and {@link #rootPath},
	     * {@link #filters(java.util.List)}, {@link #requestSpecification}, {@link #responseSpecification},
	     * {@link #urlEncodingEnabled}, {@link #config}, {@link #sessionId} and {@link #proxy} to their default values of {@value #DEFAULT_URI}, {@value #DEFAULT_PATH}, {@value #UNDEFINED_PORT},
	     * <code>no authentication</code>, &lt;empty string&gt;, <code>null</code>, <code>null</code>,
	     * &lt;empty list&gt;, <code>null</code>, <code>null</code>, <code>none</code>, <code>true</code>, <code>new RestAssuredConfig()</code>, <code>null</code> and <code>null</code>.
	     */
	    public static void reset() {
	        baseURI = DEFAULT_URI;
	        port = UNDEFINED_PORT;
	        basePath = DEFAULT_PATH;
	        authentication = DEFAULT_AUTH;
	        rootPath = DEFAULT_BODY_ROOT_PATH;
	        filters = new LinkedList<Filter>();
	        requestSpecification = null;
	        responseSpecification = null;
	        urlEncodingEnabled = DEFAULT_URL_ENCODING_ENABLED;
	        RESPONSE_PARSER_REGISTRAR = new ResponseParserRegistrar();
	        defaultParser = null;
	        config = new RestAssuredConfig();
	        sessionId = DEFAULT_SESSION_ID_VALUE;
	        proxy = null;
	    }

	    private static TestSpecificationImpl createTestSpecification() {
	        if (defaultParser != null) {
	            RESPONSE_PARSER_REGISTRAR.registerDefaultParser(defaultParser);
	        }
	        final ResponseParserRegistrar responseParserRegistrar = new ResponseParserRegistrar(RESPONSE_PARSER_REGISTRAR);
	        applySessionIdIfApplicable();
	        LogRepository logRepository = new LogRepository();
	        RestAssuredConfig restAssuredConfig = config();
	        return new TestSpecificationImpl(
	                new RequestSpecificationImpl(baseURI, port, basePath, authentication, filters,
	                        requestSpecification, urlEncodingEnabled, restAssuredConfig, logRepository, proxy),
	                new ResponseSpecificationImpl(rootPath, responseSpecification, responseParserRegistrar, restAssuredConfig, logRepository)
	        );
	    }

	    private static void applySessionIdIfApplicable() {
	        if (!StringUtils.equals(sessionId, DEFAULT_SESSION_ID_VALUE)) {
	            final RestAssuredConfig configToUse;
	            if (config == null) {
	                configToUse = new RestAssuredConfig();
	            } else {
	                configToUse = config;
	            }
	            config = configToUse.sessionConfig(configToUse.getSessionConfig().sessionIdValue(sessionId));
	        }
	    }

	    /**
	     * Use relaxed HTTP validation with protocol {@value #SSL}. This means that you'll trust all hosts regardless if the SSL certificate is invalid. By using this
	     * method you don't need to specify a keystore (see {@link #keyStore(String, String)} or trust store (see {@link #trustStore(java.security.KeyStore)}.
	     * <p>
	     * This is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().sslConfig(sslConfig().relaxedHTTPSValidation());
	     * </pre>
	     */
	    public static void useRelaxedHTTPSValidation() {
	        useRelaxedHTTPSValidation(SSL);
	    }

	    /**
	     * Use relaxed HTTP validation with a specific protocol. This means that you'll trust all hosts regardless if the SSL certificate is invalid. By using this
	     * method you don't need to specify a keystore (see {@link #keyStore(String, String)} or trust store (see {@link #trustStore(java.security.KeyStore)}.
	     * <p>
	     * This is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().sslConfig(sslConfig().relaxedHTTPSValidation(&lt;protocol&gt;));
	     * </pre>
	     *
	     * @param protocol The standard name of the requested protocol. See the SSLContext section in the <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SSLContext">Java Cryptography Architecture Standard Algorithm Name Documentation</a> for information about standard protocol names.
	     */
	    public static void useRelaxedHTTPSValidation(String protocol) {
	        config = RestAssured.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation(protocol));
	    }

	    /**
	     * Enable logging of both the request and the response if REST Assureds test validation fails with log detail equal to {@link LogDetail#ALL}.
	     * <p/>
	     * <p>
	     * This is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
	     * </pre>
	     */
	    public static void enableLoggingOfRequestAndResponseIfValidationFails() {
	        enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
	    }

	    /**
	     * Enable logging of both the request and the response if REST Assureds test validation fails with the specified log detail.
	     * <p/>
	     * <p>
	     * This is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails(logDetail));
	     * </pre>
	     *
	     * @param logDetail The log detail to show in the log
	     */
	    public static void enableLoggingOfRequestAndResponseIfValidationFails(LogDetail logDetail) {
	        LogConfig logConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(logDetail);
	        config = RestAssured.config().logConfig(logConfig);

	        // Update request specification if already defined otherwise it'll override the configs.
	        // Note that request spec also influence response spec when it comes to logging if validation fails due to the way filters work
	        if (requestSpecification != null && requestSpecification instanceof RequestSpecificationImpl) {
	            RestAssuredConfig restAssuredConfig = ((RequestSpecificationImpl) requestSpecification).getConfig();
	            if (restAssuredConfig == null) {
	                restAssuredConfig = config;
	            } else {
	                LogConfig logConfigForRequestSpec = restAssuredConfig.getLogConfig().enableLoggingOfRequestAndResponseIfValidationFails(logDetail);
	                restAssuredConfig = restAssuredConfig.logConfig(logConfigForRequestSpec);
	            }
	            requestSpecification.config(restAssuredConfig);
	        }
	    }

	    /**
	     * Apply a keystore for all requests
	     * <pre>
	     * given().keyStore("/truststore_javanet.jks", "test1234"). ..
	     * </pre>
	     * </p>
	     * <p>
	     * Note that this is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().sslConfig(sslConfig().keyStore(pathToJks, password));
	     * </pre>
	     *
	     * @param pathToJks The path to the JKS. REST Assured will first look in the classpath and if not found it will look for the JKS in the local file-system
	     * @param password  The store pass
	     */
	    public static void keyStore(String pathToJks, String password) {
	        Validate.notEmpty(password, "Password cannot be empty");
	        applyKeyStore(pathToJks, password);
	    }

	    /**
	     * The following documentation is taken from <a href="HTTP Builder">https://github.com/jgritman/httpbuilder/wiki/SSL</a>:
	     * <p>
	     * <h1>SSL Configuration</h1>
	     * <p/>
	     * SSL should, for the most part, "just work." There are a few situations where it is not completely intuitive. You can follow the example below, or see HttpClient's SSLSocketFactory documentation for more information.
	     * <p/>
	     * <h1>SSLPeerUnverifiedException</h1>
	     * <p/>
	     * If you can't connect to an SSL website, it is likely because the certificate chain is not trusted. This is an Apache HttpClient issue, but explained here for convenience. To correct the untrusted certificate, you need to import a certificate into an SSL truststore.
	     * <p/>
	     * First, export a certificate from the website using your browser. For example, if you go to https://dev.java.net in Firefox, you will probably get a warning in your browser. Choose "Add Exception," "Get Certificate," "View," "Details tab." Choose a certificate in the chain and export it as a PEM file. You can view the details of the exported certificate like so:
	     * <pre>
	     * $ keytool -printcert -file EquifaxSecureGlobaleBusinessCA-1.crt
	     * Owner: CN=Equifax Secure Global eBusiness CA-1, O=Equifax Secure Inc., C=US
	     * Issuer: CN=Equifax Secure Global eBusiness CA-1, O=Equifax Secure Inc., C=US
	     * Serial number: 1
	     * Valid from: Mon Jun 21 00:00:00 EDT 1999 until: Sun Jun 21 00:00:00 EDT 2020
	     * Certificate fingerprints:
	     * MD5:  8F:5D:77:06:27:C4:98:3C:5B:93:78:E7:D7:7D:9B:CC
	     * SHA1: 7E:78:4A:10:1C:82:65:CC:2D:E1:F1:6D:47:B4:40:CA:D9:0A:19:45
	     * Signature algorithm name: MD5withRSA
	     * Version: 3
	     * ....
	     * </pre>
	     * Now, import that into a Java keystore file:
	     * <pre>
	     * $ keytool -importcert -alias "equifax-ca" -file EquifaxSecureGlobaleBusinessCA-1.crt -keystore truststore_javanet.jks -storepass test1234
	     * Owner: CN=Equifax Secure Global eBusiness CA-1, O=Equifax Secure Inc., C=US
	     * Issuer: CN=Equifax Secure Global eBusiness CA-1, O=Equifax Secure Inc., C=US
	     * Serial number: 1
	     * Valid from: Mon Jun 21 00:00:00 EDT 1999 until: Sun Jun 21 00:00:00 EDT 2020
	     * Certificate fingerprints:
	     * MD5:  8F:5D:77:06:27:C4:98:3C:5B:93:78:E7:D7:7D:9B:CC
	     * SHA1: 7E:78:4A:10:1C:82:65:CC:2D:E1:F1:6D:47:B4:40:CA:D9:0A:19:45
	     * Signature algorithm name: MD5withRSA
	     * Version: 3
	     * ...
	     * Trust this certificate? [no]:  yes
	     * Certificate was added to keystore
	     * </pre>
	     * Now you want to use this truststore in your client:
	     * <pre>
	     * RestAssured.trustSture("/truststore_javanet.jks", "test1234");
	     * </pre>
	     * or
	     * <pre>
	     * given().trustStore("/truststore_javanet.jks", "test1234"). ..
	     * </pre>
	     * </p>
	     * <p>
	     * Note that this is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().sslConfig(sslConfig().trustStore(pathToJks, password));
	     * </pre>
	     *
	     * @param pathToJks The path to the JKS. REST Assured will first look in the classpath and if not found it will look for the JKS in the local file-system
	     * @param password  The store pass
	     */
	    public static void trustStore(String pathToJks, String password) {
	        Validate.notEmpty(password, "Password cannot be empty");
	        applyTrustStore(pathToJks, password);
	    }

	    /**
	     * Specify a trust store that'll be used for HTTPS requests. A trust store is a {@link java.security.KeyStore} that has been loaded with the password.
	     * If you wish that REST Assured loads the KeyStore store and applies the password (thus making it a trust store) please see some of the
	     * <code>keystore</code> methods such as {@link #keyStore(java.io.File, String)}.
	     *
	     * @param truststore A pre-loaded {@link java.security.KeyStore}.
	     * @see #keyStore(String, String)
	     */
	    public static void trustStore(KeyStore truststore) {
	        Validate.notNull(truststore, "Truststore cannot be null");
	        config = config().sslConfig(SSLConfig.sslConfig().trustStore(truststore));
	    }

	    /**
	     * Use a keystore located on the file-system. See {@link #keyStore(String, String)} for more details.
	     * * <p>
	     * Note that this is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().sslConfig(sslConfig().keyStore(pathToJks, password));
	     * </pre>
	     *
	     * @param pathToJks The path to JKS file on the file-system
	     * @param password  The password for the keystore
	     * @see #keyStore(String, String)
	     */
	    public static void keyStore(File pathToJks, String password) {
	        Validate.notNull(pathToJks, "Path to JKS on the file system cannot be null");
	        applyKeyStore(pathToJks, password);
	    }

	    /**
	     * Use a trust store located on the file-system. See {@link #trustStore(String, String)} for more details.
	     * * <p>
	     * Note that this is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().sslConfig(sslConfig().trustStore(pathToJks, password));
	     * </pre>
	     *
	     * @param pathToJks The path to JKS file on the file-system
	     * @param password  The password for the keystore
	     * @see #keyStore(String, String)
	     */
	    public static void trustStore(File pathToJks, String password) {
	        Validate.notNull(pathToJks, "Path to JKS on the file system cannot be null");
	        applyTrustStore(pathToJks, password);
	    }

	    /**
	     * Uses the user default keystore stored in @{user.home}/.keystore
	     * * <p>
	     * Note that this is just a shortcut for:
	     * </p>
	     * <pre>
	     * RestAssured.config = RestAssured.config().sslConfig(sslConfig().keyStore(password));
	     * </pre>
	     *
	     * @param password - Use null for no password
	     */
	    public static void keyStore(String password) {
	        applyKeyStore(null, password);
	    }

	    /**
	     * Instruct REST Assured to connect to a proxy on the specified host and port.
	     *
	     * @param host The hostname of the proxy to connect to (for example <code>127.0.0.1</code>)
	     * @param port The port of the proxy to connect to (for example <code>8888</code>)
	     */
	    public static void proxy(String host, int port) {
	        proxy(host(host).withPort(port));
	    }

	    /**
	     * Instruct REST Assured to connect to a proxy on the specified host on port <code>8888</code>.
	     *
	     * @param host The hostname of the proxy to connect to (for example <code>127.0.0.1</code>). Can also be a URI represented as a String.
	     * @see #proxy(String, int)
	     */
	    public static void proxy(String host) {
	        if (UriValidator.isUri(host)) {
	            try {
	                proxy(new URI(host));
	            } catch (URISyntaxException e) {
	                throw new RuntimeException("Internal error in REST Assured when constructing URI for Proxy.", e);
	            }
	        } else {
	            proxy(host(host));
	        }
	    }

	    /**
	     * Instruct REST Assured to connect to a proxy on the specified port on localhost.
	     *
	     * @param port The port of the proxy to connect to (for example <code>8888</code>)
	     * @see #proxy(String, int)
	     */
	    public static void proxy(int port) {
	        proxy(ProxySpecification.port(port));
	    }

	    /**
	     * Instruct REST Assured to connect to a proxy on the specified port on localhost with a specific scheme.
	     *
	     * @param host   The hostname of the proxy to connect to (for example <code>127.0.0.1</code>)
	     * @param port   The port of the proxy to connect to (for example <code>8888</code>)
	     * @param scheme The http scheme (http or https)
	     */
	    public static void proxy(String host, int port, String scheme) {
	        proxy(new ProxySpecification(host, port, scheme));
	    }

	    /**
	     * Instruct REST Assured to connect to a proxy using a URI.
	     *
	     * @param uri The URI of the proxy
	     */
	    public static void proxy(URI uri) {
	        if (uri == null) {
	            throw new IllegalArgumentException("Proxy URI cannot be null");
	        }
	        proxy(new ProxySpecification(uri.getHost(), uri.getPort(), uri.getScheme()));
	    }

	    /**
	     * Instruct REST Assured to connect to a proxy using a {@link ProxySpecification}.
	     *
	     * @param proxySpecification The proxy specification to use.
	     * @see RequestSpecification#proxy(ProxySpecification)
	     */
	    public static void proxy(ProxySpecification proxySpecification) {
	        RestAssured.proxy = proxySpecification;
	    }

	    private static void applyKeyStore(Object pathToJks, String password) {
	        RestAssuredConfig restAssuredConfig = config();
	        final SSLConfig updatedSSLConfig;
	        if (pathToJks instanceof File) {
	            updatedSSLConfig = restAssuredConfig.getSSLConfig().keyStore((File) pathToJks, password);
	        } else {
	            updatedSSLConfig = restAssuredConfig.getSSLConfig().keyStore((String) pathToJks, password);
	        }
	        config = config().sslConfig(updatedSSLConfig.allowAllHostnames()); // Allow all host names to be backward-compatible
	    }

	    private static void applyTrustStore(Object pathToJks, String password) {
	        RestAssuredConfig restAssuredConfig = config();
	        final SSLConfig updatedSSLConfig;
	        if (pathToJks instanceof File) {
	            updatedSSLConfig = restAssuredConfig.getSSLConfig().trustStore((File) pathToJks, password);
	        } else {
	            updatedSSLConfig = restAssuredConfig.getSSLConfig().trustStore((String) pathToJks, password);
	        }
	        config = config().sslConfig(updatedSSLConfig.allowAllHostnames()); // Allow all host names to be backward-compatible
	    }

	    /**
	     * @return The assigned config or a new config is no config is assigned
	     */
	    public static RestAssuredConfig config() {
	        return config == null ? new RestAssuredConfig() : config;
	    }
}
