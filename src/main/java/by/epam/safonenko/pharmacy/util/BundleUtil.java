package by.epam.safonenko.pharmacy.util;

import java.util.Locale;
import java.util.ResourceBundle;

public final class BundleUtil {
    private static final String BUNDLE_NAME = "property.message";

    public static ResourceBundle getResourceBundle(String lang){
        return ResourceBundle.getBundle(BUNDLE_NAME, Locale.forLanguageTag(lang.replace("_", "-")));
    }
}
