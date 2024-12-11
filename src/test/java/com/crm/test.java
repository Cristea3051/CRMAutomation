package com.crm;

import org.openqa.selenium.By;

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
    public void signIn(String username, String password) throws InterruptedException {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

         driver.get("http://crm-dash/google-accounts");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        Helpers.waitForSeconds(3);

        WebElement select = driver.findElement(By.name("google-accounts-list_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "li.active:nth-child(13)"))).click();

        Helpers.waitForSeconds(3);
        

        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver.findElements(By.cssSelector("#google-accounts-list_wrapper .table-striped.dataTable thead th"));
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#google-accounts-list tbody tr:first-child td"));
        
        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText().trim();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";
            
            if (!header.isEmpty()) {
                System.out.println(header + " -> " + content);
            } else {
                System.out.println("Header is empty for index " + i);
            }
        }
        
        driver.quit();

    }
}
