package com.captain.template.segment;

import lombok.Data;

/**
 * Created by wurz on 2018/6/15.
 */
@Data
public class TableFileSegment {
    /**
     * mapper.xml文件代码
     */
    private String mapperSql;
    /**
     * mapper.java文件代码
     */
    private String mapperJava;
    /**
     * bean文件代码
     */
    private String beanJava;
    /**
     * Service接口代码
     */
    private String serviceJava;
    /**
     * ServiceImpl代码
     */
    private String serviceImplJava;


}
