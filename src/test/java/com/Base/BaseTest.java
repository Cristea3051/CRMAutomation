package com.Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
  protected WebDriver driver;
  protected Properties locators;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        locators = new Properties();
        try {
            FileInputStream fis = new FileInputStream("/home/victorcristea/Documents/AutomationCRM/crmAuto/src/test/java/com/resources/configfiles/locators.properties");
            locators.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
