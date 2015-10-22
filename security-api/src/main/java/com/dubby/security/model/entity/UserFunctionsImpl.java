package com.dubby.security.model.entity;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.ObjectStringConverter;
import com.dubby.security.exception.DubbyInvalidPasswordException;
import com.dubby.security.model.PasswordValidator;
import com.dubby.security.model.SecurityDictionary;
import com.dubby.security.model.entity.setting.PasswordPolicy;
import com.dubby.security.model.repo.PasswordPolicyRepo;
import com.dubby.security.model.repo.UserRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class UserFunctionsImpl implements UserFunctions {

    @Autowired
    UserRepo userRepoImpl;

    @Autowired
    PasswordPolicyRepo passwordPolicyRepoImpl;

    @Autowired
    PasswordValidator passwordValidatorImpl;

    @Autowired
    ObjectStringConverter objectStringConverterImpl;

    @Autowired
    SecurityDictionary securityDictionary;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword, String newPassword2, String changeBy)
            throws BaseException {

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {

            throw new DubbyInvalidPasswordException(securityDictionary
                    .constructString("security.error.wrong.password"));
        }

        if (!newPassword.equals(newPassword2)) {

            throw new DubbyInvalidPasswordException(securityDictionary
                    .constructString("security.error.password.unmatched"));
        }

        passwordValidatorImpl.validate(newPassword);

        PasswordPolicy passwordPolicy = passwordPolicyRepoImpl.getPolicy();

        String encPassword = passwordEncoder.encode(newPassword);

        if (passwordPolicy.getMaxPasswordCache() > 0) {

            if (passwordEncoder.matches(newPassword, user.getPassword())) {

                throw new DubbyInvalidPasswordException(securityDictionary
                        .constructString("security.error.password.already.used"));
            }

            List<String> passwordCache = new ArrayList<String>();

            if (!user.getPasswordCache().isEmpty()) {

                try {
                    passwordCache = objectStringConverterImpl
                            .convertToListOfObjects(new TypeReference<List<String>>() {
                            }, user.getPasswordCache());
                } catch (Exception e) {

                    throw new BaseException("security.error.processing.password.cache", e);
                }

                for (String pCache : passwordCache) {

                    if (passwordEncoder.matches(newPassword, pCache)) {
                        throw new DubbyInvalidPasswordException(securityDictionary
                                .constructString("security.error.password.already.used"));
                    }
                }
            }

            passwordCache.add(0, encPassword);

            if (passwordCache.size() > passwordPolicy.getMaxPasswordCache()) {
                passwordCache.remove(passwordCache.size() - 1);
            }

            try {

                user.setPasswordCache(objectStringConverterImpl.convertToString(List.class, passwordCache));
            } catch (Exception e) {

                throw new BaseException("security.error.processing.password.cache", e);
            }

        }

        user.setPassword(encPassword);

        if (passwordPolicy.getPasswordAgeInDays().compareTo(Short.valueOf("0")) > 0) {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, passwordPolicy.getPasswordAgeInDays());

            user.setPasswordExpire(calendar.getTime());
        } else {

            user.setPasswordExpire(null);
        }

        userRepoImpl.saveOrUpdate(user, false, changeBy);
    }

    @Override
    public void openUserLock(User user, String openBy) throws BaseException {

        user.setFailCount(Short.valueOf("0"));
        userRepoImpl.saveOrUpdate(user, false, openBy);
    }

    @Override
    public void releaseLocationLocke(User user, String releaseBy) throws BaseException {

        user.setLastLoginFrom(null);
        userRepoImpl.saveOrUpdate(user, false, releaseBy);
    }
}
