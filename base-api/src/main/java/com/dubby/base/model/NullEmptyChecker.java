package com.dubby.base.model;

import java.util.AbstractMap;
import java.util.Collection;

public class NullEmptyChecker {

    public static Boolean isNotNullOrEmpty(Object object) {

        return !isNullOrEmpty(object);
    }

    public static Boolean isNullOrEmpty(Object object) {

        if (object == null) {

            return true;
        } else {

            if (object instanceof Collection) {

                if (((Collection) object).isEmpty()) {

                    return true;
                }
            } else if (object instanceof AbstractMap) {

                if (((AbstractMap) object).isEmpty()) {

                    return true;
                }
            } else if (object.toString().trim().equals("")) {

                return true;
            }
        }

        return false;
    }
}
