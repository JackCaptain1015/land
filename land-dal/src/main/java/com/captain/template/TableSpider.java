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
    @Value("${generate.createTimeFieldName}")
    private String createTimeFieldName;
    @Value("${generate.modifyTimeFieldName}")
    private String modifyTimeFieldName;


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
                String resultSegEnumValue;
                if ("id".equals(field.getFieldSourceName())){
                    resultSegEnumValue = SqlSegmentEnums.RESULTMAP_RESULT_ID_SEG.getValue();
                }else{
                    resultSegEnumValue = SqlSegmentEnums.RESULTMAP_RESULT_SEG.getValue();
                }
                String resultSegStr = StringUtils.replaceSequenced(resultSegEnumValue, field.getFieldSourceName(),field.getFieldCamelName(),
                        JAVA_TYPE_MAP.get(field.getFieldDescType().toLowerCase()));
                resultMapSegSb.append(resultSegStr);

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
            String allFieldNamStr = this.getColumnSql(fieldList);
            String columnSql = StringUtils.replaceSequenced(SqlSegmentEnums.SQL_TAG_SEG.getValue(), allFieldNamStr);
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
                        .append(field.getFieldSourceName()).append(" = #{").append(field.getFieldCamelName()).append("}").append(",\n</if>");
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

    /**
     * 生成insertSql
     * @return
     */
    private Map<String,String> generateInsertSql(){
        Map<String,String> tableResultMap = Maps.newHashMap();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) -> {

            StringBuffer insertColumnSqlBuffer = new StringBuffer();
            StringBuffer insertValueSqlBuffer = new StringBuffer();
            fieldList.forEach(field -> {
                boolean isCreateTimeExist = createTimeFieldName != null && createTimeFieldName.length() != 0 && createTimeFieldName.equals(field.getFieldSourceName());
                boolean isModifyTimeExist = modifyTimeFieldName != null && modifyTimeFieldName.length() != 0 && modifyTimeFieldName.equals(field.getFieldSourceName());
                if (isCreateTimeExist || isModifyTimeExist){
                    insertColumnSqlBuffer.append(field.getFieldSourceName()).append(",");
                    insertValueSqlBuffer.append("now(),");
                }else {
                    insertColumnSqlBuffer.append(this.getIfTagSql(field.getFieldSourceName(),SqlSegmentEnums.INSERT_COLUMN_IF_TAG_SEG.getKey()));
                    insertValueSqlBuffer.append(this.getIfTagSql(field.getFieldSourceName(),SqlSegmentEnums.INSERT_IF_TAG_SEG.getKey()));
                }
            });
            String insertSql = StringUtils.replaceSequenced(SqlSegmentEnums.INSERT_TAG_SEG.getValue(), entityLocation, tableName, insertColumnSqlBuffer.toString(), insertValueSqlBuffer.toString());

            tableResultMap.put(tableName,insertSql);
        });
        return tableResultMap;
    }

    private Map<String,String> generateUpdateSql(){
        Map<String,String> tableResultMap = Maps.newHashMap();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();


        tableFieldsMap.forEach((tableName,fieldList) -> {

            StringBuffer updateSqlBuffer = new StringBuffer();
            fieldList.forEach(field -> {
                boolean isCreateTimeExist = createTimeFieldName != null && createTimeFieldName.length() != 0 && createTimeFieldName.equals(field.getFieldSourceName());
                boolean isModifyTimeExist = modifyTimeFieldName != null && modifyTimeFieldName.length() != 0 && modifyTimeFieldName.equals(field.getFieldSourceName());
                if (isCreateTimeExist){
                    updateSqlBuffer.append("");
                }else if(isModifyTimeExist){
                    updateSqlBuffer.append(field.getFieldSourceName()).append(" = now(),\n");
                }else{
                    updateSqlBuffer.append(this.getIfTagSql(field.getFieldSourceName(),SqlSegmentEnums.UPDATE_IF_TAG_SEG.getKey()));
                }
            });
            String updateSql = StringUtils.replaceSequenced(SqlSegmentEnums.UPDATE_TAG_SEG.getValue(), entityLocation, tableName, updateSqlBuffer.toString());

            tableResultMap.put(tableName,updateSql);
        });
        return tableResultMap;
    }

    /**
     * 比如id,name
     * @return
     */
    private String getColumnSql(List<Field> fieldList){
        StringBuffer allFieldNameBuffer = new StringBuffer();
        fieldList.forEach(field -> {
            allFieldNameBuffer.append(field.getFieldSourceName()).append(",");
        });
        allFieldNameBuffer.deleteCharAt(allFieldNameBuffer.length()-1);
        return allFieldNameBuffer.toString();
    }

    /**
     * <if>标签sql
     * @return
     */
    private String getIfTagSql(String sourceColumnName,String ifTagType){
        if (SqlSegmentEnums.INSERT_COLUMN_IF_TAG_SEG.getKey().equals(ifTagType)){
            return StringUtils.replaceSequenced(SqlSegmentEnums.INSERT_COLUMN_IF_TAG_SEG.getValue(),StringUtils.underline2Camel(sourceColumnName,true),sourceColumnName);
        }
        if (SqlSegmentEnums.INSERT_IF_TAG_SEG.getKey().equals(ifTagType)){
            return StringUtils.replaceSequenced(SqlSegmentEnums.INSERT_IF_TAG_SEG.getValue(),StringUtils.underline2Camel(sourceColumnName,true));
        }
        if (SqlSegmentEnums.UPDATE_IF_TAG_SEG.getKey().equals(ifTagType)){
            return StringUtils.replaceSequenced(SqlSegmentEnums.UPDATE_IF_TAG_SEG.getValue(),StringUtils.underline2Camel(sourceColumnName,true),sourceColumnName);
        }
        return "";
    }

    private String getMapperLocation(String tableName){
        return mapperLocation+"."+StringUtils.underline2Camel(tableName,false)+"Mapper";
    }

    private String getEntityLocation(String tableName){
        return entityLocation+"."+StringUtils.underline2Camel(tableName,false);
    }

}
