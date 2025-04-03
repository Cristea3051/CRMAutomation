package com.crm.GoogleAccounts.OtherTests;

import com.Base.BaseTest;
import com.aventstack.extentreports.Status;
import com.resources.CredentialsProvider;
import com.resources.Helpers;
import com.utilities.Login;
import com.utilities.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Listeners(TestListener.class)
public class FilterGoogleVueTest extends BaseTest {
    private Login login;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        login = new Login(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dataProvider = "GlobalCred", dataProviderClass = CredentialsProvider.class)
    public void signIn(String username, String password) {
        login.performLogin(username, password);
        TestListener.getTest().log(Status.PASS, "Utilizator " + username + " s-a logat");

        login.closeDebugBar();

        driver.get("http://crm-dash/google-accounts-v2");
        Helpers.waitForSeconds(2);

        TestListener.getTest().log(Status.INFO, "Începem testarea sortării coloanelor:");
        testColumnSortingWithScroll();
    }

    private void testColumnSortingWithScroll() {
        WebElement scrollBar = driver.findElement(By.cssSelector("revogr-scroll-virtual.horizontal.hydrated"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollLeft = 0;", scrollBar);
        Helpers.waitForSeconds(1);

        int headerIndex = 0;
        boolean canScroll = true;
        Set<String> processedHeaders = new HashSet<>(); // Pentru a evita procesarea acelorași headere

        while (canScroll) {
            List<WebElement> headers = driver.findElements(By.cssSelector("div.rgHeaderCell div.header-content"));

            for (WebElement header : headers) {
                String headerText = header.getText().trim();
                if (!headerText.isEmpty() && header.isDisplayed() && !processedHeaders.contains(headerText)) {
                    processedHeaders.add(headerText);
                    TestListener.getTest().log(Status.INFO, "Testăm sortarea pentru header: " + headerText);

                    wait.until(d -> header.isDisplayed() && header.isEnabled());
                    header.click();
                    Helpers.waitForSeconds(4);

                    List<String> columnData = getColumnData(headerIndex);
                    boolean isAscending = checkAscendingOrder(columnData);
                    boolean isDescending = checkDescendingOrder(columnData);

                    if (isAscending) {
                        TestListener.getTest().log(Status.PASS, "Coloana '" + headerText + "' este sortată ascendent.");
                    } else if (isDescending) {
                        TestListener.getTest().log(Status.PASS, "Coloana '" + headerText + "' este sortată descendent.");
                    } else {
                        TestListener.getTest().log(Status.FAIL, "Coloana '" + headerText + "' nu este sortată corect.");
                    }

                    wait.until(d -> header.isDisplayed() && header.isEnabled());
                    header.click();
                    Helpers.waitForSeconds(4);

                    columnData = getColumnData(headerIndex);
                    isAscending = checkAscendingOrder(columnData);
                    isDescending = checkDescendingOrder(columnData);

                    if (isAscending) {
                        TestListener.getTest().log(Status.PASS, "Coloana '" + headerText + "' (al doilea click) este sortată ascendent.");
                    } else if (isDescending) {
                        TestListener.getTest().log(Status.PASS, "Coloana '" + headerText + "' (al doilea click) este sortată descendent.");
                    } else {
                        TestListener.getTest().log(Status.FAIL, "Coloana '" + headerText + "' (al doilea click) nu este sortată corect.");
                    }

                    headerIndex++;
                }
            }

            double scrollLeft = ((Number) js.executeScript("return arguments[0].scrollLeft;", scrollBar)).doubleValue();
            double scrollWidth = ((Number) js.executeScript("return arguments[0].scrollWidth;", scrollBar)).doubleValue();
            double clientWidth = ((Number) js.executeScript("return arguments[0].clientWidth;", scrollBar)).doubleValue();

            if (scrollLeft + clientWidth >= scrollWidth) {
                TestListener.getTest().log(Status.INFO, "Am ajuns la capătul scrollbar-ului. Test finalizat.");
                canScroll = false;
            } else {
                js.executeScript("arguments[0].scrollLeft += 300;", scrollBar);1
                Helpers.waitForSeconds(1);

                double newScroll = ((Number) js.executeScript("return arguments[0].scrollLeft;", scrollBar)).doubleValue();
                if (newScroll == scrollLeft) {
                    TestListener.getTest().log(Status.INFO, "Scroll-ul nu mai avansează. Test finalizat.");
                    canScroll = false;
                }
            }
        }
    }

    private List<String> getColumnData(int columnIndex) {
        List<String> columnData = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.cssSelector("div.rgRow"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.cssSelector("div.rgCell"));
            if (columnIndex < cells.size()) {
                String cellText = cells.get(columnIndex).getText().trim();
                if (!cellText.isEmpty()) {
                    columnData.add(cellText);
                }
            }
        }
        return columnData;
    }

    private boolean checkAscendingOrder(List<String> data) {
        if (data.isEmpty()) return true;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).compareTo(data.get(i - 1)) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDescendingOrder(List<String> data) {
        if (data.isEmpty()) return true;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).compareTo(data.get(i - 1)) > 0) {
                return false;
            }
        }
        return true;
    }

}