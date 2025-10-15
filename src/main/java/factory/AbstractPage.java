package factory;


import com.microsoft.playwright.Locator;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static factory.PageFactory.getCurrentPage;

public abstract class AbstractPage implements SelectorFactory {

    /**
     * Map to hold selector names and their corresponding By objects.
     */
    protected Map<String, Locator> mapSelectors = new HashMap<String, Locator>();

    /**
     * Constructor to initialize common selectors.
     */
    public AbstractPage() {
    }

    /**
     * Returns a map of selector mappings.
     * @return Map of selector mappings.
     */
    public abstract Map<String, Locator> mapSelectors();

    /**
     * Retrieves the By object for the given selector name.
     * Throws an exception if the selector is not found.
     * @param selector the name of the selector.
     * @return the By object corresponding to the selector name.
     */
    public Locator getSelector(String selector) {
        Locator foundSelector = mapSelectors().get(selector);
        if (foundSelector == null) {
            throw new NoSuchElementException("Selector '" + selector + "' not found. In page: " + getCurrentPage().toString());
        }
        return foundSelector;
    }
}
