package com.resources.configfiles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SettingsHelper {

    private WebDriver driver;

    public SettingsHelper(WebDriver driver) {
        this.driver = driver;
    }

    // Selectează valorile multiple în funcție de opțiuni
    public void selectMultipleValuesByValue(String[] values) {
        WebElement selectElement = driver.findElement(By.cssSelector("select[id='swap-from']"));
        Select settings = new Select(selectElement);

        if (settings.isMultiple()) {
            for (String value : values) {
                settings.selectByValue(value);
            }
        }
    }

    // Apasă pe butonul de navigare
    public void clickNavigationButton(String iconClass) {
        driver.findElement(By.cssSelector("i[class='" + iconClass + "']")).click();
    }

    // Mută elementele folosind săgețile
    public void moveElements(String iconClass, int times) {
        for (int i = 0; i < times; i++) {
            clickNavigationButton(iconClass);
        }
    }
}
