package com.captain.template.segment;

import com.captain.template.bean.Field;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
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

    /**
     * return key:tableName
     */
    public Map<String,List<Field>> getField() throws SQLException {
        List<String> queryList = Lists.newArrayList();
        for (String tableName : tableList) {
            String querySql = "select * from "+tableName;
            queryList.add(querySql);
        }
        try(
            SqlSession sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
        ){
            for (String querySql : queryList) {
                ResultSetMetaData metaData = connection.prepareStatement(querySql).getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.println(metaData.getColumnClassName(i));
                    System.out.println(metaData.getColumnName(i));
                }
                System.out.println();
            }
        }

        return null;
    }

    public void getFieldSql(){

    }
}
