package com.crm.SGCampaigns;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.resources.Helpers;

public class DeleteSettingsPerOffer {
   private WebDriver driver;
    private WebDriverWait wait;

    public DeleteSettingsPerOffer(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;

    }

        public void deleteTableSettingsPerOffer() {
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//button[@title='Columns Table Settings' and contains(@class, 'button-settings') and @aria-controls='binom-offers-reports-sg']"))).click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn[data-wizard='next']"))).click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fas.fa-trash.text-red-600"))).click();

        Helpers.waitForSeconds(5);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();
        Helpers.waitForSeconds(3);

        Reporter.log("A fost ștearsă cu succes setarea" + "\n");
      List<WebElement> headers = driver
                .findElements(By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable thead th"));
        List<WebElement> firstRow = driver.findElements(
                By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable tbody tr:first-child td"));

        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            Reporter.log(header + " -> " + content);
        }
        Helpers.waitForSeconds(1);
    }
}
