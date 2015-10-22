package com.dubby.base.model;

import com.dubby.base.enumeration.StatusType;
import com.dubby.base.exception.BaseException;
import com.dubby.base.model.entity.HasCreateEntity;
import com.dubby.base.model.entity.HasRecordStatus;
import com.dubby.base.model.entity.HasSerializableEntity;
import com.dubby.base.model.entity.HasUpdateEntity;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class PersistenceManagerImpl implements PersistenceManager {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Dictionary baseDictionary;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Dictionary getDictionary() {
        return baseDictionary;
    }

    @Override
    public <T extends HasSerializableEntity> void create(T instance) throws BaseException {
        create(instance, null);
    }

    @Override
    public <T extends HasSerializableEntity> void create(T instance, String userName) throws BaseException {

        Session session = getSessionFactory().getCurrentSession();

        if (instance instanceof HasCreateEntity) {

            if (NullEmptyChecker.isNullOrEmpty(userName)) {
                throw new BaseException(
                        getDictionary().constructString("base.error.user.name.required.for.create.audit"));
            }

            ((HasCreateEntity) instance).setCreateBy(userName);
            ((HasCreateEntity) instance).setCreateDate(new Date());
        }

        if (instance instanceof HasUpdateEntity) {

            if (NullEmptyChecker.isNullOrEmpty(userName)) {
                throw new BaseException(
                        getDictionary().constructString("base.error.user.name.required.for.update.audit"));
            }

            ((HasUpdateEntity) instance).setLastUpdateBy(userName);
            ((HasUpdateEntity) instance).setLastUpdateDate(new Date());
        }

        if (instance instanceof HasRecordStatus) {
            if (NullEmptyChecker.isNullOrEmpty(((HasRecordStatus) instance).getStatus())) {
                ((HasRecordStatus) instance).setStatus(StatusType.Active.getVal());
            }
        }

        session.save(instance);

        session.flush();

    }

    @Override
    public <T extends HasSerializableEntity> void update(T instance) throws BaseException {
        update(instance, null);
    }

    @Override
    public <T extends HasSerializableEntity> void update(T instance, String userName) throws BaseException {

        Session session = getSessionFactory().getCurrentSession();

        if (instance instanceof HasUpdateEntity) {

            if (NullEmptyChecker.isNullOrEmpty(userName)) {
                throw new BaseException(
                        getDictionary().constructString("base.error.user.name.required.for.update.audit"));
            }

            ((HasUpdateEntity) instance).setLastUpdateBy(userName);
            ((HasUpdateEntity) instance).setLastUpdateDate(new Date());
        }


        session.update(instance);

        session.flush();

    }

    @Override
    public <T extends HasSerializableEntity> T merge(T instance) throws BaseException {
        return merge(instance, null);
    }

    @Override
    public <T extends HasSerializableEntity> T merge(T instance, String userName)
            throws BaseException {

        Session session = getSessionFactory().getCurrentSession();

        if (instance instanceof HasUpdateEntity) {

            if (NullEmptyChecker.isNullOrEmpty(userName)) {
                throw new BaseException(
                        getDictionary().constructString("base.error.user.name.required.for.update.audit"));
            }

            ((HasUpdateEntity) instance).setLastUpdateBy(userName);
            ((HasUpdateEntity) instance).setLastUpdateDate(new Date());
        }

        instance = (T) session.merge(instance);

        session.flush();
        return instance;
    }

    public <T extends HasSerializableEntity> void delete(T instance) {
        getSessionFactory().getCurrentSession().delete(instance);
    }

    public <T extends HasSerializableEntity> T refreshEntity(T instance) {
        getSessionFactory().getCurrentSession().refresh(instance);

        return instance;
    }

    @Override
    public <T extends HasSerializableEntity> List<T> find(Class<T> entityClass) {

        Session session = getSessionFactory().getCurrentSession();

        Criteria criteria = session.createCriteria(entityClass);

        List<T> result = criteria.list();

        return result;
    }

    @Override
    public <T extends HasSerializableEntity> List<T> find(Class<T> entityClass, Criterion criterion) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass).add(criterion);

        return find(detachedCriteria);
    }

    @Override
    public <T extends HasSerializableEntity> List<T> find(DetachedCriteria detachedCriteria) {

        Session session = getSessionFactory().getCurrentSession();

        Criteria criteria = detachedCriteria.getExecutableCriteria(session);

        List<T> result = criteria.list();

        return result;
    }

    @Override
    public <T extends HasSerializableEntity> T find(Class<T> entityClass, Serializable key) {
        Session session = getSessionFactory().getCurrentSession();

        return (T) session.get(entityClass, key);
    }

    public <T extends HasSerializableEntity> T findWithLock(Class<T> entityClass, Serializable key) {

        Session session = getSessionFactory().getCurrentSession();

        return (T) session.get(entityClass, key, LockOptions.UPGRADE);
    }

    @Override
    public PagedResult findPagedList(DetachedCriteria detachedCriteria, Integer pageSize, Integer pageIndex) throws BaseException {
        PagedResult pagedResult = new PagedResultImpl();
        pagedResult.setPageSize(pageSize);

        Session session = getSessionFactory().getCurrentSession();

        Criteria criteria = detachedCriteria.getExecutableCriteria(session).setProjection(Projections.rowCount());

        Object object = criteria.uniqueResult();

        if (object != null) {

            pagedResult.setActualRowCount(((Long) object).intValue());

            if (pagedResult.getActualRowCount() > 0) {
                criteria.setProjection(null);
                criteria.setResultTransformer(Criteria.ROOT_ENTITY);
                criteria.setMaxResults(pageSize);

                while (pagedResult.getResult().isEmpty()) {
                    int start = pageSize * (pageIndex - 1);

                    criteria.setFirstResult(start);

                    List result = criteria.list();

                    if (result.isEmpty()) {
                        pageIndex--;
                    } else {
                        pagedResult.setResult(result);
                    }

                    if (pageIndex < 0) {
                        throw new BaseException(getDictionary().constructString("base.error.list.invalid.row.size"));
                    }
                }

                pagedResult.setCurrentPageIndex(pageIndex);
            }
        }

        return pagedResult;

    }

    @Override
    public <T extends HasSerializableEntity> T load(Class<T> entityClass, Serializable key) {
        Session session = getSessionFactory().getCurrentSession();

        return (T) session.load(entityClass, key);
    }

    @Override
    public void executeQuery(String query) {

        executeQuery(query, null);
    }

    @Override
    public void executeQuery(String query, Map<String, Object> args) {

        Session session = getSessionFactory().getCurrentSession();

        Query executeQuery = session.createQuery(query);

        if (NullEmptyChecker.isNotNullOrEmpty(args)) {
            for (String key : args.keySet()) {
                executeQuery.setParameter(key, args.get(key));
            }
        }

        executeQuery.executeUpdate();

        session.flush();
    }

    @Override
    public Object executeUniqueCriteria(DetachedCriteria detachedCriteria) {

        Session session = getSessionFactory().getCurrentSession();

        return detachedCriteria.getExecutableCriteria(session)
                .uniqueResult();
    }

    @Override
    public <T extends HasSerializableEntity> T executeUniqueResult(Class<T> entityClass, String query) {

        Session session = getSessionFactory().getCurrentSession();

        Query executeQuery = session.createQuery(query);

        return (T) executeQuery.uniqueResult();
    }

    @Override
    public <T extends HasSerializableEntity> T executeUniqueResult(Class<T> entityClass, Criterion criterion) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass).add(criterion);

        return (T) executeUniqueResult(detachedCriteria);
    }

    @Override
    public <T extends HasSerializableEntity> T executeUniqueResult(DetachedCriteria detachedCriteria) {
        Session session = getSessionFactory().getCurrentSession();

        return (T) detachedCriteria.getExecutableCriteria(session)
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public <T extends HasSerializableEntity> Long count(Class<T> type, Criterion criterion) {

        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(type);

        if(NullEmptyChecker.isNotNullOrEmpty(criterion)){
            criteria.add(criterion);
        }

        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

}
