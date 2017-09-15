package com.wk.android;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by bshn on 2017/9/5.
 */
class EventDBServeice {
    private int mJudgeCommitNum = 0;//判断提交的条数阈值 (>=)
    private int mCommitEventNum = 0;//提交一次的条数
    private int mMaxFailNum = 0;//一条数据最大的失败次数
    private Context mContext;
    private EventDBModel mEventDBModel;
    private EventCommitInterface mCommitInterface = null;
    private boolean ifCommitting = false;
    private int finishNum = 0;

    private static EventDBServeice instance = null;

    synchronized static EventDBServeice getInstance(Context context, EventCommitInterface commitInterface, int judgeCommitNum, int commitEventNum, int maxFailNum) {
        if (instance == null) {
            instance = new EventDBServeice(context, commitInterface, judgeCommitNum, commitEventNum, maxFailNum);
        }

        if (commitInterface != instance.mCommitInterface) {
            instance = new EventDBServeice(context, commitInterface, judgeCommitNum, commitEventNum, maxFailNum);
        }
        return instance;
    }

    private EventDBServeice(Context context, EventCommitInterface commitInterface, int judgeCommitNum, int commitEventNum, int maxFailNum) {
        this.mContext = context;
        this.mCommitInterface = commitInterface;
        this.mJudgeCommitNum = judgeCommitNum;
        this.mCommitEventNum = commitEventNum;
        this.mMaxFailNum = maxFailNum;
        this.mEventDBModel = EventDBModel.getInstance(mContext);
    }

    /**
     * 插入数据库事件并提交
     * 谨慎使用
     */
    @Deprecated
    void insertEventAddCommit(BaseEventBean eventBean) {
        insertEvent(eventBean, true);
    }

    /**
     * 插入数据库事件
     */
    synchronized void insertEvent(BaseEventBean eventBean) {
        insertEvent(eventBean, false);
    }

    private void insertEvent(BaseEventBean eventBean, boolean mustCommit) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(eventBean);

        mEventDBModel.insertOneEvent(jsonStr, eventBean.eventName, eventBean.eventType, System.currentTimeMillis() + "");
        checkCommit(mustCommit);
    }

    private void checkCommit(boolean mustCommit) {
        int eventSum = mEventDBModel.getEventDataSum();
        if (!mustCommit) {
            if (eventSum < mJudgeCommitNum) {
                //条数不足提交
                return;
            }
        }
        commitEvent(eventSum);
    }

    private void commitEvent(int eventSum) {
        if (eventSum <= 0) {
            return;
        }

        //todo 判断是否有网


        if (ifCommitting) {//判断是否在提交中
            return;
        }

        final int commitCount = eventSum % mCommitEventNum != 0 ? eventSum / mCommitEventNum + 1 : mCommitEventNum;

        if (mCommitInterface == null) {
            return;
        }

        finishNum = 0;
        ifCommitting = true;


        commitEventWithNet(commitCount);
    }

    private void commitEventWithNet(final int commitCount) {
//        final List<EventDBBean> beans = mEventDBModel.getEventDatas(finishNum, COMMIT_EVENT_NUM);
        final List<EventDBBean> beans = mEventDBModel.getEventDatas(0, mCommitEventNum);
        if (beans == null || beans.size() == 0) {
            commitFinish(commitCount);
            return;
        }
        mCommitInterface.commit(beans, new EventCommitCallback() {
            @Override
            public void onSuccess() {
                for (EventDBBean bean : beans) {
                    mEventDBModel.delectEventData(bean.id);
                }

                commitFinish(commitCount);
            }

            @Override
            public void onFail(boolean needMarkFail) {
                //如果失败 ，加失败次数
                if (needMarkFail) {
                    for (EventDBBean bean : beans) {
                        mEventDBModel.updateFailInfo(bean.id);
                    }
                }

                commitFinish(commitCount);
            }
        });
    }

    /**
     * 一次提交完成的处理
     */
    private void commitFinish(int commitCount) {
        finishNum++;
        if (finishNum >= commitCount) {
            deleteFailEventData();
        } else {
            commitEventWithNet(commitCount);
        }
    }

    /**
     * 提交成功后，删除失败的数据
     */
    private void deleteFailEventData() {
        mEventDBModel.deleteFailData(mMaxFailNum);

        ifCommitting = false;
    }

}

