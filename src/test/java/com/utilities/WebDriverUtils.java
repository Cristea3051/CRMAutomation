package com.utilities;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;

import java.util.List;
import java.util.Random;

@Listeners(com.utilities.TestListener.class)
public class WebDriverUtils {
    private static final Logger logger = LogManager.getLogger(WebDriverUtils.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Constructor care primește driver și wait ca dependințe
    public WebDriverUtils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Mutăm funcția enterText
    public void enterText(By locator, String text) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
        logger.debug("Introdus text: " + text + " în câmpul " + locator);
    }

    // Mutăm funcția selectDropdownOption
    public void selectDropdownOption(By locator, int index) {
        WebElement selectElement = driver.findElement(locator);
        Select dropdown = new Select(selectElement);
        dropdown.selectByIndex(index);
        logger.debug("Selectat opțiunea cu index " + index + " din dropdown-ul " + locator);
    }

    // Mutăm funcția clickAndSelectRandomDay
    public void clickAndSelectRandomDay(By locator) {
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

    // Mutăm funcția handleAutocomplete
    public void handleAutocomplete(By locator, String inputText, By autocompleteListLocator) {
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
            throw e;
        }
    }
}