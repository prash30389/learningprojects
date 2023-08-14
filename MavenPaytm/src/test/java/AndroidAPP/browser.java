package AndroidAPP;

import java.io.IOException;

import org.testng.annotations.BeforeSuite;



public class browser {
	
	@BeforeSuite
	public void browse() throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec("cmd /c start C:\\appiumstart.bat");
		Thread.sleep(7000L);
	}

}
