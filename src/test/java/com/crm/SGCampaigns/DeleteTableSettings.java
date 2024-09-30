package com.crm.SGCampaigns;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.resources.Helpers;

public class DeleteTableSettings {
 private WebDriver driver;
    private WebDriverWait wait;

    public DeleteTableSettings(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

     public void deleteTableSettings() {
        driver.findElement(By.cssSelector(".fa-table")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn[data-wizard='next']"))).click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fas.fa-trash.text-red-600"))).click();

        Helpers.waitForSeconds(3);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();
        Helpers.waitForSeconds(3);

        Reporter.log("A fost ștearsă cu succes setarea" + "\n");

        List<WebElement> headers = driver.findElements(By.cssSelector("#sg-campaigns-list_wrapper .table-striped.dataTable thead th")); 
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#sg-campaigns-list tbody tr:first-child td"));
        
        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            Reporter.log(header + " -> " + content);

    }
    Helpers.waitForSeconds(2);
}

}