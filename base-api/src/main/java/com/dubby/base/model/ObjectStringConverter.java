package com.dubby.base.model;

import com.fasterxml.jackson.core.type.TypeReference;

public interface ObjectStringConverter {

    <T> T convertToObject(Class<T> objectClass, byte[] content) throws Exception;

    <T> T convertToObject(Class<T> objectClass, String content) throws Exception;

    <T> byte[] convertToDeflatedString(Class<T> objectClass, Object object) throws Exception;

    <T> String convertToString(Class<T> objectClass, Object object) throws Exception;

    <T> T convertToListOfObjects(TypeReference<T> type, String content) throws Exception;
}
