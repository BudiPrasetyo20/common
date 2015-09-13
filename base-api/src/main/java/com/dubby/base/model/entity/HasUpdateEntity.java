package com.dubby.base.model.entity;

import java.util.Date;

public interface HasUpdateEntity {

    public String getLastUpdateBy();
    public void setLastUpdateBy(String lastUpdateBy);
    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date lastUpdateDate);

}
