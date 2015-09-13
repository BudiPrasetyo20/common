package com.dubby.base.model.repo;


import com.dubby.base.model.ObjectStringConverter;
import com.dubby.base.model.entity.ModuleSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SettingRepoImpl extends ARepo<ModuleSetting> implements SettingRepo {

    @Autowired
    private ObjectStringConverter objectStringConverterImpl;

    private Map<String, Object> cache = new HashMap<String, Object>();

    protected ObjectStringConverter getObjectStringConverter() {

        return objectStringConverterImpl;
    }

    protected Map<String, Object> getCache() {

        return cache;
    }

    @Override
    protected Class getEntityClass() {
        return ModuleSetting.class;
    }

    @Override
    @Transactional
    public <T> T getSetting(Class<T> clazz) throws Exception {

        if (getCache().containsKey(clazz.toString())) {
            return (T) getCache().get(clazz.toString());
        } else {

            ModuleSetting moduleSetting = find(clazz.toString());
            T instance;

            if (moduleSetting == null) {
                saveOrUpdate(clazz, clazz.newInstance(), "SYSTEM");

                instance = clazz.newInstance();
            } else {
                instance = getObjectStringConverter().convertToObject(clazz, moduleSetting.getSetting());
            }

            getCache().put(clazz.toString(), instance);

            return instance;
        }
    }

    @Override
    @Transactional
    public <T> void saveOrUpdate(Class<T> clazz, T entity, String userName) throws Exception {

        ModuleSetting moduleSetting = find(clazz.toString());

        if (moduleSetting == null) {

            moduleSetting = new ModuleSetting();
            moduleSetting.setId(clazz.toString());
            moduleSetting.setSetting(getObjectStringConverter().convertToString(clazz, entity));

            saveOrUpdate(moduleSetting, true, userName);
        } else {

            moduleSetting.setSetting(getObjectStringConverter().convertToString(clazz, entity));

            saveOrUpdate(moduleSetting, false, userName);
        }

        getCache().put(clazz.toString(), entity);
    }

}
