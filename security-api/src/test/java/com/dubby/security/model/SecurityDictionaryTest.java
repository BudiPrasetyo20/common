package com.dubby.security.model;

import com.dubby.base.model.LocaleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Locale;

@Test
@ContextConfiguration(locations = { "classpath:security-api-test-config.xml" })
public class SecurityDictionaryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    SecurityDictionary securityDictionary;

    @Autowired
    LocaleHelper localeHelper;

    public void test() {

        localeHelper.setLocale(new Locale("en", "US"));

        Assert.assertEquals(securityDictionary.constructString("security.label.enum.data.access.restriction.A"), "All Branch");

        localeHelper.setLocale(new Locale("in", "ID"));

        Assert.assertEquals(securityDictionary.constructString("security.label.enum.data.access.restriction.A"), "Semua Cabang");
    }
}
