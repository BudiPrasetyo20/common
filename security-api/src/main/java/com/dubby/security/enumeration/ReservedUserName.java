package com.dubby.security.enumeration;

public enum ReservedUserName {

    System("SYSTEM"),
    Migrate("MIGRATE");

    private String val;

    ReservedUserName(String val) {
        this.val = val;
    }

    public ReservedUserName valOf(String val) {

        if (val.equals(System.getVal())) {

            return System;
        } else if (val.equals(Migrate.getVal())) {

            return Migrate;
        }

        return null;
    }

    public String getVal() {

        return  val;
    }
}
