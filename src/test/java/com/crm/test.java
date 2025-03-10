package com.crm;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class test extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        super.driver.get("http://crm-dash/google-accounts-v2");

        Helpers.waitForSeconds(3); // Așteaptă un pic pentru a da timp aplicației să se încarce
        
        // Găsește scroll-ul orizontal
        WebElement scrollElement = driver.findElement(By.cssSelector("revogr-scroll-virtual.horizontal.hydrated[style='min-height: 15px;']"));  // Înlocuiește cu selectorul corect al elementului care conține scroll-ul
        
        // Găsește toate header-ele
        List<WebElement> headers = driver.findElements(By.cssSelector("div[role='columnheader'].rgHeaderCell div.header-content"));
        int currentIndex = 0;
        int totalColumns = headers.size();
        
        // Derulează orizontal până când toate coloanele sunt vizibile
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int previousScrollLeft = 0;
        int currentScrollLeft = 0;
        
        while (currentIndex < totalColumns) {
            // Așteaptă câteva momente pentru a da timp aplicației să încarce coloanele vizibile
            Helpers.waitForSeconds(1);  // Ajustează timpul după nevoile tale
        
          // Derulează puțin la dreapta
js.executeScript("arguments[0].scrollLeft += 200;", scrollElement);

// Obține poziția actuală a scroll-ului și convertește într-un număr de tip Long
currentScrollLeft = ((Long) js.executeScript("return arguments[0].scrollLeft;", scrollElement)).intValue();

            
            // Verifică dacă s-a ajuns la finalul scroll-ului
            if (currentScrollLeft == previousScrollLeft) {
                break;  // Dacă scroll-ul nu s-a mai mișcat, înseamnă că am ajuns la capăt
            }
        
            // Actualizează lista de header-e după fiecare derulare pentru a obține noile coloane vizibile
            headers = driver.findElements(By.cssSelector("div[role='columnheader'].rgHeaderCell div"));
        
            // Verifică textul din header-ul curent și adaugă-l în raport
            String headerText = headers.get(currentIndex).getText();
            if (!headerText.isEmpty()) {
                Reporter.log("Header: " + headerText + "/n");
            } else {
                Reporter.log("Header is empty");
            }
        
            // Mergi la următorul element
            currentIndex++;
            previousScrollLeft = currentScrollLeft;
        }
        
    }
}
