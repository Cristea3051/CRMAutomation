package com.crm;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class test extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        super.driver.get("http://crm-dash/google-accounts-v2");

        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver.findElements(By.cssSelector(
                "div[role='columnheader']"));


        for (WebElement webElement : headers) {
            // Scroll până la elementul curent din header
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);

            String header = webElement.getText().trim();

            if (!header.isEmpty()) {
                Reporter.log(header);
            } else {
                Reporter.log("Header is empty for index ");
            }
        }
    }
}
