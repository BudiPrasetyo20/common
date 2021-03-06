package com.dubby.base.model.repo;


import com.dubby.base.exception.BaseException;
import com.dubby.base.model.NullEmptyChecker;
import com.dubby.base.model.PagedResult;
import com.dubby.base.model.PersistenceManager;
import com.dubby.base.model.entity.HasCreateEntity;
import com.dubby.base.model.entity.HasSerializableEntity;
import com.dubby.base.model.entity.HasUpdateEntity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public abstract class ARepo<T extends HasSerializableEntity> implements Repo<T> {

    @Autowired
    PersistenceManager persistenceManagerImpl;

    protected PersistenceManager getPersistenceManager() {
        return persistenceManagerImpl;
    }

    protected abstract Class<T> getEntityClass();

    @Transactional
    @Override
    public T saveOrUpdate(T instance, boolean isNew, String userName) throws BaseException {

        if (isNew) {
            getPersistenceManager().create(instance, userName);
        } else {
            instance = getPersistenceManager().merge(instance, userName);
        }

        return instance;
    }

    @Transactional
    @Override
    public T saveOrUpdate(T instance, String userName) throws BaseException {

        Boolean isNew;

        if (instance instanceof HasCreateEntity) {
            isNew = NullEmptyChecker.isNullOrEmpty(((HasCreateEntity) instance).getCreateBy());
        } else if (instance instanceof HasUpdateEntity) {
            isNew = NullEmptyChecker.isNullOrEmpty(((HasUpdateEntity) instance).getLastUpdateBy());
        } else {
            isNew = NullEmptyChecker.isNullOrEmpty(instance.getId());
        }

        return saveOrUpdate(instance, isNew, userName);
    }

    @Transactional
    @Override
    public void delete(T instance) throws BaseException {
        getPersistenceManager().delete(instance);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> find() {

        return getPersistenceManager().find(getEntityClass());
    }

    @Transactional(readOnly = true)
    @Override
    public T find(Serializable key) {

        return getPersistenceManager().find(getEntityClass(), key);
    }

    @Transactional(readOnly = true)
    @Override
    public T findOne(Criterion criterion) throws BaseException {

        return getPersistenceManager().executeUniqueResult(getEntityClass(), criterion);
    }

    @Transactional(readOnly = true)
    @Override
    public T findOne(DetachedCriteria detachedCriteria) throws BaseException {

        return getPersistenceManager().executeUniqueResult(detachedCriteria);
    }


    @Transactional(readOnly = true)
    @Override
    public T load(Serializable key) {
        return getPersistenceManager().load(getEntityClass(), key);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> find(Criterion criterion) {

        return getPersistenceManager().find(getEntityClass(), criterion);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> find(DetachedCriteria detachedCriteria) {

        return getPersistenceManager().find(detachedCriteria);
    }

    @Transactional(readOnly = true)
    @Override
    public PagedResult findPagedList(Criterion criterion, Integer pageSize, Integer pageIndex) throws BaseException {

        return findPagedList(DetachedCriteria.forClass(getEntityClass()).add(criterion),
                pageSize, pageIndex);
    }

    @Transactional(readOnly = true)
    @Override
    public PagedResult findPagedList(DetachedCriteria detachedCriteria, Integer pageSize, Integer pageIndex) throws BaseException {

        return getPersistenceManager().findPagedList(detachedCriteria, pageSize, pageIndex);
    }

    @Transactional(readOnly = true)
    @Override
    public Long count(Criterion criterion) {

        return getPersistenceManager().count(getEntityClass(), criterion);
    }
}
