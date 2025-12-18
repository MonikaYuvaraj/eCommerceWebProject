package com.Bstackdemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.Bstackdemo.base.BasePage;

public class OffersPage extends BasePage {
	public OffersPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this); // initialize @FindBy elements

	}

	@FindBy(css = "main")
	public By noOffersMessage; 


	public void waitForOffersPage() {
		wait.until(ExpectedConditions.urlContains("/offers"));
	}

	public boolean isNoOffersMessageDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated( noOffersMessage)).isDisplayed();
	}

	public String getNoOffersText() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated( noOffersMessage)).getText();
	}

	public boolean isOffersPageDisplayed() {
		return wait.until(ExpectedConditions.urlContains("/offers"));
	}
}
