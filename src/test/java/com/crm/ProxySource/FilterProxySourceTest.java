package com.crm.ProxySource;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Filters;
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

public class FilterProxySourceTest extends BaseTest {
    private WebDriverWait wait;
    private Login login;
    private Filters filters;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        login = new Login(super.driver); 
        wait = new WebDriverWait(super.driver, Duration.ofSeconds(10));
        filters = new Filters(super.driver);
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        super.driver.get("http://crm-dash/accounts-proxy-source");

        WebElement select = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("facebook-accounts-list_length")));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        try {
            filters.applyRandomFilters();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         Helpers.waitForSeconds(3);
        List<WebElement> headers = driver.findElements(By.cssSelector(
                ".dataTables_scrollHeadInner > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(1) > th"));
        List<WebElement> firstRow = driver
                .findElements(By.cssSelector("#facebook-accounts-list tbody tr:first-child td"));

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
}
}