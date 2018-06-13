package com.captain.template.enums;

import lombok.Data;

/**
 * Created by wurz on 2018/6/8.
 */
public enum SqlSegmentEnums {
    HEAD_SEG("head","<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n"),
    MAPPER_SEG("mapper","<mapper namespace=\"{0}\" >\n {1} \n</mapper>\n"),
    RESULTMAP_SEG("resultMap","<resultMap id=\"BaseResultMap\" type=\"{0}\" >\n {1} \n</resultMap>\n"),
    RESULTMAP_RESULT_ID_SEG("id","<id column=\"{0}\" property=\"{1}\" jdbcType=\"{2}\" />\n"),
    RESULTMAP_RESULT_SEG("result","<result column=\"{0}\" property=\"{1}\" jdbcType=\"{2}\" /> \n"),
    SQL_TAG_SEG("sqlTag","<sql id=\"Base_Column_List\" >\n {0} \n</sql>\n"),
    SELECT_CONDITION_TAG_SEG("selectConditionTag","<select id=\"selectByCondition\" resultMap=\"BaseResultMap\" parameterType=\"{0}\" >\n" +
            "select \n" +
            "    <include refid=\"Base_Column_List\" />\n" +
            "    from {1}\n" +
            "    <where>\n {2}\n </where>\n </select>\n"),
    DELETE_ID_TAG_SEG("deleteIdTagSeg","<delete id=\"deleteByPrimaryKey\" parameterType=\"{0}\" >\n" +
            "    delete from {1}\n" +
            "    where id = #{id}\n" +
            "  </delete>"),
    INSERT_TAG_SEG("insertSelective","<insert id=\"insertSelective\" parameterType=\"{0}\" >\n" +
            "    insert into {1}\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      {2}\n"+
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      {3}\n"+
            "    </trim>\n" +
            "  </insert>"),
    UPDATE_TAG_SEG("updateByPrimaryKey","<update id=\"updateByPrimaryKey\" parameterType=\"{0}\" >\n" +
            "    update {1}\n" +
            "    <set >\n" +
            "      {2}\n"+
            "    </set>\n" +
            "    where id = #{id}\n" +
            "  </update>"),
    INSERT_COLUMN_IF_TAG_SEG("insertColumnIfTag","<if test=\"{0} != null\" >\n{1},\n</if>\n"),
    INSERT_IF_TAG_SEG("insertIfTag","<if test=\"{0} != null\" >\n#{{0}},\n</if>\n"),
    UPDATE_IF_TAG_SEG("updateIfTag","<if test=\"{0} != null\" >\n{1} = #{{0}},\n</if>\n");

    private String key;
    private String value;

    SqlSegmentEnums(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
