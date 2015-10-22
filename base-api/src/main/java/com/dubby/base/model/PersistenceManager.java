package com.dubby.base.model;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.entity.HasSerializableEntity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface PersistenceManager {

    public <T extends HasSerializableEntity> void create(T instance) throws BaseException;
    public <T extends HasSerializableEntity> void create(T instance, String userName) throws BaseException;

    public <T extends HasSerializableEntity> void update(T instance) throws BaseException;
    public <T extends HasSerializableEntity> void update(T instance, String userName) throws BaseException;

    public <T extends HasSerializableEntity> T merge(T instance)throws BaseException;
    public <T extends HasSerializableEntity> T merge(T instance, String userName)throws BaseException;

    public <T extends HasSerializableEntity> void delete(T instance) throws BaseException;

    public <T extends HasSerializableEntity> T refreshEntity(T instance) throws BaseException;

    public <T extends HasSerializableEntity> T find(Class<T> entityClass, Serializable key);
    public <T extends HasSerializableEntity> List<T> find(Class<T> entityClass);
    public <T extends HasSerializableEntity> List<T> find(Class<T> entityClass, Criterion criterion);
    public <T extends HasSerializableEntity> List<T> find(DetachedCriteria detachedCriteria);

    public <T extends HasSerializableEntity> T findWithLock(Class<T> entityClass, Serializable key);

    public PagedResult findPagedList(DetachedCriteria detachedCriteria, Integer pageSize, Integer pageIndex) throws BaseException;

    public <T extends HasSerializableEntity> T load(Class<T> entityClass, Serializable key);

    public void executeQuery(String query);
    public void executeQuery(String query, Map<String, Object> args);

    public Object executeUniqueCriteria(DetachedCriteria detachedCriteria);

    public <T extends HasSerializableEntity> T executeUniqueResult(Class<T> entityClass, String query);
    public <T extends HasSerializableEntity> T executeUniqueResult(Class<T> entityClass, Criterion criterion) throws BaseException;
    public <T extends HasSerializableEntity> T executeUniqueResult(DetachedCriteria detachedCriteria) throws BaseException;

    public <T extends HasSerializableEntity> Long count(Class<T> type, Criterion criterion);

}
