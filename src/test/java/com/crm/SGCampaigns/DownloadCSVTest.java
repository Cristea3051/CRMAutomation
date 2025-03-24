package com.crm.SGCampaigns;

import java.time.Duration;

import com.Base.BaseTest;
import com.aventstack.extentreports.Status;
import com.utilities.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

@Listeners(com.utilities.TestListener.class)
public class DownloadCSVTest extends BaseTest {
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
        TestListener.getTest().log(Status.PASS,"Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-dashboard/sg-campaigns");

        String title = driver.getTitle();
        TestListener.getTest().log(Status.PASS,"Utilizatorul a navigat cu succes la pagina - " + title);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "div.daterangepicker[style*=\"display: block\"] div.ranges li[data-range-key=\"All Time\"]")))
                .click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//button[@title='Export to CSV File' and contains(@class, 'buttons-csv') and @aria-controls='sg-campaigns-list']")))
                .click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver.findElement(By.id("sg-campaigns-list-export-button"));
            confirmButton.click();
            TestListener.getTest().log(Status.PASS,"A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            TestListener.getTest().log(Status.FAIL,"Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }
        Helpers.waitForSeconds(3);
    }
}
