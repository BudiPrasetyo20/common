/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubby.base.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="BASE_MODULE_SETTING")
public class ModuleSetting implements HasSerializableEntity, HasUpdateEntity{

    private static final long serialVersionUID = 1L;

    protected String id;
    protected String setting;
    private String lastUpdateBy;
    private Date lastUpdateDate;

    @Id
    @Column(name="ID", length=100)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "SETTING")
    @Lob
    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    @Column(name = "LAST_UPDATE_BY")
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    @Version
    @Column(name = "LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        ModuleSetting rhs = (ModuleSetting) obj;

        return new EqualsBuilder()
                .append(id, rhs.id)
                .append(setting, rhs.setting)
                .append(lastUpdateBy, rhs.lastUpdateBy)
                .append(lastUpdateDate, rhs.lastUpdateDate)
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37).
                append(id).
                append(setting).
                append(lastUpdateBy).
                append(lastUpdateDate).
                toHashCode();
    }
}
