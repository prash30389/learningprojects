package practice.testbase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

import practice.utility.PerformPayment;
import practice.utility.ProposalRequest;
import practice.utility.ProposalRequest_grace;


import io.restassured.RestAssured;

public class PageObj extends TestBase {

	@FindBy(xpath = "(//input[@id='signInFormUsername'])[2]")
	public WebElement username;

	@FindBy(xpath = "(//input[@name='password'])[2]")
	public WebElement password;

	@FindBy(xpath = "(//input[@name='signInSubmitButton'])[2]")
	public WebElement loginBtn;

	@FindBy(css = "input[type=text]")
	public WebElement enterPolicyNumber;

	// @FindBy(xpath = "//a[contains(text(),'Submit')]")
	@FindBy(xpath = "//button[contains(text(),'Search')]")
	public WebElement clickSubmit;

	@FindBy(xpath = "//div[@id='navbarMenu']/div[2]/a/span/i")
	public WebElement logoutBtn;

	/* Objects to validate against policy numbers */

	@FindBy(id = "policy-number")
	public WebElement PolicyNumber;

	// Initializing the Page Objects:
	public PageObj() {
		PageFactory.initElements(w, this);
	}

	public void initDriver() {
		PageFactory.initElements(w, this);
	}

	public void openBrowser_ops_console() {
		TestBase.init();
		String projectPath = System.getProperty("user.dir");
		String os = prop.getProperty("os");
		String url_ops = prop.getProperty("url_ops");
		if(os.equalsIgnoreCase("linux")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			try {
				System.out.println("Trying to get Token:");
				System.out.println("Driver Path:"+ projectPath + "/chromedriver");
				System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
				System.setProperty("webdriver.chrome.silentOutput", "true");
				chromeOptions.addArguments("--window-size=1920,1080");
				chromeOptions.addArguments("--start-maximized");
				chromeOptions.addArguments("--headless");
				w = new ChromeDriver(chromeOptions);
				w.manage().window().maximize();
				w.get(url_ops);
			} catch (Exception e) {
				System.out.println("Exception occured while getting token:"+e);
				e.printStackTrace();
			}
		} else if (os.equalsIgnoreCase("window")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", projectPath + "\\chromedriver.exe");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			chromeOptions.addArguments("--window-size=1366,768");
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--headless");
			w = new ChromeDriver(chromeOptions);
			w.manage().window().maximize();
			w.get(url_ops);
		}
	}
	
	public static String mockgracePolicy() throws Exception {
		String status = "DRAFT";
		SoftAssert st = new SoftAssert();
		do {
			try {
				IssuanceQueue.mockgraceProposal(ProposalRequest_grace.graceCode());
				IssuanceQueue.waitSec(4);
				System.out.println("Status     : " + IssuanceQueue.response.getStatusCode());
				System.out.println("Response   : " + IssuanceQueue.response.getBody().asString());
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				System.out.println("");
				pno = ProposalRequest_grace.pno();
				IssuanceQueue.waitSec(20);
				ProposalRequest_grace.actual_payment(pno);
				IssuanceQueue.waitSec(8);
				// IssuanceQueue.verifyStatus(pno);
				status = IssuanceQueue.getStatus(pno);
			} catch (Exception e) {
				System.out.println("Something went wrong !!!");
			}
		} while (!status.equalsIgnoreCase("INFORCE"));
		policyNo = pno;
		return pno;
	}
	
	public static String mockgracePolicy_claim() throws Exception {
		String status = "DRAFT";
		SoftAssert st = new SoftAssert();
		do {
			try {
				IssuanceQueue.mockgraceProposal(ProposalRequest_grace.graceClaimPolicy());
				IssuanceQueue.waitSec(4);
				System.out.println("Status     : " + IssuanceQueue.response.getStatusCode());
				System.out.println("Response   : " + IssuanceQueue.response.getBody().asString());
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				System.out.println("");
				pno = ProposalRequest_grace.pno();
				IssuanceQueue.waitSec(20);
				ProposalRequest_grace.actual_payment(pno);
				IssuanceQueue.waitSec(8);
				// IssuanceQueue.verifyStatus(pno);
				status = IssuanceQueue.getStatus(pno);
			} catch (Exception e) {
				System.out.println("Something went wrong !!!");
			}
		} while (!status.equalsIgnoreCase("INFORCE"));
		policyNo = pno;
		return pno;
	}
	
