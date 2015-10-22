package com.dubby.common.model;

import com.dubby.common.model.entity.customInfo.BranchCustomInfoTest;
import com.dubby.common.model.entity.Branch;
import com.dubby.common.model.repo.BranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = "Branch")
@ContextConfiguration(locations = {"classpath:security-api-test-config.xml"})
public class BranchTest extends AbstractTestNGSpringContextTests {

    @Autowired
    BranchRepo branchRepo;

    @Transactional
    public void testInsertBranch() {

        try {
            Branch hq = new Branch();
            hq.setId("HQ");
            hq.setBranchName("Tanah Abang");
            hq.setAddress("Jl. Tanah Abang IV/32");
            hq.setPhoneNo("9999999");

            BranchCustomInfoTest bcit = new BranchCustomInfoTest();
            bcit.setBranchManager("Ali Ahmad");

            hq.setCustomInfo(bcit);

            branchRepo.saveOrUpdate(hq, true, "System");

            Branch cabang1 = new Branch();
            cabang1.setId("CBNG1");
            cabang1.setFidBranch("HQ");
            cabang1.setBranchName("Cabang I");
            cabang1.setAddress("Jl. Cabang I");
            cabang1.setPhoneNo("1111111111");

            branchRepo.saveOrUpdate(cabang1, true, "System");

            Branch cabang2 = new Branch();
            cabang2.setId("CBNG2");
            cabang2.setFidBranch("HQ");
            cabang2.setBranchName("Cabang II");
            cabang2.setAddress("Jl. Cabang II");
            cabang2.setPhoneNo("2222222222");

            branchRepo.saveOrUpdate(cabang2, true, "System");

            Branch cabang11 = new Branch();
            cabang11.setId("CB1.1");
            cabang11.setFidBranch("CBNG1");
            cabang11.setBranchName("Cabang I.I");
            cabang11.setAddress("Jl. Cabang I.I");
            cabang11.setPhoneNo("1212121212");

            branchRepo.saveOrUpdate(cabang11, true, "System");

            Branch cabang12 = new Branch();
            cabang12.setId("CB1.2");
            cabang12.setFidBranch("CBNG1");
            cabang12.setBranchName("Cabang I.II");
            cabang12.setAddress("Jl. Cabang I.II");
            cabang12.setPhoneNo("1313131313");

            branchRepo.saveOrUpdate(cabang12, true, "System");

            Branch branch121 = new Branch();
            branch121.setId("1.2.1");
            branch121.setFidBranch("CB1.2");
            branch121.setBranchName("Cabang I.II.I");
            branch121.setAddress("Jl. Cabang I.II.I");
            branch121.setPhoneNo("121121121");

            branchRepo.saveOrUpdate(branch121, true, "System");

        } catch (Exception e) {

            Assert.fail("Unexpected exception.", e);
        }
    }

    public void testRetrieveCustomInfo() {

        try {

            Branch branchHq = branchRepo.findHeadquarter();

            BranchCustomInfoTest customInfoTest = branchHq.getCustomInfo();
            Assert.assertEquals(customInfoTest.getBranchManager(), "Ali Ahmad");
        } catch (Exception e) {

            Assert.fail("Unexpected exception.", e);
        }
    }

}
