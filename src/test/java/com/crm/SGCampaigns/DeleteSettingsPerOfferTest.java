package com.crm.SGCampaigns;

import java.util.List;
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
public class DeleteSettingsPerOfferTest extends BaseTest {
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
                TestListener.getTest().log(Status.PASS, "Utilizator " + username + " s-a logat");

                login.closeDebugBar();

                driver.get("http://crm-dash/google-dashboard/sg-campaigns");

                String title = driver.getTitle();
                TestListener.getTest().log(Status.PASS, "Utilizatorul a navigat cu succes la pagina - " + title);

                Helpers.waitForSeconds(2);

                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "div.daterangepicker[style*=\"display: block\"] div.ranges li[data-range-key=\"All Time\"]")))
                        .click();

                Helpers.waitForSeconds(2);
                wait.until(ExpectedConditions.elementToBeClickable(By.id("scroll-top-dt-tables"))).click();
                Helpers.waitForSeconds(2);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                                "//h3[contains(text(), 'Google Campaigns')]/following-sibling::div[@class='block-options']//button[@data-action='content_toggle']//i[@class='si si-arrow-up']")))
                                .click();

                Helpers.waitForSeconds(2);
                wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath(
                                                "//button[@title='Columns Table Settings' and contains(@class, 'button-settings') and @aria-controls='binom-offers-reports-sg']")))
                                .click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn[data-wizard='next']")))
                                .click();

                Helpers.waitForSeconds(2);
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fas.fa-trash.tw-text-red-600")))
                                .click();

                Helpers.waitForSeconds(2);
                WebElement deletePreset = wait
                                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
                deletePreset.click();

                Helpers.waitForSeconds(2);
                wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();
                Helpers.waitForSeconds(2);
                wait.until(ExpectedConditions.elementToBeClickable(By.id("scroll-top-dt-tables"))).click();
                Helpers.waitForSeconds(2);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                                "//h3[contains(text(), 'Google Campaigns')]/following-sibling::div[@class='block-options']//button[@data-action='content_toggle']//i[@class='si si-arrow-up']")))
                        .click();
                Helpers.waitForSeconds(2);
                TestListener.getTest().log(Status.PASS,"A fost ștearsă cu succes setarea" + "\n");
                Helpers.waitForSeconds(2);

                List<WebElement> headers = driver
                                .findElements(By.cssSelector(
                                                "#binom-offers-reports-sg_wrapper .table-striped.dataTable thead th"));
                List<WebElement> firstRow = driver.findElements(
                                By.cssSelector("#binom-offers-reports-sg_wrapper .table-striped.dataTable tbody tr:first-child td"));

                for (int i = 0; i < firstRow.size(); i++) {
                        String header = headers.get(i).getText();
                        String content = (i < firstRow.size()) ? firstRow.get(i).getText() : "";
                        TestListener.getTest().log(Status.INFO,header + " -> " + content);
                }
                Helpers.waitForSeconds(1);
        }
}
