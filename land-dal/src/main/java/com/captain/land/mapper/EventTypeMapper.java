package com.captain.land.mapper;


import com.captain.entity.EventType;

public interface EventTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EventType record);

    int insertSelective(EventType record);

    EventType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EventType record);

    int updateByPrimaryKey(EventType record);
}