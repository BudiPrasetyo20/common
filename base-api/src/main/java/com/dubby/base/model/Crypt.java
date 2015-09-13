package com.dubby.base.model;

import java.security.NoSuchAlgorithmException;

public interface Crypt {

    public String hash(String word) throws NoSuchAlgorithmException;
    public String encrypt(String passphrase, String word) throws Exception;
    public String decrypt(String passphrase, String word) throws Exception;
}
