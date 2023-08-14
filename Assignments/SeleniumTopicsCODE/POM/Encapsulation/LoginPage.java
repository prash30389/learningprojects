package POM.Encapsulation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	@FindBy(name = "username")
	private WebElement UnTxtBx;

	@FindBy(name = "pwd")
	private WebElement PwdTxtBx;

	@FindBy(id = "loginButton")
	private WebElement loginBtn;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getUnTxtBx() {
		return UnTxtBx;
	}

	public WebElement getPwdTxtBx() {
		return PwdTxtBx;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}
}
