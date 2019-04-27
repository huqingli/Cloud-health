package com.moy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.moy.pojo.UserPOJO;
import com.moy.util.MyApplication;

/**
 * Created by Administrator on 2017/10/6.
 */

public class UserDao {

    private MySqliteOpenHelper mySqliteOpenHelper;

    public UserDao(Context context){
        mySqliteOpenHelper = new MySqliteOpenHelper(context);

    }

    public boolean add(String table,ContentValues values){


        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        long result = db.insert(table,null,values);
        db.close();
        if(result != -1){
            return true;
        }else{
            return false;
        }
    }

    public int del(String table,String id){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //返回成功删除的行数
        int result = db.delete(table,"phonenum = ?",new String[]{id});
        db.close();
        return result;

    }

    public int update(String table,ContentValues values,String id){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //update(表名，修改的列名与值，查询条件，条件占位符)
        //返回成功修改的行数
        int result = db.update(table,values,"phonenum =?",new String[]{id});
        db.close();
        return result;
    }

    public Cursor quard(String table,String colunms[],String id){
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //query(表名，查询列，查询条件，条件占位符，按什么字段分组，分组条件，排序方式)
        Cursor cursor = db.query(table,colunms,"phonenum =?",new String[]{id},null,null,null);
        return cursor;

    }
}
