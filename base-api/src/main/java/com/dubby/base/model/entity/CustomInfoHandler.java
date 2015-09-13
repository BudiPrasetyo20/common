package com.dubby.base.model.entity;

public interface CustomInfoHandler {

    public Object getCustomInfo(String rawCustomInfo) throws Exception;
    public String getRawCustomInfo(Object customInfo) throws Exception;
}
