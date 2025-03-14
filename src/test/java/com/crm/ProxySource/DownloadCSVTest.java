package com.crm.ProxySource;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class DownloadCSVTest extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/accounts-proxy-source");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        Helpers.waitForSeconds(3);
        WebElement select = driver.findElement(By.cssSelector("select.custom-select[name='account-proxy-sources_length']"));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "button.buttons-csv[aria-controls='account-proxy-sources']")))
                .click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver.findElement(By.id("account-proxy-sources-export-button"));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }

        Helpers.waitForSeconds(5);

    }
}
