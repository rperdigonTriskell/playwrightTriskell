package stepdefinitions;

import factory.DriverFactory;
import io.cucumber.java.en.*;
import pages.LoginPage;

public class LoginSteps {

    LoginPage loginPage = new LoginPage(DriverFactory.getPage());

    @Given("^navigates to \"([^\"]*)\"$")
    public void navigateToUrl(String url) {
        loginPage.navigateToUrl(url);
    }

    @When("^enters room \"([^\"]*)\"$")
    public void entersRoom(String room) {
        loginPage.enterGuestRoom(room);
    }

    @When("^enters last name \"([^\"]*)\"$")
    public void entersLastName(String room) {
        loginPage.enterLastName(room);
    }

    @When("^click ACCEDER")
    public void clickACCEDER() {
        loginPage.clickACCEDER();
    }

    @Then("^verify the error message is \"([^\"]*)\"$")
    public void verifyTheErrorMessageIs(String message) {
       loginPage.verifyTheErrorMessageContains(message);
    }
}
