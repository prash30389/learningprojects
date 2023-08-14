package HandleTypeOfWebElements;

import java.io.File;
import java.io.IOException;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class HandleSSL {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//SSl certificates

		//Desired capabilities=
		//general chrome profile
		//DesiredCapabilities chf = DesiredCapabilities.firefox();
		DesiredCapabilities ch=DesiredCapabilities.chrome();
		//ch.acceptInsecureCerts();
		ch.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		ch.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		/*chf.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		chf.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);*/
		//Belows to your local browser
		ChromeOptions c= new ChromeOptions();
		c.merge(ch);
		//FirefoxOptions f = new FirefoxOptions();
		//f.merge(chf);
		System.setProperty("webdriver.chrome.driver", "./ExeFiles/chromedriver.exe");
		WebDriver driver = new ChromeDriver(c);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		//driver.manage().deleteCookieNamed("sessionkey");
		
		String url = "http://google.com";
		driver.get(url);
		
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtil.copyFile(src,new File("./screenshots/screenshot.png"));
	}

}
