package tests;

import org.testng.annotations.*;
import static org.testng.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import utils.Configuration;

import java.time.Duration;
import java.util.Arrays;

public class StaticPagesTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // 1) create driver based on config
        String browser = Configuration.get("browser");
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            // add more browsers here if you like…
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // 2) maximize window if wanted
       
            driver.manage().window().maximize();
        

        // 3) explicit wait from config
        int timeout = Configuration.getInt("timeout.seconds");
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    @Test(description="Verify that each static page has the correct H1")
    public void testStaticPages() {
        // read the list of page-keys
        String[] keys = Configuration.get("static.pages").split(",");

        for (String key : keys) {
            String url     = Configuration.get("static.page." + key + ".url");
            String heading = Configuration.get("static.page." + key + ".heading");

            driver.get(url);
            WebElement h1 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
            );
            String actual = h1.getText().trim();
            assertTrue(
                actual.contains(heading),
                String.format(
                    "On %s, expected H1 to contain '%s' but was '%s'",
                    url, heading, actual
                )
            );
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
