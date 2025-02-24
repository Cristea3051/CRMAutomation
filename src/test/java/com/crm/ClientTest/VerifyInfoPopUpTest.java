package com.crm.ClientTest;
import org.openqa.selenium.By;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;


import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.utilities.Login;

public class VerifyInfoPopUpTest extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "ClientCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) throws InterruptedException {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.block-content.ribbon-bottom"))).click();

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);
        

        boolean isElement1Present = driver.findElements(By.cssSelector("div.content:nth-child(2) > div:nth-child(1) > div:nth-child(1) > h3")).size() > 0;

        boolean isElement2Present = driver.findElements(By.cssSelector("#dashboard-info-button")).size() > 0;
    
        if (!isElement1Present && !isElement2Present) {
            Reporter.log("Niciunul dintre elemente NU este afișat, => PASSED!");
            Assert.assertTrue(true); 
        } else {
            Reporter.log("Cel puțin unul dintre elemente este afișat, => FAILED!");
            Assert.fail("Unul sau ambele elemente au fost găsite pe pagină, dar trebuiau să fie absente!");
        }
    
        driver.quit();

    }
}
