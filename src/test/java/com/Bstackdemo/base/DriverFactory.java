package com.Bstackdemo.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;

public class DriverFactory {

	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	public static WebDriver driver = null;
	public static WebDriver initDriver(String browser) {
	

		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new RuntimeException("Invalid browser name: " + browser);
		}

		tlDriver.set(driver);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return getDriver();
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public static void quitDriver() {
		if (getDriver() != null) {
			getDriver().quit();
			tlDriver.remove();
		}
	}

}
