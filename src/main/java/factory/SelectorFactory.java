package factory;


import com.microsoft.playwright.Locator;

public interface SelectorFactory {
    /**
     * Returns a selector by name.
     *
     * @param name the name of the selector
     * @return the selector
     */
    Locator getSelector(String name);
}