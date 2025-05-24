package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class ProfilePage extends BasePage {
    // Toggle the "My Facebook Link" checkbox
    @FindBy(css = "social-selector[unique-name='facebook'] input[type='checkbox']")
    private WebElement facebookCheckbox;

    // The Facebook Link text input (visible once checkbox is checked)
    @FindBy(id = "facebook")
    private WebElement facebookLinkInput;

    // The About Me textarea
    @FindBy(id = "aboutMe")
    private WebElement aboutMeTextarea;

    // The Save button to submit profile changes
    @FindBy(css = "button[type='submit'].btn.btn-primary")
    private WebElement saveButton;

    // Toast message displayed after successful save
    @FindBy(css = "div.toast-success div.toast-message")
    private WebElement successToastMessage;

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Opens the My Profile page.
     */
    public void open() {
        driver.get("https://platform.utest.com/account/my-profile/");
    }

    /**
     * Toggles the "My Facebook Link" checkbox.
     * @param enable true to check (show link input), false to uncheck
     */
    public void toggleFacebook(boolean enable) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement checkbox = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("social-selector[unique-name='facebook'] input[type='checkbox']")
            )
        );
        if (checkbox.isSelected() != enable) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }

    /**
     * Sets the Facebook link URL.
     * @param url the Facebook profile URL
     */
    public void setFacebookLink(String url) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(facebookLinkInput));
        facebookLinkInput.clear();
        facebookLinkInput.sendKeys(url);
    }

    /**
     * Gets the current value of the Facebook link.
     * @return the link text
     */
    public String getFacebookLink() {
        return facebookLinkInput.getDomProperty("value");
    }

    /**
     * Sets the About Me text.
     * @param text the bio or summary
     */
    public void setAboutMe(String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(aboutMeTextarea));
        aboutMeTextarea.clear();
        aboutMeTextarea.sendKeys(text);
    }

    /**
     * Gets the current About Me text.
     * @return the textarea content
     */
    public String getAboutMe() {
        return aboutMeTextarea.getDomProperty("value");
    }

    /**
     * Clicks the Save button to submit profile changes.
     */
    public void saveChanges() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

    /**
     * Waits for and returns the success toast message text after saving.
     * @return the toast message, e.g. "Changes saved"
     */
    public String waitForSuccessToast() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement toast = wait.until(ExpectedConditions.visibilityOf(successToastMessage));
        return toast.getText().trim();
    }
}
