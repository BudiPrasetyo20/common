package com.dubby.security.model.repo;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.PagedResult;
import com.dubby.common.model.abstracts.ACommonPickerRepo;
import com.dubby.security.enumeration.ReservedUserName;
import com.dubby.security.exception.DubbyInvalidUserException;
import com.dubby.security.model.entity.User;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepoImpl extends ACommonPickerRepo<User> implements UserRepo {

    @Override
    protected Class<User> getEntityClass() {

        return User.class;
    }

    @Override
    public User saveOrUpdate(User instance, boolean isNew, String userName) throws BaseException {

        for (ReservedUserName reservedUserName : ReservedUserName.values()) {

            if (instance.getId().equals(reservedUserName.getVal())) {
                throw new DubbyInvalidUserException("security.error.user.id.reserved");
            }
        }

        return super.saveOrUpdate(instance, isNew, userName);
    }

    @Override
    @Transactional
    public void changePassword(String id, String oldPassword, String newPassword, String updateBy)
            throws BaseException {

        User user = getPersistenceManager().find(getEntityClass(), id);

        if (user == null) {
            throw new DubbyInvalidUserException("security.error.wrong.user.name");
        }

        user.changePassword(oldPassword, newPassword, newPassword, updateBy);
    }

    @Override
    @Transactional
    public List<User> findAndLoadRoles() {

        List<User> result = getPersistenceManager().find(getEntityClass());

        for (User user : result) {
            Hibernate.initialize(user.getRoles());
        }

        return result;
    }

    @Override
    @Transactional
    public User findAndLoadRoles(String id) {

        User result = getPersistenceManager().find(getEntityClass(), id);

        if (result != null) {
            Hibernate.initialize(result.getRoles());
        }

        return result;
    }

    @Override
    @Transactional
    public PagedResult findPagedAndLoadRoles(Criterion criterion, Integer pageSize, Integer pageIndex) throws Exception {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);

        if (criterion != null) {
            detachedCriteria.add(criterion);
        }

        PagedResult pagedResult = getPersistenceManager().findPagedList(detachedCriteria, pageSize, pageIndex);

        for (Object result : pagedResult.getResult()) {

            User user = (User) result;
            Hibernate.initialize(user.getRoles());

        }

        return pagedResult;
    }

    @Override
    public void retrieveRoles(User user) {
        Hibernate.initialize(user.getRoles());
    }
}
