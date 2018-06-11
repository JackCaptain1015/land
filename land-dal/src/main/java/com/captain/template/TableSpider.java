package com.captain.template;

import com.captain.template.bean.Field;
import com.captain.template.segment.FieldSegment;
import com.google.common.collect.Maps;
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
    //key:tableName value.key:sql片段的名字 value.value:sql片段
    private Map<String,Map<String,String>> tableSqlSegment = Maps.newConcurrentMap();

    @Resource
    private FieldSegment fieldSegment;

    public void generateSqlMapper() throws SQLException {
        fieldSegment.init();

    }

    private String generateResultMapSql(){
        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();
        tableFieldsMap.forEach((tableName,fieldList) -> {

        });
        return null;
    }
}
