package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.HomePage;
import static org.testng.Assert.*;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        homePage  = new HomePage(driver);
    }

    @Test
    public void testValidLogin() throws InterruptedException {
        loginPage.open();
        loginPage.login("sadok.laouissi.sl@gmail.com", "?+aYvt;9EwF+ek!");

        
        // Wait for the user menu toggle to appear (indicates post‐login UI is ready)
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("nav-sidebar-dropdown-toggle")
            ));

        // Now open the menu and sign out
        homePage.openUserMenu();
        assertTrue(homePage.isSignOutVisible());

        // Give the server a moment before clicking sign out
        Thread.sleep(1000);

        homePage.signOut();

        
        // Wait until the URL is exactly https://www.utest.com/
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.urlToBe("https://www.utest.com/"));

        // Assert we’re on the homepage
        assertEquals(driver.getCurrentUrl(), "https://www.utest.com/",
                    "After logout, should be redirected to the uTest homepage");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
