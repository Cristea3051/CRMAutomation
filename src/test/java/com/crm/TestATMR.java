package com.crm;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.resources.configfiles.SettingsHelper;
import com.utilities.Login;

public class TestATMR extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/mr-dashboard/google/at-mr-campaigns");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);
        createAndOrderTableSettings();
        downloadCSV();
        deleteTableSettings();
        createAndOrderTableSettingsPerOffer();
        downloadCSVPerOffer();
        deleteTableSettingsPerOffer();
        try {
            // Verificăm dacă elementul există
            driver.findElement(By.name("atmr-google-search-daily-breakdown-campaigns_length"));
            // Dacă elementul a fost găsit, executăm metodele
            DailyBreakdownTableSettings();
            DailyBreakdownExportCSV();
            DailyBreakdownDeleteTableSettings();
        } catch (NoSuchElementException e) {
            // Elementul nu a fost găsit, sarim peste execuție
            System.out.println("Elementul nu a fost găsit, sar peste execuție.");
        }
    }

    private void createAndOrderTableSettings() {
        Helpers.waitForSeconds(3);

        WebElement select = driver
                .findElement(By.name("google-at-mr-campaigns-list_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@style='display: block; top: 222.594px; left: auto; right: 0px;'] //li[@data-range-key='All Time']")))
                .click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.cssSelector(".fa-table")).click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.id("setting-name")).sendKeys("ProxyAutoTableSetting");

        Helpers.waitForSeconds(3);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(3);
        // Selectează multiple valori
        String[] valuesToSelect = { "GEO", "TS", "Owner", "TMZ", "G Search Abs Top Impr", "G Search Top Impr Share",
                "G Search Impr Share", "Impr" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        // Apasă pe butonul de navigare
        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        // Mută elementele
        settingsHelper.selectMultipleValuesByValue(new String[] { "AVG Cpc" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 10);

        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(3);
        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();
    }

    private void downloadCSV() {

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("csv_button")))).click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver.findElement(By.cssSelector("#google-at-mr-campaigns-list-export-button"));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }

        Helpers.waitForSeconds(5);
    }

    private void deleteTableSettings() {
        wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("table_setings_button"))))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("delete_preset")))).click();

        Helpers.waitForSeconds(3);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();
        Helpers.waitForSeconds(3);

        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();

        Reporter.log("A fost ștearsă cu succes setarea" + "\n");

        Helpers.waitForSeconds(2);
        driver.findElement(By.id("scroll-top-dt-tables")).click();
        Helpers.waitForSeconds(2);
        driver.findElement(By.xpath(
                "//h3[text()='ATMR Campaigns']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]"))
                .click();
    }

    // PER OFFER Report tabble

    private void createAndOrderTableSettingsPerOffer() {
        Helpers.waitForSeconds(3);

        WebElement select = driver
                .findElement(By.name("binom-roi-offers-reports-atmr_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);
        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(locators.getProperty("atmr_per_offer_table_settings")))).click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.id("setting-name")).sendKeys("ProxyAutoTableSetting");

        Helpers.waitForSeconds(3);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(3);
        // Selectează multiple valori
        String[] valuesToSelect = { "GEO", "Conv", "CPA", "ECPA" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        // Apasă pe butonul de navigare
        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        // Mută elementele
        settingsHelper.selectMultipleValuesByValue(new String[] { "FTD" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 5);

        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(5);
        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();
    }

    private void downloadCSVPerOffer() {

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(locators.getProperty("atmr_per_offer_download_csv")))).click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver
                    .findElement(By.cssSelector("#binom-roi-offers-reports-atmr-export-button"));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }

        Helpers.waitForSeconds(5);
    }

    private void deleteTableSettingsPerOffer() {
        wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(locators.getProperty("atmr_per_offer_table_settings")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("delete_preset")))).click();

        Helpers.waitForSeconds(5);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();
        Helpers.waitForSeconds(3);

        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();

        Reporter.log("A fost ștearsă cu succes setarea" + "\n");

        Helpers.waitForSeconds(1);
        driver.findElement(By.id("scroll-top-dt-tables")).click();
        Helpers.waitForSeconds(1);
        driver.findElement(By.xpath(
                "//h3[text()='ATMR Campaigns']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]"))
                .click();
        Helpers.waitForSeconds(1);
        driver.findElement(By.xpath(
                "//h3[text()='Per Offer Report']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]"))
                .click();
    }

    private void DailyBreakdownTableSettings() {
        Helpers.waitForSeconds(3);

        WebElement select = driver
                .findElement(By.name("atmr-google-search-daily-breakdown-campaigns_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);
        Helpers.waitForSeconds(3);

        driver.findElement(By.xpath(
                "//button[@aria-controls='atmr-google-search-daily-breakdown-campaigns'][.//i[contains(@class, 'fas fa-table')]]"))
                .click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.id("setting-name")).sendKeys("DBreakTableSetting");

        Helpers.waitForSeconds(3);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(3);
        // Selectează multiple valori
        String[] valuesToSelect = { "TS", "Owner", "TMZ", "G Search Abs Top Impr", "G Search Top Impr Share",
                "G Search Impr Share", "Impr" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        // Apasă pe butonul de navigare
        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        // Mută elementele
        settingsHelper.selectMultipleValuesByValue(new String[] { "AVG Cpc" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 10);

        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(3);
        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();

        Helpers.waitForSeconds(1);
        driver.findElement(By.id("scroll-top-dt-tables")).click();
        Helpers.waitForSeconds(1);
        driver.findElement(By.xpath(
                "//h3[text()='ATMR Campaigns']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]"))
                .click();
        Helpers.waitForSeconds(1);
        driver.findElement(By.xpath(
                "//h3[text()='Per Offer Report']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]"))
                .click();
    }

    private void DailyBreakdownExportCSV() {
        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(
                        "//button[@aria-controls='atmr-google-search-daily-breakdown-campaigns'][.//i[contains(@class, 'fa fa-file-csv')]]")))
                .click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver
                    .findElement(By.id("atmr-google-search-daily-breakdown-campaigns-export-button"));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }

        Helpers.waitForSeconds(3);

    }

    private void DailyBreakdownDeleteTableSettings() {
        driver.findElement(By.xpath(
                "//button[@aria-controls='atmr-google-search-daily-breakdown-campaigns'][.//i[contains(@class, 'fas fa-table')]]"))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("delete_preset")))).click();

        Helpers.waitForSeconds(5);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();
        Helpers.waitForSeconds(3);

        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();

        Reporter.log("A fost ștearsă cu succes setarea" + "\n");
    }

}