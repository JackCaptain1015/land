package com.captain.template.enums;

/**
 * Created by wurz on 2018/6/11.
 */
public enum JavaTypeMap {

    /**
     * 字符串
     */
    STRING("String","VARCHAR"),

    /**
     * 浮点数
     */
    BIGDECIMAL("BigDecimal","DECIMAL"),
    FLOAT("float","DECIMAL"),

    /**
     * 数字
     */
    BOOLEAN("boolean","BIT"),
    BYTE("byte","TINYINT"),
    SHORT("short","SMALLINT"),
    INT("int","INTEGER"),
    INTEGER("Integer","INTEGER"),
    LONG("long","BIGINT"),
    /**
     * 日期
     */
    DATE("Date","TIMESTAMP");

    JavaTypeMap(String javaType, String jdbcType) {
        this.javaType = javaType;
        this.jdbcType = jdbcType;
    }

    private String javaType;
    private String jdbcType;

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
}
