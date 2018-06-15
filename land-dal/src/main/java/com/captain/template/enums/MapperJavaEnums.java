package com.captain.template.enums;

/**
 * Created by wurz on 2018/6/15.
 */
public enum MapperJavaEnums {
    PACKAGE("package","package {0}"),
    IMPORT("import","import {0}"),
    INTERFACE("interface","public interface {0} {\n{1}\n}"),
    METHOD("method","{0} {1} ({3} {4});")
    ;
    private String key;
    private String value;

    MapperJavaEnums(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
