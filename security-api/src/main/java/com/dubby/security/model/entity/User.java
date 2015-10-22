package com.dubby.security.model.entity;

import com.dubby.base.enumeration.StatusType;
import com.dubby.base.exception.BaseException;
import com.dubby.base.model.BaseDictionary;
import com.dubby.base.model.Logger;
import com.dubby.base.model.NullEmptyChecker;
import com.dubby.base.model.ObjectStringConverter;
import com.dubby.base.model.entity.*;
import com.dubby.common.model.ValueTextBuilder;
import com.dubby.common.model.entity.Branch;
import com.dubby.common.model.entity.Division;
import com.dubby.common.model.entity.PickerItem;
import com.dubby.common.model.repo.BranchRepo;
import com.dubby.security.enumeration.DataAccessRestriction;
import com.dubby.security.model.entity.customInfo.UserCustomInfoHandler;
import com.dubby.security.model.entity.setting.PasswordPolicy;
import com.dubby.security.model.repo.PasswordPolicyRepo;
import com.dubby.security.model.repo.RoleRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Configurable
@Entity
@Table(name = "SEC_USER")
public class User implements HasSerializableEntity, HasUpdateEntity, HasRecordStatus, HasPicker, HasCustomInfo {

    private static final long serialVersionUID = 1L;

    @Autowired
    UserFunctions userFunctionsImpl;

    @Autowired
    BaseDictionary baseDictionary;

    @Autowired
    BranchRepo branchRepo;

    @Autowired
    ObjectStringConverter objectStringConverterImpl;

    @Autowired
    RoleRepo roleRepoImpl;

    @Autowired
    UserCustomInfoHandler userCustomInfoHandlerImpl;

    @Autowired
    Logger loggerImpl;

    @Autowired
    PasswordPolicyRepo passwordPolicyRepoImpl;

    private Collection<SimpleGrantedAuthority> authorities = new HashSet<>();

    private String id;

    @Id
    @Column(name = "ID", length = 20)
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String fidBranch;

    @Column(name = "FID_BRANCH", length = 5, nullable = false)
    public String getFidBranch() {
        return fidBranch;
    }

    public void setFidBranch(String fidBranch) {
        this.fidBranch = fidBranch;
    }

    private String fidCmnDivision;

    @Column(name = "FID_CMN_DIVISION", length = 5)
    public String getFidCmnDivision() {
        return fidCmnDivision;
    }

    public void setFidCmnDivision(String fidCmnDivision) {
        this.fidCmnDivision = fidCmnDivision;
    }

    private String fidSupervisor;

    @Column(name = "FID_SUPERVISOR", length = 20)
    public String getFidSupervisor() {
        return fidSupervisor;
    }

    public void setFidSupervisor(String fidSupervisor) {
        this.fidSupervisor = fidSupervisor;
    }

    private String accessBranch;

    @Column(name = "ACCESS_BRANCH", length = 255)
    public String getAccessBranch() {
        return accessBranch;
    }

    public void setAccessBranch(String accessBranch) {
        this.accessBranch = accessBranch;
    }

    private String address;

    @Column(name = "ADDRESS", length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private Date birthDate;

    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.DATE)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    private String birthPlace;

    @Column(name = "BIRTH_PLACE", length = 50)
    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    private Character dataAccessRestriction = DataAccessRestriction.SelfBranch.getVal();

    @Column(name = "DATA_ACCESS_RESTRICTION", length = 1)
    public Character getDataAccessRestriction() {
        return dataAccessRestriction;
    }

    public void setDataAccessRestriction(Character dataAccessRestriction) {
        this.dataAccessRestriction = dataAccessRestriction;
    }

    private String defaultLanguage;

    @Column(name = "DEFAULT_LANGUAGE", length = 10)
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    private String employeeId;

    @Column(name = "EMPLOYEE_ID", length = 20)
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    private Short failCount = 0;