	public static String mockBackdatedPolicyStd() throws Exception {
		String status = "DRAFT";
		SoftAssert st = new SoftAssert();
		do {
			try {
				TestBase.init();
				RestAssured.baseURI = prop.getProperty("proposal");
				httpRequest = RestAssured.given();
				httpRequest.header("Content-Type", "application/json");
				httpRequest.body(ProposalRequest_Backdate.BackdatedCode());
				response = httpRequest.post("/mockproposals/submission?isSubstandard=standard");
				Thread.sleep(2000);
				System.out.println("Policy Number : " + ProposalRequest_Backdate.pno());
				IssuanceQueue.waitSec(4);
				System.out.println("Status     : " + IssuanceQueue.response.getStatusCode());
				System.out.println("Response   : " + IssuanceQueue.response.getBody().asString());
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				System.out.println("");
				pno = ProposalRequest_Backdate.pno();
				IssuanceQueue.waitSec(20);
				ProposalRequest_Backdate.actual_payment(pno);
				IssuanceQueue.waitSec(8);
				// IssuanceQueue.verifyStatus(pno);
				status = IssuanceQueue.getStatus(pno);
			} catch (Exception e) {
				System.out.println("Something went wrong !!!");
			}
		} while (!status.equalsIgnoreCase("INFORCE"));
		policyNo = pno;
		return pno;
	}

	public void openBrowser_universe() {
		TestBase.init();
		String projectPath = System.getProperty("user.dir");
		String os = prop.getProperty("os");
		String url = prop.getProperty("url");
		if (os.equalsIgnoreCase("linux")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--window-size=1920,1080");
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--headless");
			System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			w = new ChromeDriver(chromeOptions);
			w.manage().window().maximize();
			w.get(url);
		} else if (os.equalsIgnoreCase("window")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", projectPath + "\\chromedriver.exe");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			chromeOptions.addArguments("--window-size=1920,1080");
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--headless");
			w = new ChromeDriver(chromeOptions);
			w.manage().window().maximize();
			w.get(url);
		}
	}

	public void login(String un, String pwd) throws Exception {
		username.sendKeys(un);
		password.sendKeys(pwd);
		loginBtn.click();
		Thread.sleep(4000);
		w.navigate().refresh();
		Thread.sleep(4000);
	}

	public void logout() throws Exception {
		Thread.sleep(2000);
		w.quit();
	}

	public static String getItemFromLocalStorage() {
		JavascriptExecutor js = ((JavascriptExecutor) TestBase.w);
		return (String) js.executeScript(String.format("return window.localStorage.access_token;"));
	}

	static String policyNo;

	public static String mockPolicy() throws Exception {
		String status = "DRAFT";
		SoftAssert st = new SoftAssert();
		do {
			try {
				IssuanceQueue.mockProposal(ProposalRequest.Code());
				IssuanceQueue.waitSec(4);
				System.out.println("Status     : " + IssuanceQueue.response.getStatusCode());
				System.out.println("Response   : " + IssuanceQueue.response.getBody().asString());
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				System.out.println("");
				pno = ProposalRequest.pno();
				IssuanceQueue.waitSec(40);
				PerformPayment.actual_payment(pno);
				IssuanceQueue.waitSec(8);
				// IssuanceQueue.verifyStatus(pno);
				status = IssuanceQueue.getStatus(pno);
			} catch (Exception e) {
				System.out.println("Something went wrong !!!");
			}
		} while (!status.equalsIgnoreCase("INFORCE"));
		policyNo = pno;
		return pno;
	}
	
