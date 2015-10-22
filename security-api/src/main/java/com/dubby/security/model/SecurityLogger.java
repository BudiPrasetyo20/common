package com.dubby.security.model;

import com.dubby.base.model.Dictionary;
import com.dubby.base.model.LoggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityLogger extends LoggerImpl {

    @Autowired
    Dictionary securityDictionary;

    protected Dictionary getDictionary() {
        return securityDictionary;
    }
}
