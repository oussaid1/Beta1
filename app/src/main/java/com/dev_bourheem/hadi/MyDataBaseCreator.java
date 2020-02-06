package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;


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

    // public static final String SHOP_NAME = "name";
    // public static final String SHOP_PHONE = "phone";
    //public static final String SHOP_EMAIL = "email";

    public MyDataBaseCreator(Context context) {
        super(context, database_name, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( "+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + col1 + " TEXT," + Quantifier+" TEXT , " + Quantity + " DOUBLE ," + col2 + " DOUBLE," + person + " TEXT,"+ da + " TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
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
    public boolean DeleteItemSelected(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,   ID + " = ?" , new String[]{id});
        return true;
    }

    public Cursor GetDBdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery("select "+col1+",sum("+col2+")as total from " + TABLE_NAME+" group  by Item_Name ",null);
        return c1;

    }
    public Cursor GetDBdataAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cu = db.rawQuery("select * from " + TABLE_NAME +" order by history ",null);
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

    public static void backUp() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.dev_bourheem.hadi//databases//School.db";
                String backupDBPath = "School.db";

                File currentDB = new File( data, currentDBPath );
                File backupDB = new File( sd, backupDBPath );

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream( currentDB ).getChannel();
                    FileChannel dst = new FileOutputStream( backupDB ).getChannel();
                    dst.transferFrom( src, 0, src.size() );
                    src.close();
                    dst.close();
                    //Toast.makeText(MyDataBaseCreator.this , "Backup is successful to SD card", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restore() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.dev_bourheem.hadi//databases//School.db";
                String backupDBPath = "School.db";
                File currentDB = new File( data, currentDBPath );
                File backupDB = new File( sd, backupDBPath );

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream( backupDB ).getChannel();
                    FileChannel dst = new FileOutputStream( currentDB ).getChannel();
                    dst.transferFrom( src, 0, src.size() );
                    src.close();
                    dst.close();
                    //  Toast.makeText(getApplicationContext(), "Database Restored successfully", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
