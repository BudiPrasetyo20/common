package com.dubby.base.model;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;


@Component
public class LocaleHelperImpl implements LocaleHelper {

    @Override
    public Locale getLocale() {
        return Locale.getDefault();
    }

    @Override
    public void setLocale(Locale locale) {

        Locale.setDefault(locale);
    }

    @Override
    public Locale[] getAvailableLocales() {

        return new Locale[] { new Locale("en", "US"), new Locale("in", "ID") };
    }

    @PostConstruct
    private void init() {

        setLocale(new Locale("en", "US"));
    }

}
