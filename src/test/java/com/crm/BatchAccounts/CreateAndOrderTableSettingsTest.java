package com.crm.BatchAccounts;

import java.time.Duration;
import java.util.List;

import com.Base.BaseTest;
import com.aventstack.extentreports.Status;
import com.utilities.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.resources.configfiles.SettingsHelper;
import com.utilities.Login;

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
    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        TestListener.getTest().log(Status.PASS, "Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/batch-accounts");

        String title = driver.getTitle();
        TestListener.getTest().log(Status.PASS,"Utilizatorul a navigat cu succes la pagina - " + title);

        Helpers.waitForSeconds(3);

        WebElement select = driver.findElement(By.name("accounts-batch-list_length"));
        Select rows = new Select(select);
        rows.selectByIndex(0);

        Helpers.waitForSeconds(3);

        driver.findElement(By.cssSelector(".fa-table")).click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.id("setting-name")).sendKeys("AutoTableSetting");

        Helpers.waitForSeconds(3);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(3);
        // Selectează multiple valori
        String[] valuesToSelect = {"Updated At", "Created At" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        // Apasă pe butonul de navigare
        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        // Mută elementele
        settingsHelper.selectMultipleValuesByValue(new String[] { "Owner" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 10);

        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();

        Helpers.waitForSeconds(3);
        TestListener.getTest().log(Status.PASS,"A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver.findElements(By.cssSelector(
                ".dataTables_scrollHeadInner > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(1) > th"));
        List<WebElement> firstRow = driver
                .findElements(By.cssSelector("#accounts-batch-list tbody tr:first-child td"));

        for (int i = 0; i < headers.size(); i++) {

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headers.get(i));

            String header = headers.get(i).getText().trim();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";

            if (!header.isEmpty()) {
                TestListener.getTest().log(Status.INFO,header + " -> " + content);
            } else {
                TestListener.getTest().log(Status.INFO,"Header is empty for index ");
            }

        }
    }
}
