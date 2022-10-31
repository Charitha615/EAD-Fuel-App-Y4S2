package com.example.fuelqueueapplication.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * db helper class
 * **/
public class DBHelper extends SQLiteOpenHelper {

    //constructor
    public DBHelper(Context context) {
        super(context, "database.db", null, 1);
    }

    //on create method
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table user (id integer primary key autoincrement,userid text, username text, role text)");
    }

    //on update method
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists user");
        onCreate(DB);

    }

    // save user in sqlite
    public boolean saveUser(String userid, String username, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userid",userid);
        cv.put("username",username);
        cv.put("role",role);

        long result = db.insert("User",null,cv);
        return result == -1;
    }
}
