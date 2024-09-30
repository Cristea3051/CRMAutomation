package com.crm.SGCampaigns;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class TestSgCampaigns extends BaseTest {
    private Login login;
    private WebDriverWait wait;
    private CreateAndOrderTableSettings createAndOrderTableSettings;
    private DownloadCSV downloadCSVFile;
    private DeleteTableSettings deleteTableSettings;
    private CreateTableSettingsPerOffer createSettingPerOfferSG;
    private DownloadCSVPerOffer downloadCSVPerOferSG;
    private DeleteSettingsPerOffer deleteSettingsPerOfferSG;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        createAndOrderTableSettings = new CreateAndOrderTableSettings(driver, wait);
        downloadCSVFile = new DownloadCSV(driver, wait);
        deleteTableSettings = new DeleteTableSettings(driver, wait);
        createSettingPerOfferSG = new CreateTableSettingsPerOffer(driver, wait);
        downloadCSVPerOferSG = new DownloadCSVPerOffer(driver, wait);
        deleteSettingsPerOfferSG = new DeleteSettingsPerOffer(driver, wait);
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
        createSettingPerOfferSG.createSettingPerOffer();
        Helpers.waitForSeconds(2);
        driver.findElement(By.id("scroll-top-dt-tables")).click();
        Helpers.waitForSeconds(2);
        driver.findElement(By.xpath(
                "//h3[text()='Google Campaigns']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]"))
                .click();
        downloadCSVPerOferSG.downloadCSVFilePerOffer();
        deleteSettingsPerOfferSG.deleteTableSettingsPerOffer();
    }
}