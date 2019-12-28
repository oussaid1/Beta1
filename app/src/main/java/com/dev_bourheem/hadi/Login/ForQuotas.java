package com.dev_bourheem.hadi.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dev_bourheem.hadi.Main3Activity;


public class ForQuotas extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public static final int ID = 1;
    public static final String database_name= "girls.db";
    public static final String TABLE_NAME = "boys";
    public static final String col11 = "userQuota";
    public static final String col22 = "userGQuota";
    //private Main3Activity M3Actvt;

    public ForQuotas (Context context){
        super(context,database_name,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT," + col11 + " DOUBLE," + col22 + " DOUBLE )");
        // db.execSQL("CREATE TABLE "+ SHOP_TABLE + "(_ID INTEGER Primary key autoincrement,"+SHOP_NAME +" TEXT, " +SHOP_PHONE +" TEXT, "+SHOP_EMAIL+ " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        // db.execSQL("drop table if exists molhanotinfo");
        onCreate(db);
    }
    public boolean InjectData(double Q1, double Q2) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col11, Q1);
        values.put(col22, Q2);

        long insertStaus = db.insert(this.TABLE_NAME, null, values);
        if (insertStaus == -1)
            return false;
        else
            return true;
    }

    public Cursor JibData (){
       // M3Actvt= new Main3Activity();
        SQLiteDatabase db =getReadableDatabase();
        Cursor b1 = db.rawQuery("select * from " + TABLE_NAME +" ORDER BY id DESC limit 1" ,null );
        return b1;
    }
}
