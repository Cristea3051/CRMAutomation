package com.crm;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.utilities.Login;

public class test extends BaseTest {
private Login login;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
    }

    @Test(dataProvider = "MediaBuyerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password)throws InterruptedException {
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/mr-dashboard/google/at-mr-campaigns");

        String title = driver.getTitle();
        Reporter.log("Utilizatorul a navigat cu succes la pagina - " + title);

        Thread.sleep(3000);
           WebElement select = driver
                .findElement(By.name("google-at-mr-campaigns-list_length"));
        Select type = new Select(select);
        type.selectByValue("10");
        Thread.sleep(2000);

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

         wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fa.fa-caret-down"))).click();

        new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@style='display: block; top: 222.594px; left: auto; right: 0px;'] //li[@data-range-key='All Time']")))
                .click();
                Thread.sleep(6000);

                driver.findElement(By.id("scroll-top-dt-tables")).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("//h3[text()='ATMR Campaigns']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]")).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("//h3[text()='Per Offer Report']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]")).click();
                Thread.sleep(2000);
                try {
    WebElement arrowUpIcon = driver.findElement(By.xpath("//h3[text()='Daily breakdown Campaigns']/ancestor::div[@class='block-header']//i[contains(@class, 'si-arrow-up')]"));
    arrowUpIcon.click();
} catch (NoSuchElementException e) {
    Reporter.log("Elementul nu a fost găsit, sar peste click.");
}
                Thread.sleep(6000);

    }
}
