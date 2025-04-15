package com.crm.ProxySource;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

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
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/accounts-proxy-source");

        WebElement select = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("account-proxy-sources_length")));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        Helpers.waitForSeconds(3);
        driver.findElement(By.cssSelector(".fa-table")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn[data-wizard='next']"))).click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fas.fa-trash.tw-text-red-600"))).click();

        Helpers.waitForSeconds(3);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();
        Helpers.waitForSeconds(3);

        Reporter.log("A fost ștearsă cu succes setarea" + "\n");

         List<WebElement> headers = driver.findElements(By.cssSelector(
                ".dataTables_scrollHeadInner > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(1) > th"));
        List<WebElement> firstRow = driver
                .findElements(By.cssSelector("#account-proxy-sources tbody tr:first-child td"));

        for (int i = 0; i < headers.size(); i++) {

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headers.get(i));

            String header = headers.get(i).getText().trim();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";

            if (!header.isEmpty()) {
                Reporter.log(header + " -> " + content);
            } else {
                Reporter.log("Header is empty for index ");
            }

        }
        Helpers.waitForSeconds(2);
        driver.quit();
    }

}