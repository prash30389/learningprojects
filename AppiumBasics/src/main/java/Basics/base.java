package Basics;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;


public class base {

	public static AndroidDriver<AndroidElement> capabilities() throws MalformedURLException
	 {
		AndroidDriver<AndroidElement>  driver;
		File appDir = new File("apkfiles");
		File app = new File(appDir, "ApiDemos-debug.apk.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"10");  
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability(MobileCapabilityType.UDID,"a41d9d2a");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
		capabilities.setCapability(MobileCapabilityType.NO_RESET,true);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,"25");
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver<AndroidElement>(url,capabilities);
		return driver;
	}
	public static void main(String[] args) throws MalformedURLException {
		base.capabilities();
	}
}
