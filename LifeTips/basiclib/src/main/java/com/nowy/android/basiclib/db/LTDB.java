package com.nowy.android.basiclib.db;

/**
 * Created by Nowy on 2015/12/31.
 */
public interface LTDB {
    String NAME="LT.db";
    int VERSION = 1 ;

     interface BackTask{
        String TABLE_NAME = "back_task";

        String COLUMN_ID = "_id";
        String COLUMN_OWNER = "owner"; //参数
        String COLUMN_PATH = "path";   //请求路径
        String COLUMN_STATE = "state";// 0:未执行 1:正在执行 2:执行完成


        String SQL_CREATE_TABLE = "create table " + TABLE_NAME + " ("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_OWNER + " text," + COLUMN_PATH + " text,"
                + COLUMN_STATE + " integer" + ")";

    }

}
