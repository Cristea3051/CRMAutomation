package com.crm.GoogleAccounts;

import com.Base.BaseTest;
import com.crm.GoogleAccounts.pages.GoogleAccountsPage;
import com.resources.CredentialsProvider;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.utilities.Login;
public class CreateGoogleAccountTest extends BaseTest {
        private Login login;
        private GoogleAccountsPage accountsPage;

        @BeforeMethod
        @Override
        public void setUp() {
                super.setUp();
                login = new Login(driver);
                accountsPage = new GoogleAccountsPage(driver);
        }

        @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
        public void signIn(String username, String password) {
                login.performLogin(username, password);
                login.closeDebugBar();
                accountsPage.navigateTo();
                accountsPage.clickCreateButton();
                accountsPage.fillCreateForm();
                boolean found = accountsPage.searchAndVerify("GoogleAccountNameTestJava");
                Assert.assertTrue(found, "Contul nu a fost găsit în tabel!");
                Reporter.log("Cont creat și verificat cu succes");
        }
}