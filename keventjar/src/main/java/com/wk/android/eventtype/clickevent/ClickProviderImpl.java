package com.wk.android.eventtype.clickevent;

import com.wk.android.BaseEventBean;
import com.wk.android.eventtype.EventTypeEnum;

/**
 * Created by bshn on 2017/9/1.
 */

public class ClickProviderImpl implements ClickProvider {
    private static ClickProviderImpl instance;

    public synchronized static ClickProviderImpl getInstance() {
        if (instance == null) {
            instance = new ClickProviderImpl();
        }
        return instance;
    }

    private ClickProviderImpl() {
    }


    @Override
    public BaseEventBean buildEventBean(String eventName, String eventParams) {
        ClickEventBean bean = new ClickEventBean(this, eventName, eventParams, EventTypeEnum.CLICK, System.currentTimeMillis() + "");

        return bean;
    }

    @Override
    public String getDeviceInfo() {

        return "设备信息";
    }

    @Override
    public String getAppVersion() {

        return "123";
    }

    @Override
    public String getCountryCode() {

        return "CN";
    }
}
