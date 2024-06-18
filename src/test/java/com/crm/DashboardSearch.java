package com.crm;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.utilities.Login;

public class DashboardSearch extends BaseTest {
    private Login login;

    @BeforeMethod
    public void setUp() {
        super.setUp(); // Apelăm metoda setUp() din clasa de bază
        login = new Login(driver); // Inițializăm obiectul Login
    }

    @Test(dataProvider = "credentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        // Initializez elementul wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Localizez si aplic click() pe butonul user dropdown din header
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locators.getProperty("user_dropdown")))).click(); 

        // Extrag textul cu rolul pe care
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locators.getProperty("get_userrole"))));
        String role = element.getText();

        Reporter.log("Utilizator "  + username + " - " + role + " s-a logat logat");

    //   Localizez elementul cu ajutorul lui wait
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locators.getProperty("localize_card"))));

    // Afisez lista de elemente cu acelasi css selector 
    List<WebElement> spanElements = driver.findElements(By.cssSelector(locators.getProperty("get_dashcard_name")));

    // Se iterează prin lista de elemente și se extrage textul din fiecare element
    for (WebElement spanElement : spanElements) {
        String card = spanElement.getText();
        Reporter.log("A fost localizat: "  + card);
    }

    }
}
