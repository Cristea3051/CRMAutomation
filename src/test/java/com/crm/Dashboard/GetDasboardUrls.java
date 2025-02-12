package com.crm.Dashboard;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.utilities.Login;

public class GetDasboardUrls {
    private WebDriver driver;
    private Login login;
    private WebDriverWait wait;
    

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {

        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        List<WebElement> urls = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.block-content.ribbon-primary a")));

         for (WebElement link : urls) {
            String url = link.getAttribute("href");
            Reporter.log(url);
         }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}