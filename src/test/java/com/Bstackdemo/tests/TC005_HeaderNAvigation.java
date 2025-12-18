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
import com.Bstackdemo.pages.FavouritesPage;
import com.Bstackdemo.pages.HomePage;
import com.Bstackdemo.pages.LoginPage;
import com.Bstackdemo.pages.OffersPage;
import com.Bstackdemo.pages.OrdersPage;
@Listeners(TestListener.class)
public class TC005_HeaderNAvigation {

	public WebDriver driver;
	public HomePage homePage;
	public LoginPage loginPage;
	public FavouritesPage favPage;
	public OffersPage offPage;
	public OrdersPage orderPage;
	Logger log = LoggerFactory.getLogger(TC003_FavouritesTest.class);

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		driver = DriverFactory.initDriver("chrome");
		DriverManager.setDriver(driver);
		driver.get("https://bstackdemo.com/");

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		offPage = new OffersPage(driver);
		orderPage = new OrdersPage(driver);
		favPage = new FavouritesPage(driver);
		
		log.info("TC005 setup Completed");


	}

	@Test(priority = 1, groups = { "smoke", "navigation" }, description = "Verify header navigation links after login")
	public void testHeaderNavigation() {
		log.info("Verify header navigation links after login");
		// Login once for all purchase flows
		homePage.clickSignIn();
		loginPage.login();

		HomePage homePage = new HomePage(driver);
		OrdersPage ordersPage = new OrdersPage(driver);
		OffersPage offersPage = new OffersPage(driver);
		FavouritesPage favPage = new FavouritesPage(driver);

		// Verify SignIn button
		Assert.assertTrue(homePage.isSignInVisible(), "Sign In not visible");

		// Navigate to Orders page
		homePage.openOrders();
		Assert.assertTrue(ordersPage.isOrdersPageDisplayed(), "Orders page not displayed");

		// Navigate to Offers page
		homePage.openOffers();
		Assert.assertTrue(offersPage.isOffersPageDisplayed(), "Offers page not displayed");

		// Navigate to Favourites page
		homePage.openFavourites();
		Assert.assertTrue(favPage.isFavouritesPageDisplayed(), "Favourites page not displayed");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("TC005 completed");
		if (driver != null) {
            driver.quit();
            DriverManager.unload();
        }	}
}
