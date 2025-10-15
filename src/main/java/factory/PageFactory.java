package factory;

import java.util.HashMap;
import java.util.Map;

public class PageFactory{

    /**
     * Map associating page names with their corresponding instances.
     */
    private static final Map<String, AbstractPage> map = new HashMap<String, AbstractPage>() {{

    }};

    /**
     * The currently selected page.
     */
    private static AbstractPage currentPage;

    /**
     * Gets the current page that is selected.
     *
     * @return The currently selected page.
     */
    public static AbstractPage getCurrentPage() {
        return currentPage;
    }

    /**
     * Gets the name of the current page that is selected.
     *
     * @return The name of the currently selected page.
     */
    public static String getCurrentPageName() {
        for (Map.Entry<String, AbstractPage> entry : map.entrySet()) {
            if (entry.getValue().equals(currentPage)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Sets the current page based on the provided page name.
     *
     * @param page Name of the page to set as the current page.
     */
    public static void setCurrentPage(String page) {
        currentPage = map.get(page);
    }

}