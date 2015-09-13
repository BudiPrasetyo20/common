package com.dubby.common.base.model;

import com.dubby.base.model.Dictionary;
import com.dubby.base.model.LocaleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.ResourceBundle;

@Test
@ContextConfiguration(locations = { "classpath:base-lib-test-config.xml" })
public class DictionaryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    Dictionary baseDictionary;

    @Autowired
    LocaleHelper localeHelper;

    public void getResources() {
        ResourceBundle bundle = baseDictionary.getResource();

        Assert.assertNotNull(bundle);
    }

    public void constructString() {
        String constructedString = baseDictionary.constructString("base.error.list.invalid.row.size");

        Assert.assertNotNull(constructedString);
        Assert.assertEquals(constructedString, "Invalid row size");

        constructedString = baseDictionary.constructString("xxx");

        localeHelper.setLocale(new Locale("en", "US"));

        Assert.assertEquals(constructedString, "xxx");

        localeHelper.setLocale(new Locale("in", "ID"));

        constructedString = baseDictionary.constructString("base.error.list.invalid.row.size");

        Assert.assertEquals(constructedString, "Ukurang baris tidak sesuai");

        localeHelper.setLocale(new Locale("en", "US"));

        constructedString = baseDictionary.constructString("base.error.list.invalid.row.size");

        Assert.assertEquals(constructedString, "Invalid row size");

        System.out.println("Blah Hahahahahahahaha!!!");
    }
}
