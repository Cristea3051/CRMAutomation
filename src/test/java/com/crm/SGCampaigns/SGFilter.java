package com.crm.SGCampaigns;

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

public class SGFilter {
    private WebDriver driver;
    private WebDriverWait wait;
    private Login login;
    private Filters filters;

    // Constructor gol
    public SGFilter() {
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

        driver.get("http://crm-dash/google-dashboard/sg-campaigns");

        WebElement select = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("sg-campaigns-list_length")));
Select rows = new Select(select);
rows.selectByIndex(0);

wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
    "//div[@style='display: block; top: 222.594px; left: auto; right: 0px;'] //li[@data-range-key='All Time']")))
    .click();

        try {
            filters.applyRandomFilters(); // Reuse Filters logic
        } catch (InterruptedException e) {
            e.printStackTrace(); // Gestionează eroarea aici
        }

        driver.quit();
    }

}