package com.captain.template;

import com.captain.template.bean.Field;
import com.captain.template.enums.SqlSegmentEnums;
import com.captain.template.generate.GenerateMapperXml;
import com.captain.template.segment.FieldSegment;
import com.captain.template.utils.FileUtils;
import com.captain.template.utils.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by wurz on 2018/6/6.
 */
@Component
public class TableSpider {

    @Resource
    private GenerateMapperXml generateMapper;


    public void generateSqlMapper() throws SQLException, IOException {

        Map<String, String> mapperMap = generateMapper.generateMapper();
        
        File mapperXml = FileUtils.createFile(FileUtils.class.getResource("").getPath());

    }





}
