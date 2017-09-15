package com.wk.android;

/**
 * Created by bshn on 2017/9/1.
 */

public class BaseEventBean {

    /**
     * 统计事件名称
     */
    String eventName;

    /**
     * 统计事件参数
     */
    String eventValue;

    /**
     * 统计事件类型
     */
    String eventType;

    /**
     * 统计事件时间
     */
    String eventTime;

    public BaseEventBean(String eventName, String eventValue, String eventType, String eventTime) {
        this.eventName = eventName;
        this.eventValue = eventValue;
        this.eventType = eventType;
        this.eventTime = eventTime;
    }


}
