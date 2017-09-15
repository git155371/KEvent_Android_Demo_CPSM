package com.wk.android;

/**
 * Created by bshn on 2017/9/5.
 */

class EventDBBean {
    int id;
    String eventValue;
    String eventName;
    String eventType;
    int failNum;//失败提交次数
    String eventTime;//储存时间

    EventDBBean(int id, String eventValue, String eventName, String eventType, int failNum, String eventTime) {
        this.id = id;
        this.eventValue = eventValue;
        this.eventName = eventName;
        this.eventType = eventType;
        this.failNum = failNum;
        this.eventTime = eventTime;
    }
}
