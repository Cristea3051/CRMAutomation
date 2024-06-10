package com.crm;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {
    public static void main(String[] args) {
        // Inițializează ChromeDriver folosind WebDriverManager
       

        // Inițializează instanța WebDriver pentru Chrome
        WebDriver driver = new ChromeDriver();

        // Deschide browser-ul și navighează la adresa specificată
        driver.get("http://crm-dash/login");

        try {
            // Așteaptă 3 secunde
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("login-username")).sendKeys("victor.cristea@vebo.io");
        driver.findElement(By.id("login-password")).sendKeys("j8L3pc5hJ20Sjn10Lp!");
        
        driver.findElement(By.tagName("button")).click();


        // Închide browser-ul
        driver.quit();
    }
}
