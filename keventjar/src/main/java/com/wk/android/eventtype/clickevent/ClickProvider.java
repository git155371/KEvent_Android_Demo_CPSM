package com.wk.android.eventtype.clickevent;

import com.wk.android.BaseProvider;

/**
 * Created by bshn on 2017/9/1.
 */

public interface ClickProvider extends BaseProvider {
    /**
     * 设备信息
     */
    String getDeviceInfo();

    /**
     * 应用版本
     */
    String getAppVersion();

    /**
     * 国家码
     */
    String getCountryCode();
}
