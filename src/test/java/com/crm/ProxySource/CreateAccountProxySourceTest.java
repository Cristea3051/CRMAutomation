package com.crm.ProxySource;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class CreateAccountProxySourceTest extends BaseTest {

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

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.text-success i.fa-plus-circle"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input#create_name[name='name']")))
                .sendKeys("AutomatedAccountProxySource");


        driver.findElement(By.cssSelector("input#create_port[name='port']"))
                .sendKeys("225200");

        driver.findElement(By.cssSelector("input#create_username[name='username']"))
                .sendKeys("AutomatedAccountProxySourceUsername");

        driver.findElement(By.cssSelector("input#create_password[name='password']"))
                .sendKeys("AutomatedAccountProxySourcePassword");

        driver.findElement(By.id("create-accounts-proxy-source-button")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.form-control[type='search']")))
                .sendKeys("AutomatedAccountProxySource");


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
    }
}