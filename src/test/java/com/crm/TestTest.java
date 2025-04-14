package com.crm;

import com.aventstack.extentreports.Status;
import com.utilities.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;


@Listeners(com.utilities.TestListener.class)
public class TestTest extends BaseTest {
    private Login login;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        TestListener.getTest().log(Status.PASS, "Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        super.driver.get("http://crm-dash/google-accounts-v2");

        Helpers.waitForSeconds(3);

        WebElement scrollElement = driver.findElement(By.cssSelector("revogr-scroll-virtual.horizontal.hydrated[style='min-height: 15px;']"));

        List<WebElement> headers = driver.findElements(By.cssSelector("div[role='columnheader'].rgHeaderCell div.header-content"));
        int currentIndex = 0;
        int totalColumns = headers.size();

        // Derulează orizontal până când toate coloanele sunt vizibile
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int previousScrollLeft = 0;
        int currentScrollLeft = 0;

        while (currentIndex < totalColumns) {
            Helpers.waitForSeconds(1);

            // Derulează puțin la dreapta
            js.executeScript("arguments[0].scrollLeft += 200;", scrollElement);

            // Obține poziția actuală a scroll-ului
            Object scrollValue = js.executeScript("return arguments[0].scrollLeft;", scrollElement);
            currentScrollLeft = ((Number) scrollValue).intValue();

            // Verifică dacă s-a ajuns la finalul scroll-ului
            if (currentScrollLeft == previousScrollLeft) {
                break; // Dacă scroll-ul nu s-a mai mișcat, înseamnă că am ajuns la capăt
            }

            // Actualizează lista de header-e după fiecare derulare
            headers = driver.findElements(By.cssSelector("div[role='columnheader'].rgHeaderCell div"));

            // Verifică textul din header-ul curent și adaugă-l în raport
            String headerText = headers.get(currentIndex).getText();
            if (!headerText.isEmpty()) {
                TestListener.getTest().log(Status.INFO, "Header: " + headerText + "\n");
            } else {
                TestListener.getTest().log(Status.INFO, "Header is empty");
            }

            // Mergi la următorul element
            currentIndex++;
            previousScrollLeft = currentScrollLeft;
        }
    }
}
