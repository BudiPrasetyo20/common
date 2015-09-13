package com.dubby.base.model.entity;

import java.io.Serializable;

public interface HasRecordStatus extends Serializable {
    public Character getStatus();
    public void setStatus(Character status);
    public String getStatusDesc() throws Exception;
}
