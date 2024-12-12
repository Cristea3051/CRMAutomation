package com.crm.GoogleAccounts;

import java.time.Duration;

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

import com.resources.CredentialsProvider;
import com.utilities.Filters;
import com.utilities.Login;

public class Filter {
    private WebDriver driver;
    private WebDriverWait wait;
    private Login login;
    private Filters filters;

    // Constructor gol
    public Filter() {
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        filters = new Filters(driver);
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts");

        WebElement select = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("google-accounts-list_length")));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        try {
            filters.applyRandomFilters(); // Reuse Filters logic
        } catch (InterruptedException e) {
            e.printStackTrace(); // Gestionează eroarea aici
        }

        driver.quit();
    }

}