package com.crm.SGCampaigns;

import java.util.List;
import java.time.Duration;

import com.Base.BaseTest;
import com.aventstack.extentreports.Status;
import com.utilities.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

@Listeners(com.utilities.TestListener.class)
public class DeleteTableSettingsTest extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        TestListener.getTest().log(Status.PASS,"Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-dashboard/sg-campaigns");

        WebElement select = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("sg-campaigns-list_length")));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "div.daterangepicker[style*=\"display: block\"] div.ranges li[data-range-key=\"All Time\"]")))
                .click();
        Helpers.waitForSeconds(2);
        driver.findElement(By.cssSelector(".fa-table")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn[data-wizard='next']"))).click();

        Helpers.waitForSeconds(2);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fas.fa-trash.swap-list-remove-item.tw-text-red-600[onclick=\"deleteDtColSetting(event)\"]"))).click();

        Helpers.waitForSeconds(2);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();
        Helpers.waitForSeconds(2);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();

        Helpers.waitForSeconds(2);

        TestListener.getTest().log(Status.PASS,"A fost ștearsă cu succes setarea" + "\n");

        Helpers.waitForSeconds(2);

        List<WebElement> headers = driver
                .findElements(By.cssSelector("#sg-campaigns-list_wrapper .table-striped.dataTable thead th"));
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#sg-campaigns-list tbody tr:first-child td"));

        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            TestListener.getTest().log(Status.INFO,header + " -> " + content);

        }
        Helpers.waitForSeconds(1);
    }

}