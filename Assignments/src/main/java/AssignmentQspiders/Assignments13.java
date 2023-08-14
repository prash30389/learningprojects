package AssignmentQspiders;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyTestListener.class)
public class Assignments13 {

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

	// --------------------Write a script to Handle Accordion
	// widgets---------------------------
	@Test
	public void scriptOne() throws InterruptedException {

		driver.get("http://demo.automationtesting.in/Accordion.html");
		List<WebElement> panelHeading = driver.findElements(
				By.xpath("//div[contains(@class,'panel-group')]//div[contains(@class,'panel-heading')]//a"));
		int size = panelHeading.size();
		System.out.println("The size of panel heading is" + size);
		List<WebElement> panelBody = driver
				.findElements(By.xpath("//div[contains(@class,'panel-group')]//div[contains(@class,'panel-body')]"));
		int size1 = panelBody.size();
		
		
		System.out.println("The text of ToggleWidgets is :"  +panelBody.get(0).getText());
		System.out.println("--------------------------------------------------");
		System.out.println();

		System.out.println("The size of panel Body is" + size1);

		for (int i = 1; i < size; i++) {
			
			

			panelHeading.get(i).click();
			new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(panelBody.get(i)));
			String str = panelBody.get(i).getText();
			
			System.out.println("The text of ToggleWidgets is :"  +str);
			System.out.println("--------------------------------------------------");
			System.out.println();

		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
