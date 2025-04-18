package com.crm.GoogleAccounts.pages;

import com.aventstack.extentreports.Status;
import com.resources.Helpers;
import com.utilities.TestListener;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class GoogleAccountsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locatori
    private final By createButton = By.cssSelector("button.btn-dual:nth-child(5)");
    private final By searchInput = By.cssSelector("input.form-control[type='search']");
    private final By rowsSelect = By.name("google-accounts-list_length");
    private final By exportCsvButton = By.xpath("//button[@title='Export to CSV File' and contains(@class, 'buttons-csv') and @aria-controls='google-accounts-list']");
    private final By confirmExportButton = By.id("google-accounts-list-export-button");
    private final By editButton = By.cssSelector("i.fa-edit");
    private final By deleteButton = By.cssSelector("i.fa-trash-alt");
    private final By confirmDeleteButton = By.cssSelector("button.swal2-confirm[type='button']");
    private final By rowClick = By.cssSelector("td.text-center.desktop.sorting_1.dtfc-fixed-left");

    // Locatori formular creare/editare
    private final By accountNameField = By.cssSelector("textarea.form-control");
    private final By accountIdField = By.cssSelector("input.form-control.js-maxlength[name='account_id'][data-modal-field-id='create_account_id']");
    private final By selectAccountStatus = By.cssSelector("select.form-control.js-maxlength[name='status'][data-modal-field-id='create_status']");
    private final By selectIdVerificationDate= By.cssSelector("input.form-control.js-maxlength[name='id_verification'][data-modal-field-id='create_id_verification']");
    //      NU treb de uitat de adaugat alte elemente
    private final By saveButton = By.cssSelector("button#create-google-accounts-button");
    private final By editSaveButton = By.id("edit-google-accounts-button");

    public GoogleAccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateTo() {
        driver.get("http://192.168.0.57/google-accounts");
        // Așteaptă ca pagina să fie încărcată complet
        wait.until(ExpectedConditions.urlContains("google-accounts"));
        TestListener.getTest().log(Status.PASS,"Navigat la pagina: " + driver.getTitle());
    }

    public void selectRows(int index) {
        WebElement select = wait.until(ExpectedConditions.visibilityOfElementLocated(rowsSelect));
        wait.until(ExpectedConditions.elementToBeClickable(select)); // Așteaptă să fie clicabil
        new Select(select).selectByIndex(index);
    }

    public void clickCreateButton() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(createButton));
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }
    public void fillCreateForm() {
        enterText(accountNameField, "GoogleAccountNameTestJava");
        enterText(accountIdField, "1234567890");
        selectDropdownOption(selectAccountStatus);
        clickAndSelectRandomDay(selectIdVerificationDate);
//      NU treb de uitat de adaugat alte elemente
        WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(saveButton));
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    }

    public void fillEditForm() {
        By editNameField = By.cssSelector("input.form-control.js-maxlength[data-modal-field-id='edit_name'][maxlength='100']");
        By editAccountIdField = By.cssSelector("input.form-control.js-maxlength[name='account_id'][data-modal-field-id='edit_account_id']");
        enterText(editNameField, "GoogleAccountUpdatedTestJava");
        enterText(editAccountIdField, "2345678901");
        // Așteaptă ca editSaveButton să fie vizibil și clicabil
        WebElement editSaveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(editSaveButton));
        wait.until(ExpectedConditions.elementToBeClickable(editSaveBtn)).click();
    }

    public boolean searchAndVerify(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            // Pas 1: Introdu text în câmpul de căutare și așteaptă 1 secundă
            enterText(searchInput, keyword);
            TestListener.getTest().log(Status.INFO, "Text introdus în câmpul de căutare: " + keyword);

            Helpers.waitForSeconds(3);

            // Pas 2: Așteaptă ca indicatorul de încărcare să dispară (dacă există)
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".dataTables_processing")));
                TestListener.getTest().log(Status.INFO, "Încărcarea tabelului finalizată");
            } catch (TimeoutException e) {
                TestListener.getTest().log(Status.INFO, "Niciun indicator de încărcare detectat");
            }

            // Pas 3: Așteaptă ca antetele să fie prezente
            By tableHeaders = By.cssSelector(".dataTables_scrollHeadInner th");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableHeaders));

            // Pas 4: Așteaptă ca primul rând să fie prezent
            By firstRowCells = By.cssSelector("#google-accounts-list tbody tr:first-child td");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(firstRowCells));

            // Pas 5: Parcurge antetele și celulele, reîmprospătând referințele
            boolean found = false;
            List<WebElement> headers = driver.findElements(tableHeaders);
            int headerCount = headers.size();
            TestListener.getTest().log(Status.INFO, "Antete găsite: " + headerCount);

            for (int i = 0; i < headerCount; i++) {
                // Reîmprospătează lista antetelor pentru a evita stale elements
                headers = driver.findElements(tableHeaders);
                WebElement header = wait.until(ExpectedConditions.visibilityOf(headers.get(i)));

                // Scroll la antet pentru a-l face vizibil
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'start'});", header);

                // Așteaptă ca antetul să fie stabil după scroll
                wait.until(ExpectedConditions.visibilityOf(header));
                String headerText = header.getText().trim();

                // Reîmprospătează lista celulelor
                List<WebElement> firstRow = driver.findElements(firstRowCells);
                String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";

                // Loghează antetul și celula
                TestListener.getTest().log(Status.INFO, "Antet[" + i + "]: " + headerText + " -> Celulă[" + i + "]: " + content);

                // Verifică dacă keyword este în conținut
                if (content.contains(keyword)) {
                    found = true;
                }
            }

            TestListener.getTest().log(found ? Status.PASS : Status.FAIL,
                    found ? "Cuvânt cheie găsit: " + keyword : "Cuvânt cheie negăsit: " + keyword);
            return found;
        } catch (Exception e) {
            TestListener.getTest().log(Status.FAIL, "Eroare la căutarea/verificarea tabelului: " + e.getMessage());
            throw e;
        }
    }

    public void exportCsv() {
        WebElement exportBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(exportCsvButton));
        wait.until(ExpectedConditions.elementToBeClickable(exportBtn)).click();
        try {
            WebElement confirmBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmExportButton));
            wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
            Thread.sleep(5000);
            TestListener.getTest().log(Status.PASS,"Fișier CSV descărcat cu succes");
        } catch (Exception e) {
            TestListener.getTest().log(Status.FAIL,"Eroare la descărcarea fișierului CSV: " + e.getMessage());
        }
    }

    public void deleteAccount(String keyword) {
        try {
            // Pas 1: Introdu text în câmpul de căutare
            By searchField = By.cssSelector("input.form-control[type='search']");
            enterText(searchField, keyword);
            TestListener.getTest().log(Status.INFO, "Text introdus în câmpul de căutare: " + keyword);
            Thread.sleep(2000); // Pauză de 1 secundă

            // Pas 2: Așteaptă și selectează rândul
            WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowClick));
            wait.until(ExpectedConditions.elementToBeClickable(row)).click();
            TestListener.getTest().log(Status.INFO, "Rând selectat");
            Thread.sleep(2000); // Pauză de 1 secundă

            // Pas 3: Așteaptă și apasă butonul de ștergere
            WebElement deleteBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButton));
            wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
            TestListener.getTest().log(Status.INFO, "Buton de ștergere apăsat");
            Thread.sleep(2000); // Pauză de 1 secundă

            // Pas 4: Așteaptă și confirmă ștergerea
            WebElement confirmBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDeleteButton));
            wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
            TestListener.getTest().log(Status.INFO, "Ștergere confirmată");
            Thread.sleep(2000); // Pauză de 1 secundă

            // Log succes
            TestListener.getTest().log(Status.PASS, "Cont șters cu succes: " + keyword);
        } catch (InterruptedException e) {
            TestListener.getTest().log(Status.FAIL, "Eroare la pauza între pași: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void editAccount(String keyword) {
        By searchField = By.cssSelector("input.form-control[type='search']");
        enterText(searchField, keyword);
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowClick));
        wait.until(ExpectedConditions.elementToBeClickable(row)).click();
        WebElement editBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(editButton));
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
    }

    private void enterText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.clear();
        element.sendKeys(text);
    }

    private void selectDropdownOption(By locator) {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(selectElement));
        new Select(selectElement).selectByIndex(2);
    }

    private void clickAndSelectRandomDay(By locator) {
        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(dateInput)).click();
        // Așteaptă ca flatpickr să fie vizibil
        WebElement activeCalendar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flatpickr-calendar.open")));
        List<WebElement> days = activeCalendar.findElements(By.cssSelector(".flatpickr-day:not(.prevMonthDay):not(.nextMonthDay)"));
        if (!days.isEmpty()) {
            Random random = new Random();
            WebElement day = wait.until(ExpectedConditions.elementToBeClickable(days.get(random.nextInt(days.size()))));
            day.click();
        } else {
            TestListener.getTest().log(Status.INFO,"Nu există zile disponibile în calendar.");
        }
    }

    public void handleAutocomplete(By locator, String inputText, By autocompleteListLocator) {
        enterText(locator, inputText);
        // Așteaptă lista de autocomplete
        WebElement autocompleteList = wait.until(ExpectedConditions.visibilityOfElementLocated(autocompleteListLocator));
        List<WebElement> options = autocompleteList.findElements(By.tagName("li"));
        if (!options.isEmpty()) {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(options.get(0)));
            option.click();
        }
    }
}