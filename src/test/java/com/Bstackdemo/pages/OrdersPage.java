package com.Bstackdemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.Bstackdemo.base.BasePage;

public class OrdersPage extends BasePage {
	public OrdersPage(WebDriver driver) {
		super(driver);
	}
	@FindBy(css = "main")
	public WebElement ordersHeading; 


	public void waitForOrdersPage() {
		wait.until(ExpectedConditions.urlContains("/orders"));
	}


	public boolean isOrdersPageDisplayed() {
		
		return wait.until(ExpectedConditions.urlContains("/orders"));
	}
	
	
	
	
}
