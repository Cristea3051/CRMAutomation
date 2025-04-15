package com.crm.GoogleAccounts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;

public class TableSettingsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private By tableSettingsButton = By.cssSelector(".fa-table");
    private By settingNameField = By.id("setting-name");
    private By nextButton = By.cssSelector(".btn[data-wizard='next']");
    private By applyButton = By.id("apply-swap-list-settings");
    private By deleteButton = By.cssSelector("i.fas.fa-trash.tw-text-red-600");
    private By confirmDeleteButton = By.cssSelector(".swal2-confirm");

    public TableSettingsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openSettings() {
        wait.until(ExpectedConditions.elementToBeClickable(tableSettingsButton)).click();
    }

    public void createSettings(String name, String[] columnsToSelect) {
        enterText(settingNameField, name);
        selectMultipleValuesByValue(columnsToSelect);
        clickNavigationButton("fa fa-arrow-circle-right");
        clickNext();
        clickApply();
        Reporter.log("Setări create cu succes: " + name);
    }

    public void deleteSettings() {
        clickNext();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton)).click();
        clickApply();
        Reporter.log("Setări șterse cu succes");
    }

    private void enterText(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    private void selectMultipleValuesByValue(String[] values) {
        // Reutilizează logica din SettingsHelper.selectMultipleValuesByValue
        // Ex. selectare valori din dropdown
    }

    private void clickNavigationButton(String iconClass) {
        driver.findElement(By.cssSelector("i." + iconClass)).click();
    }

    private void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    private void clickApply() {
        wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
    }
}