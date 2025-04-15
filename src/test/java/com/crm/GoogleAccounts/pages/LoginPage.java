package com.crm.GoogleAccounts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class LoginPage {
    private WebDriver driver;

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login");
    private By debugBar = By.cssSelector(".debug-bar");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void performLogin(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        Reporter.log("Utilizator " + username + " s-a logat");
    }

    public void closeDebugBar() {
        driver.findElement(debugBar).click();
    }
}