package com.dubby.security.model;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.Logger;
import com.dubby.base.model.repo.SettingRepo;
import com.dubby.security.enumeration.ReservedUserName;
import com.dubby.security.exception.*;
import com.dubby.security.model.entity.User;
import com.dubby.security.model.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

@Test(groups = "Authenticator", dependsOnGroups = "User")
@ContextConfiguration(locations = {"classpath:security-api-test-config.xml"})
public class AuthenticatorTest extends AbstractTestNGSpringContextTests {

    @Autowired
    Logger securityLogger;

    @Autowired
    UserRepo userRepoImpl;

    @Autowired
    SettingRepo settingRepo;

    @Autowired
    Authenticator authenticator;

    public void testLoginSuccess() {
        authenticator.login("a", "aaa", "LOCATION 1");
    }

    @Test(expectedExceptions = UsernameNotFoundException.class)
    public void testWrongUserName() throws UsernameNotFoundException {
        authenticator.login("x", "aaa", "LOCATION 1");
    }

    @Test(expectedExceptions = BadCredentialsException.class)
    public void testWrongPassword() throws BadCredentialsException {
        authenticator.login("b", "aaa", "LOCATION 1");
    }

    @Test(dependsOnMethods = "testWrongPassword")
    public void testThreeMoreWrongPassword() {

        try {
            authenticator.login("b", "bbb", "LOCATION 1");
        } catch (BadCredentialsException bce) {
        }

        try {
            authenticator.login("b", "ccc", "LOCATION 1");
        } catch (BadCredentialsException bce) {
        }

        try {
            authenticator.login("b", "ddd", "LOCATION 1");
        } catch (BadCredentialsException bce) {
        }
    }

    @Test(dependsOnMethods = "testThreeMoreWrongPassword", expectedExceptions = LockedException.class)
    public void testLockUser() {
        authenticator.login("b", "xxx", "LOCATION 1");
    }

    @Test(dependsOnMethods = "testLockUser")
    @Transactional
    public void testUnlock() throws Exception {

        User b = userRepoImpl.find("b");
        b.openUserLock(ReservedUserName.System.getVal());
    }

    @Test(dependsOnMethods = "testUnlock")
    public void testLoginAfterUnlock() {

        authenticator.login("b", "xxx", "LOCATION 1");
    }


    @Test(expectedExceptions = DisabledException.class)
    public void testInactiveUser() throws DisabledException {
        authenticator.login("c", "xxx", "LOCATION 1");
    }

    @Test(dependsOnMethods = "testLoginSuccess", expectedExceptions = MultipleLoginException.class)
    public void testMultipleLogin() throws MultipleLoginException {
        authenticator.login("a", "aaa", "LOCATION 2");
    }

    @Test(dependsOnMethods = "testMultipleLogin")
    public void testLogout() {

        authenticator.logout("a");
    }

    @Test(dependsOnMethods = "testLogout")
    public void testMultipleLoginSuccess() {
        authenticator.login("a", "aaa", "LOCATION 2");
    }

    @Test(dependsOnMethods = "testMultipleLoginSuccess")
    public void testMultipleLoginSuccessSameSource() {
        authenticator.login("a", "aaa", "LOCATION 2");
    }

    @Test(dependsOnMethods = "testMultipleLoginSuccessSameSource", expectedExceptions = MultipleLoginException.class)
    public void testMultipleLoginFailSecondCheck() throws MultipleLoginException {
        authenticator.login("a", "aaa", "LOCATION 1");
    }

    @Test(dependsOnMethods = "testMultipleLoginFailSecondCheck")
    @Transactional
    public void testReleaseLocationLock() throws BaseException {

        User a = userRepoImpl.find("a");
        a.releaseLocationLocke(ReservedUserName.System.getVal());
    }

    @Test(dependsOnMethods = "testReleaseLocationLock")
    public void testLoginAfterReleaseLocationLock() {

        authenticator.login("a", "aaa", "LOCATION 1");
    }

    @Test(expectedExceptions = CredentialsExpiredException.class)
    public void testPasswordExpire() throws CredentialsExpiredException {

        authenticator.login("d", "aaa", "LOCATION 1");
    }

}
