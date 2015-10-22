package com.dubby.security.model.entity;

import com.dubby.base.exception.BaseException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

public class DubbyUser extends User {

    private String branchCode;
    private String divisionCode;
    private String rights;

    public String getBranchCode() {
        return branchCode;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public DubbyUser(com.dubby.security.model.entity.User user) throws AuthenticationException, BaseException {

        super(user.getId(), "[#####]",
                user.isActive(),
                !user.isCredentialsExpire(),
                !user.isCredentialsExpire(), !user.isAccountLocked(), user.getAuthorities());

        this.branchCode = user.getFidBranch();
        this.divisionCode = user.getFidCmnDivision();
    }
}
