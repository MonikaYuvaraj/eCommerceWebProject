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
@Listeners(TestListener.class)
public class TC003_FavouritesTest  {

	public WebDriver driver;
	public HomePage homePage;
	public LoginPage loginPage;
	public FavouritesPage favPage;
	Logger log = LoggerFactory.getLogger(TC003_FavouritesTest.class);

	// Test data
	String favProduct1 = "iPhone 12";
	String favProduct2 = "iPhone 12 Pro Max";
	String removeProduct = "iPhone 11";

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		driver = DriverFactory.initDriver("chrome");
		DriverManager.setDriver(driver);
		driver.get("https://bstackdemo.com/");

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		favPage = new FavouritesPage(driver);
		log.info("TC003 setup Completed");

	}

	@Test(priority = 1, groups = { "regression", "favourites" }, description = "Add multiple products to favourites")
	public void addproductToFav() {
		log.info("Add multiple products to favourites");
		homePage.clickSignIn();
		loginPage.login();

		homePage.addFavModel("One Plus 8 Pro");
		homePage.addFavModel(favProduct1);
		homePage.addFavModel(favProduct2);
		homePage.addFavModel("Galaxy S20");
		homePage.addFavModel(removeProduct);

		homePage.openFavourites();

		Assert.assertTrue(favPage.isFavoriteProductDisplayed(favProduct2), "Product not added to Favorites page!");

		favPage.verifyAndPrintFavoriteProduct("Galaxy S20");
	}

	@Test(priority = 2, groups = { "regression",
			"favourites" }, dependsOnMethods = "addproductToFav", description = "Verify favourite products are displayed")
	public void verifyFavouriteProductDisplayed() {
		log.info("Verify favourite products are displayed");
		homePage.openFavourites();

		Assert.assertTrue(favPage.isFavoriteProductDisplayed("One Plus 8 Pro"),
				"Favourite product is NOT displayed on favourites page!");

		System.out.println("Favourite product displayed: " + "One Plus 8 Pro");
	}

	@Test(priority = 3, groups = { "regression",
			"favourites" }, dependsOnMethods = "verifyFavouriteProductDisplayed", description = "Remove product from favourites")
	public void removefavfromPage() {
		log.info("Remove product from favourites");
		favPage.removeFavmodel(removeProduct);

		Assert.assertFalse(favPage.isFavoriteProductDisplayed(removeProduct),
				"Product still visible in favorites after removal!");

		System.out.println("Removed from favorites: " + removeProduct);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("TC003 Completed");
		if (driver != null) {
            driver.quit();
            DriverManager.unload();
        }	}

}
