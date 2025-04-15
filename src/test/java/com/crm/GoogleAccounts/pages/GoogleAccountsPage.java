package com.crm.GoogleAccounts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class GoogleAccountsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locatori
    private By createButton = By.cssSelector("button.btn-dual:nth-child(5)");
    private By searchInput = By.cssSelector("input.form-control-sm");
    private By tableHeaders = By.cssSelector(".dataTables_scrollHeadInner > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(1) > th");
    private By firstRowCells = By.cssSelector("#google-accounts-list tbody tr:first-child td");
    private By rowsSelect = By.name("google-accounts-list_length");
    private By exportCsvButton = By.xpath("//button[@title='Export to CSV File' and contains(@class, 'buttons-csv') and @aria-controls='google-accounts-list']");
    private By confirmExportButton = By.id("google-accounts-list-export-button");
    private By editButton = By.cssSelector("i.fa-edit");
    private By deleteButton = By.cssSelector("i.fa-trash-alt");
    private By confirmDeleteButton = By.cssSelector("button.swal2-confirm[type='button']");
    private By rowClick = By.cssSelector("td.text-center.desktop.sorting_1.dtfc-fixed-left");

    // Locatori formular creare/editare
    private By accountNameField = By.cssSelector("textarea.form-control");
    private By accountIdField = By.cssSelector("input.form-control.js-maxlength[name='account_id'][data-modal-field-id='create_account_id']");
    private By saveButton = By.cssSelector("button#create-google-accounts-button");
    private By editSaveButton = By.id("edit-google-accounts-button");

    public GoogleAccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateTo() {
        driver.get("http://crm-dash/google-accounts");
        // Așteaptă ca pagina să fie încărcată complet
        wait.until(ExpectedConditions.urlContains("google-accounts"));
        Reporter.log("Navigat la pagina: " + driver.getTitle());
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
        selectDropdownOption(By.cssSelector("select.form-control.js-maxlength[name='status'][data-modal-field-id='create_status']"), 2);
        clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='id_verification'][data-modal-field-id='create_id_verification']"));
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
        enterText(searchInput, keyword);
        // Așteaptă ca tabelul să fie vizibil
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableHeaders));
        List<WebElement> headers = driver.findElements(tableHeaders);
        List<WebElement> firstRow = driver.findElements(firstRowCells);
        boolean found = false;

        for (int i = 0; i < headers.size(); i++) {
            WebElement header = wait.until(ExpectedConditions.visibilityOf(headers.get(i)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", header);
            String headerText = header.getText().trim();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";
            Reporter.log(headerText + " -> " + content);

            if (content.contains(keyword)) {
                found = true;
            }
        }
        return found;
    }

    public void exportCsv() {
        WebElement exportBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(exportCsvButton));
        wait.until(ExpectedConditions.elementToBeClickable(exportBtn)).click();
        try {
            WebElement confirmBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmExportButton));
            wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
            Reporter.log("Fișier CSV descărcat cu succes");
        } catch (Exception e) {
            Reporter.log("Eroare la descărcarea fișierului CSV: " + e.getMessage());
        }
    }

    public void deleteAccount(String keyword) {
        By searchField = By.cssSelector("input.form-control[type='search']");
        enterText(searchField, keyword);
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowClick));
        wait.until(ExpectedConditions.elementToBeClickable(row)).click();
        WebElement deleteBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButton));
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        WebElement confirmBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDeleteButton));
        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
        Reporter.log("Cont șters cu succes: " + keyword);
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

    private void selectDropdownOption(By locator, int index) {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(selectElement));
        new Select(selectElement).selectByIndex(index);
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
            Reporter.log("Nu există zile disponibile în calendar.");
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