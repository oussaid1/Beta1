package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDataBaseCreator extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public static final int ID = 1;
    public static final String database_name = "Students.db";
    public static final String TABLE_NAME = "Hugs";
    public static final String col1 = "Item_Name";
    public static final String col2 = "Item_Price";
    public static final String person = "MoolHanout";
    // public static final String SHOP_NAME = "name";
    // public static final String SHOP_PHONE = "phone";
    //public static final String SHOP_EMAIL = "email";

    public MyDataBaseCreator(Context context) {
        super(context, database_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," + col1 + " TEXT," + col2 + " DOUBLE," + person + " TEXT)");
        // db.execSQL("CREATE TABLE "+ SHOP_TABLE + "(_ID INTEGER Primary key autoincrement,"+SHOP_NAME +" TEXT, " +SHOP_PHONE +" TEXT, "+SHOP_EMAIL+ " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        // db.execSQL("drop table if exists molhanotinfo");
        onCreate(db);
    }

    public boolean InjectData(String Sir, String name, double prix) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, name);
        values.put(col2, prix);
        values.put(person, Sir);
        long insertStaus = db.insert(this.TABLE_NAME, null, values);
        if (insertStaus == -1)
            return false;
        else
            return true;
    }


    public Cursor GetDBdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery("select ( '- ' ||+" + col1 + "|| ' =  ' || " + col2 + " ||'  from :  ' || " + person + ") AS FullItem from " + TABLE_NAME, null);
        return c1;

    }

    //delete table
    public boolean deleteall() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from  " + TABLE_NAME);
        return true;
    }
    // this mehotd gets the sum of all Item_price elements
    public Cursor GetSum() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT SUM (Item_Price) FROM " + TABLE_NAME, null);
        return cr;
    }

    public  Cursor GetItemNames() {
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cur = db.rawQuery("select distinct "+ col1 +" from "+TABLE_NAME,null);
       return cur;
    }
    public  Cursor GetMolhanot() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct "+ person +" from "+TABLE_NAME,null);
        return cur;
    }

}
