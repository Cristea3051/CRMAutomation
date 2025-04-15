package com.crm.AccountProxy;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.resources.configfiles.SettingsHelper;
import com.utilities.Login;

public class TestAccountProxy extends BaseTest {
    private Login login;
    private WebDriverWait wait;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat" + "\n");

        login.closeDebugBar();

        driver.get("http://crm-dash/accounts-proxy");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title + "\n");
        createProxy();
        updateProxy();
        createAndOrderTableSettings();
        downloadCSVandDeleteProxy();
        deleteTableSettings();
    }

    private void createProxy() {
        Helpers.waitForSeconds(4);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fa-plus-circle"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create_name"))).sendKeys("21.226.116.124:4444");

        driver.findElement(By.id("create_username")).sendKeys("ProxyAutoUsername");

        driver.findElement(By.id("create_password")).sendKeys("ProxyAutoPass");

        WebElement select = driver
                .findElement(By.cssSelector("label[for='create_proxy_types'] ~ select[name='proxy_type']"));
        Select type = new Select(select);
        type.selectByValue("Residential");

        WebElement source = driver.findElement(By.cssSelector("input[data-modal-field-id='create_source_id']"));
        Helpers.waitForSeconds(4);
        source.sendKeys("ip");
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        driver.findElement(By.id("create-accounts-proxy-button")).click();

        Helpers.waitForSeconds(4);
        Reporter.log("A fost creat poxyul");

        Helpers helpers = new Helpers(driver, locators);
        helpers.iterateAndLogTableData();
    }

    private void updateProxy() {
        WebElement proxyCheckbox = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("checkbox_element"))));
        proxyCheckbox.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_header_button")))).click();
        WebElement editName = wait
                .until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_name_input"))));
        editName.clear();
        editName.sendKeys("21.226.116.124:4444");

        WebElement editUserName = wait
                .until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_username_input"))));
        editUserName.clear();
        editUserName.sendKeys(inputInfo.getProperty("edited_username"));

        WebElement editPassword = wait
                .until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("edit_password_input"))));
        editPassword.clear();
        editPassword.sendKeys(inputInfo.getProperty("edited_password"));

        WebElement select = driver
                .findElement(By.cssSelector("label[for='edit_proxy_type'] ~ select[name='proxy_type']"));
        Select type = new Select(select);
        type.selectByValue("Other");

        WebElement source = driver.findElement(By.cssSelector("input[data-modal-field-id='edit_source']"));
        source.clear();
        Helpers.waitForSeconds(4);
        source.sendKeys("Bla");
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        driver.findElement(By.id("edit-accounts-proxy-button")).click();

        Helpers.waitForSeconds(5);
        Reporter.log("A fost updatat poxyul" + "\n");
        Helpers showData = new Helpers(driver, locators);
        showData.iterateAndLogTableData();
    }

    private void createAndOrderTableSettings() {

        Helpers.waitForSeconds(3);

        driver.findElement(By.cssSelector(".fa-table")).click();

        Helpers.waitForSeconds(3);

        driver.findElement(By.id("setting-name")).sendKeys("ProxyAutoTableSetting");

        Helpers.waitForSeconds(3);

        SettingsHelper settingsHelper = new SettingsHelper(driver);

        Helpers.waitForSeconds(3);

        String[] valuesToSelect = { "Owner", "Updated At", "Created At" };
        settingsHelper.selectMultipleValuesByValue(valuesToSelect);

        settingsHelper.clickNavigationButton("fa fa-arrow-circle-right");

        settingsHelper.selectMultipleValuesByValue(new String[] { "Source" });
        settingsHelper.moveElements("fa fa-arrow-circle-up", 4);

        driver.findElement(By.cssSelector(".btn[data-wizard='next']")).click();

        Helpers.waitForSeconds(3);

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();

        Helpers.waitForSeconds(3);
        Reporter.log("A fost creat cu succes noua setare cu coloanele:" + "\n");

        Helpers.waitForSeconds(5);
        Helpers dataHelpers = new Helpers(driver, locators);
        dataHelpers.iterateAndLogTableData();
    }

    private void downloadCSVandDeleteProxy() {
        Helpers.waitForSeconds(5);
        WebElement selectCheckbox = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(
                        "//td[text()='21.226.116.124:4444']//preceding-sibling::td//input[@class='form-check-input']")));
        selectCheckbox.click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("csv_button")))).click();

        try {
            Helpers.waitForSeconds(3);
            WebElement confirmButton = driver.findElement(By.xpath(locators.getProperty("confirm_export_proxy")));
            confirmButton.click();

            Reporter.log("A fost descarcat cu success fiserul CSV" + "\n");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut descarca fisierul!" + "\n");
        }

        Helpers.waitForSeconds(5);
        WebElement deleteButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("delete_button"))));
        deleteButton.click();

        try {
            Helpers.waitForSeconds(5);
            WebElement confirmButton = driver.findElement(By.cssSelector(locators.getProperty("confirm_delete_modal")));
            confirmButton.click();

            Reporter.log("Proxy-ul a fost șters cu succes!" + "\n");
        } catch (Exception e) {
            Reporter.log("Eroare: Nu s-a putut șterge proxy-ul!" + "\n");
        }
        Helpers.waitForSeconds(5);
    }

    private void deleteTableSettings() {
        wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(locators.getProperty("table_setings_button"))))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("next_button")))).click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("delete_preset")))).click();

        Helpers.waitForSeconds(5);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(locators.getProperty("apply_button")))).click();

        Helpers.waitForSeconds(3);

        Reporter.log("A fost ștearsă cu succes setarea" + "\n");
    }
}