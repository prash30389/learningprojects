package HandleTypeOfWebElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class HandleSingleDropDown { // We can use <Select> and <option> HTML
									// tags for locate webelement in dropdown

	public static void VerifyDropDown(WebElement Drpdwn) {
		Select sel = new Select(Drpdwn);
		boolean flag = sel.isMultiple(); // ismultiple : boolean
		if (flag) // true
		{
			System.out.println("mutiple Dropdown");
		} else {
			System.out.println("single dropdown");
		}
	}

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "./ExeFiles/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("file:///C:/Users/prashant/Desktop/Automation Files/NSSW22.html");

		// to select an option in single select Dropdown List ??
		WebElement Drpdwn = driver.findElement(By.name("Country"));
		Select sel = new Select(Drpdwn);
		sel.selectByIndex(5);
		sel.selectByValue("6");
		sel.selectByVisibleText("India");

		// How to verify that the dropdown list is multiple or single ??
		// call VerifyDropDown() method
		WebElement drpdwn1 = driver.findElement(By.name("Country"));
		VerifyDropDown(drpdwn1);
		WebElement drpdwn2 = driver.findElement(By.name("Courses"));
		VerifyDropDown(drpdwn2);
	}

}
