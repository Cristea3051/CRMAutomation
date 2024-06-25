package com.crm;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class AccountProxy extends BaseTest {
    private Login login;

    @BeforeMethod
    public void setUp() {
        super.setUp(); // Apelăm metoda setUp() din clasa de bază
        login = new Login(driver); // Inițializăm obiectul Login
    }

 @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        // Initializez elementul wait

        Reporter.log("Utilizator "  + username + " s-a logat logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/accounts-proxy");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String title = driver.getTitle();

        Reporter.log("Utilizatorul a navigat cu succes la pagina - "  + title);

        // Create proxy

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("create_proxy_button")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("create_name_input")))).sendKeys(inputInfo.getProperty("name"));

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("create_username_input")))).sendKeys(inputInfo.getProperty("username"));

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("create_password_input")))).sendKeys(inputInfo.getProperty("password"));

        WebElement selectSource = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("select_proxy_type"))));
        
        // Identifică elementul și execută acțiunile necesare
        selectSource.click();
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        WebElement inputElement = driver.findElement(By.cssSelector(locators.getProperty("select_proxy_sorce")));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inputElement.sendKeys((inputInfo.getProperty("input_source")));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);


       WebElement createProxy = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("submit_proxy"))));
       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createProxy);
       createProxy.click();

       try {
        Thread.sleep(4000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    Reporter.log("A fost creat poxyul");
    // Iterează primele 5 elemente sau mai puține, dacă lista are mai puțin de 5 elemente
   Helpers helpers = new Helpers(driver, locators);
        helpers.iterateAndLogTableData();
    

    // Update Proxy

 WebElement proxyCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("checkbox_element"))));
proxyCheckbox.click(); 

 wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_header_button")))).click();

 WebElement editName = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_name_input"))));

 editName.clear();
 editName.sendKeys(inputInfo.getProperty("edited_name"));

 WebElement editUserName = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_username_input"))));

 editUserName.clear();
 editUserName.sendKeys(inputInfo.getProperty("edited_username"));

 WebElement editPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_password_input"))));
 editPassword.clear();
 editPassword.sendKeys(inputInfo.getProperty("edited_password"));


wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("select_edited_proxy_type")))).click();

driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
driver.switchTo().activeElement().sendKeys(Keys.ENTER);

WebElement sourceName = driver.findElement(By.cssSelector(locators.getProperty("select_edted_proxy_sorce")));
try {
    Thread.sleep(4000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
sourceName.clear();
sourceName.sendKeys(inputInfo.getProperty("input_source"));
try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
driver.switchTo().activeElement().sendKeys(Keys.ENTER);


WebElement updateProxy = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("submit_edited_proxy"))));
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", updateProxy);
updateProxy.click();

try {
Thread.sleep(4000);
} catch (InterruptedException e) {
e.printStackTrace();
}
Reporter.log("A fost updatat poxyul");
// Iterează primele 5 elemente sau mai puține, dacă lista are mai puțin de 5 elemente
Helpers showData = new Helpers(driver, locators);
showData.iterateAndLogTableData();


    // Create tabble settings 
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
   nameInput.sendKeys(inputInfo.getProperty("setting_name"));

    for (int i = 0; i < 5; i++) {
        // Găsirea elementului și efectuarea clicului folosind XPath
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("select_column_to_hide")))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("move_in_hide")))).click();
    }
    wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

    try {
        // Începe încercarea de a găsi elementele dorite
        List<WebElement> elements = driver.findElements(By.xpath(locators.getProperty("find_second_setting")));
    
        if (elements.size() > 0) {
            // Dacă există cel puțin un al doilea element de tip label, execută a doua acțiune
            elements.get(0).click();
        } else {
            // Dacă nu există al doilea element de tip label, execută prima acțiune
            driver.findElement(By.xpath(locators.getProperty("find_setting"))).click();
        }
    } catch (Exception e) {
        // Gestionarea excepțiilor (afișarea mesajului de eroare sau altă logică dorită)
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

    // Delete table settings 
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

        // Delete proxy

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

