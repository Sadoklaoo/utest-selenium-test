package tests;

import utils.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
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
    private WebDriverWait wait;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeClass
    public void setUp() {
        // -- ChromeOptions (e.g. headless, disable-infobars, etc.) --
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // uncomment to run headless

        driver = new ChromeDriver(options);
        // implicit wait from config
        driver.manage()
              .timeouts()
              .implicitlyWait(Duration.ofSeconds(
                  Configuration.getInt("timeout.default")
              ));

        wait = new WebDriverWait(driver, Duration.ofSeconds(
                  Configuration.getInt("timeout.default")
              ));

        loginPage = new LoginPage(driver);
        homePage  = new HomePage(driver);
    }

    @Test
    public void testValidLogin() throws InterruptedException {
        // open login page via base URL
        loginPage.open();

        // perform login using properties
        loginPage.login(
            Configuration.get("credentials.username"),
            Configuration.get("credentials.password")
        );

        // wait for the user-menu toggle to be clickable (matches HomePage locator)
        wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("nav-sidebar-dropdown-toggle.nav-sidebar-user-card-dropdown__arrow > button")
        ));

        // verify Sign Out is visible via the POM
        homePage.openUserMenu();
        assertTrue(homePage.isSignOutVisible(),
                   "Sign Out should be visible after login");

        // small pause before clicking
        Thread.sleep(500);

        // do logout
        homePage.signOut();

        System.out.println(Configuration.get("home.url"));
        assertEquals(driver.getCurrentUrl(),
                     Configuration.get("home.url"),
                     "After logout, should land on public uTest homepage");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
