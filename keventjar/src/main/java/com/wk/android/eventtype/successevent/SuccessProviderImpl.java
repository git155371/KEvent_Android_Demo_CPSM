package com.wk.android.eventtype.successevent;

import com.wk.android.BaseEventBean;
import com.wk.android.eventtype.EventTypeEnum;

/**
 * Created by bshn on 2017/9/1.
 */

public class SuccessProviderImpl implements SuccessProvider {
    private static SuccessProviderImpl instance;

    public synchronized static SuccessProviderImpl getInstance() {
        if (instance == null) {
            instance = new SuccessProviderImpl();
        }
        return instance;
    }

    private SuccessProviderImpl() {
    }


    @Override
    public BaseEventBean buildEventBean(String eventName, String eventParams) {
        return new SuccessEventBean(this, eventName, eventParams, EventTypeEnum.SUCCESS, System.currentTimeMillis() + "");
    }

}
