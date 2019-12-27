package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserDataTables extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public static final int ID = 1;
    public static final String database_name= "Students.db";
    public static final String TABLE_ISSM = "Hugs";
    public static final String colomnA = "UserQuota";
    public static final String colomnB= "UserGuestQt";
    public static final String person ="MoolHanout";
    public static final String SHOP_TABLE = "shopinfo";
    public static final String SHOP_NAME = "name";
    public static final String SHOP_PHONE = "phone";
    public static final String SHOP_EMAIL ="email";

    public UserDataTables(@Nullable Context context) {

        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ISSM + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," + colomnA + " Double," +  colomnB + " Double )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ISSM);
    }

    public boolean InjectData(double usrQt, double GustQt) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colomnA, usrQt);
        values.put(colomnB, GustQt);
        long insertStaus = db.insert(this.TABLE_ISSM, null, values);
        if (insertStaus == -1)
            return false;
        else
            return true;
    }



    public Cursor AwidQuotDBz() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor Cur = db.rawQuery("select * from " + TABLE_ISSM , null);

        //db.rawQuery("select ("+col1+"|| ' =  ' || "+col2+" ||'  from:  ' || "+person+") AS FullItem from "+ TABLE_NAME  ,null);
        return Cur ;
    }

    public boolean deleteall(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from  "+ TABLE_ISSM);
        return true;
    }
    public Cursor GetSum(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr=db.rawQuery("SELECT SUM (Item_Price) FROM "+TABLE_ISSM ,null);
        return cr;
    }

    public static void MsgBox(String message) {
        Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
    }

}
