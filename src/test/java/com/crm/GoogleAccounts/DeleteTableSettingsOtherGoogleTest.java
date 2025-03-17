package com.crm.GoogleAccounts;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeleteTableSettingsOtherGoogleTest extends BaseTest {

    private Login login;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts-v2");
        Helpers.waitForSeconds(3);

        Reporter.log("Lista completă a coloanelor înainte de modificări:");
        List<String> beforeHeaders = printTableHeaders();
        for (String header : beforeHeaders) {
            Reporter.log(header);
        }
        Reporter.log("Numărul total de coloane afișate: " + beforeHeaders.size());

        driver.findElement(By.cssSelector("button.tw-mr-1:nth-child(5)")).click();
        Helpers.waitForSeconds(3);

        driver.findElement(By.id("save-modal-swap-list")).click();
        Helpers.waitForSeconds(3);
        Reporter.log("A trecut pe next wizard");

        driver.findElement(By.cssSelector("i.fa-trash")).click();
        Helpers.waitForSeconds(3);
        driver.findElement(By.cssSelector("button.swal2-confirm")).click();
        Helpers.waitForSeconds(3);
        driver.findElement(By.id("apply-swap-list-settings")).click();

        Reporter.log("Lista coloanelor după modificări:");
        List<String> afterHeaders = printTableHeaders();
        for (String header : afterHeaders) {
            Reporter.log(header);
        }
        Reporter.log("Numărul total de coloane afișate: " + afterHeaders.size());
    }

    private List<String> printTableHeaders() {
        WebElement scrollBar = driver.findElement(By.cssSelector("revogr-scroll-virtual.horizontal.hydrated"));
        Set<String> foundHeaders = new HashSet<>();
        List<String> headerList = new ArrayList<>(); // Păstrăm ordinea

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = 0;", scrollBar);
        Helpers.waitForSeconds(3);

        double initialScroll = 0.0;
        boolean canScroll = true;

        while (canScroll) {
            List<WebElement> headers = driver.findElements(By.cssSelector("div.rgHeaderCell div.header-content"));
            for (WebElement header : headers) {
                String cleanHeader = header.getText().trim();
                if (header.isDisplayed() && !cleanHeader.isEmpty() && !foundHeaders.contains(cleanHeader)) {
                    foundHeaders.add(cleanHeader);
                    headerList.add(cleanHeader);
                }
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft += 300;", scrollBar);
            Helpers.waitForSeconds(3);

            double newScroll = (double) ((JavascriptExecutor) driver).executeScript(
                    "return arguments[0].scrollLeft;", scrollBar);

            if (newScroll == initialScroll) {
                canScroll = false;
            }
            initialScroll = newScroll;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = 0;", scrollBar);
        Helpers.waitForSeconds(3);

        return headerList;
    }
}