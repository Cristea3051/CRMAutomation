package com.crm.FacebookAccounts;

import java.time.Duration;

import com.Base.BaseTest;
import com.aventstack.extentreports.Status;
import com.utilities.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

@Listeners(com.utilities.TestListener.class)
public class DownloadCSVFacebookTest  extends BaseTest {
    private static final Logger logger = LogManager.getLogger(DownloadCSVFacebookTest.class);
    private Login login;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("Setup complet pentru UpdateFacebookAccountTest");
    }

    @Test(dataProvider = "credentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        TestListener.getTest().log(Status.PASS, "Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/facebook-accounts");

        String title = driver.getTitle();
        TestListener.getTest().log(Status.PASS, "Utilizatorul a navigat cu succes la pagina - " + title);

        Helpers.waitForSeconds(3);
        WebElement select = driver.findElement(By.cssSelector("select.custom-select[name='facebook-accounts-list_length']"));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//button[@title='Export to CSV File' and contains(@class, 'buttons-csv') and @aria-controls='facebook-accounts-list']")))
                .click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver.findElement(By.id("facebook-accounts-list-export-button"));
            confirmButton.click();

            TestListener.getTest().log(Status.PASS, "A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            TestListener.getTest().log(Status.FAIL, "Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }

    }
}
