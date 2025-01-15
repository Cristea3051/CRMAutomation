package com.crm.GoogleAccounts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class FindAndDeleteGoogleAccountTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Login login;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts");
        String title = driver.getTitle();
        Reporter.log("S-a navigat cu succes la pagina - " + title);

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.form-control[type='search']")))
                .sendKeys("GoogleAccountUpdatedTestJava");

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector("td.text-center.desktop.sorting_1.dtfc-fixed-left"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa-trash-alt"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.swal2-confirm[type='button']")))
                .click();

        Helpers.waitForSeconds(3);

        Reporter.log("A fost sters cu succes contul - GoogleAccountNameTestJava");

        driver.quit();

    }

}