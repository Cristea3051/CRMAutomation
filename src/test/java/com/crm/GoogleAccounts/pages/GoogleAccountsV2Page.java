package com.crm.GoogleAccounts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GoogleAccountsV2Page {
    private final WebDriver driver;

    private By settingsButton = By.cssSelector("button.tw-mr-1:nth-child(5)");
    private By saveButton = By.id("save-modal-swap-list");
    private By applyButton = By.id("apply-swap-list-settings");
    private By deleteButton = By.cssSelector("i.fa-trash");
    private By confirmDeleteButton = By.cssSelector("button.swal2-confirm");

    public GoogleAccountsV2Page(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get("http://crm-dash/google-accounts-v2");
    }

    public List<String> getTableHeaders() {
        return printTableHeaders();
    }

    public void createSettings(String name, String[] columnsToSelect) {
        driver.findElement(settingsButton).click();
        driver.findElement(By.id("setting-name")).sendKeys(name);
        // Reutilizează logica din SettingsHelper pentru selectare coloane
        driver.findElement(saveButton).click();
        driver.findElement(applyButton).click();
        Reporter.log("Setări create pentru google-accounts-v2: " + name);
    }

    public void deleteSettings() {
        driver.findElement(settingsButton).click();
        driver.findElement(saveButton).click();
        driver.findElement(deleteButton).click();
        driver.findElement(confirmDeleteButton).click();
        driver.findElement(applyButton).click();
        Reporter.log("Setări șterse pentru google-accounts-v2");
    }

    private List<String> printTableHeaders() {
        WebElement scrollBar = driver.findElement(By.cssSelector("revogr-scroll-virtual.horizontal.hydrated"));
        Set<String> foundHeaders = new HashSet<>();
        List<String> headerList = new ArrayList<>();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = 0;", scrollBar);
        double initialScroll = 0.0;
        boolean canScroll = true;

        while (canScroll) {
            List<WebElement> headers = driver.findElements(By.cssSelector("div.rgHeaderCell div.header-content"));
            for (WebElement header : headers) {
                String cleanHeader = header.getText().trim();
                if (header.isDisplayed() && !cleanHeader.isEmpty() && !foundHeaders.contains(cleanHeader)) {
                    foundHeaders.add(cleanHeader);
                    headerList.add(cleanHeader);
                }
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft += 300;", scrollBar);
            Object scrollResult = ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollLeft;", scrollBar);
            double newScroll = ((Number) scrollResult).doubleValue();
            if (newScroll == initialScroll) {
                canScroll = false;
            }
            initialScroll = newScroll;
        }
        return headerList;
    }
}