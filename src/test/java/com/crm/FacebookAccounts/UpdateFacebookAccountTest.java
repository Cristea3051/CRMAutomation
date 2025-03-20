package com.crm.FacebookAccounts;

import com.Base.BaseTest;
import com.utilities.TestListener;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class UpdateFacebookAccountTest extends BaseTest {
        private static final Logger logger = LogManager.getLogger(UpdateFacebookAccountTest.class);
        private Login login;

        @BeforeMethod
        @Override
        public void setUp() {
                super.setUp();
                login = new Login(driver);
                wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                logger.info("Setup complet pentru UpdateFacebookAccountTest");
        }

        @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
        public void signIn(String username, String password) {
                logger.info("Încep testul signIn cu utilizatorul: " + username);
                TestListener.getTest().log(Status.INFO, "Încep testul cu utilizatorul: " + username);

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
                        .sendKeys("FacebookAccountNameTestJava");
                logger.debug("Căutat cont: FacebookAccountNameTestJava");

                Helpers.waitForSeconds(3);
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td.text-center.desktop.sorting_1.dtfc-fixed-left")))
                        .click();
                logger.debug("Cont selectat pentru editare");

                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa-edit"))).click();
                logger.debug("Apasat butonul de editare");
                TestListener.getTest().log(Status.INFO, "Deschis formularul de editare");

                Helpers.waitForSeconds(3);

                fillFormFields();

                Helpers.waitForSeconds(3);
                String searchKeyword = "FacebookAccountUpdatedTestJava";
                WebElement search = driver.findElement(By.cssSelector("input.form-control-sm"));
                search.clear();
                search.sendKeys(searchKeyword);
                logger.debug("Căutat cont actualizat: " + searchKeyword);
                Helpers.waitForSeconds(5);

                List<WebElement> headers = driver.findElements(By.cssSelector(
                        ".dataTables_scrollHeadInner > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(1) > th"));
                List<WebElement> firstRow = driver.findElements(By.cssSelector("#facebook-accounts-list tbody tr:first-child td"));
                boolean found = false;
                for (int i = 0; i < headers.size(); i++) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headers.get(i));

                        String header = headers.get(i).getText().trim();
                        String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";

                        if (!header.isEmpty()) {
                                logger.debug(header + " -> " + content);
                                TestListener.getTest().log(Status.INFO, header + " -> " + content); 
                        } else {
                                logger.warn("Header is empty for index " + i);
                                TestListener.getTest().log(Status.INFO, "Header is empty for index " + i);
                        }
                        if (content.contains(searchKeyword)) {
                                found = true;
                        }
                }

                Assert.assertTrue(found, "Keyword-ul '" + searchKeyword + "' nu a fost găsit în prima linie a tabelului!");
                logger.info("Keyword-ul '" + searchKeyword + "' a fost găsit în prima linie a tabelului");
                TestListener.getTest().log(Status.PASS, "Keyword-ul '" + searchKeyword + "' a fost găsit și contul actualizat cu succes");

                Helpers.waitForSeconds(3);
        }

        private void fillFormFields() {
                logger.info("Completare câmpuri formular actualizare cont");
                TestListener.getTest().log(Status.INFO, "Completare formular actualizare cont");

                enterText(By.cssSelector("input.form-control.js-maxlength[data-modal-field-id='edit_name'][maxlength='100']"),
                        "FacebookAccountUpdatedTestJava");
                enterText(By.cssSelector("input.form-control.js-maxlength[name='fb_ad_account_id'][data-modal-field-id='edit_fb_ad_account_id']"),
                        "2345678901");
                selectDropdownOption(By.cssSelector("select.form-control.js-maxlength[name='status'][data-modal-field-id='edit_status'][table-id='facebook-accounts-list']"),
                        3);
                enterText(By.cssSelector("input.form-control.js-maxlength[name='other_fees'][data-modal-field-id='edit_other_fees']"),
                        "2335");
                enterText(By.cssSelector("input.form-control.js-maxlength[name='username'][data-modal-field-id='edit_username']"),
                        "AutomationTestJavaUserUpdated");
                enterText(By.cssSelector("input[name='password'][data-modal-field-id='edit_password'][placeholder='Password']"),
                        "AutomationTestJavaPassUpdated");
                enterText(By.cssSelector("input.form-control.js-maxlength[name='email_login'][data-modal-field-id='edit_email_login'][placeholder='Email Login']"),
                        "AutomationUpdatedEmail@Login");
                enterText(By.cssSelector("input.form-control.js-maxlength[name='email_password'][data-modal-field-id='edit_email_password'][placeholder='Email Password']"),
                        "AutomationEmailUpdatedPassword");
                enterText(By.cssSelector("input.form-control.js-maxlength[name='farmer_comments'][data-modal-field-id='edit_farmer_comments']"),
                        "1commentUpdated / 2commentsUpdated / 3commentsUpdated");
                enterText(By.cssSelector("input.form-control.js-maxlength[name='2fa'][data-modal-field-id='edit_2fa']"),
                        "gq52limcbz2fj7iqowo6a6hzufm2vjxd");
                enterText(By.cssSelector("input.form-control.js-maxlength[name='backup_code'][data-modal-field-id='edit_backup_code']"),
                        "7781 2286 7437 6456 9022 9765");
                selectDropdownOption(By.cssSelector("select.js-select2.multiple-selector-inputs[name='account-cards[]'][data-modal-field-id='edit_credit_cards']"),
                        2);
                clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='monthly_fees_date'][data-modal-field-id='edit_monthly_fees_date']"));
                clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='sync_from_date'][data-modal-field-id='edit_sync_from_date']"));
                clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='bh_date'][data-modal-field-id='edit_bh_date']"));
                clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='mb_delivery_date'][data-modal-field-id='edit_mb_delivery_date']"));
                enterText(By.cssSelector("input.form-control.js-maxlength[name='first_last_name'][data-modal-field-id='edit_first_last_name']"),
                        "FirstNameTestUpdated LastNameTestUpdated");
                selectDropdownOption(By.cssSelector("select.form-control[name='account-domains[]'][data-modal-field-id='edit_domains']"),
                        1);
                handleAutocomplete(By.cssSelector("input.form-control.js-maxlength[inputname='account_owner'][data-modal-field-id='edit_account_owner_name']"),
                        "Anna B", By.id("autocomplete-list"));
                enterText(By.cssSelector("input.form-control.js-maxlength[name='mb_comments'][data-modal-field-id='edit_mb_comments']"),
                        "1commentMBUpdated / 2commentsMBUpdated / 3commentsMBUpdated");

                driver.findElement(By.id("edit-facebook-accounts-button")).click();
                logger.info("Apasat butonul de salvare actualizare cont");
                TestListener.getTest().log(Status.INFO, "Cont actualizat salvat");
        }

        private void enterText(By locator, String text) {
                WebElement element = driver.findElement(locator);
                element.clear();
                element.sendKeys(text);
                logger.debug("Introdus text: " + text + " în câmpul " + locator);
        }

        private void selectDropdownOption(By locator, int index) {
                WebElement selectElement = driver.findElement(locator);
                Select dropdown = new Select(selectElement);
                dropdown.selectByIndex(index);
                logger.debug("Selectat opțiunea cu index " + index + " din dropdown-ul " + locator);
        }

        private void clickAndSelectRandomDay(By locator) {
                driver.findElement(locator).click();
                WebElement activeCalendar = driver.findElement(By.cssSelector(".flatpickr-calendar.open"));
                List<WebElement> days = activeCalendar.findElements(By.cssSelector(".flatpickr-day:not(.prevMonthDay):not(.nextMonthDay)"));

                if (!days.isEmpty()) {
                        Random random = new Random();
                        WebElement randomDay = days.get(random.nextInt(days.size()));
                        randomDay.click();
                        logger.debug("Selectat o zi aleatoare din calendarul " + locator);
                } else {
                        logger.warn("Nu există zile disponibile în calendarul " + locator);
                }
        }

        private void handleAutocomplete(By locator, String inputText, By autocompleteListLocator) {
                try {
                        WebElement autocompleteInput = wait.until(ExpectedConditions.elementToBeClickable(locator));
                        autocompleteInput.clear();
                        autocompleteInput.sendKeys(inputText);
                        logger.debug("Introdus textul '" + inputText + "' în câmpul de autocomplete " + locator);

                        WebElement autocompleteList = wait.until(ExpectedConditions.visibilityOfElementLocated(autocompleteListLocator));
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("div")));
                        List<WebElement> options = autocompleteList.findElements(By.tagName("div"));

                        if (!options.isEmpty()) {
                                WebElement firstOption = options.get(0);
                                String optionText = firstOption.getText();
                                firstOption.click();
                                logger.info("Selectat primul element din autocomplete: " + optionText);
                                TestListener.getTest().log(Status.INFO, "Selectat: " + optionText);

                                wait.until(ExpectedConditions.attributeContains(autocompleteInput, "value", optionText));
                                logger.debug("Valoare input după selecție: " + autocompleteInput.getAttribute("value"));
                        } else {
                                logger.warn("Nu există sugestii disponibile pentru '" + inputText + "'");
                                TestListener.getTest().log(Status.WARNING, "Nu există sugestii pentru '" + inputText + "'");
                        }
                } catch (Exception e) {
                        logger.error("Eroare în handleAutocomplete: " + e.getMessage(), e);
                        TestListener.getTest().log(Status.FAIL, "Eroare autocomplete: " + e.getMessage());
                        throw e; // Pentru a raporta fail-ul
                }
        }
}