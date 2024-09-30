package com.crm.SGCampaigns;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.resources.Helpers;

public class DownloadCSV {
    private WebDriver driver;
    private WebDriverWait wait;

    public DownloadCSV(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void downloadCSVFile() {

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//button[@title='Export to CSV File' and contains(@class, 'buttons-csv') and @aria-controls='sg-campaigns-list']")))
                .click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver.findElement(By.id("sg-campaigns-list-export-button"));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }

        Helpers.waitForSeconds(5);

    }
}
