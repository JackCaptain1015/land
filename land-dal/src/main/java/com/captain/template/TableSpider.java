package com.captain.template;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by wurz on 2018/6/6.
 */
@Component
public class TableSpider {
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public void test() throws SQLException {
        ResultSetMetaData metaData = sqlSessionTemplate.getConnection().createStatement().executeQuery("select * from event").getMetaData();
        System.out.println(metaData);
    }

}
