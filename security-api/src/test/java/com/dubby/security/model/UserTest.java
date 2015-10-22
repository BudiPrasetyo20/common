package com.dubby.security.model;

import com.dubby.base.enumeration.StatusType;
import com.dubby.base.model.PagedResult;
import com.dubby.base.model.repo.SettingRepo;
import com.dubby.common.model.repo.BranchRepo;
import com.dubby.security.enumeration.DataAccessRestriction;
import com.dubby.security.enumeration.ReservedUserName;
import com.dubby.security.exception.DubbyInvalidPasswordException;
import com.dubby.security.exception.DubbyInvalidUserException;
import com.dubby.security.model.entity.Role;
import com.dubby.security.model.entity.User;
import com.dubby.security.model.entity.setting.PasswordPolicy;
import com.dubby.security.model.repo.RoleRepo;
import com.dubby.security.model.repo.UserRepo;
import org.hibernate.LazyInitializationException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Test(groups = "User", dependsOnGroups = {"Branch", "Role", "PasswordValidator"})
@ContextConfiguration(locations = {"classpath:security-api-test-config.xml"})
public class UserTest extends AbstractTestNGSpringContextTests {

    @Autowired
    UserRepo userRepoImpl;

    @Autowired
    RoleRepo roleRepoImpl;

    @Autowired
    BranchRepo branchRepo;

    @Autowired
    SettingRepo settingRepo;

    @Autowired
    Authenticator authenticator;

