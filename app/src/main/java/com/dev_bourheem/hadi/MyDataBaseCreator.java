package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDataBaseCreator extends SQLiteOpenHelper {
MainActivity mainac=new MainActivity();
    public static final String database_name = "Trains.db";
    public static final String TABLE_NAME = "Girls";
    public static final String col1 = "Item_Name";
    public static final String col2 = "Item_Price";
    public static final String person = "MoolHanout";
    public static final String da = "history";
    public  final String dat = mainac.GetDate();
    // public static final String SHOP_NAME = "name";
    // public static final String SHOP_PHONE = "phone";
    //public static final String SHOP_EMAIL = "email";

    public MyDataBaseCreator(Context context) {
        super(context, database_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," + col1 + " TEXT," + col2 + " DOUBLE," + person + " TEXT,"+ da + " TEXT )");
        // db.execSQL("CREATE TABLE "+ SHOP_TABLE + "(_ID INTEGER Primary key autoincrement,"+SHOP_NAME +" TEXT, " +SHOP_PHONE +" TEXT, "+SHOP_EMAIL+ " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        // db.execSQL("drop table if exists molhanotinfo");
        onCreate(db);
    }

    public boolean InjectData(String Sir, String name, double prix,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(person, Sir);
        values.put(col1, name);
        values.put(col2, prix);
        values.put(da, daat);
        long insertStaus = db.insert(TABLE_NAME, null, values);
        return insertStaus != -1;
    }


    public Cursor GetDBdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery("select ( '- ' ||+" + col1 + "|| ' =  ' || "
                + col2 + " ||'  من عند :  ' || " + person + "|| ': يوم :'||"+ da + ") " +
                "AS FullItem from " + TABLE_NAME+" order by history ",null);
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
        Cursor cr = db.rawQuery("SELECT SUM (Item_Price) FROM " + TABLE_NAME +" group by history ", null);
        return cr;
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

}
