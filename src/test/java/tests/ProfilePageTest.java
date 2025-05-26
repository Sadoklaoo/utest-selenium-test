package tests;

import utils.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProfilePage;
import static org.testng.Assert.*;

import java.time.Duration;

public class ProfilePageTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;

    @BeforeClass
    public void setUp() {
        // — ChromeOptions (headless, disable-infobars, etc.) —
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");

        driver = new ChromeDriver(options);

        // unified implicit wait
        driver.manage()
              .timeouts()
              .implicitlyWait(Duration.ofSeconds(
                  Configuration.getInt("timeout.default")
              ));

        // maximize window if desired
        
         driver.manage().window().maximize();
        

        loginPage   = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        // 1) Login
        loginPage.open();
        loginPage.login(
            Configuration.get("credentials.username"),
            Configuration.get("credentials.password")
        );

        // 2) Navigate to “My Profile” edit page
        profilePage.open();
    }

    @Test(description = "Edit Facebook link and About Me, then save and verify success toast")
    public void testEditProfileAndSave() {
        String fbUrl = Configuration.get("profile.facebookUrl");
        profilePage.toggleFacebook(true);
        profilePage.setFacebookLink(fbUrl);
        assertEquals(profilePage.getFacebookLink(), fbUrl,
            "Facebook link input should reflect the URL entered");

        String aboutText = Configuration.get("profile.aboutMe");
        profilePage.setAboutMe(aboutText);
        assertEquals(profilePage.getAboutMe(), aboutText,
            "About Me textarea should contain the text entered");

        profilePage.saveChanges();
        String toastMessage = profilePage.waitForSuccessToast();
        assertEquals(toastMessage, Configuration.get("profile.expectedToast"),
            "A success toast with the expected message should appear");
    }

    @Test(description = "Select gender radio button and verify selection")
    public void testSelectGender() {
        for (String code : new String[]{ "M", "F" }) {
            profilePage.selectGender(code);
            assertEquals(profilePage.getSelectedGender(), code,
                "Selected gender radio should be '" + code + "'");
        }
    }

    @Test(description = "Select country from dropdown and verify selection")
    public void testSelectCountry() {
        String country = Configuration.get("profile.country");
        profilePage.selectCountry(country);
        assertEquals(profilePage.getSelectedCountry(), country,
            "Selected country should be '" + country + "'");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
