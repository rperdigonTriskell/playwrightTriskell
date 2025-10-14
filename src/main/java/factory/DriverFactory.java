package factory;

import com.microsoft.playwright.*;
import utils.WebActions;

import java.nio.file.Paths;

public class DriverFactory {
    private Playwright playwright; // Mantener referencia para cerrar correctamente
    private Browser browser;
    public static BrowserContext context;
    private static Page page;

    // ThreadLocal para ejecución paralela
    private static ThreadLocal<Page> threadLocalDriver = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> threadLocalContext = new ThreadLocal<>();

    /**
     * Inicializa el driver Playwright en modo local o remoto.
     * @param browserName Nombre del navegador: "chrome", "firefox", "webkit"
     * @return Page instancia lista para usar
     */
    public Page initDriver(String browserName) {
        String remoteServerUrl = WebActions.getProperty("remoteServer"); // Ej: ws://localhost:3000
        boolean headless = Boolean.parseBoolean(WebActions.getProperty("headless"));

        try {
            playwright = Playwright.create();
            BrowserType browserType;

            switch (browserName.toLowerCase()) {
                case "firefox":
                    browserType = playwright.firefox();
                    break;
                case "chrome":
                    browserType = playwright.chromium();
                    break;
                case "webkit":
                    browserType = playwright.webkit();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }

            if (remoteServerUrl != null && !remoteServerUrl.isEmpty()) {
                // Conexión remota (Playwright Server)
                browser = browserType.connect(remoteServerUrl);
            } else {
                // Ejecución local
                browser = browserType.launch(new BrowserType.LaunchOptions()
                        .setHeadless(headless)
                        .setChannel(browserName.equalsIgnoreCase("chrome") ? "chrome" : null));
            }

            // Crear contexto con opciones adicionales (vídeo opcional)
            context = browser.newContext(new Browser.NewContextOptions()
                    .setRecordVideoDir(Paths.get("videos/"))
                    .setRecordVideoSize(1280, 720));

            // Activar trazas
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true));

            // Crear página
            page = context.newPage();
            threadLocalDriver.set(page);
            threadLocalContext.set(context);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize browser: " + e.getMessage(), e);
        }

        return page;
    }

    /**
     * Obtiene la página asociada al hilo actual.
     */
    public static synchronized Page getPage() {
        return threadLocalDriver.get();
    }

    /**
     * Obtiene el contexto asociado al hilo actual.
     */
    public static synchronized BrowserContext getContext() {
        return threadLocalContext.get();
    }

    /**
     * Cierra el navegador y Playwright.
     */
    public void quitDriver() {
        try {
            if (context != null) {
                context.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get("trace.zip")));
                context.close();
            }
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}