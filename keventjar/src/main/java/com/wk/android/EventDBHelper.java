package com.wk.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bshn on 2017/9/1.
 */

class EventDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "event.db";
    static final String DB_TABLE = "event";
    private static final int DB_VERSION = 1;

    EventDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String sql = "create table if not exists " + DB_TABLE + " (Id integer primary key, CustomName text, OrderPrice integer, Country text)";
        String sql = "create table if not exists " + DB_TABLE + " (ID integer primary key, EventJson text, EventName text, EventType text, FailNum integer, Time text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //为了方便，临时处理
        String sql = "DROP TABLE IF EXISTS " + DB_TABLE;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
