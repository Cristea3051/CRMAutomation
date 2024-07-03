package com.crm;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class AccountSource extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator "  + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/accounts-proxy-source");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - "  + title);
        createAccountSource();
        updateAccountSource();
        createTableSettingsAccountSource();
        deleteTableSettingsAccountSource();
        downloadCSVaccountSource();
        deleteAccountSource();
    }

    private void createAccountSource() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("add_source")))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("create_source_name")))).sendKeys(inputInfo.getProperty("account_source_name"));
       WebElement createSourceProxy = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("create_account_source_butt"))));
       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createSourceProxy);
       createSourceProxy.click();
       try {
        Thread.sleep(4000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    Reporter.log("A fost creat Account Source");
    // Iterează primele 5 elemente sau mai puține, dacă lista are mai puțin de 5 elemente
   Helpers helpers = new Helpers(driver, locators);
        helpers.iterateAndLogTableData();
    }

    private void updateAccountSource() {
        WebElement proxyCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("checkbox_element"))));
        proxyCheckbox.click(); 
         wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_header_button")))).click();
         WebElement editName = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_name"))));
         editName.clear();
         editName.sendKeys(inputInfo.getProperty("update_account_source_name"));
         WebElement editSourcePort = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_source_port"))));
         editSourcePort.clear();
         editSourcePort.sendKeys(inputInfo.getProperty("update_source_port"));
         WebElement editUserName = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_source_login"))));
         editUserName.clear();
         editUserName.sendKeys(inputInfo.getProperty("update_login_source"));
         WebElement editPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_source_password"))));
         editPassword.clear();
         editPassword.sendKeys(inputInfo.getProperty("update_password"));

        WebElement updateProxy = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("update_proxy_source_button"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", updateProxy);
        updateProxy.click();
        try {
        Thread.sleep(4000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        Reporter.log("A fost updatata Sursa Proxy");
        // Iterează primele 5 elemente sau mai puține, dacă lista are mai puțin de 5 elemente
        Helpers showData = new Helpers(driver, locators);
        showData.iterateAndLogTableData();
    }

    private void createTableSettingsAccountSource() {
        try {
            Thread.sleep(3000);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("table_setings_button")))).click();
        try {
            Thread.sleep(3000);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
       WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("table_setings_name"))));
       nameInput.click();
       nameInput.sendKeys(inputInfo.getProperty("source_setting_name"));
        for (int i = 0; i < 2; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("select_column_to_hide")))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("move_in_hide")))).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();
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
        try {
            Thread.sleep(3000);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
            Reporter.log("A fost creat cu succes noua setare cu coloanele:");
            Helpers dataHelpers = new Helpers(driver, locators);
            dataHelpers.iterateAndLogTableData();
    }

    private void deleteTableSettingsAccountSource() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("table_setings_button")))).click();

    // Așteaptă apariția și clicabilizarea butonului din prima fereastră modală (dacă există unul)
    wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();
    
    // Așteaptă pentru a da timp fereaștrii modale să se încarce complet (puteți ajusta timpul de așteptare)
    try {
        Thread.sleep(4000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    // Faceți clic pe butonul care deschide a doua fereastră modală sau următoarea acțiune
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("delete_preset")))).click();
    
    // Așteaptă pentru a da timp fereaștrii modale să se încarce complet
    try {
        Thread.sleep(4000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    // Faceți clic pe butonul din a doua fereastră modală (folosiți un selector mai specific pentru butonul din fereastra modală)
   WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("delete_setting"))));
   deletePreset.click();
   deletePreset.click();
    
    // Așteaptă pentru a da timp fereaștrii modale să se încarce complet
    try {
        Thread.sleep(4000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    // Faceți clic pe alt buton sau finalizați acțiunea
    wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();
    
    // Așteaptă pentru a da timp fereaștrii modale să se încarce complet
    try {
        Thread.sleep(4000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    Reporter.log("A fost ștearsă cu succes setarea");
    }

    private void downloadCSVaccountSource() {

        WebElement selectCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("edited_chechbox_element"))));
        selectCheckbox.click();
        try {
            Thread.sleep(3000);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("csv_button")))).click();
        
        try {
            // Așteaptă un interval pentru a fi sigur că butonul de confirmare este prezent
            Thread.sleep(3000);
            // Încearcă să găsești și să apeși butonul de confirmare
            WebElement confirmButton = driver.findElement(By.id(locators.getProperty("confirm_export_proxy_source")));
            confirmButton.click();
            // Afișează un mesaj către utilizator pentru a indica succesul
            Reporter.log("A fost descarcat cu success fiserul CSV");
        } catch (Exception e) {
            // Afiseaza un mesaj de eroare dacă nu a fost posibil de apasat butonul de confirmare
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!");
        }
    }

    private void deleteAccountSource() {
        try {
            Thread.sleep(4000);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }

        WebElement selectCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("edited_chechbox_element"))));
        selectCheckbox.click();
        
        try {
            // Așteaptă un interval pentru a fi sigur că se face clic pe proxy
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Identifică și selectează butonul pentru ștergere
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("delete_button"))));
        deleteButton.click();
        
        try {
            // Așteaptă un interval pentru a fi sigur că butonul de confirmare este prezent
            Thread.sleep(4000);
            // Încearcă să găsești și să apeși butonul de confirmare
            WebElement confirmButton = driver.findElement(By.cssSelector(locators.getProperty("confirm_delete_modal")));
            confirmButton.click();
            // Afișează un mesaj către utilizator pentru a indica succesul
            Reporter.log("Proxy-ul a fost șters cu succes!");
        } catch (Exception e) {
            // Afiseaza un mesaj de eroare dacă nu a fost posibil de apasat butonul de confirmare
            Reporter.log("Eroare: Nu s-a putut șterge proxy-ul!");
        }
        try {
            // Așteaptă un interval pentru a fi sigur că proxy s-a sters
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
