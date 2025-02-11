package com.crm.BatchAccounts;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select; // Importă Select
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.Base.BaseTest;
import com.resources.CredentialsProvider;
import com.resources.Helpers;

public class DeleteTableSettings extends BaseTest { // Moștenește BaseTest

    @Test(dataProvider = "FarmerGlobalCredentials", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        // Nu mai trebuie să scrii setările pentru driver, acestea sunt deja în setUp() din BaseTest
        login.performLogin(username, password);
        Reporter.log("Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/batch-accounts");

        WebElement select = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("accounts-batch-list_length")));
        Select rows = new Select(select); // Asigură-te că importi corect Select
        rows.selectByIndex(0);

        Helpers.waitForSeconds(3);

        driver.findElement(By.cssSelector(".fa-table")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn[data-wizard='next']"))).click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.fas.fa-trash.text-red-600"))).click();

        Helpers.waitForSeconds(3);
        WebElement deletePreset = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".swal2-confirm")));
        deletePreset.click();

        Helpers.waitForSeconds(3);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("apply-swap-list-settings"))).click();
        Helpers.waitForSeconds(3);

        Reporter.log("A fost ștearsă cu succes setarea" + "\n");

        List<WebElement> headers = driver.findElements(By.cssSelector(
                ".dataTables_scrollHeadInner > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(1) > th"));
        List<WebElement> firstRow = driver.findElements(By.cssSelector("#accounts-batch-list tbody tr:first-child td"));

        for (int i = 0; i < headers.size(); i++) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headers.get(i));
            
            String header = headers.get(i).getAttribute("innerText").trim();
            String content = (i < firstRow.size()) ? firstRow.get(i).getText().trim() : "";

            Reporter.log("Index: " + i + ", Text: '" + header + "'");
            if (!header.isEmpty()) {
                Reporter.log(header + " -> " + content);
            } else {
                Reporter.log("Header is empty for index: " + i);
            }
        }

        Helpers.waitForSeconds(2);
    }
}
