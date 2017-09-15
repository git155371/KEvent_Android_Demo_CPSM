package com.wk.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bshn on 2017/9/1.
 */

class EventDBDao {

    private static EventDBDao mEventDBDao = null;

    synchronized static EventDBDao getInstance(Context context) {
        if (mEventDBDao == null) {
            mEventDBDao = new EventDBDao(context.getApplicationContext());
        }
        return mEventDBDao;
    }


    private Context mContext;
    private EventDBHelper mEventDBHelper;

    private EventDBDao(Context context) {

        this.mContext = context;
        mEventDBHelper = new EventDBHelper(context);
    }

    /**
     * 插入一条数据
     * eventDBBean.id是自增的，所以不使用
     */
    synchronized void insertOne(EventDBBean eventDBBean) {
        SQLiteDatabase db = null;

        try {
            db = mEventDBHelper.getWritableDatabase();
            db.beginTransaction();
//            db.execSQL("insert into " + "event" + " (EventJson, EventName, EventType, Time) values ('" + jsonStr + "', '" + eventName + "', "  + eventType + ", '"+ time +"')");
            ContentValues contentValues = new ContentValues();
            contentValues.put("EventJson", eventDBBean.eventValue);
            contentValues.put("EventName", eventDBBean.eventName);
            contentValues.put("EventType", eventDBBean.eventType);
            contentValues.put("FailNum", eventDBBean.failNum);
            contentValues.put("Time", eventDBBean.eventTime);
            db.insert(mEventDBHelper.DB_TABLE, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 获取数据的条数
     */
    synchronized int getDataSum() {
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mEventDBHelper.getReadableDatabase();
            cursor = db.rawQuery("select count(ID) from " + mEventDBHelper.DB_TABLE, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            } else {
                count = 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return count;
    }

    /**
     * 分页获取数据列表
     */
    synchronized List<EventDBBean> getDatasWithPage(int pageNum, int pageSize) {
        List<EventDBBean> beans = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mEventDBHelper.getReadableDatabase();
            String sqlText = "select ID, EventJson, EventName, EventType, FailNum, Time from " + mEventDBHelper.DB_TABLE + " order by ID limit " + pageSize + " offset " + pageNum * pageSize;
            cursor = db.rawQuery(sqlText, null);
            while (cursor.moveToNext()) {
                beans.add(new EventDBBean(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return beans;
    }

    /**
     * 通过ID删除一条数据
     */
    synchronized void delectDataById(int id) {
        SQLiteDatabase db = null;

        try {
            db = mEventDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.delete(mEventDBHelper.DB_TABLE, "ID =?", new String[]{id + ""});

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 通过ID获取一条数据
     */
    synchronized EventDBBean getDataById(int id) {
        EventDBBean bean = null;

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mEventDBHelper.getReadableDatabase();
            cursor = db.query(false, mEventDBHelper.DB_TABLE, new String[]{"ID", "EventJson", "EventName", "EventType", "FailNum", "Time"}, "id=?", new String[]{id + ""}, null, null, null, null);
            while (cursor.moveToNext()) {
                bean = new EventDBBean(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return bean;
    }

    /**
     * 获取超过规定失败次数的 数据
     */
    synchronized List<EventDBBean> getDatasMoreFailNum(int maxFailNum) {
        List<EventDBBean> beans = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mEventDBHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT ID, EventJson, EventName, EventType, FailNum, Time FROM " + mEventDBHelper.DB_TABLE + " where FailNum>=?", new String[]{maxFailNum + ""});
            while (cursor.moveToNext()) {
                beans.add(new EventDBBean(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return beans;
    }

    /**
     * 更新失败次数 通过ID
     */
    synchronized void updateFailDataById(int id, int newFailNum) {
        SQLiteDatabase db = null;

        try {
            db = mEventDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("FailNum", newFailNum);

            db.update(mEventDBHelper.DB_TABLE, values, "id=?", new String[]{id + ""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
