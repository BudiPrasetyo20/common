package com.dubby.security.model.entity.customInfo;

import com.dubby.base.model.abstracts.ACustomInfoHandler;
import com.dubby.base.model.entity.CustomInfo;
import org.springframework.stereotype.Repository;

@Repository
public class UserCustomInfoHandlerImpl extends ACustomInfoHandler implements UserCustomInfoHandler {

    @Override
    protected Class getCustomInfoClass() {
        return CustomInfo.class;
    }

}
