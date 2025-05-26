package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

/**
 * Page Object for uTest "My Profile" page, encapsulating profile edit interactions.
 */
public class ProfilePage extends BasePage {

    @FindBy(css = "social-selector[unique-name='facebook'] input[type='checkbox']")
    private WebElement facebookCheckbox;

    @FindBy(id = "aboutMe")
    private WebElement aboutMeTextarea;  // we’ll still locate this via driver in getter/setter

    @FindBy(css = "button[type='submit'].btn.btn-primary")
    private WebElement saveButton;

    @FindBy(css = ".alert-success, .toast-success")
    private WebElement successToastMessage;

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        System.out.println("[ProfilePage] Navigating to My Profile page");
        driver.get("https://platform.utest.com/account/my-profile");
    }

    public void dismissCookiesBanner() {
        try {
            WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement closeBtn = w.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector(
                        "#onetrust-accept-btn-handler, .cc-btn-accept, .cookie-close, .cookie__close, button[aria-label*='cookie']"
                    )
                )
            );
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
            System.out.println("[ProfilePage] dismissCookiesBanner: banner dismissed");
            return;
        } catch (Exception ignored) { }

        ((JavascriptExecutor) driver).executeScript(
            "document.querySelectorAll('#onetrust-banner-sdk, .ot-sdk-cookie-consent, .cookie-banner, .cookie-policy').forEach(el => el.remove());"
        );
        System.out.println("[ProfilePage] dismissCookiesBanner: banner forcibly removed");
    }

    public void toggleFacebook(boolean enable) {
        System.out.println("[ProfilePage] toggleFacebook: desired state = " + enable);
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement checkbox = w.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("social-selector[unique-name='facebook'] input[type='checkbox']")
            )
        );
        boolean current = checkbox.isSelected();
        System.out.println("[ProfilePage] toggleFacebook: current state = " + current);
        if (current != enable) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            System.out.println("[ProfilePage] toggleFacebook: clicked checkbox");
        } else {
            System.out.println("[ProfilePage] toggleFacebook: no action needed");
        }
    }

    public void setFacebookLink(String url) {
        System.out.println("[ProfilePage] setFacebookLink: url = " + url);
        dismissCookiesBanner();
        WebElement input = new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("facebook")));
        input.clear();
        input.sendKeys(url);
        System.out.println("[ProfilePage] setFacebookLink: value set");
    }

    public String getFacebookLink() {
        WebElement input = driver.findElement(By.id("facebook"));
        String val = input.getAttribute("value");
        System.out.println("[ProfilePage] getFacebookLink: value = " + val);
        return val;
    }

    public void setAboutMe(String text) {
        System.out.println("[ProfilePage] setAboutMe: text = " + text);
        dismissCookiesBanner();
        WebElement area = new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("aboutMe")));
        area.clear();
        area.sendKeys(text);
        System.out.println("[ProfilePage] setAboutMe: text set");
    }

    public String getAboutMe() {
        WebElement area = driver.findElement(By.id("aboutMe"));
        String val = area.getAttribute("value");
        System.out.println("[ProfilePage] getAboutMe: value = " + val);
        return val;
    }

    public void saveChanges() {
        System.out.println("[ProfilePage] saveChanges: clicking Save");
        dismissCookiesBanner();
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(saveButton))
            .click();
        System.out.println("[ProfilePage] saveChanges: Save clicked");
    }

    public String waitForSuccessToast() {
        dismissCookiesBanner();
        System.out.println("[ProfilePage] waitForSuccessToast: locating toasts");
        try {
            WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(successToastMessage));
            String raw = toast.getText().trim();
            System.out.println("[ProfilePage] waitForSuccessToast: raw = '" + raw + "'");
            String cleaned = raw.replace("×", "").trim();
            System.out.println("[ProfilePage] waitForSuccessToast: cleaned = '" + cleaned + "'");
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toast);
                System.out.println("[ProfilePage] waitForSuccessToast: toast clicked away");
            } catch (Exception e) {
                System.out.println("[ProfilePage] waitForSuccessToast: unable to click toast");
            }
            return cleaned;
        } catch (TimeoutException te) {
            throw te;
        }
    }

    public void selectGender(String code) {
        System.out.println("[ProfilePage] selectGender: code = " + code);
        dismissCookiesBanner();
        WebElement radio = driver.findElement(
            By.xpath("//input[@name='optionsGender' and @value='" + code + "']")
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
        System.out.println("[ProfilePage] selectGender: clicked radio");
    }

    public String getSelectedGender() {
        for (WebElement radio : driver.findElements(By.name("optionsGender"))) {
            if (radio.isSelected()) {
                String val = radio.getAttribute("value");
                System.out.println("[ProfilePage] getSelectedGender: selected = " + val);
                return val;
            }
        }
        return null;
    }

    /**
     * Select a country by typing its name into the filter and hitting ENTER.
     */
    public void selectCountry(String country) {
        dismissCookiesBanner();

        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement dropdown = w.until(
            ExpectedConditions.elementToBeClickable(By.id("country"))
        );
        dropdown.click();
        System.out.println("[ProfilePage] selectCountry: dropdown opened");

        WebElement filter = w.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".inputFilter"))
        );
        filter.clear();
        filter.sendKeys(country);
        filter.sendKeys(Keys.ENTER);
        System.out.println("[ProfilePage] selectCountry: filter typed & ENTER pressed = " + country);

        // wait for dropdown to close (no items visible)
        w.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".multiSelectItem")));
        System.out.println("[ProfilePage] selectCountry: dropdown closed");

        // verify selected label updated
        w.until(ExpectedConditions.textToBePresentInElementLocated(
            By.cssSelector("button#country .buttonLabel"), country));
        System.out.println("[ProfilePage] selectCountry: verified = " + country);
    }

    public String getSelectedCountry() {
        String text = driver.findElement(By.id("country")).getText();
        String cleaned = text.replaceAll("[\\u25BE\\u25BC\\u25B2]", "").trim();
        System.out.println("[ProfilePage] getSelectedCountry: text = '" + cleaned + "'");
        return cleaned;
    }
}
