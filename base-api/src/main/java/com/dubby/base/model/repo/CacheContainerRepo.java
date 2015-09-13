package com.dubby.base.model.repo;

import org.hamcrest.Matcher;

import java.util.List;

public interface CacheContainerRepo<T> {

    public void fillCache();
    public List<T> search(String keyword);
    public List<T> search(Matcher constraint);
    public T findOne(Matcher constraint);
    public void reloadCache();
}
