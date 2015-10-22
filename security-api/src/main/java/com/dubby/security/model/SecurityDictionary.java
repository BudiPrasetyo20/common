package com.dubby.security.model;

import com.dubby.base.model.Dictionary;
import com.dubby.base.model.abstracts.ADictionary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SecurityDictionary extends ADictionary implements Dictionary {

    protected String getBundlePath() {

        return "com.dubby.security.resource.dictionary";
    }

    @PostConstruct
    protected void init() {

        fillResourcesCache();
    }

}
