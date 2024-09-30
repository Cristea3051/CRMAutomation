package com.crm.SGCampaigns;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.resources.Helpers;
import com.resources.configfiles.SettingsHelper;

public class CreateTableSettingsPerOffer {
    private WebDriver driver;
    private WebDriverWait wait;

    public CreateTableSettingsPerOffer(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;

    }

    public void createSettingPerOffer() {
        Helpers.waitForSeconds(3);

        WebElement select = driver
                .findElement(By.name("binom-offers-reports-sg_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);
        Helpers.waitForSeconds(3);
        driver.findElement(By.xpath(
                "//button[@title='Columns Table Settings' and contains(@class, 'button-settings') and @aria-controls='binom-offers-reports-sg']"))
                .click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.id("setting-name")).sendKeys("PerOfferAutoSetting");

        Helpers.waitForSeconds(3);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(3);
        // Selectează multiple valori
        String[] valuesToSelect = { "Rev", "Conv", "Ftd", "EPC" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        // Apasă pe butonul de navigare
        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        // Mută elementele
        settingsHelper.selectMultipleValuesByValue(new String[] { "CPA" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 5);

        List<WebElement> swapedCols = driver.findElements(By.cssSelector(".select[id='swap-to'] option"));
        for (int i = 0; i < swapedCols.size(); i++) {
            Reporter.log(swapedCols.get(i).getText());
        }

        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(5);
        List<WebElement> headers = driver
                .findElements(By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable thead th"));
        List<WebElement> firstRow = driver.findElements(
                By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable tbody tr:first-child td"));

        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            Reporter.log(header + " -> " + content);
        }
    }
}