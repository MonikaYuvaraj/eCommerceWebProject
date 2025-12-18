package com.Bstackdemo.listerners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.Bstackdemo.driver.DriverManager;
import com.Bstackdemo.utilities.ExtentReportManager;
import com.Bstackdemo.utilities.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestListener implements ITestListener {

	ExtentReports extent = ExtentReportManager.getReport();
	ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		test.set(extent.createTest(result.getMethod().getMethodName()));
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, "Test Passed");
		captureScreenshot(result, "PASS");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, result.getThrowable());
		captureScreenshot(result, "FAIL");
	}

	private void captureScreenshot(ITestResult result, String status) {
		try {
			WebDriver driver = DriverManager.getDriver();

			if (driver == null) {
				test.get().log(Status.WARNING, "Driver is NULL. Screenshot not captured");
				return;
			}

			String path = ScreenshotUtils.takeScreenshot(driver, result.getName() + "_" + status);

			if (path != null) {
				test.get().addScreenCaptureFromPath(path);
			}
		} catch (Exception e) {
			test.get().log(Status.WARNING, "Screenshot failed: " + e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