    @Transactional
    public void testCreate() {

        try {

            Role role1 = roleRepoImpl.find(1L);
            Role role2 = roleRepoImpl.find(2L);

            User user = new User();
            user.setId("a");
            user.setFidBranch("HQ");
            user.setRawPassword("aaa");
            user.setStatus(StatusType.Active.getVal());
            user.getRoles().add(role1);

            userRepoImpl.saveOrUpdate(user, true, ReservedUserName.System.getVal());

            user = new User();
            user.setId("b");
            user.setFidBranch("HQ");
            user.setRawPassword("xxx");
            user.setStatus(StatusType.Active.getVal());
            user.getRoles().add(role1);
            user.getRoles().add(role2);

            userRepoImpl.saveOrUpdate(user, true, ReservedUserName.System.getVal());

            user = new User();
            user.setId("c");
            user.setFidBranch("HQ");
            user.setRawPassword("xxx");
            user.setStatus(StatusType.Inactive.getVal());
            user.getRoles().add(role2);

            userRepoImpl.saveOrUpdate(user, true, ReservedUserName.System.getVal());

            user = new User();
            user.setId("d");
            user.setFidBranch("HQ");
            user.setRawPassword("aaa");
            user.setStatus(StatusType.Active.getVal());
            user.setPasswordExpire(new Date());

            userRepoImpl.saveOrUpdate(user, true, ReservedUserName.System.getVal());

            User e = new User();
            e.setId("e");
            e.setFidBranch("HQ");
            e.setRawPassword("xxx");
            e.setStatus(StatusType.Active.getVal());

            userRepoImpl.saveOrUpdate(e, true, ReservedUserName.System.getVal());

            User f = new User();
            f.setId("f");
            f.setFidBranch("CBNG1");
            f.setDataAccessRestriction(DataAccessRestriction.Custom.getVal());
            f.setAccessBranch("CBNG2;CB1.2");
            f.setRawPassword("xxx");
            f.setStatus(StatusType.Active.getVal());

            userRepoImpl.saveOrUpdate(f, true, ReservedUserName.System.getVal());

            User g = new User();
            g.setId("g");
            g.setFidBranch("CBNG2");
            g.setDataAccessRestriction(DataAccessRestriction.Subordinate.getVal());
            g.setRawPassword("xxx");
            g.setStatus(StatusType.Active.getVal());

            userRepoImpl.saveOrUpdate(g, true, ReservedUserName.System.getVal());

            User h = new User();
            h.setId("h");
            h.setFidBranch("CB1.1");
            h.setDataAccessRestriction(DataAccessRestriction.AllBranch.getVal());
            h.setRawPassword("xxx");
            h.setStatus(StatusType.Active.getVal());

            userRepoImpl.saveOrUpdate(h, true, ReservedUserName.System.getVal());

            User i = new User();
            i.setId("i");
            i.setFidBranch("HQ");
            i.setDataAccessRestriction(DataAccessRestriction.SelfBranch.getVal());
            i.setRawPassword("xxx");
            i.setStatus(StatusType.Active.getVal());

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);

            i.setPasswordExpire(cal.getTime());

            userRepoImpl.saveOrUpdate(i, true, ReservedUserName.System.getVal());

            PasswordPolicy passwordPolicy = settingRepo.getSetting(PasswordPolicy.class);
            passwordPolicy.setAllowMultipleLogin(false);

            settingRepo.saveOrUpdate(PasswordPolicy.class, passwordPolicy, ReservedUserName.System.getVal());

        } catch (Exception e) {

            Assert.fail("Unexpected exception", e);
        }
    }

    @Transactional
    @Test(expectedExceptions = PropertyValueException.class)
    public void testBranchRequired() throws Exception {

        User user = new User();
        user.setId("x");
        user.setPassword("aaa");
        user.setStatus(StatusType.Active.getVal());
        user.setPasswordExpire(new Date());

        userRepoImpl.saveOrUpdate(user, true, ReservedUserName.System.getVal());
    }

    @Transactional
    @Test(expectedExceptions = DubbyInvalidUserException.class)
    public void testReservedUserId() throws Exception {

        User user = new User();
        user.setId(ReservedUserName.System.getVal());
        user.setPassword("aaa");
        user.setStatus(StatusType.Active.getVal());
        user.setPasswordExpire(new Date());

        userRepoImpl.saveOrUpdate(user, true, ReservedUserName.System.getVal());
    }

    @Test(dependsOnMethods = "testCreate")
    @Transactional
    public void testFindAll() {

        List<User> listOfAllUser = userRepoImpl.find();

        Assert.assertEquals(listOfAllUser.size(), 9);
    }

    @Test(dependsOnMethods = "testCreate")
    @Transactional
    public void testFind() {

        User user = userRepoImpl.find("a");

        Assert.assertNotNull(user.getId());

        user = userRepoImpl.find("x");

        Assert.assertNull(user);
    }

    @Test(dependsOnMethods = "testCreate")
    @Transactional
    public void testPassLoad() {

        User user = userRepoImpl.load("a");
        Assert.assertNotNull(user.getId());
    }

    @Test(dependsOnMethods = "testCreate",
            expectedExceptions = {ObjectNotFoundException.class, LazyInitializationException.class})
    @Transactional
    public void testFailLoad() {

        User user = userRepoImpl.load("xxx");
        Assert.assertNull(user);
    }

    @Test(dependsOnMethods = "testCreate")
    @Transactional
    public void testPagedList() {

        try {

            PagedResult pagedResult = userRepoImpl.findPagedList(DetachedCriteria.forClass(User.class), 5, 1);

            Assert.assertEquals(pagedResult.getActualRowCount().intValue(), 9);
            Assert.assertEquals(pagedResult.getResult().size(), 5);

            pagedResult = userRepoImpl.findPagedList(DetachedCriteria.forClass(User.class), 5, 2);

            Assert.assertEquals(pagedResult.getActualRowCount().intValue(), 9);
            Assert.assertEquals(pagedResult.getResult().size(), 4);

            pagedResult = userRepoImpl.findPagedList(DetachedCriteria.forClass(User.class), 5, 3);

            Assert.assertEquals(pagedResult.getActualRowCount().intValue(), 9);
            Assert.assertEquals(pagedResult.getResult().size(), 4);
            Assert.assertEquals(pagedResult.getCurrentPageIndex().intValue(), 2);

        } catch (Exception e) {

            Assert.fail("Unexpected Exception.", e);
        }
    }

    @Test(dependsOnMethods = "testCreate")
    @Transactional
    public void testGetBranchAccess() {

        try {

            User a = userRepoImpl.find("a");
            Assert.assertEquals(a.getAccessableBranch().size(), 1);

            User e = userRepoImpl.find("e");
            Assert.assertEquals(e.getAccessableBranch().size(), 1);

            User f = userRepoImpl.find("f");
            Assert.assertEquals(f.getAccessableBranch().size(), 2);

            User g = userRepoImpl.find("g");
            Assert.assertEquals(g.getAccessableBranch().size(), 1);

            User h = userRepoImpl.find("h");
            Assert.assertNull(h.getAccessableBranch());

            User i = userRepoImpl.find("i");
            Assert.assertEquals(i.getAccessableBranch().size(), 1);

        } catch (Exception e) {

            Assert.fail("Unexpected Exception.", e);
        }
    }

    @Test(expectedExceptions = DubbyInvalidPasswordException.class, dependsOnMethods = "testCreate")
    @Transactional
    public void testChangePasswordWrongPassword() throws Exception {

        PasswordPolicy passwordPolicy = settingRepo.getSetting(PasswordPolicy.class);
        passwordPolicy.setMaxPasswordCache(Short.valueOf("3"));

        settingRepo.saveOrUpdate(PasswordPolicy.class, passwordPolicy, ReservedUserName.System.getVal());

        User user = userRepoImpl.find("i");

        user.changePassword("aaa", "xxx", "xxx", ReservedUserName.System.getVal());
        System.out.println("user = " + user);
    }

    @Test(expectedExceptions = DubbyInvalidPasswordException.class, dependsOnMethods = "testChangePasswordWrongPassword")
    @Transactional
    public void testChangePasswordCachedPassword1() throws Exception {

        User user = userRepoImpl.find("i");
        user.changePassword("xxx", "xxx", "xxx", ReservedUserName.System.getVal());
        System.out.println("user = " + user);
    }

    @Test(dependsOnMethods = "testChangePasswordCachedPassword1")
    @Transactional
    public void testChangePasswordSuccess1() throws Exception {

        User user = userRepoImpl.find("i");

        user.changePassword("xxx", "a@1234", "a@1234", ReservedUserName.System.getVal());
        user.changePassword("a@1234", "b&2345", "b&2345", ReservedUserName.System.getVal());
        user.changePassword("b&2345", "c#3456", "c#3456", ReservedUserName.System.getVal());
    }

    @Test(expectedExceptions = DubbyInvalidPasswordException.class, dependsOnMethods = "testChangePasswordSuccess1")
    @Transactional
    public void testChangePasswordCachedPassword2() throws Exception {

        User user = userRepoImpl.find("i");
        user.changePassword("c#3456", "b&2345", "b&2345", ReservedUserName.System.getVal());
    }

    @Test(dependsOnMethods = "testChangePasswordCachedPassword2")
    @Transactional
    public void testLogin() {

        try {

            authenticator.login("i", "c#3456", "LOCATION 1");
        } catch (Exception e) {

            Assert.fail("Unexpected exception.", e);
        }
    }

    @Test(dependsOnMethods = "testCreate")
    @Transactional
    public void testUserAuthorization() {

        User userA = userRepoImpl.find("a");
        Assert.assertTrue(userA.isAuthorized("01"));
        Assert.assertTrue(userA.isAuthorized("01-01"));
        Assert.assertFalse(userA.isAuthorized("02"));
        Assert.assertFalse(userA.isAuthorized("02-01-01"));
        Assert.assertFalse(userA.isAuthorized("02-01-02"));
        Assert.assertFalse(userA.isAuthorized("XXX"));

        User userB = userRepoImpl.find("b");
        Assert.assertTrue(userB.isAuthorized("01"));
        Assert.assertTrue(userB.isAuthorized("01-01"));
        Assert.assertTrue(userB.isAuthorized("02"));
        Assert.assertTrue(userB.isAuthorized("02-01-01"));
        Assert.assertTrue(userB.isAuthorized("02-01-02"));
        Assert.assertFalse(userB.isAuthorized("XXX"));

        User userC = userRepoImpl.find("c");
        Assert.assertFalse(userC.isAuthorized("01"));
        Assert.assertFalse(userC.isAuthorized("01-01"));
        Assert.assertTrue(userC.isAuthorized("02"));
        Assert.assertTrue(userC.isAuthorized("02-01-01"));
        Assert.assertTrue(userC.isAuthorized("02-01-02"));
        Assert.assertFalse(userC.isAuthorized("XXX"));
    }
}
