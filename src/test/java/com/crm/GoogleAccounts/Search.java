package com.crm.GoogleAccounts;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class Search {
    private WebDriver driver;
    private Login login;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        login = new Login(driver);
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

        String searchKeyword = "GUL-GR1838-FRM-162811";
        driver.findElement(By.cssSelector("input.form-control-sm")).sendKeys(searchKeyword);

        Helpers.waitForSeconds(3);

        // Extragem prima linie a tabelului
        List<WebElement> headers = driver
                .findElements(By.cssSelector("#google-accounts-list_wrapper .table-striped.dataTable thead th"));
        List<WebElement> firstRow = driver
                .findElements(By.cssSelector("#google-accounts-list tbody tr:first-child td"));

        // Construim conținutul primei linii pentru a verifica dacă există keyword-ul
        boolean found = false;
        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            Reporter.log(header + " -> " + content);

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