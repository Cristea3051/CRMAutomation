package com.crm;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

import java.util.ArrayList;
import java.util.List;

 public class test extends BaseTest{

    private Login login;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        login = new Login(driver);
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        tableAuto();
        scrollTable();
    }



    private void tableAuto() {
        Helpers.waitForSeconds(3);
        try {
         
// Localizează elementul antetului tabelului
WebElement headerRow = driver.findElement(By.cssSelector(".dataTables_scrollHeadInner > table:nth-child(1) > thead"));

// Obține toate celulele antetului
List<WebElement> headers = headerRow.findElements(By.tagName("th"));

// Așteaptă câteva secunde (dacă este necesar)
Helpers.waitForSeconds(3);

// Creează o listă pentru a stoca textele antetului
List<String> headerTexts = new ArrayList<>();

// Iterează prin fiecare celulă antetului
for (int i = 0; i < headers.size(); i++) {
    WebElement header = headers.get(i);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true);", header);
    String headerText = header.getText();
    headerTexts.add(headerText);  
    // Afișează textul antetului în log
    Reporter.log((i + 1) + ": " + headerText);
}

// Locate the first row of the table body
WebElement firstRow = driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[2]/table/tbody/tr[1]"));

// Get all cells in the first row
List<WebElement> cells = firstRow.findElements(By.tagName("td"));

// Print the data from each cell in the first row with the corresponding header
Reporter.log("Data in the first row:");
for (int i = 0; i < cells.size(); i++) {
    WebElement cell = cells.get(i);
    String cellText = cell.getText();
    String headerText = headerTexts.get(i); // Get corresponding header text
    Reporter.log((i + 1) + ": " + headerText + ": " + cellText);
}
        } finally {
            // Close the browser
            driver.quit();
        }
    }


private void scrollTable(){
    Helpers.waitForSeconds(5);
    // Locating the table
// WebElement table = driver.findElement(By.cssSelector("#google-accounts-list_wrapper > div.dataTables_scroll.dtfc-has-left > div.dataTables_scrollBody"));


Helpers.waitForSeconds(5);
// Scroll to the end of the table horizontally
WebElement element = driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div/div/div/div[2]/div[2]/div/div[1]/div/div[3]/div[1]/div/table/thead/tr/th[45]"));

JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].scrollIntoView(true);", element);
Helpers.waitForSeconds(5);

String text = element.getText();
Reporter.log(text);
}


}
