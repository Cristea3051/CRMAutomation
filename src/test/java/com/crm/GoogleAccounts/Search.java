package com.crm.GoogleAccounts;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class Search extends BaseTest{
     private Login login;
     @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
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

        Helpers.waitForSeconds(3);

        String searchKeyword = "GoogleAccountUpdatedTestJava";
        driver.findElement(By.cssSelector("input.form-control-sm")).sendKeys(searchKeyword);

        Helpers.waitForSeconds(3);

        List<WebElement> firstRow = driver
                .findElements(By.cssSelector("#google-accounts-list tbody tr:first-child td"));

        // Construim conținutul primei linii pentru a verifica dacă există keyword-ul
        boolean found = false;
        for (int i = 0; i < firstRow.size(); i++) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstRow.get(i));
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            Reporter.log(content);

            if (content.contains(searchKeyword)) {
                found = true;
            }
        }

        // Adăugăm aserția
        Assert.assertTrue(found, "Keyword-ul '" + searchKeyword + "' nu a fost găsit în prima linie a tabelului!");
        Reporter.log("Keyword-ul '" + searchKeyword + "' a fost găsit în prima linie a tabelului.");

        driver.quit();
    }
}