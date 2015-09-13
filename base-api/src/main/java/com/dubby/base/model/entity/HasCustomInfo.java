package com.dubby.base.model.entity;

import java.io.Serializable;

public interface HasCustomInfo extends Serializable {

    public String getRawCustomInfo();
    public void setRawCustomInfo(String rawCustomInfo);
    public <T extends CustomInfo> T getCustomInfo() throws Exception;
    public <T extends CustomInfo> void setCustomInfo(T customInfo) throws Exception;
}
