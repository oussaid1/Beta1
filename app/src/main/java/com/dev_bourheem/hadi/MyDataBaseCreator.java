package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MyDataBaseCreator extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public static final int ID = 1;
    public static final String database_name= "Students.db";
    public static final String TABLE_NAME = "Hugs";
    public static final String col1 = "Item_Name";
    public static final String col2 = "Item_Price";
    public static final String person ="MoolHanout";
    public static final String SHOP_TABLE = "shopinfo";
    public static final String SHOP_NAME = "name";
    public static final String SHOP_PHONE = "phone";
    public static final String SHOP_EMAIL ="email";

    public MyDataBaseCreator (Context context){
        super(context,database_name,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," + col1 + " TEXT," + col2 + " DOUBLE," + person + " TEXT )");
       // db.execSQL("CREATE TABLE "+ SHOP_TABLE + "(_ID INTEGER Primary key autoincrement,"+SHOP_NAME +" TEXT, " +SHOP_PHONE +" TEXT, "+SHOP_EMAIL+ " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL("drop table if exists molhanotinfo");
        onCreate(db);
    }
    public boolean InjectData(String Sir ,String name, double prix) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, name);
        values.put(col2, prix);
        values.put(person,Sir);
        long insertStaus = db.insert(this.TABLE_NAME, null, values);
        if (insertStaus == -1)
            return false;
        else
            return true;
    }

    public boolean InjectDataIntable2(String name, String phone,String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SHOP_NAME, name);
        values.put(SHOP_PHONE, phone);
        values.put(SHOP_EMAIL,email);
        long insertStaus = db.insert(this.SHOP_TABLE, null, values);
        if (insertStaus == -1)
            return false;
        else
            return true;
    }

    public Cursor GetDBdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor c1 = db.rawQuery("select Item_Name  from " + TABLE_NAME, null);

        return db.rawQuery("select (" +col1+"|| ' =>  ' || "+col2+" ||' From:  ' || "+person+") AS FullItem from "+ TABLE_NAME, null);
    }

    public boolean deleteall(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        return true;
    }
    public Cursor GetSum(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr=db.rawQuery("SELECT SUM (Item_Price) FROM "+TABLE_NAME ,null);
        return cr;
    }

    public static void MsgBox(String message) {
        Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
    }
}