	public static String mockPolicyFreelook() throws Exception {
		String status = "DRAFT";
		int i = 0;
		SoftAssert st = new SoftAssert();
		do {
			try {
				if (i == 3)
					break;
				RestAssured.baseURI = prop.getProperty("proposal");
				httpRequest = RestAssured.given();
				httpRequest.header("Content-Type", "application/json");
				httpRequest.body(ProposalRequest.Code());
				response = httpRequest.post("/mockproposals/submission?isSubstandard=substandard");
				IssuanceQueue.waitSec(2);
				i++;
				System.out.println("Policy Number : " + ProposalRequest.pno());
				System.out.println("Status     : " + IssuanceQueue.response.getStatusCode());
				System.out.println("Response   : " + IssuanceQueue.response.getBody().asString());
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				System.out.println("");
				pno = ProposalRequest.pno();
				IssuanceQueue.waitSec(40);
				PerformPayment.actual_payment(pno);
				IssuanceQueue.waitSec(12);
				// IssuanceQueue.verifyStatus(pno);
				status = IssuanceQueue.getStatus(pno);
			} catch (Exception e) {
				System.out.println("Something went wrong !!!");
			}
		} while (!status.equalsIgnoreCase("INFORCE"));
		policyNo = pno;
		return pno;
	}
	
	public static String mockPolicySurrender() throws Exception {
		String status = "DRAFT";
		int i = 0;
		SoftAssert st = new SoftAssert();
		do {
			try {
				if (i == 3)
					break;
				RestAssured.baseURI = prop.getProperty("proposal");
				httpRequest = RestAssured.given();
				httpRequest.header("Content-Type", "application/json");
				httpRequest.body(ProposalRequest.surrenderCode());
				response = httpRequest.post("/mockproposals/submission?isSubstandard=substandard");
				IssuanceQueue.waitSec(2);
				i++;
				System.out.println("Policy Number : " + ProposalRequest.pno());
				System.out.println("Status     : " + IssuanceQueue.response.getStatusCode());
				System.out.println("Response   : " + IssuanceQueue.response.getBody().asString());
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				System.out.println("");
				pno = ProposalRequest.pno();
				IssuanceQueue.waitSec(40);
				PerformPayment.actual_payment(pno);
				IssuanceQueue.waitSec(12);
				// IssuanceQueue.verifyStatus(pno);
				status = IssuanceQueue.getStatus(pno);
			} catch (Exception e) {
				System.out.println("Something went wrong !!!");
			}
		} while (!status.equalsIgnoreCase("INFORCE"));
		policyNo = pno;
		return pno;
	}
	
	public static String mockBackdatedPolicy() throws Exception {
		String status = "DRAFT";
		int i = 0;
		SoftAssert st = new SoftAssert();
		do {
			try {
				if (i == 3)
					break;
				IssuanceQueue.mockProposal(ProposalRequest_Backdate.BackdatedCode());
				IssuanceQueue.waitSec(4);
				i++;
				System.out.println("Status     : " + IssuanceQueue.response.getStatusCode());
				System.out.println("Response   : " + IssuanceQueue.response.getBody().asString());
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				System.out.println("");
				pno = ProposalRequest_Backdate.pno();
				IssuanceQueue.waitSec(20);
				ProposalRequest_Backdate.actual_payment(pno);
				IssuanceQueue.waitSec(8);
				// IssuanceQueue.verifyStatus(pno);
				status = IssuanceQueue.getStatus(pno);
			} catch (Exception e) {
				System.out.println("Something went wrong !!!");
			}
		} while (!status.equalsIgnoreCase("INFORCE"));
		policyNo = pno;
		return pno;
	}

	
	
	//For Claim
	public static String mockPolicy(String sumAssured) throws Exception {
		String status = "DRAFT";
		SoftAssert st = new SoftAssert();
		do {
			try {
				IssuanceQueue.mockProposal(ProposalRequest.Code(sumAssured));
				IssuanceQueue.waitSec(4);

				System.out.println("Status     : " + IssuanceQueue.response.getStatusCode());
				System.out.println("Response   : " + IssuanceQueue.response.getBody().asString());
				st.assertEquals(IssuanceQueue.response.getStatusCode(), 200);
				System.out.println("");
				pno = ProposalRequest.pno();
				IssuanceQueue.waitSec(40);

				PerformPayment.actual_payment(pno);
				IssuanceQueue.waitSec(8);
				// IssuanceQueue.verifyStatus(pno);
				status = IssuanceQueue.getStatus(pno);
			} catch (Exception e) {
				System.out.println("Something went wrong !!!");
			}
		} while (!status.equalsIgnoreCase("INFORCE"));
		policyNo = pno;
		return pno;
	}

	public static String pnoMock() {
		String p = policyNo;
		return p;
	}
}
