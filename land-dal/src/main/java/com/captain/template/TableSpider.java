package com.captain.template;

import com.captain.template.bean.Field;
import com.captain.template.enums.SqlSegmentEnums;
import com.captain.template.segment.FieldSegment;
import com.captain.template.utils.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by wurz on 2018/6/6.
 */
@Component
public class TableSpider {
    public static final Map<String,String> JAVA_TYPE_MAP = Maps.newHashMap();

    static{
        /**
         * 字符串
         */
        JAVA_TYPE_MAP.put("string","VARCHAR");
        /**
         * 浮点数
         */
        JAVA_TYPE_MAP.put("bigdecimal","DECIMAL");
        JAVA_TYPE_MAP.put("float","DECIMAL");
        /**
         * 数字
         */
        JAVA_TYPE_MAP.put("boolean","BIT");
        JAVA_TYPE_MAP.put("byte","TINYINT");
        JAVA_TYPE_MAP.put("short","SMALLINT");
        JAVA_TYPE_MAP.put("int","INTEGER");
        JAVA_TYPE_MAP.put("integer","INTEGER");
        JAVA_TYPE_MAP.put("long","BIGINT");
        /**
         * 日期
         */
        JAVA_TYPE_MAP.put("date","TIMESTAMP");

    }

    //key:tableName value.key:sql片段的名字 value.value:sql片段
    private Map<String,Map<String,String>> tableSqlSegment = Maps.newConcurrentMap();

    @Resource
    private FieldSegment fieldSegment;
    @Value("${generate.mapperLocation}")
    private String mapperLocation;
    @Value("${generate.entityLocation}")
    private String entityLocation;
    @Value("${generate.serviceLocation}")
    private String serviceLocation;

    public void generateSqlMapper() throws SQLException {
        fieldSegment.init();
        StringBuffer mapperSqlBuffer = new StringBuffer();
        mapperSqlBuffer.append(SqlSegmentEnums.HEAD_SEG.getValue());

    }



    private Map<String,String> generateMapper(){
        Map<String,String> tableMapperMap = Maps.newHashMap();


        return null;
    }

    /**
     *
     * @return key:tableName value:ResultMap
     */
    private Map<String,String> generateResultMapSql(){
        Map<String,String> tableResultMap = Maps.newHashMap();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) -> {
            StringBuffer resultMapSegSb = new StringBuffer();

            fieldList.forEach((field) -> {
                String resultIdStr = "";
                if ("id".equals(field.getFieldSourceName())){
                    resultIdStr = StringUtils.replaceSequenced(SqlSegmentEnums.RESULTMAP_RESULT_ID_SEG.getValue(),
                            field.getFieldSourceName(),field.getFieldCamelName(), JAVA_TYPE_MAP.get(field.getFieldDescType().toLowerCase()));
                }else{
                    resultIdStr = StringUtils.replaceSequenced(SqlSegmentEnums.RESULTMAP_RESULT_SEG.getValue(),
                            field.getFieldSourceName(),field.getFieldCamelName(), JAVA_TYPE_MAP.get(field.getFieldDescType().toLowerCase()));
                }
                resultMapSegSb.append(resultIdStr);

            });

            String resultMap = StringUtils.replaceSequenced(SqlSegmentEnums.RESULTMAP_SEG.getValue(),StringUtils.underline2Camel(tableName,false),resultMapSegSb.toString());
            tableResultMap.put(tableName,resultMap);
        });
        return tableResultMap;
    }
}
