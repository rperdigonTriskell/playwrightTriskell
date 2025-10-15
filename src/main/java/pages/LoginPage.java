package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.WebActions;

public class LoginPage {
    private Page page;
    private final Locator GUEST_ROOM;
    private final Locator LASTNAME;
    private final Locator LOGIN_BUTTON;
    private final Locator EMPTY_MESSAGE;

    public LoginPage(Page page) {
        this.page = page;
        this.GUEST_ROOM = page.locator("#guest_room");
        this.LASTNAME = page.locator("#guest_name");
        this.LOGIN_BUTTON = page.locator("#btn_login");
        this.EMPTY_MESSAGE = page.locator(".notyf__message");
    }

    public void navigateToUrl(String url) {
        this.page.navigate(url);
    }

    public void enterGuestRoom(String username) {
        GUEST_ROOM.fill(username);
    }

    public void enterLastName(String username) {
        LASTNAME.fill(username);
    }

    public void clickACCEDER() {
        LOGIN_BUTTON.click();
    }

    public void verifyTheErrorMessageContains(String... expectedParts) {
        EMPTY_MESSAGE.waitFor();
        String actualMessage = EMPTY_MESSAGE.innerText().trim();
        for (String part : expectedParts) {
            String normalizedPart = part.replace("#", "");
            if (!actualMessage.contains(normalizedPart)) {
                throw new AssertionError("Message was expected to contain '" + normalizedPart + "', but found: '" + actualMessage + "'");
            }
        }
    }
}
