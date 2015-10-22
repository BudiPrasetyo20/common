package com.dubby.security.model.entity;

import com.dubby.base.exception.BaseException;
import com.dubby.security.model.entity.User;

public interface UserFunctions {

    public String encodePassword(String rawPassword);

    public void changePassword(User user, String oldPassword, String newPassword, String newPassword2,
                               String changeBy) throws BaseException;

    public void openUserLock(User user, String openBy) throws BaseException;

    public void releaseLocationLocke(User user, String releaseBy) throws BaseException;
}
