package com.nowy.android.basiclib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nowy.android.basiclib.model.BackTask;


/**
 * Created by Nowy on 2015/12/31.
 */
public class BackTaskDao {
    private LTDBOpenHelper dbHelper = null;
    public BackTaskDao(Context context){
        dbHelper = LTDBOpenHelper.getInstance(context);
    }



    public void addTask(BackTask task){
        SQLiteDatabase sqLiteDatabase =dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( LTDB.BackTask.COLUMN_OWNER,task.getOwner());
        contentValues.put( LTDB.BackTask.COLUMN_PATH,task.getPath());
        contentValues.put( LTDB.BackTask.COLUMN_STATE,task.getState());
        task.setId(sqLiteDatabase.insert( LTDB.BackTask.TABLE_NAME,null,contentValues));
    }


    public void updateTask(BackTask task){
        SQLiteDatabase dbBase =dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LTDB.BackTask.COLUMN_OWNER,task.getOwner());
        contentValues.put(LTDB.BackTask.COLUMN_PATH,task.getPath());
        contentValues.put(LTDB.BackTask.COLUMN_STATE,task.getState());

        String whereClause =  LTDB.BackTask.COLUMN_ID + "=?";
        String[] whereArgs = new String[] { task.getId() + "" };

        dbBase.update( LTDB.BackTask.TABLE_NAME, contentValues, whereClause, whereArgs);

    }


    public void updateTask(long id,int state){
        SQLiteDatabase dbBase =dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put( LTDB.BackTask.COLUMN_STATE,state);

        String whereClause =  LTDB.BackTask.COLUMN_ID + "=?";
        String[] whereArgs = new String[] { id + "" };

        dbBase.update( LTDB.BackTask.TABLE_NAME,contentValues,whereClause,whereArgs);

    }


    public Cursor query(String owner){
        SQLiteDatabase dbBase =dbHelper.getReadableDatabase();
        String sql = "select * from " +  LTDB.BackTask.TABLE_NAME + " where "
                +  LTDB.BackTask.COLUMN_OWNER + "=?";
        return dbBase.rawQuery(sql,new String[]{ owner });
    }


    public Cursor query(String owner,int state){
        SQLiteDatabase dbBase =dbHelper.getReadableDatabase();
        String sql = "select * from " +  LTDB.BackTask.TABLE_NAME + " where "
                +  LTDB.BackTask.COLUMN_OWNER + "=? and "
                + LTDB.BackTask.COLUMN_STATE + "=?";
        return dbBase.rawQuery(sql,new String[]{ owner ,state+"" });
    }
}
