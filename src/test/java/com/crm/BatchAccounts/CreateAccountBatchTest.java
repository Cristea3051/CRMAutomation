package com.crm.BatchAccounts;

import com.aventstack.extentreports.Status;
import com.utilities.Login;
import com.utilities.TestListener;
import com.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;

import java.time.Duration;

@Listeners(com.utilities.TestListener.class)
public class CreateAccountBatchTest extends BaseTest {
    private Login login;
    private WebDriverWait wait;
    private WebDriverUtils utils;
    private static final Logger logger = LogManager.getLogger(CreateAccountBatchTest.class);

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        utils = new WebDriverUtils(driver, wait);
    }

    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        TestListener.getTest().log(Status.PASS, "Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/batch-accounts");
        Helpers.waitForSeconds(2);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button i.fa-plus-circle"))).click();

        Helpers.waitForSeconds(2);
        fillFormFields(username);

        Helpers.waitForSeconds(2);
    }

    private void fillFormFields(String username) {  // Adăugăm parametrul username
        TestListener.getTest().log(Status.INFO, "Completare formular creare cont");
        String uniqueBatchName = "Batch_Java_" + username;
        utils.enterText(By.cssSelector("input[data-modal-field-id='create_name'][name='name']"), uniqueBatchName);

        utils.enterText(By.cssSelector("input[data-modal-field-id='create_monthly_fee'][name='monthly_fee']"), "20");

        utils.enterText(By.cssSelector("input[data-modal-field-id='create_raw_price']"), "50");

        utils.enterText(By.cssSelector("input[data-modal-field-id='create_price'][name='price']"), "100");

        utils.handleAutocomplete(By.cssSelector("input[data-modal-field-id='create_source_id'][inputname='source_id']"),
                "Super", By.id("autocomplete-list"));

        utils.selectDropdownOption(
                By.cssSelector("select[data-modal-field-id='create_batch_type'][name='batch_type']"), 2);

        utils.enterText(By.cssSelector("input[data-modal-field-id='create_spendshare_amount'][name='spendshare_amount']"), "50");

        utils.enterText(By.cssSelector("input[data-modal-field-id='create_spendshare_percentage'][name='spendshare_percentage']"), "100");

        utils.clickAndSelectRandomDay(By.cssSelector("input[data-modal-field-id='create_spendshare_date'][name='spendshare_date']"));

        Helpers.waitForSeconds(3);
        driver.findElement(By.cssSelector("button#create-batch-accounts-button")).click();
        logger.info("Apasat butonul de salvare cont");
        TestListener.getTest().log(Status.PASS, "Cont salvat");
    }
}