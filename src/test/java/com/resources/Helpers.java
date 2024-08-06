package com.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import java.util.Properties;
import java.util.List;

public class Helpers{
    private WebDriver driver;
    private Properties locators;

    public Helpers(WebDriver driver, Properties locators) {
        this.driver = driver;
        this.locators = locators;
    }

    public void iterateAndLogTableData() {
        List<WebElement> headerCells = driver.findElements(By.cssSelector(locators.getProperty("list_table_headers")));
        List<WebElement> cells = driver.findElements(By.cssSelector(locators.getProperty("list_table_elements")));

        int count = Math.min(cells.size(), 13);
        for (int i = 0; i < count; i++) {
            WebElement cell = cells.get(i);
            cell.getText();
        }

        int headcount = Math.min(headerCells.size(), 13);
        for (int i = 0; i < headcount; i++) {
            WebElement headerCell = headerCells.get(i);
            headerCell.getText();
        }

        String result = "";
        for (int i = 0; i < Math.min(count, headcount); i++) {
            String cellText = cells.get(i).getText();
            String headerText = headerCells.get(i).getText();
            result += headerText + ": " + cellText + "\n";
        }

        Reporter.log(result);
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
