package POM.Encapsulation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumPagePOM 
{
@FindBy(linkText ="Downloads")
private WebElement DwnloadEle ;

@FindBy(linkText ="Projects")
private WebElement ProjectEle;

@FindBy(linkText ="Support")
private WebElement SupportEle ;

@FindBy(xpath="/nav[@id='navbar']//a[text()='blog']")
private WebElement BlogEle ;

public SeleniumPagePOM (WebDriver driver)
{
	PageFactory.initElements(driver,this);
}

public WebElement getDwnloadEle() {
	return DwnloadEle;
}

public WebElement getProjectEle() {
	return ProjectEle;
}

public WebElement getSupportEle() {
	return SupportEle;
}

public WebElement getBlogEle() {
	return BlogEle;
}


}