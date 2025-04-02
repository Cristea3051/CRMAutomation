package com.Base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.resources.Helpers;
import com.utilities.Login;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Properties locators;
    protected Properties inputInfo;
    protected Helpers helpers;
    protected Login login;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
         options.addArguments("--headless");
         options.addArguments("--disable-gpu");
         options.addArguments("--window-size=1920,1080");
         options.addArguments("--no-sandbox");
         options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        locators = loadProperties(
                "/home/victorcristea/Documents/AutomationCRM/crmAuto/src/test/java/com/resources/configfiles/locators.properties");
        inputInfo = loadProperties(
                "/home/victorcristea/Documents/AutomationCRM/crmAuto/src/test/java/com/resources/configfiles/inputinfo.properties");

        helpers = new Helpers(driver, locators);
        login = new Login(driver);

        logger.info("Driver inițializat și configurat pentru test");
    }

    private Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            logger.debug("Fișier de proprietăți încărcat: " + filePath);
        } catch (IOException e) {
            logger.error("Eroare la încărcarea fișierului de proprietăți: " + filePath, e);
            e.printStackTrace();
        }
        return properties;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver închis după test");
        }
    }
}