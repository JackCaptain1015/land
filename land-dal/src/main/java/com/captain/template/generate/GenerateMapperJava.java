package com.captain.template.generate;

import com.captain.template.bean.Field;
import com.captain.template.segment.FieldSegment;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wurz on 2018/6/15.
 */
@Component
public class GenerateMapperJava {

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

    public void generateMapperJava(){

    }

    private Map<String,String> generatePackage(){
        Map<String,String> tablePackageMap = Maps.newHashMap();

        Map<String, List<Field>> tableFieldsMap = fieldSegment.getTableFieldsMap();

        return null;
    }

}
