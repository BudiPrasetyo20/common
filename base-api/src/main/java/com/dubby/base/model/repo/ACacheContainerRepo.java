package com.dubby.base.model.repo;


import com.dubby.base.enumeration.StatusType;
import com.dubby.base.exception.BaseException;
import com.dubby.base.model.NullEmptyChecker;
import com.dubby.base.model.entity.HasCache;
import org.hamcrest.Matcher;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.select;
import static ch.lambdaj.Lambda.selectUnique;

public abstract class ACacheContainerRepo<T extends HasCache> extends ARepo<T> implements CacheContainerRepo<T> {

    List<T> cache = new ArrayList<T>();

    protected List<T> getCache() {

        if (NullEmptyChecker.isNullOrEmpty(cache)) {
            fillCache();
        }

        return cache;
    }

    protected void setCache(List<T> cache) {
        this.cache = cache;
    }

    @Override
    public void fillCache() {

        setCache(find(Restrictions.or(Restrictions.eq("status", StatusType.Active.getVal()),
                Restrictions.eq("status", StatusType.System.getVal()))));
    }

    @Override
    public T saveOrUpdate(T instance, boolean isNew, String userName) throws BaseException {
        instance = super.saveOrUpdate(instance, isNew, userName);
        reloadCache();
        return instance;
    }

    @Override
    public List<T> search(Matcher constraint) {

        return select(getCache(), constraint);
    }

    @Override
    public T findOne(Matcher constraint) {

        return (T) selectUnique(getCache(), constraint);
    }

    @Override
    public void reloadCache() {

        getCache().clear();
        fillCache();
    }

}
