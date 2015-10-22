package com.dubby.security.model;

import com.dubby.base.enumeration.StatusType;
import com.dubby.base.exception.BaseException;
import com.dubby.base.model.Logger;
import com.dubby.base.model.NullEmptyChecker;
import com.dubby.base.model.ObjectStringConverter;
import com.dubby.security.enumeration.ModuleCode;
import com.dubby.security.enumeration.ReservedUserName;
import com.dubby.security.enumeration.SecurityLogActivity;
import com.dubby.security.exception.MultipleLoginException;
import com.dubby.security.model.entity.DubbyUser;
import com.dubby.security.model.entity.User;
import com.dubby.security.model.entity.setting.PasswordPolicy;
import com.dubby.security.model.repo.PasswordPolicyRepo;
import com.dubby.security.model.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class AuthenticatorImpl implements Authenticator {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ObjectStringConverter objectStringConverter;

    @Autowired
    Logger securityLogger;

    @Autowired
    PasswordPolicyRepo passwordPolicyRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected User authenticate(String userName, String password) throws AuthenticationException {

        User user = userRepo.find(userName);

        if (user == null) {

            throw new UsernameNotFoundException("security.error.wrong.user.name");
        } else {

            if (!passwordEncoder.matches(password, user.getPassword())) {

                throw new BadCredentialsException("security.error.wrong.password");
            }

            userRepo.retrieveRoles(user);

            return user;
        }
    }

    protected void validateAccount(User user, String loginFrom) throws AuthenticationException {

        if (user.getStatus().equals(StatusType.Inactive.getVal())) {
            throw new DisabledException("security.error.account.inactive");
        }

        if (user.getPasswordExpire() != null) {

            if (user.getPasswordExpire().compareTo(new Date()) <= 0) {

                throw new CredentialsExpiredException("security.error.password.expire");
            }
        }

        PasswordPolicy passwordPolicy;

        try {
            passwordPolicy = passwordPolicyRepo.getPolicy();
        } catch (Exception e) {
            throw new AuthenticationServiceException("security.error.retrieve.password.policy", e);
        }

        if (user.getFailCount() >= passwordPolicy.getMaxWrongPass()) {
            throw new LockedException("security.error.account.locked");
        }

        if (!passwordPolicy.getAllowMultipleLogin()) {

            if (NullEmptyChecker.isNotNullOrEmpty(user.getLastLoginFrom())) {

                if (!user.getLastLoginFrom().equals(loginFrom)) {

                    if (NullEmptyChecker.isNullOrEmpty(user.getLastLogoutTime())) {
                        throw new MultipleLoginException(String.format("%s;%s", "security.error.locked.from.other.location",
                                user.getLastLoginFrom()));
                    } else if (user.getLastLoginTime().compareTo(user.getLastLogoutTime()) > 0) {
                        throw new MultipleLoginException(String.format("%s;%s", "security.error.locked.from.other.location",
                                user.getLastLoginFrom()));
                    }
                }
            }
        }
    }

    protected void doOnLoginSuccess(User user, String loginFrom) throws AuthenticationException {

        try {

            user.setFailCount(Short.valueOf("0"));
            user.setLastLoginFrom(loginFrom);
            user.setLastLoginTime(new Date());

            userRepo.saveOrUpdate(user, false, ReservedUserName.System.getVal());
        } catch (Exception e) {

            securityLogger.logError(e, "security.error.update.user.login.information");
            throw new AuthenticationServiceException("security.error.update.user.login.information", e);
        }
    }

    protected void doOnLoginFail(String userName) throws AuthenticationException {

        try {

            User user = userRepo.find(userName);

            if (user != null) {

                user.setFailCount((short) (user.getFailCount() + 1));
                userRepo.saveOrUpdate(user, false, ReservedUserName.System.getVal());
            }

        } catch (Exception e) {

            throw new AuthenticationServiceException("security.error.login.unknown", e);
        }
    }

    @Transactional
    protected Object executeLogin(String userName, String password, String loginFrom)
            throws AuthenticationException {

        User user = null;

        try {

            user = authenticate(userName, password);
            validateAccount(user, loginFrom);

        } catch (AuthenticationException ae) {

            doOnLoginFail(userName);
            return ae;
        }

        try {

            DubbyUser dubbyUser = new DubbyUser(user);

            doOnLoginSuccess(user, loginFrom);

            return dubbyUser;
        } catch (BaseException se) {
            throw new AuthenticationServiceException(se.getMessage(), se);
        }
    }

    @Override
    public UserDetails login(String userName, String password, String loginFrom)
            throws AuthenticationException {

        Object loginResult = executeLogin(userName, password, loginFrom);

        if (loginResult instanceof AuthenticationException) {
            throw (AuthenticationException) loginResult;
        }

        return (UserDetails) loginResult;
    }

    @Override
    @Transactional
    public void logout(String userName) throws AuthenticationException {

        try {
            User user = userRepo.find(userName);

            user.setLastLogoutTime(new Date());

            userRepo.saveOrUpdate(user, false, ReservedUserName.System.getVal());

            securityLogger.logToAuditTrail(SecurityLogActivity.Logout.getVal(), ModuleCode.Authentication.getVal(),
                    userName, "", ReservedUserName.System.getVal());
        } catch (Exception e) {

            throw new AuthenticationServiceException("security.error.logout", e);
        }
    }


//    public User authenticate(String userName, String password) throws DubbyAuthenticationException {
//
//        User user = userRepo.find(userName);
//
//        if (user == null) {
//
//            throw new DubbyInvalidUserException(securityDictionary.constructString("security.error.wrong.user.name"));
//        } else {
//
//            String cryptPassword;
//
//            try {
//
//                cryptPassword = crypt.hash(password);
//            } catch (Exception e) {
//
//                securityLogger.logError(e, "security.error.login.unknown");
//                throw new DubbyAuthenticationException(
//                        securityDictionary.constructString("security.error.login.unknown"), e);
//            }
//
//            if (!user.getPassword().equals(cryptPassword)) {
//
//                throw new DubbyInvalidPasswordException(securityDictionary.constructString("security.error.wrong.password"));
//            }
//
//            userRepo.retrieveRoles(user);
//
//            return user;
//        }
//    }
//
//    public void validateAccount(User user, String loginFrom) throws Exception {
//
//        if (user.getStatus().equals(StatusType.Inactive.getVal())) {
//            throw new DisabledException(securityDictionary.constructString("securiyt.error.account.inactive"));
//        }
//
//        if (user.getPasswordExpire() != null) {
//
//            if (user.getPasswordExpire().compareTo(new Date()) <= 0) {
//
//                throw new CredentialException(securityDictionary.constructString("security.error.password.expire"));
//            }
//        }
//
//        PasswordPolicy passwordPolicy = passwordPolicyRepo.getPolicy();
//
//        if (user.getFailCount() >= passwordPolicy.getMaxWrongPass()) {
//            throw new SkyAccountLockedException(securityDictionary.constructString("security.error.account.locked"));
//        }
//
//        if (!passwordPolicy.getAllowMultipleLogin()) {
//
//            if (NullEmptyChecker.isNotNullOrEmpty(user.getLastLoginFrom())) {
//
//                if (!user.getLastLoginFrom().equals(loginFrom)) {
//
//                    if (NullEmptyChecker.isNullOrEmpty(user.getLastLogoutTime())) {
//                        throw new SkyMultipleLoginException(securityDictionary
//                                .constructString("security.error.locked.from.other.location", user.getLastLoginFrom()));
//                    } else if (user.getLastLoginTime().compareTo(user.getLastLogoutTime()) > 0) {
//                        throw new SkyMultipleLoginException(securityDictionary
//                                .constructString("security.error.locked.from.other.location", user.getLastLoginFrom()));
//                    }
//                }
//            }
//        }
//    }
//
////    public void doOnLoginSuccess(String userName, String loginFrom) throws Exception {
////
////        User user = userRepo.find(userName);
////
////        doOnLoginSuccess(user, loginFrom);
////    }
//
//    public void doOnLoginSuccess(User user, String loginFrom) throws Exception {
//
//        user.setFailCount(Short.valueOf("0"));
//        user.setLastLoginFrom(loginFrom);
//        user.setLastLoginTime(new Date());
//
//        userRepo.saveOrUpdate(user, false, ReservedUserName.System.getVal());
//    }
//
//    public void doOnLoginFail(String userName) throws DubbyAuthenticationException {
//
//        try {
//            User user = userRepo.find(userName);
//
//            if (user != null) {
//
//                user.setFailCount((short) (user.getFailCount() + 1));
//                userRepo.saveOrUpdate(user, false, ReservedUserName.System.getVal());
//            }
//
//        } catch (Exception e) {
//
//            throw new DubbyAuthenticationException(
//                    securityDictionary.constructString("security.error.login.unknown"), e);
//        }
//    }
//
//    @Transactional
//    protected Object executeLogin(String userName, String password, String loginFrom)
//            throws DubbyAuthenticationException {
//
//        User user = null;
//
//        try {
//
//            user = authenticate(userName, password);
//            validateAccount(user, loginFrom);
//
//        } catch (AuthenticationException ae) {
//
//            doOnLoginFail(userName);
//            return ae;
//        }
//
//        try {
//
//            SkyUser skyUser = new SkyUser(user);
//
//            doOnLoginSuccess(user, loginFrom);
//
//            return skyUser;
//        } catch (BaseException se) {
//            throw new AuthenticationServiceException(se.getMessage(), se);
//        }
//    }
//
//    @Override
//    public UserDetails login(String userName, String password, String loginFrom)
//            throws AuthenticationException {
//
//        Object loginResult = executeLogin(userName, password, loginFrom);
//
//        if (loginResult instanceof AuthenticationException) {
//            throw (AuthenticationException) loginResult;
//        }
//
//        return (UserDetails) loginResult;
//    }
//
//    @Override
//    @Transactional
//    public void logout(String userName) throws Exception{
//
//        User user = userRepo.find(userName);
//
//        user.setLastLogoutTime(new Date());
//
//        userRepo.saveOrUpdate(user, false, ReservedUserName.System.getVal());
//
//        securityLogger.logToAuditTrail(SecurityLogActivity.Logout.getVal(), ModuleCode.Authentication.getVal(),
//                userName, "", ReservedUserName.System.getVal());
//    }
}
