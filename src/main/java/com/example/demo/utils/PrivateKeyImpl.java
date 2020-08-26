package com.example.demo.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

public class PrivateKeyImpl implements PrivateKey {
    private byte[] key;
    public PrivateKeyImpl(byte[] key){
        this.key = key;
    }
    @Override
    public String getAlgorithm() {
        // TODO Auto-generated method stub
        return "RSA";
    }

    @Override
    public String getFormat() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getEncoded() {
        // TODO Auto-generated method stub
        return key;
    }
    
}