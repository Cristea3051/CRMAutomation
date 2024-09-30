package com.crm.SGCampaigns;

import java.time.Duration;

import org.openqa.selenium.By;
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
import java.util.List;

public class TestSgCampaigns extends BaseTest {
    private Login login;
    private WebDriverWait wait;
    private CreateAndOrderTableSettings createAndOrderTableSettings;
    private DownloadCSV downloadCSVFile;
    private DeleteTableSettings deleteTableSettings;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        createAndOrderTableSettings = new CreateAndOrderTableSettings(driver, wait);
        downloadCSVFile = new DownloadCSV(driver, wait);
        deleteTableSettings = new DeleteTableSettings(driver, wait);
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-dashboard/sg-campaigns");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);
        createAndOrderTableSettings.createAndOrder();
        downloadCSVFile.downloadCSVFile();
        deleteTableSettings.deleteTableSettings();

        Helpers.waitForSeconds(2);
        driver.findElement(By.id("scroll-top-dt-tables")).click();
        Helpers.waitForSeconds(2);
        driver.findElement(By.xpath(
                "//h3[text()='Google Campaigns']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]"))
                .click();
    }
    // PER OFFER Report tabble

    private void createAndOrderTableSettingsPerOffer() {
        Helpers.waitForSeconds(3);

        WebElement select = driver
                .findElement(By.name("binom-offers-reports-sg_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);
        Helpers.waitForSeconds(3);
        driver.findElement(By.xpath("//button[@title='Columns Table Settings' and contains(@class, 'button-settings') and @aria-controls='binom-offers-reports-sg']")).click();

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

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(5);
        List<WebElement> headers = driver.findElements(By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable thead th")); 
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable tbody tr:first-child td"));
        
        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            Reporter.log(header + " -> " + content);
        }
        
    }

    private void downloadCSVPerOffer() {

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Export to CSV File' and contains(@class, 'buttons-csv') and @aria-controls='binom-offers-reports-sg']"))).click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver
                    .findElement(By.cssSelector("button[id='binom-offers-reports-sg-export-button']"));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }

        Helpers.waitForSeconds(5);
    }

    private void deleteTableSettingsPerOffer() {
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//button[@title='Columns Table Settings' and contains(@class, 'button-settings') and @aria-controls='binom-offers-reports-sg']"))).click();

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
    }
}