package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class homePage {
	@FindBy(id="roundTrip")
	private WebElement round;
	
	@FindBy(id="gosuggest_inputSrc")
	private WebElement FromBtn;
	
	@FindBy(id="gosuggest_inputDest")
	private WebElement DestBtn;
	
	@FindBy(id="fare_20200809")
	private WebElement Depdate;
	
	@FindBy(id="fare_20200816")
	private WebElement Returndate;
	
	@FindBy(id="gi_search_btn")
	private WebElement searchBtn;
	
	public homePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public WebElement getRound() {
		return round;
	}

	public WebElement getFromBtn() {
		return FromBtn;
	}

	public WebElement getDestBtn() {
		return DestBtn;
	}

	public WebElement getDepdate() {
		return Depdate;
	}

	public WebElement getReturndate() {
		return Returndate;
	}

	public WebElement getSearchBtn() {
		return searchBtn;
	}
	
	
}
