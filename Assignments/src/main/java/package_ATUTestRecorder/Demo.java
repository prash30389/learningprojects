package package_ATUTestRecorder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import UtilityClasses.BrowserFactory;
import atu.testrecorder.ATUTestRecorder;

public class Demo {
	static WebDriver driver;
	

	public static void main(String[] args) throws Exception {
	 ATUTestRecorder recorder;
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		recorder = new ATUTestRecorder("E:/Recordings/", "Script_Video_" + dateFormat.format(date), false);
		recorder.start();
		driver = BrowserFactory.LaunchBrowser("chrome");
		driver.quit();
		recorder.stop();
		;
	}
}
