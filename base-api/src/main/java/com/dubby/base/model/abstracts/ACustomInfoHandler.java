package com.dubby.base.model.abstracts;


import com.dubby.base.model.ObjectStringConverter;
import com.dubby.base.model.entity.CustomInfoHandler;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ACustomInfoHandler implements CustomInfoHandler {

    @Autowired
    protected ObjectStringConverter objectStringConverterImpl;

    protected abstract Class getCustomInfoClass();

    protected ObjectStringConverter getObjectStringConverter() {

        return objectStringConverterImpl;
    }

    public Object getCustomInfo(String rawCustomInfo) throws Exception {

        return getObjectStringConverter().convertToObject(getCustomInfoClass(), rawCustomInfo);

    }

    public String getRawCustomInfo(Object customInfo) throws Exception {

        return getObjectStringConverter().convertToString(getCustomInfoClass(), customInfo);

    }
}
