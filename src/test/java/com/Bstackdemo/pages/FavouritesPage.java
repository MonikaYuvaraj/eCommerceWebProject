package com.Bstackdemo.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Bstackdemo.base.BasePage;

public class FavouritesPage extends BasePage {

	public FavouritesPage(WebDriver driver) {
		super(driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this); // initialize @FindBy elements
	}

	@FindBy(xpath = "//input[@placeholder='Search']")
	public WebElement searchBox;

	@FindBy(css = "main")
	public By favouritesHeading;

	@FindBy(css = ".shelf-item")
	public List<WebElement> favcount;

	public void waitForFavouritesPage() {
		wait.until(ExpectedConditions.urlContains("/favourites"));
	}

	public boolean isFavoriteProductDisplayed(String productName) {
		By favProduct = By.xpath("//p[text()='" + productName + "']");
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(favProduct));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	// Verify product and print model name
	public boolean verifyAndPrintFavoriteProduct(String productName) {

		List<WebElement> products = driver.findElements(By.xpath("//p[text()='" + productName + "']"));

		if (!products.isEmpty()) {
			System.out.println("Favorite product found: " + products.get(0).getText());
			return true;
		} else {
			System.out.println("Favorite product NOT found: " + productName);
			return false;
		}
	}

	public void removeFavmodel(String modelName) {

		WebElement selectFavmodelbtn = driver
				.findElement(By.xpath("//p[text()='" + modelName + "']/ancestor::div[@class='shelf-item']//button"));

		selectFavmodelbtn.click();
	}

	public void addToCartPhone(String modelName) {

		WebElement addToCartBtn = driver.findElement(By.xpath(
				"//p[text()='" + modelName + "']/ancestor::div[@class='shelf-item']//div[text()='Add to cart']"));

		addToCartBtn.click();
	}

	public int getFavCount() {
		wait.until(ExpectedConditions.visibilityOfAllElements(favcount));
		return favcount.size();
	}

	public void searchProductfromFav(String productName) {
		searchBox.clear();
		searchBox.sendKeys(productName);
	}

	public boolean isFavouritesPageDisplayed() {
		// TODO Auto-generated method stub
		return 		wait.until(ExpectedConditions.urlContains("/favourites"));
	}

}
