package com.crm.ClientTest;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;


import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class SpendAndTotalSpendTest extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "ClientCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) throws InterruptedException {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.block-content.ribbon-bottom"))).click();

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-calendar:nth-child(1)"))).click();

        Helpers.waitForSeconds(2);


        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@class, 'ranges') and ancestor::div[contains(@class, 'daterangepicker') and contains(@style, 'display: block;')]]//li[text()='All Time']")));
            element.click();

        Helpers.waitForSeconds(3);
        List<WebElement> headers = driver.findElements(By.cssSelector("div.dataTables_scroll > div.dataTables_scrollHead > div > table > thead > tr > th"));
        List<WebElement> firstRow = driver.findElements(By.cssSelector("div.dataTables_scroll > div.dataTables_scrollFoot.footer-on-top > div > table > tfoot > tr > th"));

        boolean found = false;

        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i).getText().trim();

            if (header.equalsIgnoreCase("Total Spend") || header.equalsIgnoreCase("Ad Spend")) {

                String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : null;

                Reporter.log("Found column: " + header + " -> '" + content + "'");
                System.out.println("DEBUG: Found column: " + header + " -> '" + content + "'");

                Assert.assertNotNull(content, "Value under '" + header + "' should not be null");

                found = true;
            }
        }

        Assert.assertTrue(found, "'Total Spend' or 'Ad Spend' header was not found in the table");

    }
}
