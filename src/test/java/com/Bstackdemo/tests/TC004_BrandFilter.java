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
public class TC004_BrandFilter{

	public WebDriver driver;
	public HomePage homePage;
	public LoginPage loginPage;
	public FavouritesPage favPage;
	Logger log = LoggerFactory.getLogger(TC003_FavouritesTest.class);

	// Test data
	String brand1 = "Apple";
	String brand2 = "Samsung";
	String brand3 = "Google";
	String brand4 = "OnePlus";

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		driver = DriverFactory.initDriver("chrome");
		DriverManager.setDriver(driver);
		driver.get("https://bstackdemo.com/");

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		log.info("TC004 setup Completed");

	}

	@Test(priority = 1, groups = { "smoke", "filter" }, description = "Filter products by a single brand")
	public void selectsingleBrand() {
		log.info("Filter products by a single brand");
		homePage.clickSignIn();
		loginPage.login();

		homePage.toggleBrand(brand3);

		int Count = homePage.getBrandProductCount();
		System.out.println("Products after brand filter: " + Count);

		Assert.assertEquals(homePage.isProductVisible("Pixel 4"), true);

	}

	@Test(priority = 2, groups = { "regression",
			"filter" }, dependsOnMethods = "selectsingleBrand", description = "Unselect applied brand filter")
	public void unselectsingleBrand() {
		log.info("Unselect applied brand filter");
		int Count = homePage.getBrandProductCount();
		System.out.println("Products brand filter: " + Count);

		homePage.toggleBrand(brand3);

		int afterCount = homePage.getBrandProductCount();
		System.out.println("Products after brand filter: " + afterCount);

		Assert.assertEquals(homePage.isProductVisible("iPhone 11"), true);

	}

	@Test(priority = 3, groups = { "regression", "filter" }, description = "Filter products using multiple brands")
	public void selectmultipleBrand() {
		log.info("Filter products using multiple brands");
		homePage.toggleBrand(brand4);
		homePage.toggleBrand(brand2);
		homePage.toggleBrand(brand1);

		int Count = homePage.getBrandProductCount();
		System.out.println("Products after brand filter: " + Count);

		Assert.assertEquals(homePage.isProductVisible("One Plus 6T"), true);
		Assert.assertEquals(homePage.isProductVisible("iPhone XS"), true);
		Assert.assertEquals(homePage.isProductVisible("Galaxy Note 20"), true);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("TC004 Completed");
		if (driver != null) {
            driver.quit();
            DriverManager.unload();
        }
	}

}
