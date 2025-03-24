package com.crm.SGCampaigns;

import java.time.Duration;
import java.util.List;

import com.Base.BaseTest;
import com.aventstack.extentreports.Status;
import com.resources.Helpers;
import com.utilities.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.utilities.Filters;
import com.utilities.Login;

@Listeners(com.utilities.TestListener.class)
public class SGFilterTest extends BaseTest {
    private Login login;
    private WebDriverWait wait;
   private Filters filters;
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        filters = new Filters(driver);
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        TestListener.getTest().log(Status.INFO,"Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-dashboard/sg-campaigns");

        WebElement select = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("sg-campaigns-list_length")));
Select rows = new Select(select);
rows.selectByIndex(0);

wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "div.daterangepicker[style*=\"display: block\"] div.ranges li[data-range-key=\"All Time\"]")))
                .click();

        try {
            filters.applyRandomFilters();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver
                .findElements(By.cssSelector("#sg-campaigns-list_wrapper .table-striped.dataTable thead th"));
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#sg-campaigns-list tbody tr:first-child td"));
        Helpers.waitForSeconds(3);
        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            TestListener.getTest().log(Status.INFO,header + " -> " + content);
        }
    }
}