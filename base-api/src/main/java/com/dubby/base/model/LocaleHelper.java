package com.dubby.base.model;

import java.util.Locale;

public interface LocaleHelper {

    public Locale[] getAvailableLocales();

    public Locale getLocale();
    public void setLocale(Locale localeKey);
}
