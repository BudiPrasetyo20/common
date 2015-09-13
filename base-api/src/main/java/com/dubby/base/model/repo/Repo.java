/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubby.base.model.repo;

import com.dubby.base.exception.BaseException;
import com.dubby.base.model.PagedResult;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;


public interface Repo<T> {

    public void saveOrUpdate(T instance, boolean isNew, String userName) throws BaseException;
    public void delete(T instance) throws Exception;

    public List<T> find();
    public T find(Serializable key);
    public T findOne(Criterion criterion) throws Exception;
    public T findOne(DetachedCriteria detachedCriteria) throws Exception;
    public T load(Serializable key);
    public List<T> find(Criterion criterion);
    public List<T> find(DetachedCriteria detachedCriteria);

	public Long count(Criterion criterion);

    public PagedResult findPagedList(DetachedCriteria detachedCriteria, Integer pageSize, Integer pageIndex) throws BaseException;

}
