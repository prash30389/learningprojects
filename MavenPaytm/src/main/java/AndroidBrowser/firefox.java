package AndroidBrowser;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class firefox {
	
	@Test
	public void startup()
	{
		WebDriver driver=BrowserFactory.LaunchBrowser("firefox");
		driver.get("http://google.com");
	}

}
