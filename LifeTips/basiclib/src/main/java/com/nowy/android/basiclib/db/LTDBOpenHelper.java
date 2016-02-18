package com.nowy.android.basiclib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Nowy on 2015/12/31.
 */
public class LTDBOpenHelper extends SQLiteOpenHelper {

    private static LTDBOpenHelper instance = null;
    public LTDBOpenHelper(Context context){
        super(context, LTDB.NAME, null,  LTDB.VERSION);
    }


    private synchronized static void syncInit(Context context){
        if(null == instance){
            instance = new LTDBOpenHelper(context);
        }

    }

    public static LTDBOpenHelper getInstance(Context context){
        if(null == instance){
            syncInit(context);
        }

        return instance;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LTDB.BackTask.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
