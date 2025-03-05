package com.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.resources.Helpers;
import com.utilities.Login; // Importă Login

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
    protected Login login; // Adaugă instanța pentru Login

    @BeforeMethod
    public void setUp() {
        // Configurarea opțiunilor pentru ChromeDriver în mod Headless
        ChromeOptions options = new ChromeOptions();
         options.addArguments("--headless");
         options.addArguments("--disable-gpu");
         options.addArguments("--window-size=1920,1080");
         options.addArguments("--no-sandbox");
         options.addArguments("--disable-dev-shm-usage");

        // Crearea instanței ChromeDriver cu opțiunile configurate
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        
        // Creăm o instanță de WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Încarcă fișierele de proprietăți
        locators = loadProperties(
                "/home/victorcristea/Documents/AutomationCRM/crmAuto/src/test/java/com/resources/configfiles/locators.properties");
        inputInfo = loadProperties(
                "/home/victorcristea/Documents/AutomationCRM/crmAuto/src/test/java/com/resources/configfiles/inputinfo.properties");

        // Crează instanța de Helpers
        helpers = new Helpers(driver, locators);

        // Crează instanța de Login
        login = new Login(driver); // Inițializează Login
    }

    // Metodă pentru a încărca fișierele de proprietăți
    private Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    // Metodă pentru a închide driver-ul după fiecare test
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
