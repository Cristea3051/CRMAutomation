package com.crm.GetConsloeError;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.utilities.Login;

public class CaptureConsloleErrorTest {
    private WebDriver driver;
    private Login login;


    private String[] urlsToTest = {
     "http://crm-dash/google-accounts",
    "http://crm-dash/users",
    "http://crm-dash/google-dashboard/sc-campaigns",
    "http://crm-dash/sites-domains",
    "http://crm-dash/facebook-accounts",
    "http://crm-dash/google-dashboard/sg-campaigns",
    "http://crm-dash/google-warming-accounts",
    "http://crm-dash/accounts-proxy",
    "http://crm-dash/google-dashboard/dr-campaigns",
    "http://crm-dash/cron-management",
    "http://crm-dash/batch-accounts",
    "http://crm-dash/mr-dashboard/google/search-campaigns",
    "http://crm-dash/google-dashboard/rnd-campaigns",
    "http://crm-dash/other-accounts",
    "http://crm-dash/facebook-dashboard/sl-campaigns",
    "http://crm-dash/mr-dashboard/google/s2b-campaigns",
    "http://crm-dash/mr-dashboard/google/nl-mr-campaigns",
    "http://crm-dash/credit-cards",
    "http://crm-dash/mr-dashboard/google/it-mr-campaigns",
    "http://crm-dash/mr-dashboard/facebook/dlpmr-campaigns",
    "http://crm-dash/system-logs",
    "http://crm-dash/mr-dashboard/google/at-mr-campaigns",
    "http://crm-dash/mr-dashboard/google/gr-mr-campaigns",
    "http://crm-dash/bodmr2-campaigns",
    "http://crm-dash/mr-dashboard/google/ch-mr-campaigns",
    "http://crm-dash/mr-dashboard/google/uac-campaigns",
    "http://crm-dash/accounts-source",
    "http://crm-dash/mr-dashboard/google/sp-mr-campaigns",
    "http://crm-dash/mr-dashboard/google/de-mr-campaigns",
    "http://crm-dash/mr-dashboard/bodmr1-campaigns",
    "http://crm-dash/combine-namecheap-cloudflare",
    "http://crm-dash/mr-dashboard/facebook/kfb-campaigns",
    "http://crm-dash/secret-notes",
    "http://crm-dash/system-notifications",
    "http://crm-dash/taboola/taboola-campaigns",
    "http://crm-dash/stats-dashboard/st-campaigns",
    "http://crm-dash/mr-dashboard/facebook/mr-campaigns",
    "http://crm-dash/binom-campaigns",
    "http://crm-dash/sites-types",
    "http://crm-dash/accounts-rdp",
    "http://crm-dash/ktbl-campaigns",
    "http://crm-dash/mr-dashboard/bdgmr-campaigns",
    "http://crm-dash/exchange",
    "http://crm-dash/mr-dashboard/bbmr-campaigns",
    "http://crm-dash/namecheap-domain",
    "http://crm-dash/mr-dashboard/fpmf-campaigns",
    "http://crm-dash/mr-dashboard/boomr-campaigns",
    "http://crm-dash/bdgtbl-campaigns",
    "http://crm-dash/accounts-proxy-source",
    "http://crm-dash/binom-cost-update-logs",
    "http://crm-dash/rndview-dashboard/rd-campaigns",
    "http://crm-dash/mr-dashboard/fpmg-campaigns",
    "http://crm-dash/static-text",
    "http://crm-dash/system-maintenance",
    "http://crm-dash/mr-dashboard/fpmt-campaigns",
    "http://crm-dash/documentation/content",
    "http://crm-dash/namecheap-account",
    "http://crm-dash/cloudflare-account",
    "http://crm-dash/cloudflare-domain",
    "http://crm-dash/binom-campaigns-settings",
    "http://crm-dash/google-campaigns-settings",
    "http://crm-dash/facebook-campaigns-settings",
    "http://crm-dash/horizon"
    };

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.setCapability("goog:loggingPrefs", new java.util.HashMap<String, String>() {{
            put("browser", "ALL");
        }});

        driver = new ChromeDriver(options);
        login = new Login(driver);
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {

        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        for (String url : urlsToTest) {

            driver.get(url);
            String title = driver.getTitle();
            Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LogEntries logs = driver.manage().logs().get("browser");

            for (LogEntry entry : logs) {
                Reporter.log("Nivel: " + entry.getLevel());
                Reporter.log("Mesaj: " + entry.getMessage());
            }
        }

        driver.quit();
    }
}