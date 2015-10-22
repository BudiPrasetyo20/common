package com.dubby.security.model;

import com.dubby.base.model.repo.SettingRepo;
import com.dubby.security.enumeration.ReservedUserName;
import com.dubby.security.exception.DubbyInvalidPasswordException;
import com.dubby.security.model.entity.setting.PasswordPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = "PasswordValidator")
@ContextConfiguration(locations = {"classpath:security-api-test-config.xml"})
public class PasswordValidatorTest extends AbstractTestNGSpringContextTests {

    @Autowired
    PasswordValidator passwordValidator;

    @Autowired
    SettingRepo settingRepo;

    @Transactional
    public void testValidateDefault() {

        try {

            passwordValidator.validate("a");
            passwordValidator.validate("1");
            passwordValidator.validate("@");
            passwordValidator.validate("a1@");
            passwordValidator.validate("abc123_@!~");
            passwordValidator.validate("abc123_@!~abc123_@!~abc123_@!~abc123_@!~abc123_@!~abc123_@!~");
        } catch (Exception e) {

            Assert.fail("Unexpected Exception", e);
        }
    }

    @Transactional
    @Test(expectedExceptions = DubbyInvalidPasswordException.class)
    public void testEmptyPassword() throws Exception {

        passwordValidator.validate("");
    }

    @Transactional
    @Test(expectedExceptions = DubbyInvalidPasswordException.class,
            dependsOnMethods = { "testValidateDefault" ,"testEmptyPassword" })
    public void testFailMinPasswordLength() throws Exception {

        PasswordPolicy passwordPolicy = settingRepo.getSetting(PasswordPolicy.class);
        passwordPolicy.setMinPasswordLength(Short.valueOf("5"));

        settingRepo.saveOrUpdate(PasswordPolicy.class, passwordPolicy, ReservedUserName.System.getVal());

        passwordValidator.validate("a");
    }

    @Test(dependsOnMethods = { "testFailMinPasswordLength" })
    public void testPassMinPasswordLength() throws Exception {

        passwordValidator.validate("12345");
        passwordValidator.validate("123456");
        passwordValidator.validate("12345678901234567890");
    }

    @Transactional
    @Test(expectedExceptions = DubbyInvalidPasswordException.class,
            dependsOnMethods = { "testPassMinPasswordLength" })
    public void testFailMaxPasswordLength() throws Exception {

        PasswordPolicy passwordPolicy = settingRepo.getSetting(PasswordPolicy.class);
        passwordPolicy.setMaxPasswordLength(Short.valueOf("20"));

        settingRepo.saveOrUpdate(PasswordPolicy.class, passwordPolicy, ReservedUserName.System.getVal());

        passwordValidator.validate("123456789012345678901");
    }

    @Test(dependsOnMethods = { "testFailMaxPasswordLength" })
    public void testPassMaxPasswordLength() throws Exception {

        passwordValidator.validate("1234567890123456789");
        passwordValidator.validate("12345678901234567890");
    }

    @Transactional
    @Test(expectedExceptions = DubbyInvalidPasswordException.class, dependsOnMethods = "testPassMaxPasswordLength")
    public void testFailMustContainAlpha() throws Exception {

        PasswordPolicy passwordPolicy = settingRepo.getSetting(PasswordPolicy.class);
        passwordPolicy.setMustContainAlpha(true);

        settingRepo.saveOrUpdate(PasswordPolicy.class, passwordPolicy, ReservedUserName.System.getVal());

        passwordValidator.validate("!@#$%^&*()");
    }

    @Test(dependsOnMethods = { "testFailMustContainAlpha" })
    public void testPassMustContainAlpha() throws Exception {

        passwordValidator.validate("!@O#$I%^&*()U");
    }

    @Transactional
    @Test(expectedExceptions = DubbyInvalidPasswordException.class, dependsOnMethods = "testPassMustContainAlpha")
    public void testFailMustContainNumber() throws Exception {

        PasswordPolicy passwordPolicy = settingRepo.getSetting(PasswordPolicy.class);
        passwordPolicy.setMustContainNumber(true);

        settingRepo.saveOrUpdate(PasswordPolicy.class, passwordPolicy, ReservedUserName.System.getVal());

        passwordValidator.validate("!@O#$I%^&*()U");
    }

    @Test(dependsOnMethods = { "testFailMustContainNumber" })
    public void testPassMustContainNumber() throws Exception {

        passwordValidator.validate("!@1O#$I%2^&*3()U");
    }

    @Transactional
    @Test(expectedExceptions = DubbyInvalidPasswordException.class, dependsOnMethods = "testPassMustContainNumber")
    public void testFailMustContainSpecial() throws Exception {

        PasswordPolicy passwordPolicy = settingRepo.getSetting(PasswordPolicy.class);
        passwordPolicy.setMustContainSpecial(true);

        settingRepo.saveOrUpdate(PasswordPolicy.class, passwordPolicy, ReservedUserName.System.getVal());

        passwordValidator.validate("a1b2cd3e45");
    }

    @Test(dependsOnMethods = { "testFailMustContainSpecial" })
    public void testPassMustContainSpecial() throws Exception {

        passwordValidator.validate("a@1b2cd#3e$45");
    }

}
