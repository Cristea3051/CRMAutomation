package com.crm.SGCampaigns;

import java.time.Duration;
import java.util.List;

import com.Base.BaseTest;
import com.aventstack.extentreports.Status;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.resources.configfiles.SettingsHelper;
import com.utilities.Login;
import com.utilities.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.utilities.TestListener.class)
public class CreateTableSettingsPerOfferTest extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        TestListener.getTest().log(Status.PASS, "Utilizator logat: " + username);

        login.closeDebugBar();

        driver.get("http://crm-dash/google-dashboard/sg-campaigns");

        String title = driver.getTitle();
        TestListener.getTest().log(Status.PASS,"The user has successfully navigated to the page - " + title);

        Helpers.waitForSeconds(2);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "div.daterangepicker[style*=\"display: block\"] div.ranges li[data-range-key=\"All Time\"]")))
                .click();

        Helpers.waitForSeconds(2);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("scroll-top-dt-tables"))).click();
        Helpers.waitForSeconds(2);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//h3[contains(text(), 'Google Campaigns')]/following-sibling::div[@class='block-options']//button[@data-action='content_toggle']//i[@class='si si-arrow-up']")))
                .click();

        Helpers.waitForSeconds(2);

        driver.findElement(By.xpath(
                "//button[@title='Columns Table Settings' and contains(@class, 'button-settings') and @aria-controls='binom-offers-reports-sg']"))
                .click();

        Helpers.waitForSeconds(2);

        driver.findElement(By.id("setting-name")).sendKeys("PerOfferAutoSetting");

        Helpers.waitForSeconds(2);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(2);
        // Selectează multiple valori
        String[] valuesToSelect = { "Rev", "Conv", "Ftd", "EPC" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        // Apasă pe butonul de navigare
        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        // Mută elementele
        settingsHelper.selectMultipleValuesByValue(new String[] { "CPA" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 5);

        List<WebElement> swapedCols = driver.findElements(By.cssSelector(".select[id='swap-to'] option"));
        for (WebElement swapedCol : swapedCols) {
            TestListener.getTest().log(Status.INFO,swapedCol.getText());
        }

        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(2);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();

        Helpers.waitForSeconds(2);
        TestListener.getTest().log(Status.PASS,"A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver
                .findElements(By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable thead th"));
        List<WebElement> firstRow = driver.findElements(
                By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable tbody tr:first-child td"));

        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            TestListener.getTest().log(Status.INFO,header + " -> " + content);
        }
    }
}