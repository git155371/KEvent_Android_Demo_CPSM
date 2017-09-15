package com.wk.android;

import android.content.Context;

import java.util.List;

/**
 * Created by bshn on 2017/9/5.
 */

class EventDBModel {
    private static EventDBModel mEventDBModel = null;

    static synchronized EventDBModel getInstance(Context context) {
        if (mEventDBModel == null) {
            mEventDBModel = new EventDBModel(context);
        }
        return mEventDBModel;
    }

    private Context mContext;
    private EventDBDao mEventDBDao;

    private EventDBModel(Context context) {
        this.mContext = context;
        this.mEventDBDao = EventDBDao.getInstance(context);
    }

    void insertOneEvent(String jsonStr, String eventName, String eventType, String time) {
        EventDBBean bean = new EventDBBean(0, jsonStr, eventName, eventType, 0, time);
        mEventDBDao.insertOne(bean);
    }

    int getEventDataSum() {
        return mEventDBDao.getDataSum();
    }


    List<EventDBBean> getEventDatas(int pageNum, int pageSize) {
        return mEventDBDao.getDatasWithPage(pageNum, pageSize);
    }

    void delectEventData(int id) {
        mEventDBDao.delectDataById(id);
    }

    void updateFailInfo(int id) {
        EventDBBean bean = mEventDBDao.getDataById(id);
        if (bean == null) {
            return;
        }
        int failNum = bean.failNum + 1;

        mEventDBDao.updateFailDataById(id, failNum);
    }

    void deleteFailData(int maxFailNum) {
        List<EventDBBean> beans = mEventDBDao.getDatasMoreFailNum(maxFailNum);
        for (EventDBBean bean : beans) {
            mEventDBDao.delectDataById(bean.id);
        }
    }


}
