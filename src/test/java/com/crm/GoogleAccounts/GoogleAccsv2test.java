package com.crm.GoogleAccounts;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class GoogleAccsv2test extends BaseTest {
 private Login login;
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts-v2");

         driver.get("http://crm-dash/google-accounts-v2");
          Helpers.waitForSeconds(3);

            WebElement scrollBar = driver.findElement(By.cssSelector("revogr-scroll-virtual.horizontal.hydrated"));
            WebElement headerLocator = driver.findElement(By.cssSelector("div.rgHeaderCell div.header-content"));

            Set<String> foundHeaders = new HashSet<>();
            // Initial scroll position
            long initialScroll = (long) ((JavascriptExecutor) driver).executeScript(
                    "return arguments[0].scrollLeft;", scrollBar);

            boolean canScroll = true;
            while (canScroll) {
                // Get all the header texts
                List<WebElement> headers = headerLocator.findElements(By.tagName("div"));
                for (WebElement header : headers) {
                    String cleanHeader = header.getText().trim();
                    if (!cleanHeader.isEmpty() && !foundHeaders.contains(cleanHeader)) {
                        foundHeaders.add(cleanHeader);
                        System.out.println("Header găsit: " + cleanHeader);
                    }
                }

                // Scroll the page to the right
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollLeft += 300;", scrollBar);

                        Helpers.waitForSeconds(3);

                // Check the scroll position again
                long newScroll = (long) ((JavascriptExecutor) driver).executeScript(
                        "return arguments[0].scrollLeft;", scrollBar);

                if (newScroll == initialScroll) {
                    canScroll = false; // No more scrolling possible
                }

                initialScroll = newScroll; // Update scroll position
}

}

}