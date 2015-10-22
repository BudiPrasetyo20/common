package com.dubby.security.model;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.Dictionary;
import com.dubby.security.exception.DubbyInvalidPasswordException;
import com.dubby.security.model.entity.setting.PasswordPolicy;
import com.dubby.security.model.repo.PasswordPolicyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidatorImpl implements PasswordValidator {

    @Autowired
    PasswordPolicyRepo passwordPolicyRepo;

    @Autowired
    Dictionary securityDictionary;

    @Override
    public void validate(String password) throws BaseException {

        if (password.isEmpty()) {

            throw new DubbyInvalidPasswordException(
                    securityDictionary.constructString("security.error.password.empty"));
        }

        Pattern alpha = Pattern.compile("[\\w]");
        Pattern numeric = Pattern.compile("[\\d]");
        Pattern special = Pattern.compile("[\\W_]");

        Matcher mAlpha = alpha.matcher(password);
        Matcher mNum = numeric.matcher(password);
        Matcher mSpecial = special.matcher(password);

        try {

            PasswordPolicy passwordPolicy = passwordPolicyRepo.getPolicy();

            if (passwordPolicy.getMustContainAlpha() && !mAlpha.find()) {
                throw new DubbyInvalidPasswordException(
                        securityDictionary.constructString("security.error.password.not.contain.alpha"));
            } else if (passwordPolicy.getMustContainNumber() && !mNum.find()) {
                throw new DubbyInvalidPasswordException(
                        securityDictionary.constructString("security.error.password.not.contain.number"));
            } else if (passwordPolicy.getMustContainSpecial() && !mSpecial.find()) {
                throw new DubbyInvalidPasswordException(
                        securityDictionary.constructString("security.error.password.not.contain.special.character"));
            } else if ((passwordPolicy.getMinPasswordLength().compareTo(new Short("0")) > 0)
                    && (password.length() < passwordPolicy.getMinPasswordLength())) {
                throw new DubbyInvalidPasswordException(
                        securityDictionary.constructString("security.error.password.too.short",
                                passwordPolicy.getMinPasswordLength()));

            } else if ((passwordPolicy.getMaxPasswordLength().compareTo(new Short("0")) > 0)
                    && (password.length() > passwordPolicy.getMaxPasswordLength())) {

                throw new DubbyInvalidPasswordException(
                        securityDictionary.constructString("security.error.password.too.long",
                                passwordPolicy.getMaxPasswordLength()));
            }
        } catch (Exception e) {

            if (e instanceof DubbyInvalidPasswordException) {

                throw e;
            }

            throw new DubbyInvalidPasswordException(
                    securityDictionary.constructString("security.error.password.unknown"), e);
        }
    }
}
