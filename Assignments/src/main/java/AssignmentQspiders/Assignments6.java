package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments6 {

	WebDriver driver;
	// --------------------------------How to sroll to a particular web
	// element-----------------------------------------

	public static void scrollIntoViewByJS(WebElement element, WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	public static String getPageInnerTextByJS(WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String pageText = (String) js.executeScript("return document.documentElement.innerText");
		return pageText;
	}

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\SeleniumContent\\selenium assigment\\SeleniumAssigmentt\\Driver\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start:" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@Test
	public void scriptOne() {
		driver.get("https://www.naukri.com/mnjuser/homepage");
		driver.findElement(By.id("usernameField")).sendKeys("**********");
		driver.findElement(By.id("passwordField")).sendKeys("*******");
		driver.findElement(By.xpath("//button[text()='Login']")).click();

		// --------------------------------To print data below profile
		// performance-------------------------------------------

		List<WebElement> profilePerformanceContent = driver
				.findElements(By.xpath("(//div[contains(@class,'searchAppearances')]/div)[position()=2]/a//div"));

		WebElement elm1 = profilePerformanceContent.get(0);
		WebElement elm2 = profilePerformanceContent.get(1);

		System.out.println("the size of the list is:" + profilePerformanceContent.size());

		System.out.println(
				"The first element is: " + elm1.getText() + "----------" + "Second element is :" + elm2.getText());

		// ------------------------------To print all the job
		// alert------------------------------------------

		WebElement web = driver.findElement(By.xpath("(//div[contains(@class,'view-all right-align')]/a)[1]"));
		scrollIntoViewByJS(web, driver);
		web.click();

		String set = driver.getWindowHandle();

		driver.switchTo().window(set);

		System.out.println(driver.getTitle());

		List<WebElement> jobNameList = driver
				.findElements(By.xpath("//div[contains(@class,'srp_container fl multipleJobsApply')]/div//li/a"));

		int size = jobNameList.size();

		for (int i = 0; i < size; i++) {

			String str = jobNameList.get(i).getText();

			System.out.println(str);

		}
		driver.navigate().back();

		String set1 = driver.getWindowHandle();

		driver.switchTo().window(set1);

		System.out.println(driver.getTitle());

		JavascriptExecutor js = ((JavascriptExecutor) driver);

		js.executeScript("scroll(250,0)");

		// --------------Expanding update option and printing everthing inside and logout
		// it----------------------------

		driver.findElement(By.xpath("//div[text()='UPDATE PROFILE']")).click();

		String str = getPageInnerTextByJS(driver);

		System.out.println(str);

		WebElement web1 = driver.findElement(By.xpath("//div[text()='My Naukri']"));

		Actions act = new Actions(driver);
		act.moveToElement(web1).build().perform();

		WebElement web2 = driver.findElement(By.linkText("Logout"));
		act.moveToElement(web2).click().build().perform();

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
