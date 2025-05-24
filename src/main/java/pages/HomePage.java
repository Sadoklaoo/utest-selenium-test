package pages;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class HomePage extends BasePage {
    // 1) The wrapper component that holds the expand/collapse button
    @FindBy(css = "nav-sidebar-dropdown-toggle.nav-sidebar-user-card-dropdown__arrow > button")
    private WebElement userMenuToggle;

    // 2) The ‘Sign Out’ link inside the expanded dropdown
    @FindBy(xpath = "//nav-sidebar-dropdown-item[@text='Sign Out']//a[@class='nav-sidebar-dropdown-child__link']")
    private WebElement signOutLink;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /** Opens the user‐menu dropdown (if not already expanded). */
    public void openUserMenu() {
        // aria-expanded will be "false" initially, and "true" once open
        if (!Boolean.parseBoolean(userMenuToggle.getDomAttribute("aria-expanded"))) {
            wait.until(ExpectedConditions.elementToBeClickable(userMenuToggle))
                .click();
        }
    }

    /** Returns true if “Sign Out” is visible after opening the menu. */
    public boolean isSignOutVisible() {
        openUserMenu();
        try {
            wait.until(ExpectedConditions.visibilityOf(signOutLink));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Convenience: open the menu (if needed) and click Sign Out. */
    public void signOut() {
        openUserMenu();
        wait.until(ExpectedConditions.elementToBeClickable(signOutLink))
            .click();
    }
}
