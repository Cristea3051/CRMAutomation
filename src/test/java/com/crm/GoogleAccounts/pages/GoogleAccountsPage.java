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
    // Adaugă locatori pentru toate câmpurile din formular

    public GoogleAccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateTo() {
        driver.get("http://crm-dash/google-accounts");
        Reporter.log("Navigat la pagina: " + driver.getTitle());
    }

    public void selectRows(int index) {
        WebElement select = wait.until(ExpectedConditions.visibilityOfElementLocated(rowsSelect));
        new Select(select).selectByIndex(index);
    }

    public void clickCreateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }

    public void fillCreateForm() {
        enterText(accountNameField, "GoogleAccountNameTestJava");
        enterText(accountIdField, "1234567890");
        selectDropdownOption(By.cssSelector("select.form-control.js-maxlength[name='status'][data-modal-field-id='create_status']"), 2);
        // Adaugă restul câmpurilor din CreateGoogleAccount.fillFormFields
        clickAndSelectRandomDay(By.cssSelector("input.form-control.js-maxlength[name='id_verification'][data-modal-field-id='create_id_verification']"));
        driver.findElement(saveButton).click();
    }

    public void fillEditForm() {
        enterText(By.cssSelector("input.form-control.js-maxlength[data-modal-field-id='edit_name'][maxlength='100']"), "GoogleAccountUpdatedTestJava");
        enterText(By.cssSelector("input.form-control.js-maxlength[name='account_id'][data-modal-field-id='edit_account_id']"), "2345678901");
        // Adaugă restul câmpurilor din UpdateGoogleAccount.fillFormFields
        driver.findElement(editSaveButton).click();
    }

    public boolean searchAndVerify(String keyword) {
        enterText(searchInput, keyword);
        List<WebElement> headers = driver.findElements(tableHeaders);
        List<WebElement> firstRow = driver.findElements(firstRowCells);
        boolean found = false;

        for (int i = 0; i < headers.size(); i++) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headers.get(i));
            String header = headers.get(i).getText().trim();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";
            Reporter.log(header + " -> " + content);

            if (content.contains(keyword)) {
                found = true;
            }
        }
        return found;
    }

    public void exportCsv() {
        wait.until(ExpectedConditions.elementToBeClickable(exportCsvButton)).click();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(confirmExportButton)).click();
            Reporter.log("Fișier CSV descărcat cu succes");
        } catch (Exception e) {
            Reporter.log("Eroare la descărcarea fișierului CSV: " + e.getMessage());
        }
    }

    public void deleteAccount(String keyword) {
        enterText(By.cssSelector("input.form-control[type='search']"), keyword);
        wait.until(ExpectedConditions.elementToBeClickable(rowClick)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton)).click();
        Reporter.log("Cont șters cu succes: " + keyword);
    }

    public void editAccount(String keyword) {
        enterText(By.cssSelector("input.form-control[type='search']"), keyword);
        wait.until(ExpectedConditions.elementToBeClickable(rowClick)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
    }

    private void enterText(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    private void selectDropdownOption(By locator, int index) {
        WebElement selectElement = driver.findElement(locator);
        new Select(selectElement).selectByIndex(index);
    }

    private void clickAndSelectRandomDay(By locator) {
        driver.findElement(locator).click();
        WebElement activeCalendar = driver.findElement(By.cssSelector(".flatpickr-calendar.open"));
        List<WebElement> days = activeCalendar.findElements(By.cssSelector(".flatpickr-day:not(.prevMonthDay):not(.nextMonthDay)"));
        if (!days.isEmpty()) {
            Random random = new Random();
            days.get(random.nextInt(days.size())).click();
        } else {
            Reporter.log("Nu există zile disponibile în calendar.");
        }
    }

    // Reutilizează handleAutocomplete din CreateGoogleAccount
    public void handleAutocomplete(By locator, String inputText, By autocompleteListLocator) {
        // Copiază metoda handleAutocomplete din CreateGoogleAccount.java
    }
}