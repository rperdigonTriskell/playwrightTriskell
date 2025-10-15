package stepdefinitions;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import pages.LoginPage;

public class GenericStepDef {
    LoginPage loginPage = new LoginPage(DriverFactory.getPage());

    @Given("^navigates to \"([^\"]*)\"$")
    public void navigateToUrl(String url) {
        loginPage.navigateToUrl(url);
    }
}
