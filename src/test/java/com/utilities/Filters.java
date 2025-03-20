package com.utilities;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
public class Filters {
    private WebDriver driver;
    private JavascriptExecutor jsc;

    public Filters(WebDriver driver) {
        this.driver = driver;
        this.jsc = (JavascriptExecutor) driver;
    }

    public void applyRandomFilters() throws InterruptedException {
        String currentUrl = driver.getCurrentUrl();
        String[] urlParts = currentUrl.split("/");
        String locator = urlParts[urlParts.length - 1];  // Extract the last part of the URL

        // Expand the filter section
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("div." + locator + "-list-filter-title")).click();
        Thread.sleep(2000);

        // Get all filter items
        List<WebElement> filterItems = driver.findElements(By.cssSelector(".multiselect-item"));
        List<String> filterNames = getFilterNames(filterItems);

        // Ensure there are enough filters
        if (filterNames.size() < 3) {
            TestListener.getTest().log(Status.INFO,"Not enough filters available to select 3.");
            return;
        }

        // Randomly select and interact with 3 filters
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            String randomFilterName = getRandomFilter(random, filterNames);
            TestListener.getTest().log(Status.INFO,"Selected filter: " + randomFilterName);

            WebElement filterElement = driver.findElement(By.cssSelector("div[data-collapse='" + randomFilterName + "']"));
            filterElement.click();
            Thread.sleep(2000);

            interactWithFilter(driver, jsc, locator, randomFilterName);

            // Close the filter after interaction
            driver.findElement(By.cssSelector("div[data-collapse='" + randomFilterName + "']")).click();
            Thread.sleep(2000);
        }

        // Click outside to confirm the selections
        driver.findElement(By.id(locator + "-list_wrapper")).click();
        Thread.sleep(2000);
    }
    // Get filter names from filter elements
    public static List<String> getFilterNames(List<WebElement> filterItems) {
        List<String> filterNames = new ArrayList<>();
        for (WebElement item : filterItems) {
            String filterName = item.getText().replaceAll(" ", "");
            filterNames.add(filterName);
        }
        return filterNames;
    }

    // Select a random filter
    public static String getRandomFilter(Random random, List<String> filterNames) {
        int randomFilterIndex = random.nextInt(filterNames.size());
        return filterNames.get(randomFilterIndex);
    }

    // Interact with checkboxes, date ranges, and min/max inputs
    public static void interactWithFilter(WebDriver driver, JavascriptExecutor jsc, String locator, String randomFilterName) throws InterruptedException {
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("#" + locator + "-list-filter-" + randomFilterName + " .custom-control-input"));
        List<WebElement> dateRangeInputs = driver.findElements(By.cssSelector("#" + locator + "-list-filter-" + randomFilterName + " .js-flatpickr.date-range-input"));
        List<WebElement> minMaxInputs = driver.findElements(By.cssSelector("#" + locator + "-list-filter-" + randomFilterName + " input[placeholder='Min'], #" + locator + "-list-filter-" + randomFilterName + " input[placeholder='Max']"));

        if (!checkboxes.isEmpty()) {
            selectRandomCheckboxes(driver, checkboxes, jsc);
        } else if (!dateRangeInputs.isEmpty()) {
            selectDateRange(driver, jsc, dateRangeInputs);
        } else if (minMaxInputs.size() == 2) {
            setRandomMinMax(driver, minMaxInputs);
        } else {
            TestListener.getTest().log(Status.INFO,"No valid inputs found for the filter.");
        }
    }

    // Select 3 random checkboxes
    public static void selectRandomCheckboxes(WebDriver driver, List<WebElement> checkboxes, JavascriptExecutor js) throws InterruptedException {
        Random random = new Random();
        for (int j = 0; j < 3 && j < checkboxes.size(); j++) {
            int randomCheckboxIndex = random.nextInt(checkboxes.size());
            WebElement checkbox = checkboxes.get(randomCheckboxIndex);
            js.executeScript("arguments[0].click();", checkbox);
            Thread.sleep(2000);
            TestListener.getTest().log(Status.INFO,"Checkbox clicked with value: " + checkbox.getAttribute("value"));
        }
    }

    // Select a date range from the calendar
    public static void selectDateRange(WebDriver driver, JavascriptExecutor jsc, List<WebElement> dateRangeInputs) throws InterruptedException {
        TestListener.getTest().log(Status.INFO,"Selecting date range.");
        WebElement dateRangeInput = dateRangeInputs.get(0);
        Thread.sleep(1000);

        String placeholderValue = dateRangeInput.getAttribute("placeholder");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inputDatarange = driver.findElement(By.cssSelector(".js-flatpickr.date-range-input[placeholder='" + placeholderValue + "']"));
        jsc.executeScript("arguments[0].click();", inputDatarange);
        Thread.sleep(3000);

        driver.findElement(By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//span[text()='13']")).click();
        WebElement prevMonth = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.flatpickr-calendar.open span.flatpickr-prev-month")));
        for (int k = 0; k < 4; k++) {
            prevMonth.click();
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//span[text()='13']")).click();
    }

    // Set random values for min and max inputs
    public static void setRandomMinMax(WebDriver driver, List<WebElement> minMaxInputs) {
        Random random = new Random();
        int randomMinValue = random.nextInt(1000) + 1;  // Random value between 1 and 1000
        int randomMaxValue = random.nextInt(4000) + 1001;  // Random value between 1001 and 5000

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Set min value
        WebElement minInput = minMaxInputs.get(0);
        wait.until(ExpectedConditions.visibilityOf(minInput));
        minInput.clear();
        minInput.sendKeys(String.valueOf(randomMinValue));

        // Set max value
        WebElement maxInput = minMaxInputs.get(1);
        wait.until(ExpectedConditions.visibilityOf(maxInput));
        maxInput.clear();
        maxInput.sendKeys(String.valueOf(randomMaxValue));

        TestListener.getTest().log(Status.INFO,"Min value set to: " + randomMinValue + ", Max value set to: " + randomMaxValue);
    }
}
