package com.dubby.base.model;

import com.dubby.base.model.abstracts.ADictionary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BaseDictionary extends ADictionary implements Dictionary {

    protected String getBundlePath() {

        return "com.dubby.base.resource.dictionary";
    }

    @PostConstruct
    protected void init() {

        fillResourcesCache();
    }

}
