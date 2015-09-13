package com.dubby.base.model;

public interface IdGenerator {

    public Long getNextSequence(String sequenceKey) throws Exception;
    public String generateId(String[] prefixParameter, String[] suffixParameter) throws Exception;
}
