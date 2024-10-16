package com.crm.SGCampaigns;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.resources.configfiles.SettingsHelper;
import com.utilities.Login;

public class CreateTableSettingsPerOffer {
    private WebDriver driver;
    private WebDriverWait wait;
    private Login login;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-dashboard/sg-campaigns");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@style='display: block; top: 222.594px; left: auto; right: 0px;'] //li[@data-range-key='All Time']")))
                .click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("scroll-top-dt-tables"))).click();
        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//h3[contains(text(), 'Google Campaigns')]/following-sibling::div[@class='block-options']//button[@data-action='content_toggle']//i[@class='si si-arrow-up']")))
                .click();

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

        driver.quit();
    }
}