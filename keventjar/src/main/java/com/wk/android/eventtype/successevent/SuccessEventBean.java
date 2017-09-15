package com.wk.android.eventtype.successevent;

import com.wk.android.BaseEventBean;
import com.wk.android.EventType;

/**
 * Created by bshn on 2017/9/1.
 */

public class SuccessEventBean extends BaseEventBean {


    public SuccessEventBean(SuccessProvider provider, String eventName, String eventValue, EventType eventType, String eventTime) {
        super(eventName, eventValue, eventType.name(), eventTime);

    }
}
