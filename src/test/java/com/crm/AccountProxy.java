package com.crm;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;

public class AccountProxy extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/accounts-proxy");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);
        createProxy();
        updateProxy();
        createAndOrderTableSettings();
        deleteTableSettings();
        downloadCSVandDeleteProxy();
    }

    private void createProxy() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("create_proxy_button")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("create_name_input")))).sendKeys(inputInfo.getProperty("name"));

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("create_username_input")))).sendKeys(inputInfo.getProperty("username"));

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("create_password_input")))).sendKeys(inputInfo.getProperty("password"));

        WebElement selectSource = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("select_proxy_type"))));
        selectSource.click();
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        WebElement inputElement = driver.findElement(By.cssSelector(locators.getProperty("select_proxy_sorce")));

        Helpers.waitForSeconds(4);
        inputElement.sendKeys((inputInfo.getProperty("input_source")));

        Helpers.waitForSeconds(3);
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        WebElement createProxy = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("submit_proxy"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createProxy);
        createProxy.click();

        Helpers.waitForSeconds(4);
        Reporter.log("A fost creat poxyul");
        
        Helpers helpers = new Helpers(driver, locators);
        helpers.iterateAndLogTableData();
    }

    private void updateProxy() {
        WebElement proxyCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("checkbox_element"))));
        proxyCheckbox.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_header_button")))).click();
        WebElement editName = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_name_input"))));
        editName.clear();
        editName.sendKeys(inputInfo.getProperty("edited_name"));

        WebElement editUserName = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_username_input"))));
        editUserName.clear();
        editUserName.sendKeys(inputInfo.getProperty("edited_username"));

        WebElement editPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_password_input"))));
        editPassword.clear();
        editPassword.sendKeys(inputInfo.getProperty("edited_password"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("select_edited_proxy_type")))).click();
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        WebElement sourceName = driver.findElement(By.cssSelector(locators.getProperty("select_edted_proxy_sorce")));
        Helpers.waitForSeconds(4);
        sourceName.clear();
        sourceName.sendKeys(inputInfo.getProperty("input_source"));

        Helpers.waitForSeconds(3);
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        WebElement updateProxy = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("submit_edited_proxy"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", updateProxy);
        updateProxy.click();

        Helpers.waitForSeconds(5);
        Reporter.log("A fost updatat poxyul");
        Helpers showData = new Helpers(driver, locators);
        showData.iterateAndLogTableData();
    }

    private void createAndOrderTableSettings() {

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("table_setings_button")))).click();

        Helpers.waitForSeconds(3);
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("table_setings_name"))));

        nameInput.click();

        nameInput.sendKeys(inputInfo.getProperty("setting_name"));

        for (int i = 0; i < 3; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("select_column_to_hide"))))
                    .click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.w-100:nth-child(5)"))).click();
        }

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"swap-from\"]/option[2]"))).click();
        for (int i = 0; i < 3; i++) {
            WebElement moveDown = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("move_down"))));
            moveDown.click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

        Helpers.waitForSeconds(3);
        try {
            List<WebElement> elements = driver.findElements(By.xpath(locators.getProperty("find_second_setting")));

            if (elements.size() > 0) {
                elements.get(0).click();
            } else {
                driver.findElement(By.xpath(locators.getProperty("find_setting"))).click();
            }
        } catch (Exception e) {
            Reporter.log("Excepție: " + e.getMessage());
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:");

        Helpers.waitForSeconds(5);
        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();
    }

    private void deleteTableSettings() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("table_setings_button")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("delete_preset")))).click();

        Helpers.waitForSeconds(5);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-alt-primary:nth-child(2)"))).click();

        Helpers.waitForSeconds(5);
        driver.navigate().refresh();

        Reporter.log("A fost ștearsă cu succes setarea");
    }

    private void downloadCSVandDeleteProxy() {
        Helpers.waitForSeconds(5);
        WebElement selectCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("edited_chechbox_element"))));
        selectCheckbox.click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("csv_button")))).click();

        try {
            Thread.sleep(3000);
            WebElement confirmButton = driver.findElement(By.xpath(locators.getProperty("confirm_export_proxy")));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!");
        }

        Helpers.waitForSeconds(5);
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("delete_button"))));
        deleteButton.click();

        try {
            Thread.sleep(4000);
            WebElement confirmButton = driver.findElement(By.cssSelector(locators.getProperty("confirm_delete_modal")));
            confirmButton.click();

            Reporter.log("Proxy-ul a fost șters cu succes!");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut șterge proxy-ul!");
        }
        Helpers.waitForSeconds(5);
    }
}