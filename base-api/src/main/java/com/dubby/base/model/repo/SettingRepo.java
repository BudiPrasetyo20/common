package com.dubby.base.model.repo;


import com.dubby.base.exception.BaseException;
import com.dubby.base.model.entity.ModuleSetting;

public interface SettingRepo extends Repo<ModuleSetting> {
    public <T> T getSetting(Class<T> clazz) throws Exception;
    public <T> void saveOrUpdate(Class<T> clazz, T entity, String userName) throws Exception;
}
