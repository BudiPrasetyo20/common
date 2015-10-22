package com.dubby.security.enumeration;

public enum ModuleCode {

    User("USER"),
    UserRole("USRRL"),
    PasswordHistory("PWHST"),
    Role("ROLE"),
    Rights("RIGHT"),
    Authentication("AUTHE");
    
    private String val;
    
    ModuleCode(String val) {
        this.val = val;
    }

    public static ModuleCode valOf(String val) {
        if (val.equals(User.getVal())) {
            return User;
        } else if (val.equals(Role.getVal())) {
            return Role;
        } else if (val.equals(Rights.getVal())) {
            return Rights;
        } else if (val.equals(UserRole.getVal())) {
            return UserRole;
        }

        return null;
    }

    public String getVal() {
        return val;
    }

}
