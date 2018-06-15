package com.captain.template.generate;

import com.captain.template.bean.Field;
import com.captain.template.enums.MapperXmlSqlEnums;
import com.captain.template.segment.FieldSegment;
import com.captain.template.utils.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by wurz on 2018/6/15.
 */
@Component
public class GenerateMapperXml {
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

    @PostConstruct
    public void init() throws SQLException {
        fieldSegment.init();
    }

    public Map<String,String> generateMapper(){
        Map<String,String> tableMapperMap = Maps.newHashMap();
        //生成代码片段
        Map<String, String> resultMapSqlMap = this.generateResultMapSql();
        Map<String, String> columnSqlMap = this.generateColumnSql();
        Map<String, String> selectConditionSqlMap = this.generateSelectConditionSql();
        Map<String, String> deleteIdSqlMap = this.generateDeleteIdSql();
        Map<String, String> insertSqlMap = this.generateInsertSql();
        Map<String, String> updateSqlMap = this.generateUpdateSql();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) -> {
            //拼接代码片段
            StringBuffer jointSqlSegmentBuffer = new StringBuffer();
            jointSqlSegmentBuffer.append(resultMapSqlMap.get(tableName)).append(columnSqlMap.get(tableName))
                    .append(selectConditionSqlMap.get(tableName)).append(deleteIdSqlMap.get(tableName))
                    .append(insertSqlMap.get(tableName)).append(updateSqlMap.get(tableName));

            String mapperSql = StringUtils.replaceSequenced(MapperXmlSqlEnums.MAPPER_SEG.getValue(),
                    this.getMapperLocation(tableName),jointSqlSegmentBuffer.toString());
            tableMapperMap.put(tableName, MapperXmlSqlEnums.HEAD_SEG.getValue()+mapperSql);
        });

        return tableMapperMap;
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
                    resultSegEnumValue = MapperXmlSqlEnums.RESULTMAP_RESULT_ID_SEG.getValue();
                }else{
                    resultSegEnumValue = MapperXmlSqlEnums.RESULTMAP_RESULT_SEG.getValue();
                }
                String resultSegStr = StringUtils.replaceSequenced(resultSegEnumValue, field.getFieldSourceName(),field.getFieldCamelName(),
                        JAVA_TYPE_MAP.get(field.getFieldDescType().toLowerCase()));
                resultMapSegSb.append(resultSegStr);

            });

            String resultMap = StringUtils.replaceSequenced(MapperXmlSqlEnums.RESULTMAP_SEG.getValue(),
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
            String columnSql = StringUtils.replaceSequenced(MapperXmlSqlEnums.SQL_TAG_SEG.getValue(), allFieldNamStr);
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
            String selectConditionSql = StringUtils.replaceSequenced(MapperXmlSqlEnums.SELECT_CONDITION_TAG_SEG.getValue(),
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
                    String deleteIdSql = StringUtils.replaceSequenced(MapperXmlSqlEnums.DELETE_ID_TAG_SEG.getValue(),field.getFieldDescType(), tableName);
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
                    insertColumnSqlBuffer.append(this.getIfTagSql(field.getFieldSourceName(), MapperXmlSqlEnums.INSERT_COLUMN_IF_TAG_SEG.getKey()));
                    insertValueSqlBuffer.append(this.getIfTagSql(field.getFieldSourceName(), MapperXmlSqlEnums.INSERT_IF_TAG_SEG.getKey()));
                }
            });
            String insertSql = StringUtils.replaceSequenced(MapperXmlSqlEnums.INSERT_TAG_SEG.getValue(), entityLocation, tableName, insertColumnSqlBuffer.toString(), insertValueSqlBuffer.toString());

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
                    updateSqlBuffer.append(this.getIfTagSql(field.getFieldSourceName(), MapperXmlSqlEnums.UPDATE_IF_TAG_SEG.getKey()));
                }
            });
            String updateSql = StringUtils.replaceSequenced(MapperXmlSqlEnums.UPDATE_TAG_SEG.getValue(), entityLocation, tableName, updateSqlBuffer.toString());

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
        if (MapperXmlSqlEnums.INSERT_COLUMN_IF_TAG_SEG.getKey().equals(ifTagType)){
            return StringUtils.replaceSequenced(MapperXmlSqlEnums.INSERT_COLUMN_IF_TAG_SEG.getValue(),StringUtils.underline2Camel(sourceColumnName,true),sourceColumnName);
        }
        if (MapperXmlSqlEnums.INSERT_IF_TAG_SEG.getKey().equals(ifTagType)){
            return StringUtils.replaceSequenced(MapperXmlSqlEnums.INSERT_IF_TAG_SEG.getValue(),StringUtils.underline2Camel(sourceColumnName,true));
        }
        if (MapperXmlSqlEnums.UPDATE_IF_TAG_SEG.getKey().equals(ifTagType)){
            return StringUtils.replaceSequenced(MapperXmlSqlEnums.UPDATE_IF_TAG_SEG.getValue(),StringUtils.underline2Camel(sourceColumnName,true),sourceColumnName);
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
