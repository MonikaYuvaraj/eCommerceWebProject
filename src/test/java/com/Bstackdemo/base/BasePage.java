package com.Bstackdemo.base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.Bstackdemo.utilities.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class BasePage {
	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	public Select select;
	public Actions actions;
	public Logger log;
	public ExtentTest test;
	public static ExtentReports extent;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		this.actions = new Actions(driver);
		log = LoggerFactory.getLogger(this.getClass());
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ centralized wait
	}

	/* ================= WAIT METHODS ================= */

	public WebElement waitForVisibility(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement waitForClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement waitForPresence(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/* ================= COMMON ACTIONS ================= */

	public void click(WebElement element) {
		waitForClickable(element).click();
	}

	public void type(WebElement element, String text) {
		waitForVisibility(element).clear();
		element.sendKeys(text);
	}



	public String getText(WebElement element) {
		return waitForVisibility(element).getText();
	}

	/* ================= JS ACTIONS ================= */

	public void jsClick(WebElement element) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	/* ================= WINDOW HANDLING ================= */

	public void switchToChildWindow() {
		String parent = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parent)) {
				driver.switchTo().window(handle);
			}
		}
	}

	public void scrollToElement(WebElement favIcon) {
		js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", favIcon);
	}

	public void safeClick(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.elementToBeClickable(element));

		// Scroll to center
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);

		// Small wait for animation
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}

		// JS click (avoids interception)
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	/* ================= SCREENSHOT ================= */

	public String captureScreenshot(String testName) {

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = "screenshots/" + testName + ".png";

		try {
			FileUtils.copyFile(src, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	@BeforeSuite
	public void startReport() {
		extent = ExtentReportManager.getReport();
	}

	@AfterSuite
	public void endReport() {
		extent.flush();
	}

}
