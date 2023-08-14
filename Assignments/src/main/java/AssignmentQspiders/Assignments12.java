package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments12 {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\SeleniumContent\\selenium assigment\\SeleniumAssigmentt\\Driver\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("On script start" + new Date());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	// --------------------Write a script to create customer and delete in actitime
	// --------------------------------------
	@Test
	public void scriptOne() throws InterruptedException {

		driver.get("http://laptop-rdmatuke/login.do");

		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("pwd")).sendKeys("manager");

		driver.findElement(By.id("loginButton")).click();

		driver.findElement(By.xpath("//div[text()='Tasks']")).click();
		driver.findElement(By.xpath("//a[text()='Projects & Customers']")).click();
		driver.findElement(By.cssSelector("input[value='Create New Customer']")).click();

		driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("iuytre");

		driver.findElement(By.name("createCustomerSubmit")).click();

		List<WebElement> headerTxt = driver
				.findElements(By.xpath("//table[contains(@class,'active_customers_projects listTable')]//th"));

		int size = headerTxt.size();

		System.out.println("The size of header is :" + size);

		for (WebElement header : headerTxt) {
			System.out.println(header.getText());
		}

		List<WebElement> fotterTxt = driver.findElements(By.xpath(
				"//table[contains(@class,'active_customers_projects listTable')]//td[contains(@class,'customer-cell')]"));

		int size1 = headerTxt.size();

		for (WebElement fotter : fotterTxt) {
			System.out.println(fotter.getText());
		}

		System.out.println("The size of fotter is :" + size1);

		WebElement selectCustomerandProjectButton = driver
				.findElement(By.xpath("//td[contains(@class,'selectCustomer')]/input"));

		String verify = driver.findElement(By.xpath("//td[@valign='middle']//a")).getText();

		if (verify.contains("iuytre")) {

			selectCustomerandProjectButton.click();
			driver.findElement(By.xpath("//div[contains(@class,'listbuttonpane')]//input")).click();
			driver.findElement(By.xpath("//table[@id='contentTable']//input")).click();

		}

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
