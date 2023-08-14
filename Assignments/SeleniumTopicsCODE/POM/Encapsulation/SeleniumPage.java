package POM.Encapsulation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumPage {
	private WebElement DwnloadEle ;	
	private WebElement ProjectEle;
	private WebElement SupportEle;
	private WebElement BlogEle;
	
	public SeleniumPage(WebDriver driver)
	{
		DwnloadEle = driver.findElement(By.linkText("Download"));
		ProjectEle = driver.findElement(By.linkText("projects"));
		SupportEle = driver.findElement(By.linkText("support"));
		BlogEle = driver.findElement(By.xpath("/nav[@id='navbar']//a[text()='blog']"));
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
