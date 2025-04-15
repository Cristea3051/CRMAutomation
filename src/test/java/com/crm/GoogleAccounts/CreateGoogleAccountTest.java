package com.crm.GoogleAccounts;

import com.Base.BaseTest;
import com.crm.GoogleAccounts.pages.GoogleAccountsPage;
import com.crm.GoogleAccounts.pages.LoginPage;
import com.resources.CredentialsProvider;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateGoogleAccountTest extends BaseTest {
        private LoginPage loginPage;
        private GoogleAccountsPage accountsPage;

        @BeforeMethod
        @Override
        public void setUp() {
                super.setUp();
                loginPage = new LoginPage(driver);
                accountsPage = new GoogleAccountsPage(driver);
        }

        @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
        public void signIn(String username, String password) {
                loginPage.performLogin(username, password);
                loginPage.closeDebugBar();
                accountsPage.navigateTo();
                accountsPage.clickCreateButton();
                accountsPage.fillCreateForm();
                boolean found = accountsPage.searchAndVerify("GoogleAccountNameTestJava");
                Assert.assertTrue(found, "Contul nu a fost găsit în tabel!");
                Reporter.log("Cont creat și verificat cu succes");
        }
}