package com.wk.android.eventtype.clickevent;

import com.wk.android.BaseEventBean;
import com.wk.android.EventType;

/**
 * Created by bshn on 2017/9/1.
 */

public class ClickEventBean extends BaseEventBean {
    /**
     * 设备信息
     */
    public String deviceInfo;
    /**
     * 应用版本
     */
    public String appVersion;
    /**
     * 国家
     */
    public String countryCode;


    public ClickEventBean(ClickProvider clickProvider, String eventName, String eventValue, EventType eventType, String eventTime) {
        super(eventName, eventValue, eventType.name(), eventTime);
        this.deviceInfo = clickProvider.getDeviceInfo();
        this.appVersion = clickProvider.getAppVersion();
        this.countryCode = clickProvider.getCountryCode();
    }

}
