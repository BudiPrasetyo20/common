package com.dubby.security.model;

import com.dubby.base.model.PagedResult;
import com.dubby.security.enumeration.ReservedUserName;
import com.dubby.security.model.entity.Role;
import com.dubby.security.model.repo.RoleRepo;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "Role")
@ContextConfiguration(locations = {"classpath:security-api-test-config.xml"})
public class RoleTest extends AbstractTestNGSpringContextTests {

    @Autowired
    RoleRepo roleRepoImpl;

    @Transactional
    public void testCreate() {

        try {

            Role role1 = new Role();
            role1.setRoleName("Role 1");
            role1.setRights(";01;01-01;");

            roleRepoImpl.saveOrUpdate(role1, true, ReservedUserName.System.getVal());

            Role role2 = new Role();
            role2.setRoleName("Role 2");
            role2.setRights(";02;02-01-01;02-01-02;");

            roleRepoImpl.saveOrUpdate(role2, true, ReservedUserName.System.getVal());

            Role role3 = new Role();
            role3.setRoleName("Role 3");
            role3.setRights(";02;02-01-01;02-01-02;");

            roleRepoImpl.saveOrUpdate(role3, true, ReservedUserName.System.getVal());

            Role role4 = new Role();
            role4.setRoleName("Role 4");
            role4.setRights(";02;02-01-01;02-01-02;");

            roleRepoImpl.saveOrUpdate(role4, true, ReservedUserName.System.getVal());

            Role role5 = new Role();
            role5.setRoleName("Role 5");
            role5.setRights(";02;02-01-01;02-01-02;");

            roleRepoImpl.saveOrUpdate(role5, true, ReservedUserName.System.getVal());

            Role role6 = new Role();
            role6.setRoleName("Role 6");
            role6.setRights(";02;02-01-01;02-01-02;");

            roleRepoImpl.saveOrUpdate(role6, true, ReservedUserName.System.getVal());

            Role role7 = new Role();
            role7.setRoleName("Role 7");
            role7.setRights(";02;02-01-01;02-01-02;");

            roleRepoImpl.saveOrUpdate(role7, true, ReservedUserName.System.getVal());

        } catch (Exception e) {

            Assert.fail("Unexpected exception", e);
        }
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testFailDuplicateRoleName() throws Exception {

        Role role3 = new Role();
        role3.setRoleName("Role 3");
        role3.setRights(";03;03-01-01;03-01-02;");

        roleRepoImpl.saveOrUpdate(role3, true, ReservedUserName.System.getVal());
    }

    @Test(dependsOnMethods = "testFailDuplicateRoleName")
    @Transactional
    public void testFindAll() {

        List<Role> listOfAll = roleRepoImpl.find();

        Assert.assertEquals(listOfAll.size(), 7);
    }

    @Test(dependsOnMethods = "testFailDuplicateRoleName")
    @Transactional
    public void testFind() {

        Role role = roleRepoImpl.find(1L);

        Assert.assertNotNull(role.getId());

        role = roleRepoImpl.find(10L);

        Assert.assertNull(role);
    }

    @Transactional
    public void testPassLoad() {

        Role role = roleRepoImpl.load(1L);
        Assert.assertNotNull(role.getId());
    }

    @Test(expectedExceptions = HibernateException.class)
    @Transactional
    public void testFailLoad() {

        Role role = roleRepoImpl.load(10L);
        Assert.assertNull(role);
    }

    @Test(dependsOnMethods = "testFindAll")
    @Transactional
    public void testPagedList() {

        try {

            PagedResult pagedResult = roleRepoImpl.findPagedList(DetachedCriteria.forClass(Role.class), 5, 1);

            Assert.assertEquals(pagedResult.getActualRowCount().intValue(), 7);
            Assert.assertEquals(pagedResult.getResult().size(), 5);

            pagedResult = roleRepoImpl.findPagedList(DetachedCriteria.forClass(Role.class), 5, 2);

            Assert.assertEquals(pagedResult.getActualRowCount().intValue(), 7);
            Assert.assertEquals(pagedResult.getResult().size(), 2);

            pagedResult = roleRepoImpl.findPagedList(DetachedCriteria.forClass(Role.class), 5, 3);

            Assert.assertEquals(pagedResult.getActualRowCount().intValue(), 7);
            Assert.assertEquals(pagedResult.getResult().size(), 2);
            Assert.assertEquals(pagedResult.getCurrentPageIndex().intValue(), 2);

        } catch (Exception e) {

            Assert.fail("Unexpected Exception.", e);
        }
    }

}
