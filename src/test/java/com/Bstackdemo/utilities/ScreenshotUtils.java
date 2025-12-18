package com.Bstackdemo.utilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

	public static String takeScreenshot(WebDriver driver, String testName) {

		if (driver == null) {
			System.out.println("❌ Driver is NULL. Screenshot not captured.");
			return null;
		}

		try {
			String folderPath = "F:/Eclipse/New Eclipse/com.Bstackdemo/screenshots/";
			Files.createDirectories(Paths.get(folderPath));

			String filePath = folderPath + testName + "_"  + ".png";

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(filePath);

			FileUtils.copyFile(src, dest);

			System.out.println("✅ Screenshot saved: " + filePath);
			return filePath;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
