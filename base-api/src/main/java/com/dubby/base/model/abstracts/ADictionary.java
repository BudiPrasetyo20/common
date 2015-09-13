package com.dubby.base.model.abstracts;


import com.dubby.base.model.Dictionary;
import com.dubby.base.model.LocaleHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.*;

public abstract class ADictionary implements Dictionary {

    @Autowired
    private LocaleHelper localeHelperImpl;

    private Map<String, ResourceBundle> resourcesMap = new HashMap<String, ResourceBundle>();

    protected LocaleHelper getLocaleHelper() {
        return localeHelperImpl;
    }

    protected Map<String, ResourceBundle> getResourcesMap() {

        return resourcesMap;
    }

    @Override
    public ResourceBundle getResource() {
        return getResourcesMap().get(Locale.getDefault().toString());
    }

    public String constructString(String key, Object... args) {

        String result;

        try {
            result = getResource().getString(key);

            if (args != null) {
                result = MessageFormat.format(result, args);
            }
        } catch (MissingResourceException e) {
            result = key;
        }
        return result;
    }

    protected abstract String getBundlePath();

    protected void fillResourcesCache() {

        Locale[] locales = getLocaleHelper().getAvailableLocales();

        for (Locale locale : locales) {

            getResourcesMap().put(locale.toString(), ResourceBundle.getBundle(getBundlePath(), locale));
        }
    }
}
