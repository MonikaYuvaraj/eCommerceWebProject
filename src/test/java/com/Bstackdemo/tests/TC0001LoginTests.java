package com.Bstackdemo.tests;

import org.openqa.selenium.By;
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
public class TC0001LoginTests {
	public WebDriver driver;
	public HomePage homePage;
	public LoginPage loginPage;
	Logger log = LoggerFactory.getLogger(TC0001LoginTests.class);

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {

		driver = DriverFactory.initDriver("chrome");

        DriverManager.setDriver(driver);
		driver.get("https://bstackdemo.com/");

		homePage = new HomePage(driver); // ✅ must be after driver is ready
		loginPage = new LoginPage(driver);

	}

	@Test(priority = 1, groups = { "smoke",
			"regression" }, description = "Verify user can login with valid credentials")
	public void login_with_valid_credentials() {
		log.info("Executing login test");

		homePage.clickSignIn();
		loginPage.login();

		// with wait
		Assert.assertTrue(homePage.waitForPresence(By.xpath("//span[text()='Logout']")).isDisplayed(), "Login failed!");
		//ScreenshotUtils.takeScreenshot(driver, "Executing login test");
		// without wait
		// Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Logout']")).isDisplayed(),
		// "Login failed!");
	}

	@Test(priority = 2, groups = {
			"regression" }, dependsOnMethods = "login_with_valid_credentials", description = "Verify user can logout successfully")
	public void logoutFunctionality() {
		log.info("Executing logout test");

		homePage.clickSignIn();
		Assert.assertTrue(homePage.waitForVisibility(homePage.signIn).isDisplayed(), "Logout Failed");

	}

	@Test(priority = 3, groups = {
			"regression" }, dependsOnMethods = "login_with_valid_credentials", description = "Verify restricted pages cannot be accessed without login")
	public void accesswithoutLogin() throws InterruptedException {
		log.info("Verify restricted pages cannot be accessed without login");

		homePage.openOrders();

		// Optional validation:
		Assert.assertTrue(homePage.waitForPresence(By.xpath("//button[@id='login-btn']")).isDisplayed(),
				"Failed: Becausse of logout");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("TC0001 completed");
		 if (driver != null) {
	            driver.quit();
	            DriverManager.unload();
	        }
	}

}
