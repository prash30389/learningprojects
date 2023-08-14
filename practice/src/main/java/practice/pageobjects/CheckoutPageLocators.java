package practice.pageobjects;
//locator class is public static final which means it can be accessed by all and cannot be changed.

public class CheckoutPageLocators {
	public final static String CustomerID = "//input[@id='customer_id']";
	public final static String PolicyNumber = "//input[@id='policyno']";
	public final static String AgeonTransactionID = "//input[@id='aegonTransactionid']";
	public final static String OrderID = "//input[@id='order_id']";
	public final static String PayBtn = "//button[text()='Pay']";
	public final static String contactCode = "//input[@id='contact']";
	public final static String emailId = "//input[@id='email']";
	public final static String proceedBtn = "//*[@id='footer' and @role='button']";	//"//button[@id='footer']";
	public final static String userDetailPanel = "//button[@id='user-details']";
	public final static String cardDetailPanel = "//button[@method='card']";
	public final static String addNewCardBtn = "//div[@id='show-add-card']";
	public final static String cardNumber = "//input[@id='card_number']";
	public final static String cardExpiry = "//input[@id='card_expiry']";
	public final static String cardHolderName = "//input[@id='card_name']";
	public final static String cardCVV = "//input[@id='card_cvv']";
	public final static String PaymentBtn = "//*[@id='footer' and @role='button']";	//"//button[@id='footer']";
	public final static String payAmt = "//button[@id='footer']/span";
	public final static String successBtn = "//button[text()='Success']";
	public final static String failureBtn = "//button[text()='Failure']";
	public final static String selectSBI = "//div[@id='bank-item-SBIN']";
	public final static String selectHDFC = "//div[@id='bank-item-HDFC']";
	public final static String selectICIC = "//div[@id='bank-item-ICIC']";
	public final static String selectNetbanking = "//div[@id='emandate-options']//div[2]";
	public final static String labelBAN = "//label[text()='Bank Account Number']";
	public final static String textboxBAN = "//input[@id='nb-acc-no']";
	public final static String labelIFSC = "//label[text()='IFSC']";
	public final static String textboxIFSC = "//input[@id='nb-acc-ifsc']";
	public final static String labelAHN = "//label[text()='Account Holder Name']";
	public final static String textboxAHN = "//input[@id='nb-acc-name']";
	public final static String dropdownTOBA = "//select[@name='bank_account[account_type]']"; //Savings Account
	public final static String AuthenticateBtn= "//*[@id='footer' and @role='button']";
	
	//Renewal Policy Portal
	public final static String textboxPolicyNumber = "//input[@formcontrolname='policy_id']";
	public final static String textboxDOB = "//input[@formcontrolname='dob']";		
	public final static String submitBtn = "//*[@value='SUBMIT']";
	public final static String cbAllowPayment = "//label[@for='allow-for-payment']";
	public final static String paymentOptionNB = "(//div[@class='payment-mode-list'])[3]";
	public final static String cbTermsCond = "//label[@for='agree-declarations']";
	public final static String cbStandingInstructions = "//label[@for='standing-instructions']";
	public final static String proceedtopaymentBtn = "//button[text()='proceed to payment']";
}
