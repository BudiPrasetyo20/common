package com.dubby.base.model;

import java.util.ResourceBundle;

public interface Dictionary {

    public ResourceBundle getResource();
    public String constructString(String key, Object... args);
}
