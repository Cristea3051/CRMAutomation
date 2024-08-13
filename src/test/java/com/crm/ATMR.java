package com.crm;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class ATMR extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/mr-dashboard/google/at-mr-campaigns");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);
        createAndOrderTableSettings();
        downloadCSV();
        deleteTableSettings();
    }

    private void createAndOrderTableSettings() {
        Helpers.waitForSeconds(3);
        
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        new WebDriverWait(driver, Duration.ofSeconds(20));

        String[] xpaths = {
            "/html/body/div[6]/div[1]/ul/li[13]",
            "/html/body/div[7]/div[1]/ul/li[13]",
            "/html/body/div[8]/div[1]/ul/li[13]",
            "/html/body/div[9]/div[1]/ul/li[13]", 
        };

         new WebDriverWait(driver, Duration.ofSeconds(20));
         boolean clicked = false;

            for (String xpath : xpaths) {
                try {
                    // Așteaptă până când elementul devine vizibil și interactiv
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                    
                    // Dacă elementul este găsit și este clicabil, face clic pe el
                    element.click();
                    clicked = true;
                    Reporter.log("Elementul activ a fost găsit și s-a făcut clic pe el: " + xpath);
                    break; // Ieși din buclă dacă ai făcut clic pe element
                } catch (Exception e) {
                    // Nu a fost posibil să se facă clic pe element; trece la următorul
                    Reporter.log("Elementul nu a fost clicabil: " + xpath);
                }
            }

            if (!clicked) {
                Reporter.log("Niciun element clicabil nu a fost găsit.");
            }


        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("table_setings_button")))).click();

        Helpers.waitForSeconds(3);
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("table_setings_name"))));

        nameInput.click();

        nameInput.sendKeys(inputInfo.getProperty("setting_name"));

        for (int i = 0; i < 6; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("select_column_to_hide")))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.w-100:nth-child(5)"))).click();
        }

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"swap-from\"]/option[2]"))).click();
        for (int i = 0; i < 10; i++) {
            WebElement moveDown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("move_down"))));
            moveDown.click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

        Helpers.waitForSeconds(3);
        try {
            List<WebElement> elements = driver.findElements(By.xpath(locators.getProperty("find_second_setting")));

            if (elements.size() > 0) {
                elements.get(0).click();
            } else {
                driver.findElement(By.xpath(locators.getProperty("find_setting"))).click();
            }
        } catch (Exception e) {
            Reporter.log("Excepție: " + e.getMessage());
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:");

        Helpers.waitForSeconds(5);
        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();
    }

    private void downloadCSV() {
     
        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("csv_button")))).click();

        try {
            Thread.sleep(3000);
            WebElement confirmButton = driver.findElement(By.cssSelector("#google-at-mr-campaigns-list-export-button"));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!");
        }

        Helpers.waitForSeconds(5);
    }

    private void deleteTableSettings() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("table_setings_button")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("delete_preset")))).click();

        Helpers.waitForSeconds(5);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.ml-auto"))).click();

        Helpers.waitForSeconds(5);
        driver.navigate().refresh();

        Reporter.log("A fost ștearsă cu succes setarea");
    }

}