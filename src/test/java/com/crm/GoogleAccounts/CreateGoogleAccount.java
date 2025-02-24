package com.crm.GoogleAccounts;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class CreateGoogleAccount extends BaseTest {
        private Login login;
        private WebDriverWait wait;
    
        @BeforeMethod
        @Override
        public void setUp() {
            super.setUp();
            login = new Login(driver);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
        public void signIn(String username, String password) {
                login.performLogin(username, password);
                Reporter.log("Utilizator " + username + " s-a logat");

                login.closeDebugBar();

                driver.get("http://crm-dash/google-accounts");
                String title = driver.getTitle();
                Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-dual:nth-child(5)")))
                                .click();
                Helpers.waitForSeconds(3);

                fillFormFields();
               
                Helpers.waitForSeconds(3);
                String searchKeyword = "GoogleAccountNameTestJava";
                driver.findElement(By.cssSelector("input.form-control-sm")).sendKeys(searchKeyword);
              
               Helpers.waitForSeconds(5);

                List<WebElement> headers = driver.findElements(By.cssSelector(
                                ".dataTables_scrollHeadInner > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(1) > th"));
                List<WebElement> firstRow = driver
                                .findElements(By.cssSelector("#google-accounts-list tbody tr:first-child td"));
                boolean found = false;
                for (int i = 0; i < headers.size(); i++) {
                        // Scroll până la elementul curent din header
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                                        headers.get(i));

                        String header = headers.get(i).getText().trim();
                        String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";

                        if (!header.isEmpty()) {
                                Reporter.log(header + " -> " + content + "\n");
                        } else {
                                Reporter.log("Header is empty for index " + i + "\n");
                        }
                        if (content.contains(searchKeyword)) {
                                found = true;
                        }

                }

                Helpers.waitForSeconds(3);
                // Adăugăm aserția
                Assert.assertTrue(found,
                                "Keyword-ul '" + searchKeyword + "' nu a fost găsit în prima linie a tabelului!");
                Reporter.log("Keyword-ul '" + searchKeyword + "' a fost găsit în prima linie a tabelului.");

                Helpers.waitForSeconds(3);

        }

        private void fillFormFields() {
                enterText(By.cssSelector("textarea.form-control"), "GoogleAccountNameTestJava");
                enterText(
                                By.cssSelector(
                                                "input.form-control.js-maxlength[name='account_id'][data-modal-field-id='create_account_id']"),
                                "1234567890");
                selectDropdownOption(
                                By.cssSelector("select.form-control.js-maxlength[name='status'][data-modal-field-id='create_status']"),
                                2);
                selectDropdownOption(
                                By.cssSelector("select.form-control.js-maxlength[name='usage'][data-modal-field-id='create_usage']"),
                                0);
                enterText(
                                By.cssSelector(
                                                "input.form-control.js-maxlength[name='other_fees'][data-modal-field-id='create_other_fees']"),
                                "1234");
                enterText(
                                By.cssSelector(
                                                "input.form-control.js-maxlength[name='username'][data-modal-field-id='create_username']"),
                                "AutomationTestJavaUser");
                enterText(
                                By.cssSelector(
                                                "input.form-control.js-maxlength[name='password'][data-modal-field-id='create_password']"),
                                "AutomationTestJavaPass");
                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='email_login'][data-modal-field-id='create_email_login']"),
                                "AutomationEmail@Login");
                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='email_password'][data-modal-field-id='create_email_password']"),
                                "AutomationEmail@Password");

                clickAndSelectRandomDay(By.cssSelector(
                                "input.form-control.js-maxlength[name='id_verification'][data-modal-field-id='create_id_verification']"));
                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='farmer_comments'][data-modal-field-id='create_farmer_comments']"),
                                "1comment / 2comments / 3comments");
                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='2fa'][data-modal-field-id='create_2fa']"),
                                "gq52limcbz2fj7iqowo6a6hzufm2vjxd");
                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='backup_code'][data-modal-field-id='create_backup_code']"),
                                "7781 2286 7437 6456 9022 9765 6536 1766 4432 2797 3077 2043 6864 9346 0563 6609 9405 0172 7099 1031");

                selectDropdownOption(By.cssSelector(
                                "select.js-select2.multiple-selector-inputs[name='account-cards[]'][data-modal-field-id='create_credit_cards']"),
                                2);

                handleAutocomplete(
                                By.cssSelector(
                                                "input.form-control.js-maxlength[inputname='batch_id'][data-modal-field-id='create_batch_id']"),
                                "Su", By.id("autocomplete-list"));
                clickAndSelectRandomDay(By.cssSelector(
                                "input.form-control.js-maxlength[name='monthly_fees_date'][data-modal-field-id='create_monthly_fees_date']"));
                clickAndSelectRandomDay(By.cssSelector(
                                "input.form-control.js-maxlength[name='sync_from_date'][data-modal-field-id='create_sync_from_date']"));
                clickAndSelectRandomDay(By
                                .cssSelector("input.form-control.js-maxlength[name='bh_date'][data-modal-field-id='create_bh_date']"));
                clickAndSelectRandomDay(By.cssSelector(
                                "input.form-control.js-maxlength[name='mb_delivery_date'][data-modal-field-id='create_mb_delivery_date']"));

                handleAutocomplete(By.cssSelector(
                                "input.form-control.js-maxlength[inputname='source_id'][data-modal-field-id='create_source_id']"),
                                "FJ",
                                By.id("autocomplete-list"));
                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='first_last_name'][data-modal-field-id='create_first_last_name']"),
                                "FirstNameTest LastNameTest");
                handleAutocomplete(
                                By.cssSelector(
                                                "input.form-control.js-maxlength[inputname='rdp_id'][data-modal-field-id='create_rdp_id']"),
                                "GUL", By.id("autocomplete-list"));

                // Account Proxy
                selectDropdownOption(By.cssSelector(
                                "select.form-control[name='proxy_id'][data-modal-field-id='create_proxies']"),
                                1);

                // Account Domains
                selectDropdownOption(By.cssSelector(
                                "select.form-control[name='account-domains[]'][data-modal-field-id='create_domains']"),
                                0);

                // MediaBuyer
                handleAutocomplete(
                                By.cssSelector(
                                                "input.form-control.js-maxlength[inputname='account_owner'][data-modal-field-id='create_account_owner']"),
                                "Do", By.id("autocomplete-list"));

                // MediaBuyer Comments

                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='mb_comments'][data-modal-field-id='create_mb_comments']"),
                                "1commentMB / 2commentsMB / 3commentsMB");

                // License

                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='license'][data-modal-field-id='create_license']"),
                                "AutomationTestLicense");

                // GEO
                enterText(By.cssSelector(
                                "input.form-control.js-maxlength[name='geo'][data-modal-field-id='create_geo']"),
                                "ATG");

                // Save button
                Helpers.waitForSeconds(3);
                driver.findElement(By.cssSelector(
                                "button#create-google-accounts-button"))
                                .click();

        }

        private void enterText(By locator, String text) {
                driver.findElement(locator).sendKeys(text);
        }

        private void selectDropdownOption(By locator, int index) {
                WebElement selectElement = driver.findElement(locator);
                Select dropdown = new Select(selectElement);
                dropdown.selectByIndex(index);
        }

        private void clickAndSelectRandomDay(By locator) {
                driver.findElement(locator).click();
                WebElement activeCalendar = driver.findElement(By.cssSelector(".flatpickr-calendar.open"));
                List<WebElement> days = activeCalendar
                                .findElements(By.cssSelector(".flatpickr-day:not(.prevMonthDay):not(.nextMonthDay)"));

                if (!days.isEmpty()) {
                        Random random = new Random();
                        WebElement randomDay = days.get(random.nextInt(days.size()));
                        randomDay.click();
                } else {
                        System.out.println("Nu există zile disponibile în calendar.");
                }
        }

        private void handleAutocomplete(By locator, String inputText, By autocompleteListLocator) {
                try {
                        WebElement autocompleteInput = driver.findElement(locator);
                        autocompleteInput.sendKeys(inputText);

                        WebElement autocompleteList = wait
                                        .until(ExpectedConditions.visibilityOfElementLocated(autocompleteListLocator));
                        List<WebElement> options = autocompleteList.findElements(By.tagName("div"));

                        if (!options.isEmpty()) {
                                WebElement firstOption = options.get(0);
                                firstOption.click();
                                Reporter.log("Primul element selectat: " + firstOption.getText());
                        } else {
                                Reporter.log("Nu există sugestii disponibile.");
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }

        }
}