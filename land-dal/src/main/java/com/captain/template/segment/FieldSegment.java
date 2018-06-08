package com.captain.template.segment;

import com.captain.template.bean.Field;
import com.captain.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by wurz on 2018/6/7.
 */
@Slf4j
@Data
@Component
public class FieldSegment {

//    @Resource
//    private SqlSessionTemplate sqlSessionTemplate;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 1、使用@Value后，该类必须被IOC托管
     * 2、取数组的时候，配置定义为例如event,event_type
     *    然后接收用String数组，而不能是List集合
     * 3、如果复杂结构，建议直接json或者数据库
     */
    @Value("${generate.tableList}")
    private String [] tableList;

    private static final Map<String,List<Field>> tableFieldsMap = Maps.newConcurrentMap();

    /**
     * return key:tableName
     */
    public Map<String,List<Field>> getField() throws SQLException {
        //未被连接的表
        List<String> unlinkTableList = Lists.newArrayList();
        for (String tableName : tableList) {
            if (tableFieldsMap.get(tableName) == null){
                unlinkTableList.add(tableName);
            }
        }
        //key:tableName value:querySql
        Map<String,String> sqlTableMap = Maps.newHashMap();
        for (String tableName : unlinkTableList) {
            String querySql = "select * from "+tableName;
            sqlTableMap.put(tableName,querySql);
        }
        try(
            SqlSession sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
        ){
            for (Map.Entry<String,String> entry : sqlTableMap.entrySet()) {
                ResultSetMetaData metaData = connection.prepareStatement(entry.getValue()).getMetaData();
                int columnCount = metaData.getColumnCount();
                List<Field> fieldList = Lists.newArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    Field field = new Field();
                    field.setFieldSourceName(metaData.getColumnName(i));
                    field.setFieldDescType(metaData.getColumnClassName(i));
                    field.setFieldCamelName(StringUtils.underline2Camel(field.getFieldSourceName(),true));
                    fieldList.add(field);
                }
                tableFieldsMap.put(entry.getKey(),fieldList);
            }
        }
        return tableFieldsMap;
    }

    public void getFieldSql(){

    }

}