    @Column(name = "FAIL_COUNT")
    public Short getFailCount() {
        return failCount;
    }

    public void setFailCount(Short failCount) {
        this.failCount = failCount;
    }

    private String lastLoginFrom;

    @Column(name = "LAST_LOGIN_FROM", length = 100)
    public String getLastLoginFrom() {
        return lastLoginFrom;
    }

    public void setLastLoginFrom(String lastLoginFrom) {
        this.lastLoginFrom = lastLoginFrom;
    }

    private Date lastLoginTime;

    @Column(name = "LAST_LOGIN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    private Date lastLogoutTime;

    @Column(name = "LAST_LOGOUT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(Date lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    private Character maritalStatus;

    @Column(name = "MARITAL_STATUS", length = 1)
    public Character getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Character maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    private String name;

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String password = "";

    @Column(name = "PASSWORD", length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRawPassword(String rawPassword) {
        this.password = userFunctionsImpl.encodePassword(rawPassword);
    }

    private String passwordCache = "";

    @Column(name = "PASSWORD_CACHE")
    @Lob
    public String getPasswordCache() {
        return passwordCache;
    }

    public void setPasswordCache(String passwordCache) {
        this.passwordCache = passwordCache;
    }

    private Date passwordExpire;

    @Column(name = "PASSWORD_EXPIRE")
    @Temporal(TemporalType.DATE)
    public Date getPasswordExpire() {
        return passwordExpire;
    }

    public void setPasswordExpire(Date passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    private Character sex;

    @Column(name = "SEX", length = 1)
    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    private String rawCustomInfo;

    @Column(name = "RAW_CUSTOM_INFO")
    @Lob
    @Override
    @JsonIgnore
    public String getRawCustomInfo() {
        return rawCustomInfo;
    }

    @Override
    public void setRawCustomInfo(String rawCustomInfo) {
        this.rawCustomInfo = rawCustomInfo;
    }

    private Character status = StatusType.Active.getVal();

    @Column(name = "STATUS", length = 1)
    @Override
    public Character getStatus() {
        return status;
    }

    @JsonIgnore
    @Transient
    public StatusType getStatusEnum() {
        return StatusType.valOf(status);
    }

    @JsonIgnore
    @Transient
    @Override
    public String getStatusDesc() {

        StatusType statusType = StatusType.valOf(getStatus());

        return baseDictionary
                .constructString(String.format("%s%s", statusType.getDictionaryPrefix(), statusType.toString()));
    }

    @Override
    public void setStatus(Character status) {
        this.status = status;
    }

    @JsonIgnore
    public void setStatus(StatusType status) {
        this.status = status.getVal();
    }

    private String lastUpdateBy = "";

    @Column(name = "LAST_UPDATE_BY", length = 20)
    @Override
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    @Override
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    private Date lastUpdateDate;

    @Column(name = "LAST_UPDATE_DATE")
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

    private User supervisor;

    @ManyToOne(optional = true)
    @ForeignKey(name = "none")
    @JoinColumn(name = "FID_SUPERVISOR", insertable = false, updatable = false, nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @ForeignKey(name = "none")
    @JoinColumn(name = "FID_CMN_DIVISION", insertable = false, updatable = false)
    @JsonIgnore
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    private Set<Role> roles = new HashSet<Role>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @ForeignKey(name = "none")
    @JoinTable(name = "SEC_USER_ROLE",
            joinColumns = {@JoinColumn(name = "IDF_USER")},
            inverseJoinColumns = {@JoinColumn(name = "IDF_ROLE")})
    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public String getValueTextRoles() {

        List<PickerItem> result = ValueTextBuilder.buildFromEntities(roles);
        String jsonResult = null;

        try {
            jsonResult = objectStringConverterImpl.convertToString(List.class, result);
        } catch (Exception e) {
            loggerImpl.logError(e, "security.error.convert.role.to.json");
        }

        return jsonResult;
    }

    public void setValueTextRoles(String valueTextRoles) {

        try {
            List<PickerItem> listOfPickerItem = objectStringConverterImpl.convertToListOfObjects(
                    new TypeReference<List<PickerItem>>() {
                    }, valueTextRoles);
            roles.clear();

            for (PickerItem pickerItem : listOfPickerItem) {
                roles.add(roleRepoImpl.find(Long.valueOf(pickerItem.getPickerCode().toString())));
            }
        } catch (Exception e) {
            loggerImpl.logError(e, "security.error.convert.json.to.role");
        }
    }

    @Override
    @Transient
    @JsonIgnore
    public String getPickerCode() {
        return id;
    }

    @Override
    @Transient
    @JsonIgnore
    public String getPickerDescription() {
        return name;
    }

    @Override
    @Transient
    @JsonIgnore
    public <T extends CustomInfo> T getCustomInfo() throws Exception {

        return (T) userCustomInfoHandlerImpl.getCustomInfo(rawCustomInfo);
    }

    @Override
    public <T extends CustomInfo> void setCustomInfo(T customInfo) throws Exception {

        rawCustomInfo = userCustomInfoHandlerImpl.getRawCustomInfo(customInfo);
    }


    private List<String> accessableBranch = new ArrayList<String>();

    @Transient
    @JsonIgnore
    public List<String> getAccessableBranch() throws Exception {

        if (NullEmptyChecker.isNullOrEmpty(accessableBranch)) {

            if (getDataAccessRestriction().equals(
                    DataAccessRestriction.AllBranch.getVal())) {

                return null;
            } else if (getDataAccessRestriction().equals(
                    DataAccessRestriction.Subordinate.getVal())) {

                Branch branch = branchRepo.find(getFidBranch());
                System.out.println("branch = " + branch);
                accessableBranch.addAll(branch.getAllSubordinateId());
                accessableBranch.add(getFidBranch());
            } else if (getDataAccessRestriction().equals(
                    DataAccessRestriction.SelfBranch.getVal())) {

                accessableBranch = Arrays.asList(getFidBranch());
            } else {

                String[] accessItem = this.getAccessBranch().split(";");

                for (int x = 0; x < accessItem.length; x++) {
                    accessableBranch.add(accessItem[x]);
                }
            }
        }

        return accessableBranch;
    }

    public void changePassword(String oldPassword, String newPassword, String newPassword2, String changeBy)
            throws BaseException {

        userFunctionsImpl.changePassword(this, oldPassword, newPassword, newPassword2, changeBy);
    }

    public void openUserLock(String openBy) throws BaseException {

        userFunctionsImpl.openUserLock(this, openBy);
    }

    public void releaseLocationLocke(String releaseBy) throws BaseException {

        userFunctionsImpl.releaseLocationLocke(this, releaseBy);
    }


    @Transient
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (authorities.isEmpty()) {

            for (Role role : roles) {

                String[] rights = role.getRights().split(";");

                for (String right : rights) {

                    if (NullEmptyChecker.isNotNullOrEmpty(right)) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + right));
                    }
                }
            }
        }
        return authorities;
    }

    @Transient
    @JsonIgnore
    public Boolean isAuthorized(String authorizationCode) {

        return getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + authorizationCode));
    }

    @Transient
    @JsonIgnore
    public boolean isActive() {

        return status.equals(StatusType.Active.getVal()) || status.equals(StatusType.System.getVal());
    }

    @Transient
    @JsonIgnore
    public boolean isCredentialsExpire() {

        boolean expired = false;

        if (passwordExpire != null) {
            if (passwordExpire.before(new Date())) {
                expired = true;
            }
        }

        return expired;
    }

    @Transient
    @JsonIgnore
    public boolean isAccountLocked() throws BaseException {

        PasswordPolicy passwordPolicy = passwordPolicyRepoImpl.getPolicy();

        return (failCount > passwordPolicy.getMaxWrongPass());
    }
}