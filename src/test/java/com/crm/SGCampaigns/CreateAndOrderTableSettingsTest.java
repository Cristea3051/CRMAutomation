package com.crm.SGCampaigns;

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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

@Listeners(com.utilities.TestListener.class)
public class CreateAndOrderTableSettingsTest extends BaseTest {
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
        TestListener.getTest().log(Status.PASS, "Utilizator logat: " + username);

        login.closeDebugBar();

        driver.get("http://crm-dash/google-dashboard/sg-campaigns");

        String title = driver.getTitle();
        TestListener.getTest().log(Status.PASS,"Utilizatorul a navigat cu succes la pagina - " + title);

        Helpers.waitForSeconds(3);

        WebElement select = driver.findElement(By.name("sg-campaigns-list_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "div.daterangepicker[style*=\"display: block\"] div.ranges li[data-range-key=\"All Time\"]")))
                .click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.cssSelector(".fa-table")).click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.id("setting-name")).sendKeys("AutoTableSetting");

        Helpers.waitForSeconds(3);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(3);

        String[] valuesToSelect = { "Rev", "CVR", "Ftd", "Conv", "LP CTR", "GEO",
                "B Clicks", "G Clicks" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        settingsHelper.selectMultipleValuesByValue(new String[] { "CPA" });

        settingsHelper.moveElements("fa fa-arrow-circle-up", 10);

        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();

        Helpers.waitForSeconds(3);
        TestListener.getTest().log(Status.PASS,"A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver
                .findElements(By.cssSelector("#sg-campaigns-list_wrapper .table-striped.dataTable thead th"));
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#sg-campaigns-list tbody tr:first-child td"));

        for (int i = 0; i < firstRow.size(); i++) {
            String header = headers.get(i).getText();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
            TestListener.getTest().log(Status.INFO,header + " -> " + content);
        }
    }
}
