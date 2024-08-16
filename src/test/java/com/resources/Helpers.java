package com.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;

public class Helpers{
    private WebDriver driver;

    public Helpers(WebDriver driver, Properties locators) {
        this.driver = driver;
    }

    public void iterateAndLogTableData() {
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
    String headerText = header.getText();
    headerTexts.add(headerText);  
}

// Locate the first row of the table body
WebElement firstRow = driver.findElement(By.cssSelector("tr.even:nth-child(1)"));

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
    }catch (Exception e) {
        // Log any exception that occurs during execution
        Reporter.log("An error occurred: " + e.getMessage());
        e.printStackTrace();
    }

}
      /**
     * Așteaptă pentru un anumit număr de secunde.
     * @param seconds Numărul de secunde pentru care trebuie să aștepte
     */
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurarea stării întreruperii
            Reporter.log("Thread interrupted: " + e.getMessage());
        }
    }
}
