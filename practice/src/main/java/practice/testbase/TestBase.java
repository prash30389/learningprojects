package practice.testbase;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	public static WebDriverWait wait = null;
	public static WebElement el = null;
	public static WebDriver w;
	public static Properties prop;
	public static RequestSpecification httpRequest;
	public static Response response;
	public static  SoftAssert st;
	public static String pno;
	public String str;
	public static String taskId;
	public static String queueName;
	public static String driverPathExe = "/chromedriver.exe";
	public static String driverPath = "/chromedriver";
	public static String projectPath = System.getProperty("user.dir");

	public static void init() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/OR.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePropertiesFile(String key, String value) {
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/config.properties");
			Properties props = new Properties();
			props.load(fis);
			fis.close();
			System.out.println("path is :"+System.getProperty("user.dir") + File.separator + "config.properties");
			FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + File.separator + "config.properties");
			props.setProperty(key, value);
			props.store(fos, null);
			fos.close();
		} catch (IOException e) {
		    e.printStackTrace();
		} 
	}

	public static void restAssured(String endpoint) {
		RestAssured.baseURI = prop.getProperty(endpoint);
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
	}

	public static String getToken() throws Exception {
		HttpResponse<String> response1 = Unirest.post("https://auth.qa-aegonlife.com/oauth2/token")
				.header("content-type", "application/x-www-form-urlencoded")
				.header("Authorization",
						"Basic N2VjbnFndDRwYzRsYjQyYXFzbGxvZHNrdG06MWk2OHVoNTg1dnJnYm9hbm5zNDBhbG40c2FqZGsyYm92Y3ZsbDd2NzhwYzFnaWYycWpoYQ==")
				.header("Content-Type", "application/x-www-form-urlencoded").field("grant_type", "client_credentials")
				.asString();

		IssuanceQueue.waitSec(1);
		org.json.JSONObject js = new org.json.JSONObject(response1.getBody());
		String access_token = (String) js.get("access_token");
		return access_token;
	}
	
	public static String adrider_getToken() throws Exception {
		HttpResponse<String> response = Unirest.post("https://auth.qa-aegonlife.com/oauth2/token")
				  .header("Content-Type", "application/x-www-form-urlencoded")
				  .header("Authorization", "Basic MXZ2bmw2M2c4am9vMHQ3aXQ3c2N2NWxkNmo6b3BhMnIxOWk1aG5qMXNoZzZrMWl2dWR2Z21uNGZuaXYyYTNnZWNvcWcxbWtyNDQ5MGZu")
				  .header("Content-Type", "application/x-www-form-urlencoded")
				  .field("grant_type", "client_credentials")
				  .asString();
		IssuanceQueue.waitSec(1);
		org.json.JSONObject js = new org.json.JSONObject(response.getBody());
		String access_token = (String) js.get("access_token");
		return access_token;
	}

	public static void setup() {
		String os = prop.getProperty("os");
		if (os.equalsIgnoreCase("linux")) {
			System.setProperty("webdriver.chrome.driver", projectPath + driverPath);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--start-maximized");
			options.addArguments("--headless");
			w = new ChromeDriver(options);
			w.get("file://" + projectPath + "/checkout.html");
			w.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else if (os.equalsIgnoreCase("window")) {
			System.setProperty("webdriver.chrome.driver", projectPath + driverPathExe);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--window-size=1366,768");
			options.addArguments("--start-maximized");
			options.addArguments("--headless");
	        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
	        w = new ChromeDriver(options);
	       	//w.manage().window().maximize();
	       	w.get(projectPath + "/checkout.html");
	       	w.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	
	public static void switchToiFrame() {
		try {
			int size = w.findElements(By.tagName("iframe")).size();
			//System.out.println("size=>"+size);
			w.switchTo().frame(size-1);
			System.out.println("Switched to frame.");
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void enterValueInTextBox(String locator, String value) {
		wait = new WebDriverWait(w, 2);
		el = w.findElement(By.xpath(locator));
		wait.until(ExpectedConditions.elementToBeClickable(el)); 
		el.clear();
		el.sendKeys(value);
	}
	
	public static void clickButton(String locator) {
		wait = new WebDriverWait(w, 2);
		el = w.findElement(By.xpath(locator));
		wait.until(ExpectedConditions.elementToBeClickable(el));
		el.click();
	}
	
	public static String getText(String locator) {
		el = w.findElement(By.xpath(locator));
		String strText = el.getText();
		return strText;	
	}
	
	public static String currencyFormat(double amount ) {
		DecimalFormat df = null;
		try {
			df = new DecimalFormat("###,###");
			//System.out.println("PAY â‚¹ "+df.format(amount));
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return df.format(amount);
	}
	
	public static String getparentwindowHandle() {
		String parentwinHandle = w.getWindowHandle();
		//System.out.println("Parent window handle name"+ parentwinHandle);
		return parentwinHandle;
	}
	
	public static void switchtochildWindow() {
		try {
			for(String childwinHandle : w.getWindowHandles()) {
				//System.out.println(childwinHandle);
				w.switchTo().window(childwinHandle);
			}
			System.out.println("Switched to Child window.");
		} catch (NoSuchWindowException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void switchbacktoparentWindow(String parentwinHandle) {
		try {
			w.switchTo().window(parentwinHandle);
			System.out.println("Switched back to Parent window.");
		} catch (NoSuchWindowException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void handleWindowPopup() {
		// creating instance of Robot class
		Robot rb;
		try {
			rb = new Robot();
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(5000);
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void selectValueFromDropDown(String locator, String value) {
		wait = new WebDriverWait(w, 2);
		el = w.findElement(By.xpath(locator));
		wait.until(ExpectedConditions.elementToBeClickable(el)); 
		Select dropdown = new Select(el);  
		dropdown.selectByVisibleText(value);
	}
	
	public static void openURL(String url) {
		String os = prop.getProperty("os");
		if (os.equalsIgnoreCase("linux")) {
			System.setProperty("webdriver.chrome.driver", projectPath + driverPath);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--start-maximized");
			options.addArguments("--headless");
			w = new ChromeDriver(options);
			w.get(url);
			w.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else if (os.equalsIgnoreCase("window")) {
			System.setProperty("webdriver.chrome.driver", projectPath + driverPathExe);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions options = new ChromeOptions();
	        //options.addArguments("headless");
	        options.addArguments("--window-size=1366, 768");
	        options.addArguments("--start-maximized");
			options.addArguments("--headless");
	        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
	        w = new ChromeDriver(options);
	       	//w.manage().window().maximize();
	       	w.get(url);
	       	w.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	
	public static void staticWait(int sec){
		long timeSec = sec * 1000;
		try {
			Thread.sleep(timeSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void tearDown() {
		try {
			w.close();
			System.out.println("Closing chrome browser");
			w.quit();
			System.out.println("Quit chrome driver");			
		} catch (UnhandledAlertException e) {
			w.quit();		
			System.out.println("Quit chrome driver");
		}
	}
}