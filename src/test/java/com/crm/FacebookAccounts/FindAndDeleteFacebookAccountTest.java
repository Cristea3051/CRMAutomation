package com.crm.FacebookAccounts;

import com.Base.BaseTest;
import com.utilities.TestListener;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

public class FindAndDeleteFacebookAccountTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(FindAndDeleteFacebookAccountTest.class);
    private Login login;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        logger.info("Setup complet pentru FindAndDeleteFacebookAccountTest");
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        logger.info("Încep testul signIn cu utilizatorul: " + username);
        TestListener.getTest().log(Status.INFO, "Logare cu utilizatorul: " + username);

        login.performLogin(username, password);
        logger.info("Utilizator " + username + " s-a logat cu succes");
        TestListener.getTest().log(Status.PASS, "Utilizator logat: " + username);

        login.closeDebugBar();

        driver.get("http://crm-dash/facebook-accounts");
        String title = driver.getTitle();
        logger.info("Navigat la pagina: " + title);
        TestListener.getTest().log(Status.PASS, "Navigat la: " + title);

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.form-control[type='search']")))
                .sendKeys("FacebookAccountUpdatedTestJava");
        logger.debug("Căutat cont: FacebookAccountUpdatedTestJava");

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td.text-center.desktop.sorting_1.dtfc-fixed-left"))).click();
        logger.debug("Cont selectat pentru ștergere");

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa-trash-alt"))).click();
        logger.debug("Apasat butonul de ștergere");

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.swal2-confirm[type='button']")))
                .click();
        logger.info("Confirmat ștergerea contului");

        Helpers.waitForSeconds(3);

        logger.info("Contul 'FacebookAccountUpdatedTestJava' a fost șters cu succes");
        TestListener.getTest().log(Status.PASS, "Contul 'FacebookAccountUpdatedTestJava' a fost șters");
    }
}