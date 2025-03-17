package com.crm.FacebookAccounts;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FindAndDeleteFacebookAccountTest extends BaseTest {
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