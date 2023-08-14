package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TravellerDetailsPage {
	@FindBy(id = "Adulttitle1")
	private WebElement title;

	@FindBy(id = "AdultfirstName1")
	private WebElement FirstName;

	@FindBy(id = "AdultmiddleName1")
	private WebElement MiddleName;
	
	@FindBy(id = "AdultlastName1")
	private WebElement LastName;

	@FindBy(id = "email")
	private WebElement email;

	@FindBy(id = "mobile")
	private WebElement mobile;

	@FindBy(id = "risk-trip")
	private WebElement noriskchkbtn;

	@FindBy(xpath = "//div[contains(@class,'flightDetails fl')]//div[contains(text(),'Proceed ')]")
	private WebElement proceedBtn;

	public TravellerDetailsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getTitle() {
		return title;
	}

	public WebElement getFirstName() {
		return FirstName;
	}

	public WebElement getLastName() {
		return LastName;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getMobile() {
		return mobile;
	}

	public WebElement getNoriskchkbtn() {
		return noriskchkbtn;
	}

	public WebElement getProceedBtn() {
		return proceedBtn;
	}

	public WebElement getMiddleName() {
		return MiddleName;
	}

}
