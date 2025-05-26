package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.ProfilePage;

import static org.testng.Assert.*;

/**
 * TestNG tests for the uTest profile edit functionality using a dedicated ProfilePage object.
 * Includes tests for input fields, textarea, radio buttons, and dropdowns.
 */
public class ProfilePageTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private final String user = "sadok.laouissi.sl@gmail.com";
    private final String pass = "?+aYvt;9EwF+ek!";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        // Login once
        loginPage.open();
        loginPage.login(user, pass);

        // Navigate to profile edit
        profilePage.open();
    }

    @Test(description = "Edit Facebook link and About Me, then save and verify success toast")
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

    @Test(description = "Select gender radio button and verify selection")
    public void testSelectGender() {
        // Select Gender radio (Male)
        profilePage.selectGender("M");
        assertEquals(profilePage.getSelectedGender(), "M",
                "Selected gender radio should be 'M'");

        // Select Gender radio (Female)
        profilePage.selectGender("F");
        assertEquals(profilePage.getSelectedGender(), "F",
                "Selected gender radio should be 'F'");
    }

    @Test(description = "Select country from dropdown and verify selection")
    public void testSelectCountry() {
        // Assume dropdown supports selecting by visible text
        String country = "Hungary";
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
