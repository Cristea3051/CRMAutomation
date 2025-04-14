package com.crm.BatchAccounts;

import com.Base.BaseTest;
import com.aventstack.extentreports.Status;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import com.utilities.TestListener;
import com.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class UpdateBatchAccountTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(UpdateBatchAccountTest.class);
    private Login login;
    private WebDriverUtils utils;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        utils = new WebDriverUtils(driver, wait);
        logger.info("Setup complet pentru FindAndDeleteFacebookAccountTest");
    }

    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        logger.info("Încep testul signIn cu utilizatorul: " + username);
        TestListener.getTest().log(Status.INFO, "Logare cu utilizatorul: " + username);

        login.performLogin(username, password);
        logger.info("Utilizator " + username + " s-a logat cu succes");
        TestListener.getTest().log(Status.PASS, "Utilizator logat: " + username);

        login.closeDebugBar();

        driver.get("http://crm-dash/batch-accounts");
        String title = driver.getTitle();
        logger.info("Navigat la pagina: " + title);
        TestListener.getTest().log(Status.PASS, "Navigat la: " + title);

        String uniqueBatchName = "Batch_Java_" + username;

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.form-control[type='search']")))
                .sendKeys(uniqueBatchName);

        logger.debug("Căutat cont: {}", uniqueBatchName);

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td.text-center.desktop.sorting_1.dtfc-fixed-left"))).click();
        logger.debug("Cont selectat pentru edit");

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#edit-dt-row"))).click();
        logger.debug("Apasat butonul de edit");

//        Aici completez modaul de edit

        Helpers.waitForSeconds(3);

        fillFormFields(uniqueBatchName);

        Helpers.waitForSeconds(3);
    }

    private void fillFormFields(String username) {
        TestListener.getTest().log(Status.INFO, "Completare formular creare cont");
        String uniqueBatchName = "Updated_" + username;
        utils.enterText(By.cssSelector("input[data-modal-field-id='edit_name'][placeholder='Enter your batch name..']"), uniqueBatchName);

        utils.enterText(By.cssSelector("input[data-modal-field-id='edit_monthly_fee'][name='monthly_fee']"), "30");

        utils.enterText(By.cssSelector("input[data-modal-field-id='edit_raw_price'][name='raw_price']"), "30");

        utils.enterText(By.cssSelector("input[data-modal-field-id='edit_price'][name='price']"), "200");

        utils.handleAutocomplete(By.cssSelector("input[data-modal-field-id='edit_account_sources'][inputname='source_id']"),
                "CDS", By.id("autocomplete-list"));

        utils.selectDropdownOption(
                By.cssSelector("select[data-modal-field-id='edit_batch_type'][name='batch_type']"), 0);

        utils.enterText(By.cssSelector("input[data-modal-field-id='edit_spendshare_amount'][name='spendshare_amount']"), "10");

        utils.enterText(By.cssSelector("input[data-modal-field-id='edit_spendshare_percentage'][name='spendshare_percentage']"), "10");

        utils.clickAndSelectRandomDay(By.cssSelector("input[data-modal-field-id='edit_spendshare_date'][name='spendshare_date']"));

        Helpers.waitForSeconds(2);
        driver.findElement(By.cssSelector("button#edit-batch-accounts-button")).click();
        logger.info("Apasat butonul de edit cont");
        TestListener.getTest().log(Status.PASS, "Cont Editat");
    }
}