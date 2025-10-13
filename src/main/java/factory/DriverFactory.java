package factory;

import com.microsoft.playwright.*;
import utils.WebActions;

public class DriverFactory {
    private Playwright playwright; // Mantener referencia para remoto
    public Browser browser;
    public static BrowserContext context;
    public static Page page;

    public static ThreadLocal<Page> threadLocalDriver = new ThreadLocal<>();
    public static ThreadLocal<BrowserContext> threadLocalContext = new ThreadLocal<>();

    public Page initDriver(String browserName) {
        String remoteServerUrl = WebActions.getProperty("remoteServer"); // ws://localhost:3000
        boolean headless = Boolean.parseBoolean(WebActions.getProperty("headless"));

        try {
            if (remoteServerUrl != null && !remoteServerUrl.isEmpty()) {
                // Conexión a servidor remoto
                playwright = Playwright.create();
                BrowserType browserType;

                switch (browserName.toLowerCase()) {
                    case "firefox": browserType = playwright.firefox(); break;
                    case "chrome": browserType = playwright.chromium(); break;
                    case "webkit": browserType = playwright.webkit(); break;
                    default: throw new IllegalArgumentException("Unsupported browser: " + browserName);
                }

                browser = browserType.connect(remoteServerUrl);

            } else {
                // Modo local
                BrowserType browserType;
                switch (browserName.toLowerCase()) {
                    case "firefox": browserType = Playwright.create().firefox(); break;
                    case "chrome": browserType = Playwright.create().chromium(); break;
                    case "webkit": browserType = Playwright.create().webkit(); break;
                    default: throw new IllegalArgumentException("Unsupported browser: " + browserName);
                }
                browser = browserType.launch(new BrowserType.LaunchOptions()
                        .setChannel(browserName.equalsIgnoreCase("chrome") ? "chrome" : null)
                        .setHeadless(headless));
            }

            // Crear contexto y página
            context = browser.newContext();
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(false));

            page = context.newPage();
            threadLocalDriver.set(page);
            threadLocalContext.set(context);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize browser: " + e.getMessage());
        }

        return page;
    }

    public static synchronized Page getPage() {
        return threadLocalDriver.get();
    }

    public static synchronized BrowserContext getContext() {
        return threadLocalContext.get();
    }

    public void quitDriver() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}