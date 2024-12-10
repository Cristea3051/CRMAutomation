package com.crm.DashboardTest;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.utilities.Login;

public class TestDashboardRedirectCard extends BaseTest {
    private Login login;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
    }

    @Test(dataProvider = "credentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        // Initializez elementul wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Localizez si aplic click() pe butonul user dropdown din header
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locators.getProperty("user_dropdown")))).click();

        // Extrag textul cu rolul pe care
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locators.getProperty("get_userrole"))));
        String role = element.getText();

        Reporter.log("Utilizator " + username + " - " + role + " s-a logat logat");

        WebElement redirect = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//div[contains(@class, 'sign-in-redirect-container') and @route-name='google-accounts.index']//i[contains(@class, 'si-shuffle')]")));

// Verifică dacă elementul este afișat înainte de a da click
if (redirect.isDisplayed()) {
    redirect.click();
    Reporter.log("Click-ul pe 'redirect' a fost efectuat cu succes.");
} else {
    Reporter.log("Elementul 'redirect' NU este vizibil.");
}

try {
    Thread.sleep(3000); // Pauză de 3 secunde
} catch (InterruptedException e) {
    e.printStackTrace();
}
    
    // Așteaptă apariția butonului de confirmare
    WebElement confirm = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".swal2-confirm")));
    
            if (confirm.isDisplayed()) {
                confirm.click();
                Reporter.log("Click-ul pe 'confirm' a fost efectuat cu succes.");
            } else {
                Reporter.log("Elementul 'confirm' NU este vizibil.");
            }

            try {
                Thread.sleep(3000); // Pauză de 3 secunde
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Pasul 2
            // Localizez si aplic click() pe butonul user dropdown din header
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locators.getProperty("user_dropdown")))).click();
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("button.dropdown-item")));
    logoutButton.click();
    Reporter.log("Logout-ul a fost efectuat.");

    // Pasul 3: Re-autentificare
    login.performLogin(username, password);

    // Verificare redirecționare
    String currentUrl = driver.getCurrentUrl();
    if (currentUrl.contains("google-accounts")) {
        Reporter.log("Redirect-ul funcționează corect:" + username + "a fost redirecționat către pagina Google Accounts.");
    } else {
        Reporter.log("Redirect-ul NU funcționează: " + username + " NU a fost redirecționat corect.");
    }

    // Redirect catre dashboard

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".d-lg-inline-block"))).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-2 > a"))).click();

    Reporter.log("Redirecționare către dashboard efectuată.");

    WebElement disableredirect = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//div[contains(@class, 'sign-in-redirect-container') and @route-name='google-accounts.index']//i[contains(@class, 'si-shuffle')]")));

// Verifică dacă elementul este afișat înainte de a da click
if (disableredirect.isDisplayed()) {
    disableredirect.click();
    Reporter.log("Click-ul pe 'redirect' pentru a il opri, a fost efectuat cu succes.");
} else {
    Reporter.log("Elementul 'redirect' pentru a il opri, NU este vizibil.");
}

try {
    Thread.sleep(3000); // Pauză de 3 secunde
} catch (InterruptedException e) {
    e.printStackTrace();
}
    
    // Așteaptă apariția butonului de confirmare
    WebElement disableconfirm = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".swal2-confirm")));
    
            if (disableconfirm.isDisplayed()) {
                disableconfirm.click();
                Reporter.log("Click-ul pe 'confirm' pentru a opri redirectul, a fost efectuat cu succes.");
            } else {
                Reporter.log("Elementul 'confirm' pentru a opri redirectul, NU este vizibil.");
            }

            try {
                Thread.sleep(3000); // Pauză de 3 secunde
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }

}
