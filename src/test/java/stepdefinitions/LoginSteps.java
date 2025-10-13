package stepdefinitions;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import utils.WebActions;

public class LoginSteps {
    LoginPage loginPage = new LoginPage(DriverFactory.getPage());

    @Given("^user navigates to \"([^\"]*)\"$")
    public void navigateToUrl(String url) {
        loginPage.navigateToUrl(url);
    }

    @When("^user enters \"([^\"]*)\" username$")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("^user enters \"([^\"]*)\" password$")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("^user clicks Login button$")
    public void clickLogin() {
        loginPage.clickLogin();
    }

    @When("^user clicks on \"([^\"]*)\" icon in main page")
    public void clickOnIcon(String iconName) {
        loginPage.clickOnIcon(iconName);
    }

    @Then("verify that user is logged in and navigated to Profile page")
    public void verifyProfilePage() {
        Assertions.assertTrue(loginPage.verifyProfilePage());
    }

    @Then("^user verifies data as \"([^\"]*)\" in \"([^\"]*)\" row and \"([^\"]*)\" column from \"([^\"]*)\" sheet in \"([^\"]*)\" file")
    public void clickOnIcon(String expectedValue, int rowNum, int colNum, String sheetName, String fileName) {
        String actualValue = WebActions.getRowColValue(fileName, sheetName, rowNum, colNum);
        Assertions.assertEquals(expectedValue, actualValue);
    }
}
