package com.dubby.security.model.repo;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.repo.SettingRepo;
import com.dubby.security.model.entity.setting.PasswordPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordPolicyRepoImpl implements PasswordPolicyRepo {

    @Autowired
    SettingRepo settingRepo;

    @Override
    public PasswordPolicy getPolicy() throws BaseException {

        try {

            return settingRepo.getSetting(PasswordPolicy.class);
        } catch (Exception e) {

            throw new BaseException("security.error.retrieve.password.policy", e);
        }
    }
}
