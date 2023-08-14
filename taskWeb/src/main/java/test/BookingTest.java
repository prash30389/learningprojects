package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import features.homePageFeatures;
import generic.BrowserFactory;

public class BookingTest {

	@Test
	public void flightBookingTest()
	{
		WebDriver driver = BrowserFactory.launchbrowser("chrome");
		driver.get("https://www.goibibo.com/");
		homePageFeatures hpf = new homePageFeatures(driver);
		hpf.flightBooking();
		
		
	}

}
