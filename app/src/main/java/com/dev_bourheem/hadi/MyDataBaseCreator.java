package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDataBaseCreator extends SQLiteOpenHelper {
    public static final String database_name = "School.db";
    public static final String TABLE_NAME = "Goods";
    public static final String ID = "ID";
    public static final String col1 = "Item_Name";
    public static final String col2 = "Item_Price";
    public static final String person = "MoolHanout";
    public static final String da = "history";
    public static final String Quantifier = "Quantifiers";
    public static final String Quantity = "quantity";
    public static final String TABLE_NAMEArch = "GoodsArch";
    public static final String IDArch = "ID";
    public static final String col1Arch = "Item_Name";
    public static final String col2Arch = "Item_Price";
    public static final String personArch = "MoolHanout";
    public static final String daArch = "history";
    public static final String QuantifierArch = "Quantifiers";
    public static final String QuantityArch = "quantity";
    public Context context;

    // public static final String SHOP_NAME = "name";
    // public static final String SHOP_PHONE = "phone";
    //public static final String SHOP_EMAIL = "email";

    public MyDataBaseCreator(Context context) {
        super(context, database_name, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( "+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + col1 + " TEXT," + Quantifier+" TEXT , " + Quantity + " DOUBLE ," + col2 + " DOUBLE," + person + " TEXT,"+ da + " TEXT )");
        db.execSQL("CREATE TABLE " + TABLE_NAMEArch + " ( "+ IDArch +" INTEGER PRIMARY KEY AUTOINCREMENT," + col1Arch + " TEXT," + QuantifierArch+ " TEXT , " + QuantityArch + " DOUBLE ," + col2Arch + " DOUBLE," + personArch + " TEXT,"+ daArch + " TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL("drop table if exists " + TABLE_NAMEArch);
        onCreate(db);
    }

    public boolean InjectData( double Quanty,String Quantif,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, name);
        values.put(col2, prix);
        values.put(Quantifier, Quantif);
        values.put(Quantity, Quanty);
        values.put(person, Sir);
        values.put(da, daat);
        long insertStaus = db.insert(TABLE_NAME, null, values);

        return insertStaus >= 1;
    }
    public void PutInArchive(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL( "insert into "+TABLE_NAMEArch+" select * from "+TABLE_NAME +"  order by "+da );
    }
    public boolean updateData(String id ,double Quanty,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, name);
        values.put(col2, prix);
        values.put(Quantity, Quanty);
        values.put(person, Sir);
        values.put(da, daat);
         db.update(TABLE_NAME, values,  ID + " = ?" , new String[]{id});

         return true;
    }
    public boolean updateDataArch(String id ,double Quanty,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, name);
        values.put(col2, prix);
        values.put(Quantity, Quanty);
        values.put(person, Sir);
        values.put(da, daat);
        db.update(TABLE_NAMEArch, values,  IDArch + " = ?" , new String[]{id});

        return true;
    }

    public boolean DeleteItemSelected(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,   ID + " = ?" , new String[]{id});
        return true;
    }

    public boolean DeleteItemSelectedArch(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAMEArch,   IDArch + " = ?" , new String[]{id});
        return true;
    }

    public Cursor GetDBdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery( "select " + col1 + ",count(" + col1 + ")as countIt from " + TABLE_NAME + " group  by Item_Name order by countIt desc limit 8", null );
        return c1;

    }

    public Cursor GetdataByDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cu = db.rawQuery( "select " + da + ",sum(" + col2 + ")as totalbydate from " + TABLE_NAME + " group  by " + da + " order by totalbydate desc limit 15", null );
        return cu;

    }

    //delete table
    public boolean deleteall() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from  " + TABLE_NAME);
        return true;
    }


    public Cursor GetItemNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct " + col1 + " from " + TABLE_NAME, null);
        return cur;
    }

    public Cursor GetMolhanot() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct " + person + " from " + TABLE_NAME, null);
        return cur;
    }
    public Cursor GetSumall() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crall = db.rawQuery("SELECT SUM (Item_Price) FROM " + TABLE_NAME , null);
        return crall;
    }

    public Cursor getDates() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor datesCursor = db.rawQuery(" Select distinct " + da + " from " + TABLE_NAME + " order by " + da + "", null);
        return datesCursor;
    }
}
