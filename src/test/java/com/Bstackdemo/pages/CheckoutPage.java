package com.Bstackdemo.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Bstackdemo.base.BasePage;

public class CheckoutPage extends BasePage {

	public CheckoutPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// ===== Shipping Address Section =====

	@FindBy(id = "firstNameInput")
	public WebElement firstName;

	@FindBy(id = "lastNameInput")
	public WebElement lastName;

	@FindBy(id = "addressLine1Input")
	public WebElement addressLine1;

	@FindBy(id = "provinceInput")
	public WebElement state;

	@FindBy(id = "postCodeInput")
	public WebElement postalCode;

	// ===== Checkout Button =====

	@FindBy(id = "checkout-shipping-continue")
	public WebElement continueButton;

	// ===== Order Confirmation =====

	@FindBy(xpath = "//legend[@class='form-legend optimizedCheckout-headingSecondary']")
	public WebElement checkoutHeading;

	

	public void fillShippingDetails(String fName, String lName, String address, String stateName, String zip) {

		firstName.sendKeys(fName);
		lastName.sendKeys(lName);
		addressLine1.sendKeys(address);
		state.sendKeys(stateName);
		postalCode.sendKeys(zip);
	}

	public void placeOrder() {
		continueButton.click();
	}

	public boolean isCheckoutPageDisplayed() {
		return checkoutHeading.isDisplayed();
	}

	public boolean isPlaceOrderEnabled() {
		return continueButton.isEnabled();
	}

	

}
