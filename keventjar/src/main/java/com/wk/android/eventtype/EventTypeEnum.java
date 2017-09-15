package com.wk.android.eventtype;

import com.wk.android.BaseProvider;
import com.wk.android.EventType;
import com.wk.android.eventtype.clickevent.ClickProviderImpl;
import com.wk.android.eventtype.successevent.SuccessProviderImpl;

/**
 * Created by bshn on 2017/9/1.
 * CLICK --> ClickProvider, ClickProviderImpl, ClickEventBean, EventManager.logClickEvent(...)
 * SUCCESS --> SuccessProvider, SuccessProviderImpl, SuccessEventBean, EventManager.logSuccessEvent(...)
 */

public enum EventTypeEnum implements EventType{
    CLICK {
        @Override
        public BaseProvider getProvider() {
            return ClickProviderImpl.getInstance();
        }
    },
    SUCCESS {
        @Override
        public BaseProvider getProvider() {
            return SuccessProviderImpl.getInstance();
        }
    },;
}
