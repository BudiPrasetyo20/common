package com.dubby.security.enumeration;


import com.dubby.base.enumeration.HasEnumExtensible;

public enum SecurityLogActivity implements HasEnumExtensible {

    Login("LOGIN"),
    Logout("LGOUT");

    private String val;

    SecurityLogActivity(String val) {
        this.val = val;
    }

    public String getVal() {

        return val;
    }

    public SecurityLogActivity valOf(String val) {

        if (val.equals(Login.getVal())) {
            return Login;
        } else if (val.equals(Logout.getVal())) {
            return Logout;
        }

        return null;
    }

    @Override
    public String getDictionaryPrefix() {
        return "security.label.enum.logactivity";
    }
}
