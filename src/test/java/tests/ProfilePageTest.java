package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import static org.testng.Assert.*;

import pages.LoginPage;
import pages.ProfilePage;

public class ProfilePageTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private final String user = "sadok.laouissi.sl@gmail.com";
    private final String pass = "?+aYvt;9EwF+ek!";

    @BeforeClass
    public void setUp() {
        // Initialize WebDriver and page objects
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        // Perform login once for all tests
        loginPage.open();
        loginPage.login(user, pass);

        // Navigate to profile page
        profilePage.open();
    }

    @Test
    public void testEditProfileAndSave() {
        // Enable Facebook Link and set URL
        profilePage.toggleFacebook(true);
        String fbUrl = "https://facebook.com/Sadoklao/";
        profilePage.setFacebookLink(fbUrl);
        assertEquals(profilePage.getFacebookLink(), fbUrl,
                     "Facebook link input should reflect the URL entered");

        // Set About Me text and verify
        String aboutText = "Experienced Selenium tester.";
        profilePage.setAboutMe(aboutText);
        assertEquals(profilePage.getAboutMe(), aboutText,
                     "About Me textarea should contain the text entered");

        // Save changes and verify success toast
        profilePage.saveChanges();
        String toastMessage = profilePage.waitForSuccessToast();
        assertEquals(toastMessage, "Changes saved",
                     "A success toast with 'Changes saved' should appear after saving");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
