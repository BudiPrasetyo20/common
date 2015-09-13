package com.dubby.base.model.entity;

import java.util.Date;

public interface HasCreateEntity {

    public boolean isNew();
    public String getCreateBy();
    public void setCreateBy(String createBy);
    public Date getCreateDate();
    public void setCreateDate(Date createDate);

}
