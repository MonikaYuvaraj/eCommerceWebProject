package com.Bstackdemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Bstackdemo.base.BasePage;

public class CartPage extends BasePage {
    Logger log = LoggerFactory.getLogger(LoginPage.class);

	public CartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("CartPage initialized");

	}

	@FindBy(xpath = "//div[@class='buy-btn']")
	public WebElement checkoutBTN;

	@FindBy(xpath = "//h2[text()='Shipping Address']")
	public By shippingAddressHeading;

	// Locator from your screenshot

	public boolean isCheckoutPageDisplayed() {

		// URL validation
		boolean urlCheck = wait.until(ExpectedConditions.urlContains("/checkout"));

		// UI validation
		boolean headingVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(shippingAddressHeading))
				.isDisplayed();

		return urlCheck && headingVisible;
	}

	public void clickCheckout() {

		checkoutBTN.click();
        log.info("checkout btn clicked");


	}

}
