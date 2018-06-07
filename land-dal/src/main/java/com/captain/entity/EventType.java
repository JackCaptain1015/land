package com.captain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class EventType {

    /**
     * ID
     */
    private Integer id;

    /**
     * 事件id
     */
    private Integer eventId;

    /**
     * 类型key
     */
    private String typeKey;

    /**
     * 类型 值
     */
    private String typeValue;

    /**
     * 描述
     */
    private String typeDesc;
    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 更新时间
     */
    private Date gmtModified;

}
