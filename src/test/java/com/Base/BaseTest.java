package com.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.resources.Helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected Properties locators;
    protected Properties inputInfo;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Încarcă fișierele de proprietăți
        locators = loadProperties(
                "/home/victorcristea/Documents/AutomationCRM/crmAuto/src/test/java/com/resources/configfiles/locators.properties");
        inputInfo = loadProperties(
                "/home/victorcristea/Documents/AutomationCRM/crmAuto/src/test/java/com/resources/configfiles/inputinfo.properties");
    }

    Helpers helpers = new Helpers(driver, locators);

    private Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
