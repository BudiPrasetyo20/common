package com.dubby.security.model.entity.setting;

import java.io.Serializable;

public class PasswordPolicy implements Serializable {

    private static final long serialVersionUID = -61903109527337029L;

    private Boolean allowMultipleLogin = true;
    private Short autoLogOffInterval = 0;
    private Short minPasswordLength = 0;
    private Short maxPasswordLength = 0;
    private Short maxPasswordCache = 0;
    private Short passwordAgeInDays = 0;
    private Boolean mustContainNumber = false;
    private Boolean mustContainAlpha = false;
    private Boolean mustContainSpecial = false;
    private Short maxWrongPass = 3;

    public Boolean getAllowMultipleLogin() {
        return allowMultipleLogin;
    }

    public void setAllowMultipleLogin(Boolean allowMultipleLogin) {
        this.allowMultipleLogin = allowMultipleLogin;
    }

    public Short getAutoLogOffInterval() {
        return autoLogOffInterval;
    }

    public void setAutoLogOffInterval(Short autoLogOffInterval) {
        this.autoLogOffInterval = autoLogOffInterval;
    }

    public Short getMinPasswordLength() {
        return minPasswordLength;
    }

    public void setMinPasswordLength(Short minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    public Short getMaxPasswordLength() {
        return maxPasswordLength;
    }

    public void setMaxPasswordLength(Short maxPasswordLength) {
        this.maxPasswordLength = maxPasswordLength;
    }

    public Short getMaxPasswordCache() {
        return maxPasswordCache;
    }

    public void setMaxPasswordCache(Short maxPasswordCache) {
        this.maxPasswordCache = maxPasswordCache;
    }

    public Short getPasswordAgeInDays() {
        return passwordAgeInDays;
    }

    public void setPasswordAgeInDays(Short passwordAgeInDays) {
        this.passwordAgeInDays = passwordAgeInDays;
    }

    public Boolean getMustContainNumber() {
        return mustContainNumber;
    }

    public void setMustContainNumber(Boolean mustContainNumber) {
        this.mustContainNumber = mustContainNumber;
    }

    public Boolean getMustContainAlpha() {
        return mustContainAlpha;
    }

    public void setMustContainAlpha(Boolean mustContainAlpha) {
        this.mustContainAlpha = mustContainAlpha;
    }

    public Boolean getMustContainSpecial() {
        return mustContainSpecial;
    }

    public void setMustContainSpecial(Boolean mustContainSpecial) {
        this.mustContainSpecial = mustContainSpecial;
    }

    public Short getMaxWrongPass() {
        return maxWrongPass;
    }

    public void setMaxWrongPass(Short maxWrongPass) {
        this.maxWrongPass = maxWrongPass;
    }
}
