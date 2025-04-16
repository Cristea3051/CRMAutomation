package com.crm.GoogleAccounts;

import com.crm.GoogleAccounts.pages.GoogleAccountsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.utilities.Login;

public class FindAndDeleteGoogleAccountTest extends BaseTest {
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
        String keyword = "GoogleAccountNameTestJava";
        accountsPage.deleteAccount(keyword);
    }
}