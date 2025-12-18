package com.Bstackdemo.utilities;

import java.io.File;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager implements ITestListener {

	private static ExtentReports extent;
	private static ExtentTest test;

	public static ExtentReports getReport() {

		 if (extent == null) {

	            String reportDir = System.getProperty("user.dir") + "/reports/";
	            new File(reportDir).mkdirs();

	            ExtentSparkReporter spark =
	                    new ExtentSparkReporter(reportDir + "ExtentReport.html");

	            spark.config().setReportName("Automation Test Report");
	            spark.config().setDocumentTitle("Bstack Demo Results");

	            extent = new ExtentReports();
	            extent.attachReporter(spark);
	        }

	        return extent;
	    
	}

	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
	}

	public void onTestFailure(ITestResult result) {
		test.log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
		// For UI-based test failure
		String screenshotPath = "path/to/screenshot.png";
		extentTest.get().addScreenCaptureFromPath(screenshotPath);

		test.log(Status.FAIL, result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {
		test.log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
	}

	public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}
