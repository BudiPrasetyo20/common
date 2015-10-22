/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubby.base.model.repo;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.PagedResult;
import com.dubby.base.model.entity.HasSerializableEntity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;


public interface Repo<T extends HasSerializableEntity> {

    public T saveOrUpdate(T instance, boolean isNew, String userName) throws BaseException;
    public T saveOrUpdate(T instance, String userName) throws BaseException;
    public void delete(T instance) throws BaseException;

    public List<T> find();
    public T find(Serializable key);
    public T findOne(Criterion criterion) throws BaseException;
    public T findOne(DetachedCriteria detachedCriteria) throws BaseException;
    public T load(Serializable key);
    public List<T> find(Criterion criterion);
    public List<T> find(DetachedCriteria detachedCriteria);

    public PagedResult findPagedList(Criterion criterion, Integer pageSize, Integer pageIndex) throws BaseException;
    public PagedResult findPagedList(DetachedCriteria detachedCriteria, Integer pageSize, Integer pageIndex) throws BaseException;

    public Long count(Criterion criterion);
}
