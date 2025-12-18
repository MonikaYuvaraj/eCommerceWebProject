package com.Bstackdemo.tests;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.Bstackdemo.base.DriverFactory;
import com.Bstackdemo.driver.DriverManager;
import com.Bstackdemo.listerners.TestListener;
import com.Bstackdemo.pages.CartPage;
import com.Bstackdemo.pages.CheckoutPage;
import com.Bstackdemo.pages.FavouritesPage;
import com.Bstackdemo.pages.HomePage;
import com.Bstackdemo.pages.LoginPage;
import com.Bstackdemo.pages.OrderConformationPage;
import com.Bstackdemo.pages.OrdersPage;
@Listeners(TestListener.class)
public class TC006_PurchaseFlow{

	public WebDriver driver;
	public HomePage homePage;
	public LoginPage loginPage;
	public FavouritesPage favPage;
	public OrdersPage orderPage;
	public CartPage cartPage;
	public CheckoutPage checkoutPage;
	public OrderConformationPage orderconPage;
	Logger log = LoggerFactory.getLogger(TC003_FavouritesTest.class);

	@BeforeClass(alwaysRun = true)
	public void setUp() {

		driver = DriverFactory.initDriver("chrome");
		DriverManager.setDriver(driver);
		driver.get("https://bstackdemo.com/");

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		favPage = new FavouritesPage(driver);
		cartPage = new CartPage(driver);
		orderPage = new OrdersPage(driver);
		checkoutPage = new CheckoutPage(driver);
		orderconPage = new OrderConformationPage(driver);

		// Login once for all purchase flows
		homePage.clickSignIn();
		loginPage.login();

		log.info("TC006 setup Completed");

	}

	@Test(priority = 1, groups = { "smoke", "purchase" }, description = "Purchase a single product successfully")
	public void purchaseSingleProduct() {

		log.info("Purchase a single product successfully");
		homePage.addToCartAnyPhone("iPhone 12");
		Assert.assertEquals(homePage.getCartCount(), 1);

		cartPage.clickCheckout();
		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

		checkoutPage.fillShippingDetails("Monika", "M", "12 MG Road", "Karnataka", "560001");
		Assert.assertTrue(checkoutPage.isPlaceOrderEnabled());
		checkoutPage.placeOrder();

		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Order confirmation not displayed");
		String orderNo = orderconPage.getOrderNumber();
		System.out.println("orderNo " + orderNo);
		orderconPage.downloadReceipt();
	}

	// ---------------- Purchase Multiple Products ----------------
	@Test(priority = 2, groups = { "regression", "purchase" }, description = "Purchase multiple products successfully")
	public void purchaseMultipleProducts() {
		log.info("Purchase multiple products successfully");

		orderconPage.clickContinueBtn();

		homePage.addToCartAnyPhone("iPhone 11");
		homePage.addToCartAnyPhone("iPhone XS");

		Assert.assertEquals(homePage.getCartCount(), 2, "Cart count mismatch for multiple products");

		cartPage.clickCheckout();
		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

		checkoutPage.fillShippingDetails("Monika", "M", "12 MG Road", "Karnataka", "560001");
		Assert.assertTrue(checkoutPage.isPlaceOrderEnabled());
		checkoutPage.placeOrder();

		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Order confirmation not displayed");
		String orderNo = orderconPage.getOrderNumber();
		System.out.println("orderNo " + orderNo);
		orderconPage.downloadReceipt();

	}

	// ---------------- Purchase After Brand Filter ----------------
	@Test(priority = 3, groups = { "regression",
			"purchase" }, description = "Purchase product after applying brand filter")
	public void purchaseAfterFilter() {
		log.info("Purchase product after applying brand filter");

		orderconPage.clickContinueBtn();

		homePage.toggleBrand("Apple");

		homePage.addToCartAnyPhone("iPhone 12 Mini");

		Assert.assertTrue(homePage.getCartCount() > 0, "Product not added after brand filter");

		cartPage.clickCheckout();
		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

		checkoutPage.fillShippingDetails("Monika", "M", "12 MG Road", "Karnataka", "560001");
		Assert.assertTrue(checkoutPage.isPlaceOrderEnabled());
		checkoutPage.placeOrder();

		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Order confirmation not displayed");
		String orderNo = orderconPage.getOrderNumber();
		System.out.println("orderNo " + orderNo);
		orderconPage.downloadReceipt();

	}

	// ---------------- Purchase From Favorites ----------------
	@Test(priority = 4, groups = { "regression", "purchase" }, description = "Purchase product from favourites")
	public void purchaseFromFavorites() {
		log.info("Purchase product from favourites");

		orderconPage.waitForConfirmPage();

		orderconPage.clickContinueBtn();

		driver.navigate().refresh();
		homePage.waitForHomePage();

		homePage.addFavModel("Galaxy S20");
		homePage.addFavModel("One Plus 7");
		homePage.addFavModel("Pixel 2");

		homePage.openFavourites();
		favPage.waitForFavouritesPage();

		Assert.assertTrue(favPage.getFavCount() > 0, "Product not added from favorites");

		favPage.addToCartPhone("Galaxy S20");
		favPage.addToCartPhone("Pixel 2");
		Assert.assertTrue(homePage.getCartCount() > 0, "Product not added from favorites");

		cartPage.clickCheckout();
		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

		checkoutPage.fillShippingDetails("Monika", "M", "12 MG Road", "Karnataka", "560001");
		Assert.assertTrue(checkoutPage.isPlaceOrderEnabled());
		checkoutPage.placeOrder();

		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Order confirmation not displayed");
		String orderNo = orderconPage.getOrderNumber();
		System.out.println("orderNo " + orderNo);
		orderconPage.downloadReceipt();

	}

	@Test(priority = 5, groups = { "regression", "search" }, description = "Search a product and complete purchase")
	public void searchAndOrderProduct() {
		log.info("Search a product and complete purchase");

		orderconPage.clickContinueBtn();
		driver.navigate().refresh();

		homePage.waitForHomePage();

		homePage.searchProduct("Galaxy S20");

		homePage.addToCartAnyPhone("Galaxy S20");

		Assert.assertEquals(homePage.getCartCount(), 1, "Cart count mismatch");

		cartPage.clickCheckout();
		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Checkout page not loaded");

		checkoutPage.fillShippingDetails("Monika", "M", "12 MG Road", "Karnataka", "560001");

		checkoutPage.placeOrder();

		String orderNo = orderconPage.getOrderNumber();
		System.out.println("Order placed successfully. Order No: " + orderNo);

		Assert.assertFalse(orderNo.isEmpty(), "Order number not generated");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("TC006 completed");
		if (driver != null) {
            driver.quit();
            DriverManager.unload();
        }
	}

}
