package AndroidAPP;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;





import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class AppTest
{

	@Test
	public void test() throws InterruptedException, IOException {
	
		File appDir = new File("src");
		   
		    File app = new File(appDir, "net.one97.paytm-3.apk");

		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME,MobilePlatform.ANDROID);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "100");
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		AndroidDriver driver=new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),cap );
		Thread.sleep(15000L);
		try
		{
			driver.swipe(25, 40, 30, 30, 5000);
		}
		catch(Exception e)
		{
			
		}
	
		driver.findElementById("net.one97.paytm:id/edit_no").sendKeys("98454");
		Thread.sleep(3000L);
		
		driver.findElementById("net.one97.paytm:id/text_mob_op").click();
		driver.scrollTo("Vodafone").click();
		driver.scrollTo("Kerala").click();
		
driver.findElementById("net.one97.paytm:id/lyt_edit_amount").sendKeys("500");
driver.findElementById("net.one97.paytm:id/radio_postpaid").click();
Thread.sleep(3000L);
System.out.println(driver.findElementByClassName("android.widget.Button").getText());
driver.findElementByClassName("android.widget.CheckBox").click();
System.out.println(driver.findElementByClassName("android.widget.Button").getText());
driver.findElementByClassName("android.widget.Button").click();
System.out.println(driver.findElementById("android:id/message").getText());



//checkboxes radiobuttons testboxes buttons scolalble Menu,child menu, Image Banners popuu txt





	
	
	
	}


	}

