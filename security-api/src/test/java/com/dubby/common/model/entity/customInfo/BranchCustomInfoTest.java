package com.dubby.common.model.entity.customInfo;

import com.dubby.base.model.entity.CustomInfo;

public class BranchCustomInfoTest implements CustomInfo {

    private String branchManager;

    public String getBranchManager() {
        return branchManager;
    }

    public void setBranchManager(String branchManager) {
        this.branchManager = branchManager;
    }
}
