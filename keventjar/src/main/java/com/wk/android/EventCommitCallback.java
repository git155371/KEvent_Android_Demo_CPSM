package com.wk.android;

/**
 * Created by bshn on 2017/9/5.
 */

public interface EventCommitCallback {
    void onSuccess();

    void onFail(boolean needMarkFail);

}
