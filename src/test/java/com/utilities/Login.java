package com.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;

public class Login {
    private WebDriver driver;
    private WebDriverWait wait;

    public Login(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void performLogin(String username, String password) {
        driver.get("http://192.168.0.57/login");

        // Localizează și completează câmpurile de logare
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-username")));
        WebElement passwordField = driver.findElement(By.id("login-password"));
        WebElement loginButton = driver.findElement(By.tagName("button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    // Metoda pentru închiderea barei de debug
    public void closeDebugBar() {
        WebElement hideDebugBar = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("phpdebugbar-close-btn")));
        hideDebugBar.click();

        // Verifică dacă butonul a fost apăsat
        if (!hideDebugBar.isDisplayed()) {
            Reporter.log("Debug Bar a fost ascuns");
        } else {
            Reporter.log("DebugBar NU a fost ascuns.");
        }
    }
}
