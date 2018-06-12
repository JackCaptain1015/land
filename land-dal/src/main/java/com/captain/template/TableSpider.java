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

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) -> {
            // TODO: 2018/6/12 往mapper里填充数据
            String mapperSql = StringUtils.replaceSequenced(SqlSegmentEnums.MAPPER_SEG.getValue(),
                    this.getMapperLocation(tableName));

        });

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
                            field.getFieldSourceName(),field.getFieldCamelName(),
                            JAVA_TYPE_MAP.get(field.getFieldDescType().toLowerCase()));
                }else{
                    resultIdStr = StringUtils.replaceSequenced(SqlSegmentEnums.RESULTMAP_RESULT_SEG.getValue(),
                            field.getFieldSourceName(),field.getFieldCamelName(),
                            JAVA_TYPE_MAP.get(field.getFieldDescType().toLowerCase()));
                }
                resultMapSegSb.append(resultIdStr);

            });

            String resultMap = StringUtils.replaceSequenced(SqlSegmentEnums.RESULTMAP_SEG.getValue(),
                    this.getEntityLocation(tableName), resultMapSegSb.toString());
            tableResultMap.put(tableName,resultMap);
        });
        return tableResultMap;
    }

    /**
     *
     * @return key:tableName value:columnSql
     */
    private Map<String,String> generateColumnSql(){
        Map<String,String> tableResultMap = Maps.newHashMap();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) ->{
            StringBuffer allFieldNameBuffer = new StringBuffer();
            fieldList.forEach(field -> {
                allFieldNameBuffer.append(field.getFieldSourceName()).append(",");
            });
            allFieldNameBuffer.deleteCharAt(allFieldNameBuffer.length()-1);
            String columnSql = StringUtils.replaceSequenced(SqlSegmentEnums.SQL_TAG_SEG.getValue(), allFieldNameBuffer.toString());
            tableResultMap.put(tableName,columnSql);
        });
        return tableResultMap;
    }

    /**
     *
     * @return key:tableName value:selectConditionSql
     */
    private Map<String,String> generateSelectConditionSql(){
        Map<String,String> tableResultMap = Maps.newHashMap();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) ->{

            StringBuffer fieldConditionSql = new StringBuffer();
            fieldList.forEach(field -> {
                fieldConditionSql.append("<if test=\"").append(field.getFieldCamelName()).append(" != null\" >\n and")
                        .append(field.getFieldSourceName()).append(",\n</if>");
            });
            String selectConditionSql = StringUtils.replaceSequenced(SqlSegmentEnums.SELECT_CONDITION_TAG_SEG.getValue(),
                    this.getEntityLocation(tableName), tableName, fieldConditionSql.toString());
            tableResultMap.put(tableName,selectConditionSql);
        });
        return tableResultMap;
    }

    private Map<String,String> generateDeleteIdSql(){
        Map<String,String> tableResultMap = Maps.newHashMap();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) -> {
            for (Field field : fieldList) {
                if ("id".equals(field.getFieldSourceName())){
                    String deleteIdSql = StringUtils.replaceSequenced(SqlSegmentEnums.DELETE_ID_TAG_SEG.getValue(),field.getFieldDescType(), tableName);
                    tableResultMap.put(tableName,deleteIdSql);
                    break;
                }
            }
        });
        return tableResultMap;
    }

    private Map<String,String> generateInsertSql(){
        Map<String,String> tableResultMap = Maps.newHashMap();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) -> {

        });
        return null;
    }

    private String getMapperLocation(String tableName){
        return mapperLocation+"."+StringUtils.underline2Camel(tableName,false)+"Mapper";
    }

    private String getEntityLocation(String tableName){
        return entityLocation+"."+StringUtils.underline2Camel(tableName,false);
    }

}
