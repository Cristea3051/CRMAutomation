package com.crm.GoogleAccounts;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aventstack.extentreports.Status;
import com.resources.configfiles.SettingsHelper;
import com.utilities.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

@Listeners(com.utilities.TestListener.class)
public class CreateTableSettingsOtherGoogleTest extends BaseTest {
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
        TestListener.getTest().log(Status.PASS,"Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts-v2");
        Helpers.waitForSeconds(1);

        TestListener.getTest().log(Status.INFO,"Lista completă a coloanelor înainte de modificări:");
        List<String> beforeHeaders = printTableHeaders();
        for (String header : beforeHeaders) {
            TestListener.getTest().log(Status.INFO,header);
        }
        TestListener.getTest().log(Status.INFO,"Numărul total de coloane afișate: " + beforeHeaders.size());

        driver.findElement(By.cssSelector("button.tw-mr-1:nth-child(5)")).click();
        Helpers.waitForSeconds(1);

        driver.findElement(By.id("setting-name")).sendKeys("AutoTableSetting");
        Helpers.waitForSeconds(1);

        SettingsHelper settingsHelper = new SettingsHelper(driver);
        Helpers.waitForSeconds(1);

        String[] valuesToSelect = { "Account Domains", "MB Commentss", "Farmer Comments", "Backup Code",
                "Source Delivery Date", "Proxy Type", "License", "Created At", "Sync from date" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        settingsHelper.selectMultipleValuesByValue(new String[] { "Under Review Reason", "Under Review Date" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 10);
        Helpers.waitForSeconds(1);

        driver.findElement(By.id("save-modal-swap-list")).click();
        Helpers.waitForSeconds(1);
        TestListener.getTest().log(Status.PASS,"A trecut pe next wizard");

        driver.findElement(By.id("apply-swap-list-settings")).click();
        Helpers.waitForSeconds(1);

        TestListener.getTest().log(Status.PASS,"Lista coloanelor după modificări:");
        List<String> afterHeaders = printTableHeaders();
        for (String header : afterHeaders) {
            TestListener.getTest().log(Status.INFO,header);
        }
        TestListener.getTest().log(Status.INFO,"Numărul total de coloane afișate: " + afterHeaders.size());

        TestListener.getTest().log(Status.INFO,"Comparație înainte și după aplicarea setărilor:");
        for (String beforeHeader : beforeHeaders) {
            String result;
            if (afterHeaders.contains(beforeHeader)) {
                result = beforeHeader + " => " + beforeHeader;
            } else {
                result = beforeHeader + " => \"This column is hidden\"";
            }
            TestListener.getTest().log(Status.INFO,result);
        }
    }

    private List<String> printTableHeaders() {
        WebElement scrollBar = driver.findElement(By.cssSelector("revogr-scroll-virtual.horizontal.hydrated"));
        Set<String> foundHeaders = new HashSet<>();
        List<String> headerList = new ArrayList<>();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = 0;", scrollBar);
        Helpers.waitForSeconds(1);

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
            Helpers.waitForSeconds(1);

            Object scrollResult = ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollLeft;", scrollBar);
            double newScroll = ((Number) scrollResult).doubleValue();

            if (newScroll == initialScroll) {
                canScroll = false;
            }
            initialScroll = newScroll;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = 0;", scrollBar);
        Helpers.waitForSeconds(1);

        return headerList;
    }
}