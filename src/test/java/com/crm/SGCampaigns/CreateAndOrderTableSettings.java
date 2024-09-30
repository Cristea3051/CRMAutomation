package com.crm.SGCampaigns;

import java.time.Duration;
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


public class CreateAndOrderTableSettings {
    private WebDriver driver;
    private WebDriverWait wait;

    public CreateAndOrderTableSettings(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void createAndOrder() {
       Helpers.waitForSeconds(3);

        WebElement select = driver
                .findElement(By.name("sg-campaigns-list_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@style='display: block; top: 222.594px; left: auto; right: 0px;'] //li[@data-range-key='All Time']"))).click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.cssSelector(".fa-table")).click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.id("setting-name")).sendKeys("AutoTableSetting");

        Helpers.waitForSeconds(3);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(3);
        // Selectează multiple valori
        String[] valuesToSelect = { "Rev", "CVR", "Ftd", "Conv", "LP CTR", "GEO",
                "B Clicks", "G Clicks" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        // Apasă pe butonul de navigare
        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        // Mută elementele
        settingsHelper.selectMultipleValuesByValue(new String[] { "CPA" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 10);

        // List<WebElement> swapedCols = driver.findElements(By.cssSelector(".select[id='swap-to'] option"));
        // for (int i = 0; i < swapedCols.size(); i++) {
        //     Reporter.log(swapedCols.get(i).getText());
        // }
        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver.findElements(By.cssSelector("#sg-campaigns-list_wrapper .table-striped.dataTable thead th")); 
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#sg-campaigns-list tbody tr:first-child td"));
        
        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            Reporter.log(header + " -> " + content);
        }
    }
}
