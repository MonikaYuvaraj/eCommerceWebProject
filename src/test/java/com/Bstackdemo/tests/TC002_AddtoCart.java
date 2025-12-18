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
import com.Bstackdemo.pages.HomePage;
import com.Bstackdemo.pages.LoginPage;
@Listeners(TestListener.class)
public class TC002_AddtoCart  {

	public WebDriver driver;
	public HomePage homePage;
	public LoginPage loginPage;
	Logger log = LoggerFactory.getLogger(TC0001LoginTests.class);

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		// Initialize browser
		driver = DriverFactory.initDriver("chrome");
		DriverManager.setDriver(driver);
		driver.get("https://bstackdemo.com/");

		// Initialize page objects
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		log.info("TC0002 setup completed");

	}

	@Test(priority = 1, groups = { "smoke", "cart" }, description = "Add single product to cart after login")
	public void addtCartSingleProduct() {
		log.info("Add single product to cart after login");
		homePage.clickSignIn();
		loginPage.login();

		homePage.addToCartAnyPhone("iPhone XS");
		Assert.assertEquals(homePage.getCartCount(), 1, "Cart is not empty!");

	}

	@Test(priority = 2, groups = { "regression",
			"cart" }, dependsOnMethods = "addtCartSingleProduct", description = "Add multiple products to cart")
	public void addtCartMultipleProduct() {
		log.info("Add multiple products to cart");
		homePage.addToCartAnyPhone("iPhone XS");
		homePage.addToCartAnyPhone("iPhone 11");
		homePage.addToCartAnyPhone("iPhone 11 Pro");
		homePage.addToCartAnyPhone("iPhone 12");

		System.out.println("Cart Count = " + homePage.getCartCount());
		Assert.assertEquals(homePage.getCartCount(), 5, "Cart is not empty!");

	}

	@Test(priority = 3, groups = { "regression",
			"cart" }, dependsOnMethods = "addtCartMultipleProduct", description = "Remove products from cart")
	public void removeProductCart() throws InterruptedException {
		log.info("Remove products from cart");
		homePage.removeProductFromCart("iPhone XS");
		homePage.removeProductFromCart("iPhone 11");
		homePage.removeProductFromCart("iPhone 11 Pro");
		homePage.removeProductFromCart("iPhone 12");

		Assert.assertEquals(homePage.getCartCount(), 0);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("TC002 completed");
		if (driver != null) {
            driver.quit();
            DriverManager.unload();
        }
	}

}
