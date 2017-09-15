package com.wk.android;

import android.content.Context;

import javax.annotation.Nonnull;

/**
 * Created by kwang on 2017/9/14.
 * 本类是KEvent框架的外放公共方法
 */

public class KEventManager {
    /**
     * 提交的条数阈值 (>=)
     * 超过这个值，即进行提交操作
     */
    static final int JUDGE_COMMIT_NUM = 7;
    /**
     * 提交一次的条数值
     * 用户配置的网络接口每次能提交的条数值
     */
    static final int COMMIT_EVENT_NUM = 3;
    /**
     * 一条数据最大的失败次数
     * 每条数据最多能失败提交的次数
     */
    static final int MAX_FAIL_NUM = 10;

    /**
     * 框架管理者对象
     */
    static EventManager mEventManager;

    /**
     * 框架初始化方法
     *
     * @param commitInterface （Nonnull, EventCommitInterface.class）网络提交接口
     */
    public static void init(@Nonnull Context context, @Nonnull EventCommitInterface commitInterface) {
        initKEvent(context, commitInterface, JUDGE_COMMIT_NUM, COMMIT_EVENT_NUM, MAX_FAIL_NUM);
    }

    /**
     * 框架初始化方法
     * judgeCommitNum 必须大于等于 commitEventNum；
     * 如果judgeCommitNum，commitEventNum,maxFailNum传入值小于登于0，使用默认值；
     *
     * @param commitInterface （Nonnull, EventCommitInterface.class）网络提交接口
     * @param judgeCommitNum  提交的条数阈值 (>=)
     * @param commitEventNum  提交一次的条数值
     * @param maxFailNum      一条数据最大的失败次数（>=）
     */
    public static void init(@Nonnull Context context, @Nonnull EventCommitInterface commitInterface, int judgeCommitNum, int commitEventNum, int maxFailNum) {
        int judgeCommitNumTemp = JUDGE_COMMIT_NUM;
        int commitEventNumTemp = COMMIT_EVENT_NUM;
        int maxFailNumTemp = MAX_FAIL_NUM;
        if (judgeCommitNum > 0) {
            judgeCommitNumTemp = judgeCommitNum;
        }
        if (commitEventNum > 0) {
            commitEventNumTemp = commitEventNum;
        }
        if (maxFailNum > 0) {
            maxFailNumTemp = maxFailNum;
        }

        if (judgeCommitNumTemp < commitEventNumTemp) {
            throw new IllegalArgumentException("judgeCommitNum < commitEventNum , 提交的条数阈值 必须大于等于 提交一次的条数值");
        }


        initKEvent(context, commitInterface, judgeCommitNumTemp, commitEventNumTemp, maxFailNumTemp);
    }

    /**
     * 获取EventManager对象，执行埋点操作
     *
     * @return EventManager.class
     */
    public static EventManager getInstance() {
        if (mEventManager == null) {
            throw new NullPointerException("EventManager 对象参数不能为空，应该先执行EventManager.init(EventCommitInterface)");
        }
        return mEventManager;
    }

    /**
     * 初始化EventManager对象
     */
    private static void initKEvent(@Nonnull Context context, @Nonnull EventCommitInterface commitInterface, int judgeCommitNum, int commitEventNum, int maxFailNum) {
        mEventManager = new EventManager(context, commitInterface, judgeCommitNum, commitEventNum, maxFailNum);
    }

}
