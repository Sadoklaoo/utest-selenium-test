package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Step 1: enter email and click “Continue”
    @FindBy(name = "username")            // adjust if the field name/id differs
    private WebElement emailField;
    @FindBy(css = "button[type='submit']")// the Continue button on the email form
    private WebElement continueButton;

    // Step 2: enter password and click “Sign In”
    // (we locate these lazily after clicking Continue)
    private By passwordLocator = By.name("password");  
    private By signInButtonLocator = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.utest.com/login");
        // optional: dismiss cookie banner here…
    }

    public void login(String user, String pass) {
        // Step 1: email
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(user);
        continueButton.click();

        // Step 2: password
        WebElement pwd = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
        pwd.sendKeys(pass);
        driver.findElement(signInButtonLocator).click();
    }
}
