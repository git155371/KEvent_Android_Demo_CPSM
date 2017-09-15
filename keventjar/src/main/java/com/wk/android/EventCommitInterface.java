package com.wk.android;


import java.util.List;

/**
 * Created by bshn on 2017/9/5.
 */

public interface EventCommitInterface {
    void commit(List<EventDBBean> commitList, EventCommitCallback callback);
}
