package com.dubby.common.model.entity.customInfo;

import org.springframework.stereotype.Repository;

@Repository
public class BranchCustomInfoHandlerTest extends BranchCustomInfoHandlerImpl implements BranchCustomInfoHandler {

    @Override
    protected Class getCustomInfoClass() {
        return BranchCustomInfoTest.class;
    }
}
