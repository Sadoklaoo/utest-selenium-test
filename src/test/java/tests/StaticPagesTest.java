package tests;

import org.testng.annotations.*;
import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Map;

public class StaticPagesTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testStaticPages() {
        Map<String,String> pages = new java.util.HashMap<>();
        pages.put("Terms of Use for uTest Testers",   "https://www.utest.com/terms-and-conditions");
        pages.put("Privacy Policy", "https://www.utest.com/privacy-policy");

        pages.forEach((expectedHeading, url) -> {
            driver.get(url);
                       // Wait for the H1 to be visible and grab its text
            WebElement h1 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
            );
             String actualHeading = h1.getText();
            // Assert the H1 contains the expected keyword
            assertTrue(
                actualHeading.contains(expectedHeading),
                "On " + url + ", expected H1 to contain '" + expectedHeading +
                "' but was '" + actualHeading + "'"
            );
        });
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
