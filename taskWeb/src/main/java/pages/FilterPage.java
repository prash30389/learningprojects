package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FilterPage {

	@FindBy(xpath = "(//div[@class='quicks']//ul//i)[1]")
	private WebElement sortPrice;

	@FindBy(css = "(//div[@class='quicks']//ul//i)[2]")
	private WebElement sortPrice2;

	@FindBy(xpath = "//input[@id='DELBOM20200807ESG:SG8711SG284:0radioBtn']")
	private WebElement chkBtn1;

	@FindBy(xpath = "//input[@id='BOMDEL20200816EUK:UK825UK834radioBtn']")
	private WebElement chkBtn2;

	@FindBy(css = "input[value='BOOK']")
	private WebElement bookBtn;

	public FilterPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getSortPrice() {
		return sortPrice;
	}

	public WebElement getSortPrice2() {
		return sortPrice2;
	}

	public WebElement getChkBtn1() {
		return chkBtn1;
	}

	public WebElement getChkBtn2() {
		return chkBtn2;
	}

	public WebElement getBookBtn() {
		return bookBtn;
	}

}
