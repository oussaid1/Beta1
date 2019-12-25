package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class TableCretorForlogin extends SQLiteOpenHelper {
    public static final String DatabaseName = "userdata.db";
    public static final String usrdataTable="userdataTable";
    public static String userColomn="userColomn";
    public static String passColomn="passwordColomn";

    public TableCretorForlogin(Context context) {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+usrdataTable+" (_id PRIMARY KEY AUTOINCREMENT, "+userColomn+" TEXT, "+passColomn+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + usrdataTable );
        onCreate(db);
    }
    public  boolean Dirfihdata(String usrname , String userpass){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(userColomn ,usrname);
        contentValues.put(passColomn , userpass);
       long boo= db.insert(usrdataTable ,null,contentValues );
       if (boo==-1) return false;
       else return true;
    }
    public Cursor JibData (){
        SQLiteDatabase db =getReadableDatabase();
        Cursor crs = db.rawQuery("select * from "+ usrdataTable , null);
        return crs;
    }

}
