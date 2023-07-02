package com.nhom7.foodg.models.entities;

public class MyObject{
    public String key;
    public Integer val;
    public MyObject(String key, Integer val) {
        this.key = key;
        this.val = val;
    }
    public MyObject() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}
