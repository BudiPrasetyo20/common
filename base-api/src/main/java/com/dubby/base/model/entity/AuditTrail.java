package com.dubby.base.model.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.Date;

@Configurable
@Entity
@Table(name="BASE_AUDIT_TRAIL")
public class AuditTrail implements HasSerializableEntity {

	private static final long serialVersionUID = 1L;

    protected Long id;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected String activityName;

    @Column(name = "ACTIVITY_NAME", length = 5)
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    protected Date activityDate;

    @Column(name = "ACTIVITY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    protected String additionalInfo;

    @Column(name = "ADDITIONAL_INFO")
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    protected String module;

    @Column(name = "MODULE_CODE", length = 5)
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    protected String previousValue;

    @Column(name = "PREVIOUS_VALUE", nullable = true)
    @Lob
    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    protected String referenceId;

    @Column(name = "REFERENCE_ID", length = 100)
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    protected String performedBy;

    @Column(name = "PERFORMED_BY", length = 20)
    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        AuditTrail rhs = (AuditTrail) obj;

        return new EqualsBuilder()
                .append(id, rhs.id)
                .append(activityName, rhs.activityName)
                .append(activityDate, rhs.activityDate)
                .append(additionalInfo, rhs.additionalInfo)
                .append(module, rhs.module)
                .append(previousValue, rhs.previousValue)
                .append(referenceId, rhs.referenceId)
                .append(performedBy, rhs.performedBy)
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37).
                append(id).
                append(activityName).
                append(activityDate).
                append(additionalInfo).
                append(module).
                append(previousValue).
                append(referenceId).
                toHashCode();
    }

}
