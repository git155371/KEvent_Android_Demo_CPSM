package com.wk.android;


import android.content.Context;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by bshn on 2017/9/1.
 */

class EventManager {
    private Context mContext;
    private EventCommitInterface mCommitInterface;
    private int mJudgeCommitNum;
    private int commitEventNum;
    private int maxFailNum;

    EventManager(Context context, EventCommitInterface commitInterface, int judgeCommitNum, int commitEventNum, int maxFailNum) {
        this.mContext = context.getApplicationContext();
        this.mCommitInterface = commitInterface;
        this.mJudgeCommitNum = judgeCommitNum;
        this.commitEventNum = commitEventNum;
        this.maxFailNum = maxFailNum;
    }


    public void logEvent(EventType eventType, EventKey eventKey, String... values) {
        BaseProvider baseProvider = eventType.getProvider();
        event(baseProvider, false, eventKey, values);
    }

    @Deprecated
    public void logEventAddCommit(EventType eventType, EventKey eventKey, String... values) {
        BaseProvider baseProvider = eventType.getProvider();
        event(baseProvider, true, eventKey, values);
    }

    private void event(BaseProvider provider, boolean ifCommit, EventKey eventKey, String... values) {
        //处理事件参数
        String paramsStr = dealEventParams(eventKey, values);

        //创建事件对象
        BaseEventBean baseEventBean = provider.buildEventBean(eventKey.name(), paramsStr);

        //储存到数据库
        if (ifCommit) {
            EventDBServeice.getInstance(mContext, mCommitInterface, mJudgeCommitNum, commitEventNum, maxFailNum).insertEventAddCommit(baseEventBean);
        } else {
            EventDBServeice.getInstance(mContext, mCommitInterface, mJudgeCommitNum, commitEventNum, maxFailNum).insertEvent(baseEventBean);
        }

    }

    private String dealEventParams(EventKey eventKey, String... values) {
        String paramsStr = null;
        String[] keys = eventKey.getParamsName();
        if (values != null && keys != null && keys.length > 0) {

            HashMap<String, String> paramsMap = new HashMap<>();
            int keysLen = keys.length;
            int valuesLen = values.length;
            if (keysLen <= valuesLen) {
                for (int i = 0; i < keysLen; i++) {
                    paramsMap.put(keys[i], values[i]);

                }
            } else {
                for (int i = 0; i < valuesLen; i++) {
                    paramsMap.put(keys[i], values[i]);
                }
                for (int i = 0; i < keysLen - valuesLen; i++) {
                    paramsMap.put(keys[valuesLen + i], null);//TODO: 此处如果用户没有填充这个参数时，赋值成null
                }
            }

            Gson gson = new Gson();
            gson.toJson(paramsMap);
        }

        return paramsStr;
    }
}
