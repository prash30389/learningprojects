package dataDriven;

import org.testng.annotations.DataProvider;

public class ConfigNumDataProviderDriven {

@DataProvider()
public Object[][] getdata()
	{
		return new Object[][]
		{{"1","Sheet"}};
	}
}
