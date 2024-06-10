package com.crm;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import com.resources.CredentialsProvider;

public class AppTest{
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(dataProvider = "credentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        driver.get("http://crm-dash/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         // Localizează și completează câmpurile de logare
         WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-username")));
        WebElement passwordField = driver.findElement(By.id("login-password"));
      
        WebElement loginButton = driver.findElement(By.tagName("button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        Reporter.log("Utilizator logat");

    }

    @AfterMethod
    public void tearDown() {
        // Închide browser-ul
        if (driver != null) {
            driver.quit();
        }
    }
}