package com.dubby.security.model.repo;

import com.dubby.base.exception.BaseException;
import com.dubby.security.model.entity.setting.PasswordPolicy;

public interface PasswordPolicyRepo {

    public PasswordPolicy getPolicy() throws BaseException;
}
