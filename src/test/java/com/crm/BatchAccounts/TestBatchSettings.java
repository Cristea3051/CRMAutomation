package com.crm.BatchAccounts;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;

public class TestBatchSettings extends BaseTest { // Moștenește BaseTest

    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        // Nu mai trebuie să scrii setările pentru driver, acestea sunt deja în setUp() din BaseTest
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/batch-accounts");
        Helpers.waitForSeconds(2);
    }
}