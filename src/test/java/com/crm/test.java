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

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) throws InterruptedException {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

         driver.get("http://crm-dash/google-accounts");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        Helpers.waitForSeconds(3);

        WebElement select = driver.findElement(By.name("google-accounts-list_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "li.active:nth-child(13)"))).click();

        Helpers.waitForSeconds(3);
        
        driver.findElement(By.cssSelector(".fa-table")).click();
        Helpers.waitForSeconds(3);
        
        WebElement selectSettings = driver.findElement(By.cssSelector("select#swap-from.custom-select"));
        
        // Crează un obiect Select pentru a interacționa cu dropdown-ul
        Select SettingsRows = new Select(selectSettings);
        
        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver.findElements(By.cssSelector(".dataTables_scrollHeadInner > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(1) > th"));
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#google-accounts-list tbody tr:first-child td"));
        
        // Iterează prin fiecare coloană din tabel
        for (int i = 0; i < headers.size(); i++) {
            // Scroll până la elementul curent din header
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headers.get(i));
        
            String header = headers.get(i).getText().trim();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";
        
            if (!header.isEmpty()) {
                // Iterează prin opțiunile din dropdown
                boolean optionFound = false;
                for (WebElement option : SettingsRows.getOptions()) {
                    // Compară textul header + content cu opțiunile din dropdown
                    if (option.getText().contains(header) && option.getText().contains(content)) {
                        Reporter.log(header + " -> " + content + " -> Found in dropdown: " + option.getText());
                        optionFound = true;
                        break;  // Ieși din buclă dacă găsești o potrivire
                    }
                }
        
                // Dacă nu s-a găsit opțiunea, scrie un mesaj că nu a fost găsită
                if (!optionFound) {
                    Reporter.log(header + " -> " + content + " -> Not found in dropdown");
                }
            } else {
                Reporter.log("Header is empty for index " + i);
            }
        }        
        
        driver.quit();

    }
}
