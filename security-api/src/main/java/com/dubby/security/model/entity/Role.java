package com.dubby.security.model.entity;

import com.dubby.base.enumeration.StatusType;


import com.dubby.base.model.Dictionary;
import com.dubby.base.model.entity.*;
import com.dubby.security.model.entity.customInfo.RoleCustomInfoHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configurable
@Entity
@Table(name="SEC_ROLE")
public class Role implements HasSerializableEntity, HasUpdateEntity, HasPicker, HasRecordStatus, HasCustomInfo {

    private static final long serialVersionUID = 1L;

    @Autowired
    Dictionary baseDictionary;

    @Autowired
    RoleCustomInfoHandler roleCustomInfoHandlerImpl;

    private Long id;

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String roleName;

    @Column(name="ROLE_NAME",unique=true, length = 100)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private String rights;

    @Column(name="RIGHTS")
    @Lob
    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    private String rawCustomInfo;

    @Column(name = "RAW_CUSTOM_INFO")
    @Lob
    @Override
    public String getRawCustomInfo() {
        return rawCustomInfo;
    }

    @Override
    public void setRawCustomInfo(String rawCustomInfo) {
        this.rawCustomInfo = rawCustomInfo;
    }

    private Character status = StatusType.Active.getVal();

    @Column(name="STATUS", length = 1)
    public Character getStatus() {
        return status;
    }

    @JsonIgnore
    @Transient
    @Override
    public String getStatusDesc() {

        StatusType statusType = StatusType.valOf(getStatus());

        return baseDictionary
                .constructString(String.format("%s%s", statusType.getDictionaryPrefix(), statusType.toString()));
    }

    @JsonIgnore
    @Transient
    public StatusType getStatusEnum() {
        return StatusType.valOf(this.status);
    }

    @Override
    public void setStatus(Character status) {

        this.status = status;
    }

    public void setStatus(StatusType status) {
        this.status = status.getVal();
    }

    private String lastUpdateBy;

    @Column(name="LAST_UPDATE_BY", length = 20)
    @Override
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    @Override
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    private Date lastUpdateDate;

    @Column(name="LAST_UPDATE_DATE")
    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    private Set<User> users = new HashSet<User>(0);

    @ManyToMany(fetch= FetchType.LAZY)
    @ForeignKey(name = "none")
    @JoinTable(name="SEC_USER_ROLE",
            joinColumns = { @JoinColumn(name = "IDF_ROLE") },
            inverseJoinColumns = { @JoinColumn(name = "IDF_USER") })
    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    @Transient
    @JsonIgnore
    public <T extends CustomInfo> T getCustomInfo() throws Exception {

        return (T) roleCustomInfoHandlerImpl.getCustomInfo(rawCustomInfo);
    }

    @Override
    public <T extends CustomInfo> void setCustomInfo(T customInfo) throws Exception {

        rawCustomInfo = roleCustomInfoHandlerImpl.getRawCustomInfo(customInfo);
    }

    @Override
    @Transient
    @JsonIgnore
    public String getPickerCode() {
        return String.valueOf(id);
    }

    @Override
    @Transient
    public String getPickerDescription() {
        return roleName;
    }
}
