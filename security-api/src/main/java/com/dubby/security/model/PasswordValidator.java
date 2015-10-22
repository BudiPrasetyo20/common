package com.dubby.security.model;

import com.dubby.base.exception.BaseException;

public interface PasswordValidator {

    public void validate(String password) throws BaseException;
}
