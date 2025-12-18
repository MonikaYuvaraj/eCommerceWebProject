package com.Bstackdemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Bstackdemo.base.BasePage;

public class OrderConformationPage extends BasePage {

	public OrderConformationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@FindBy(xpath = "//legend[@id='confirmation-message']")
	public WebElement orderSuccessMessage;

	@FindBy(xpath = "//a[@id='downloadpdf']")
	public WebElement downloadReceiptBtn;

	@FindBy(xpath = "//div[@class='layout-main']//div[2]//strong")
	public WebElement orderNumberText;

	@FindBy(xpath = "//button[normalize-space()='Continue Shopping »']")
	public By continueBtn;

	public void waitForConfirmPage() {
		wait.until(ExpectedConditions.urlContains("/confirmation"));
	}

	public String getOrderSuccessMsg() {

		String successText = orderSuccessMessage.getText();
		System.out.println("Order placed successfully. Order Details: " + successText);
		return successText;
	}

	public String getOrderNumber() {

		String orderText = orderNumberText.getText();
		System.out.println("Order placed successfully. Order Details: " + orderText);
		return orderText;
	}

	public void downloadReceipt() {
		downloadReceiptBtn.click();
	}

	public void clickContinueBtn() {

		try {
			WebElement btn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Continue')]")));
			btn.click();

			wait.until(ExpectedConditions.urlToBe("https://bstackdemo.com/"));

		} catch (TimeoutException e) {
			// Button not present – safe to ignore
			System.out.println("Continue Shopping button not displayed, skipping.");
		}
	}

}
