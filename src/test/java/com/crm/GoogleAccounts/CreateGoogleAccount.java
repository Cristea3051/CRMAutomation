package com.crm.GoogleAccounts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.resources.configfiles.SettingsHelper;
import com.utilities.Login;

public class CreateGoogleAccount {
    private WebDriver driver;
    private WebDriverWait wait;
    private Login login;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        login = new Login(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        driver.findElement(By.cssSelector("button.btn-dual:nth-child(5)")).click();

        driver.findElement(By.cssSelector("textarea.form-control")).sendKeys("GoogleAccountNameTestJava");

        driver.findElement(By.cssSelector("input.form-control.js-maxlength[name='account_id'][data-modal-field-id='create_account_id']"))
        .sendKeys("1234567890");

        WebElement selectStatus = driver.findElement(By.name("status"));
        Select statusRow = new Select(selectStatus);
        statusRow.selectByIndex(0);

        WebElement selectUsage = driver.findElement(By.name("usage"));
        Select usageRow = new Select(selectUsage);
        usageRow.selectByIndex(0);

        driver.findElement(By.cssSelector("input.form-control.js-maxlength[name='other_fees'][data-modal-field-id='create_other_fees']")).sendKeys("1234");

        driver.findElement(By.cssSelector("input.form-control.js-maxlength[name='username'][data-modal-field-id='create_username']")).sendKeys("AutomationTestJavaUser");

        driver.findElement(By.cssSelector("input.form-control.js-maxlength[name='password'][data-modal-field-id='create_password']")).sendKeys("AutomationTestJavaPass");

        driver.findElement(By.cssSelector("input.form-control.js-maxlength[name='email_login'][data-modal-field-id='create_email_login']")).sendKeys("AutomationEmail@Login");

        driver.findElement(By.cssSelector("input.form-control.js-maxlength[name='email_password'][data-modal-field-id='create_email_password']")).sendKeys("AutomationEmail@Password");

        driver.findElement(By.cssSelector("input.form-control.js-maxlength[name='farmer_comments'][data-modal-field-id='create_farmer_comments']")).sendKeys("1comment / 2comments / 3comments");

        driver.findElement(By.cssSelector("input.form-control.js-maxlength[name='id_verification'][data-modal-field-id='create_id_verification']")).click();







}

}