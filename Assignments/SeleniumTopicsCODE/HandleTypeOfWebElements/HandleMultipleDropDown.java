package HandleTypeOfWebElements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class HandleMultipleDropDown {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "./ExeFiles/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("file:///C:/Users/prashant/Desktop/Automation Files/NSSW22.html");

		// How to find total how many option present inside the dropdown & print
		// all the options Text ???

		WebElement DrpDwn = driver.findElement(By.name("Courses"));
		Select Sel = new Select(DrpDwn);
		List<WebElement> AllOptions = Sel.getOptions(); // getOptions()
		int Count = AllOptions.size();
		System.out.println("Total Number of Options : " + Count);
		for (int i = 0; i < Count; i++) {
			WebElement option = AllOptions.get(i);
			String OptionText = option.getText();
			System.out.println(OptionText);
		}

		// WAS to select and deselect all the option in multiple select dropdown
		// ???

		for (int i = 0; i < Count; i++) {
			Sel.selectByIndex(i);
		}
		for (int j = Count - 1; j >= 0; j--) {
			Sel.deselectByIndex(j);
		}
		// or
		Sel.deselectAll();
	}
}
