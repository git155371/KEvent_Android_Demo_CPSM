package com.wk.android;

/**
 * Created by bshn on 2017/9/1.
 */

public interface BaseProvider {

    BaseEventBean buildEventBean(String eventName, String eventParams);
}
