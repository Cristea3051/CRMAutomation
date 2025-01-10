package com.crm.GoogleAccounts;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class CreateGoogleAccount {
    private WebDriver driver;
    private WebDriverWait wait;
    private Login login;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-dual:nth-child(5)"))).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("textarea.form-control")))
                .sendKeys("GoogleAccountNameTestJava");

        driver.findElement(By.cssSelector(
                "input.form-control.js-maxlength[name='account_id'][data-modal-field-id='create_account_id']"))
                .sendKeys("1234567890");

        WebElement selectStatus = driver.findElement(
                By.cssSelector("select.form-control.js-maxlength[name='status'][data-modal-field-id='create_status']"));
        Select statusRow = new Select(selectStatus);
        statusRow.selectByIndex(2);

        WebElement selectUsage = driver.findElement(
                By.cssSelector("select.form-control.js-maxlength[name='usage'][data-modal-field-id='create_usage']"));
        Select usageRow = new Select(selectUsage);
        usageRow.selectByIndex(0);

        driver.findElement(By.cssSelector(
                "input.form-control.js-maxlength[name='other_fees'][data-modal-field-id='create_other_fees']"))
                .sendKeys("1234");

        driver.findElement(By
                .cssSelector("input.form-control.js-maxlength[name='username'][data-modal-field-id='create_username']"))
                .sendKeys("AutomationTestJavaUser");

        driver.findElement(By
                .cssSelector("input.form-control.js-maxlength[name='password'][data-modal-field-id='create_password']"))
                .sendKeys("AutomationTestJavaPass");

        driver.findElement(By.cssSelector(
                "input.form-control.js-maxlength[name='email_login'][data-modal-field-id='create_email_login']"))
                .sendKeys("AutomationEmail@Login");

        driver.findElement(By.cssSelector(
                "input.form-control.js-maxlength[name='email_password'][data-modal-field-id='create_email_password']"))
                .sendKeys("AutomationEmail@Password");

        driver.findElement(By.cssSelector(
                "input.form-control.js-maxlength[name='id_verification'][data-modal-field-id='create_id_verification']"))
                .click();

        WebElement activeCalendar = driver.findElement(By.cssSelector(".flatpickr-calendar.open"));

        // Localizează toate zilele disponibile din calendar
        List<WebElement> days = activeCalendar
                .findElements(By.cssSelector(".flatpickr-day:not(.prevMonthDay):not(.nextMonthDay)"));

        // Verifică dacă există zile disponibile
        if (days.isEmpty()) {
            System.out.println("Nu există zile disponibile în calendar.");
        } else {
            // Selectează o zi aleatorie
            Random random = new Random();
            WebElement randomDay = days.get(random.nextInt(days.size()));

            // Apasă pe ziua aleatorie
            randomDay.click();

        }

        driver.findElement(By.cssSelector(
                "input.form-control.js-maxlength[name='farmer_comments'][data-modal-field-id='create_farmer_comments']"))
                .sendKeys("1comment / 2comments / 3comments");

        driver.findElement(
                By.cssSelector("input.form-control.js-maxlength[name='2fa'][data-modal-field-id='create_2fa']"))
                .sendKeys("gq52limcbz2fj7iqowo6a6hzufm2vjxd");

        driver.findElement(By.cssSelector(
                "input.form-control.js-maxlength[name='backup_code'][data-modal-field-id='create_backup_code']"))
                .sendKeys(
                        "gq52limcbz2fj7iqowo6a6hzufm2vjxd7781 2286 7437 6456 9022 9765 6536 1766 4432 2797 3077 2043 6864 9346 0563 6609 9405 0172 7099 1031");

        WebElement selectCard = driver.findElement(By.cssSelector(
                "select.js-select2.multiple-selector-inputs[name='account-cards[]'][data-modal-field-id='create_credit_cards']"));
        Select CardRow = new Select(selectCard);
        CardRow.selectByIndex(2);

        try {
            // Găsește input-ul autocomplete
            WebElement autocompleteInput = driver.findElement(By.cssSelector(
                    "input.form-control.js-maxlength[inputname='batch_id'][data-modal-field-id='create_batch_id']"));

            // Introdu textul dorit în input
            autocompleteInput.sendKeys("Su"); // Textul care declanșează sugestiile

            // Așteaptă afișarea listei de sugestii
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement autocompleteList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("autocomplete-list") // Selector pentru lista de autocomplete
            ));

            // Găsește toate opțiunile din lista de autocomplete
            List<WebElement> options = autocompleteList.findElements(By.tagName("div"));

            // Parcurge opțiunile și selectează pe cea dorită
            if (!options.isEmpty()) {
                // Selectează primul element din listă (index 0)
                WebElement firstOption = options.get(0);
                firstOption.click(); // Click pe primul element
                Reporter.log("Primul element selectat: " + firstOption.getText());
            } else {
                Reporter.log("Nu există sugestii disponibile.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        
    }

}