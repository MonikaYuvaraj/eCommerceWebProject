package com.Bstackdemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Bstackdemo.base.BasePage;

public class HomePage extends BasePage {
    Logger log = LoggerFactory.getLogger(HomePage.class);

	// Constructor
	public HomePage(WebDriver driver) {
		super(driver); // initialize BaseTest's driver
		PageFactory.initElements(driver, this); // initialize @FindBy elements
	}

	@FindBy(xpath = "//span[@id='signin']")
	public WebElement signIn;

	@FindBy(xpath = "//input[@placeholder='Search']")
	public WebElement searchBox;

	@FindBy(id = "orders")
	public WebElement ordersLink;

	@FindBy(id = "favourites")
	public WebElement favouritesLink;

	@FindBy(id = "offers")
	public WebElement offersLink;

	@FindBy(xpath = "//span[@class='bag__quantity']")
	public WebElement cartCount;

	@FindBy(css = ".shelf-container")
	private WebElement productsGrid;

	public void waitForHomePage() {
		wait.until(ExpectedConditions.urlToBe("https://bstackdemo.com/"));
	}

	// -------------------- Dynamic Locators --------------------

	public By brandBy(String brandName) {
		return By.xpath("//span[normalize-space()='" + brandName + "']");
	}

	public By brandproCountBy() {
		return By.xpath("//small[contains(@class,'products-found')]//span");
	}

	public By favIconBy(String productName) {

		// p[text()='iPhone 12 Mini']/ancestor::div[@class='shelf-item']//button

		// p[normalize-space()='iPhone 12
		// Mini']/ancestor::div[contains(@class,'shelf-item')]//button

		return By.xpath("//p[text()='" + productName + "']/ancestor::div[@class='shelf-item']//button");
	}

	// -------------------- Page Actions --------------------

	public void clickSignIn() {
        log.info("Clicking on Sign In button");
		wait.until(ExpectedConditions.elementToBeClickable(signIn)).click();
	}

	public void searchProduct(String productName) {
		wait.until(ExpectedConditions.visibilityOf(searchBox)).clear();
		searchBox.sendKeys(productName);
		log.info("product name entered");
	}

	public void addToCartAnyPhone(String modelName) {
		WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(addToCartBy(modelName)));
		addToCartBtn.click();
		log.info("add product to cart");
	}

	public By addToCartBy(String modelName) {
		return By.xpath(
				"//p[text()='" + modelName + "']" + "/ancestor::div[@class='shelf-item']//div[text()='Add to cart']");
	}

	public By deleteFromCartBy(String productName) {
		return By.xpath("//p[text()='" + productName + "']"
				+ "/ancestor::div[contains(@class,'float-cart')]//div[contains(@class,'del')]");
	}

	public void openCart() {
        log.info("Opening cart page");

		WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bag")));
		cart.click();
	}

	public int getCartCount() {
        log.info("get cart count");

		return Integer.parseInt(wait.until(ExpectedConditions.visibilityOf(cartCount)).getText());

	}

	public void removeProductFromCart(String productName) {
		WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(deleteFromCartBy(productName)));
		deleteBtn.click();
		log.info("remove pro from cart");
	}

	public void toggleBrand(String brandName) {
		By brand = By.xpath("//span[normalize-space()='" + brandName + "']");
		By products = By.xpath("//div[@class='shelf-item']");
		int oldCount = driver.findElements(products).size();
		WebElement brandElement = wait.until(ExpectedConditions.elementToBeClickable(brand));
		brandElement.click();
		log.info("click brand");
		wait.until(ExpectedConditions.not(ExpectedConditions.numberOfElementsToBe(products, oldCount)));
		
	}

	public int getBrandProductCount() {

		By products = By.xpath("//div[@class='shelf-item']");

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(products));
		log.info("get brand pro count");
		return driver.findElements(products).size();

	}

	public boolean isProductVisible(String productName) {

		By product = By.xpath("//p[normalize-space()='" + productName + "']");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(product)).isDisplayed();
	}

	public void addFavModel(String productName) {
		WebElement favBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(favIconBy(productName)));

		safeClick(favBtn);
	}

	public void removeFavModel(String productName) {
		WebElement favBtn = wait.until(ExpectedConditions.elementToBeClickable(favIconBy(productName)));

		if (favBtn.getAttribute("class").contains("active")) {
			favBtn.click();
		}
	}

	public void openOrders() {
        log.info("Opening Orders page");

		wait.until(ExpectedConditions.elementToBeClickable(ordersLink)).click();
	}

	public void openFavourites() {
        log.info("Opening fav page");

		wait.until(ExpectedConditions.elementToBeClickable(favouritesLink)).click();
	}

	public void openOffers() {
        log.info("Opening offers page");

		wait.until(ExpectedConditions.elementToBeClickable(offersLink)).click();
	}

	// Verify Sign In is visible
	public boolean isSignInVisible() {
		return wait.until(ExpectedConditions.visibilityOf(signIn)).isDisplayed();
	}

}
