package hooks;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.WebActions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {
    public DriverFactory driverFactory;
    public Page page;

    @Before
    public void launchBrowser() {
        String browserName = WebActions.getProperty("browser");  //Fetching browser value from config file
        driverFactory = new DriverFactory();
        page = driverFactory.initDriver(browserName); // Passing browser name to launch the browser
    }
    @After(order = 1)
    public void takeScreenshotAndTrace(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            byte[] screenshot = page.screenshot();
            scenario.attach(screenshot, "image/png", screenshotName);

            Path tracePath = Paths.get("target/traces/" + screenshotName + "_" + timestamp + ".zip");
            DriverFactory.context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        }
    }

    @After(order = 0)
    public void quitBrowser() {
        driverFactory.quitDriver();
    }

}
