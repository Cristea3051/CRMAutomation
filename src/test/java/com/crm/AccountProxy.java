package com.crm;


import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.utilities.Login;

public class AccountProxy {
   
    private WebDriver driver;
    private Login login;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        login = new Login(driver); // Inițializează obiectul Login
    }
 @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        // Initializez elementul wait

        Reporter.log("Utilizator "  + username + " s-a logat logat");

        driver.get("http://crm-dash/accounts-proxy");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String title = driver.getTitle();

        Reporter.log("Utilizatorul a navigat cu succes la pagina - "  + title);

        // Create proxy

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-dual:nth-child(5)"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("create_name"))).sendKeys("201.192.30.21:1123");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("create_username"))).sendKeys("ProxyAutomation");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("create_password"))).sendKeys("Password22##");

        WebElement selectSource = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"create-accounts-proxy-modal\"]/div/div/div/div/div/div/div[2]/form/div/div/div[4]/select")));
        
        // Identifică elementul și execută acțiunile necesare
        selectSource.click();
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        WebElement inputElement = driver.findElement(By.cssSelector("input[inputname='source_id']"));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inputElement.sendKeys("ip");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);


       WebElement createProxy = wait.until(ExpectedConditions.elementToBeClickable(By.id("create-accounts-proxy-button")));
       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createProxy);
       createProxy.click();

       try {
        Thread.sleep(4000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    List<WebElement> cells = driver.findElements(By.cssSelector("tr.even.grey-row td"));

    // Iterează primele 5 elemente sau mai puține, dacă lista are mai puțin de 5 elemente
    int count = Math.min(cells.size(), 6);
    for (int i = 0; i < count; i++) {
        WebElement cell = cells.get(i);
        String text = cell.getText();
        Reporter.log("A fost creat un nou proxy: " + text);
    }

    // Update Proxy


 WebElement proxyCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/main/div[2]/div/div/div/div[2]/div[2]/div/div/div/div[3]/div[2]/table/tbody/tr[1]/td[1]/div/input")));
proxyCheckbox.click(); 

 wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-dt-row"))).click();

 WebElement editName = wait.until(ExpectedConditions.elementToBeClickable(By.id("edit_name")));

 editName.clear();
 editName.sendKeys("101.232.32.25:2255");

 WebElement editUserName = wait.until(ExpectedConditions.elementToBeClickable(By.id("edit_username")));

 editUserName.clear();
 editUserName.sendKeys("UpdatedUsername");

 WebElement editPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("edit_password")));
editPassword.clear();
 editPassword.sendKeys("UpdatedPass22##");


wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"edit-accounts-proxy-modal\"]/div/div/div/div/div/div/div[2]/form/div/div/div[4]/select"))).click();

driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
driver.switchTo().activeElement().sendKeys(Keys.ENTER);

WebElement sourceName = driver.findElement(By.cssSelector("input[data-modal-field-id='edit_source']"));
try {
    Thread.sleep(4000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
sourceName.clear();
sourceName.sendKeys("ip");
try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
driver.switchTo().activeElement().sendKeys(Keys.ENTER);


WebElement updateProxy = wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-accounts-proxy-button")));
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", updateProxy);
updateProxy.click();

try {
Thread.sleep(4000);
} catch (InterruptedException e) {
e.printStackTrace();
}

// Iterează primele 5 elemente sau mai puține, dacă lista are mai puțin de 5 elemente

List<WebElement> updatedCells = driver.findElements(By.cssSelector("tr.even.grey-row td"));

Math.min(updatedCells.size(), 6);
for (int i = 0; i < count; i++) {
WebElement cell = updatedCells.get(i);
String text = cell.getText();
Reporter.log("A fost eidtat proxyul: " + text);
}

    // Create tabble settings 


    // Delete table settings 
    // Export csv
        // Delete proxy

        try {
            Thread.sleep(4000);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }

        WebElement selectCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/main/div[2]/div/div/div/div[2]/div[2]/div/div/div/div[3]/div[2]/table/tbody/tr/td[1]/div/input")));
        selectCheckbox.click();
        
        try {
            // Așteaptă un interval pentru a fi sigur că se face clic pe proxy
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Identifică și selectează butonul pentru ștergere
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("delete-dt-row")));
        deleteButton.click();
        
        try {
            // Așteaptă un interval pentru a fi sigur că butonul de confirmare este prezent
            Thread.sleep(4000);
            // Încearcă să găsești și să apeși butonul de confirmare
            WebElement confirmButton = driver.findElement(By.cssSelector(".swal2-confirm"));
            confirmButton.click();
            // Afișează un mesaj către utilizator pentru a indica succesul
            Reporter.log("Proxy-ul a fost șters cu succes!");
        } catch (Exception e) {
            // Afiseaza un mesaj de eroare dacă nu a fost posibil de apasat butonul de confirmare
            Reporter.log("Eroare: Nu s-a putut șter proxy-ul!");
        }
        try {
            // Așteaptă un interval pentru a fi sigur că proxy s-a sters
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        // Închide browser-ul
        if (driver != null) {
            driver.quit();
        }
    }
}

