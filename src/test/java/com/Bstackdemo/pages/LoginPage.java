package com.Bstackdemo.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Bstackdemo.base.BasePage;

public class LoginPage extends BasePage {

    public Actions actions;
    Logger log = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    @FindBy(xpath = "//button[@id='login-btn']")
    private WebElement btnLogin;

    @FindBy(xpath = "//div[@id='username']//div[contains(@class,'indicatorContainer')]")
    private WebElement usernameDropdown;

    @FindBy(xpath = "//div[@id='password']//div[contains(@class,'indicatorContainer')]")
    private WebElement passwordDropdown;

    // ================== Page Actions ==================

    public void selectUsername() {
        waitForVisibility(usernameDropdown);
        click(usernameDropdown);
        log.info("Entering username");

        actions.sendKeys(Keys.ENTER).perform();
    }

    public void selectPassword() {
        waitForVisibility(passwordDropdown);
        click(passwordDropdown);
        log.info("Entering password");

        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }

    public void clickLogin() {
        waitForVisibility(btnLogin);
        log.info("Clicking Login button");

        click(btnLogin);
    }

    // ================== Business Flow ==================

    public void login() {
        selectUsername();
        selectPassword();
        clickLogin();
    }
}
