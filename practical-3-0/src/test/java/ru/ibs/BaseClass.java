package ru.ibs;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    public static WebDriver driver;


    @BeforeAll
    public static void setup() {
        driver = new ChromeDriver();


        System.setProperty("webdriver.chrome.driver", "\\src\\test\\resources\\chromedriver.exe");

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        driver.get("http://localhost:8080/food");

        TakesScreenshot scrShot =((TakesScreenshot)driver);

        
    }

    @AfterAll
    public static void tearDown(){

        driver.quit();

    }

}
