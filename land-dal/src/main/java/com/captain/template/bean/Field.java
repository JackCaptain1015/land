package com.captain.template.bean;

import lombok.Data;

/**
 * Created by wurz on 2018/6/7.
 */
@Data
public class Field {
    //数据库字段名
    private String fieldSourceName;
    //驼峰式字段名
    private String fieldCamelName;
    //java中字段类型
    private String fieldDescType;

}
