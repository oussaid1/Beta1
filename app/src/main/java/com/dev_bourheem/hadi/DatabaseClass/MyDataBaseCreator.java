package com.dev_bourheem.hadi.DatabaseClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;


public class MyDataBaseCreator extends SQLiteOpenHelper {
    public static final String database_name = "Toys.db";
    public Context context;

    // public static final String SHOP_NAME = "name";
    // public static final String SHOP_PHONE = "phone";
    //public static final String SHOP_EMAIL = "email";

    public MyDataBaseCreator(Context context) {
        super(context, database_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DbContractor.TableColumns.TABLE_NAME + " ( "+ DbContractor.TableColumns._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + DbContractor.TableColumns.col1 + " TEXT," + DbContractor.TableColumns.Quantifier+
                " TEXT , " + DbContractor.TableColumns.Quantity + " DOUBLE ," + DbContractor.TableColumns.col2 + " DOUBLE," + DbContractor.TableColumns.person +
                " TEXT,"+ DbContractor.TableColumns.da + " TEXT )");

        /*****************************************************************************/

        db.execSQL("CREATE TABLE " + DbContractor.TableColumns.TABLE_NAMEArch +
                " ( "+ DbContractor.TableColumns._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DbContractor.TableColumns.col1Arch + " TEXT," + DbContractor.TableColumns.QuantifierArch+ " TEXT ," +
                " " + DbContractor.TableColumns.QuantityArch + " DOUBLE ," + DbContractor.TableColumns.col2Arch + " DOUBLE," +
                "" + DbContractor.TableColumns.personArch + " TEXT,"+ DbContractor.TableColumns.daArch + " TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DbContractor.TableColumns.TABLE_NAME);
        db.execSQL("drop table if exists " + DbContractor.TableColumns.TABLE_NAMEArch);
        onCreate(db);
    }

    public boolean InjectData( double Quanty,String Quantif,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.col1, name);
        values.put( DbContractor.TableColumns.col2, prix);
        values.put(DbContractor.TableColumns.Quantifier, Quantif);
        values.put(DbContractor.TableColumns.Quantity, Quanty);
        values.put(DbContractor.TableColumns.person, Sir);
        values.put(DbContractor.TableColumns.da, daat);
        long insertStaus = db.insert(DbContractor.TableColumns.TABLE_NAME, null, values);

        return insertStaus >= 1;
    }
    public void PutInArchive(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL( "insert into "+DbContractor.TableColumns.TABLE_NAMEArch+" select * from "+DbContractor.TableColumns.TABLE_NAME +"  order by " +DbContractor.TableColumns.da );
    }
    public boolean updateData(String id ,double Quanty,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.col1, name);
        values.put(DbContractor.TableColumns.col2, prix);
        values.put(DbContractor.TableColumns.Quantity, Quanty);
        values.put(DbContractor.TableColumns.person, Sir);
        values.put(DbContractor.TableColumns.da, daat);
         db.update(DbContractor.TableColumns.TABLE_NAME, values,  DbContractor.TableColumns._ID + " = ?" , new String[]{id});

         return true;
    }
    public boolean updateDataArch(String id ,double Quanty,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.col1, name);
        values.put(DbContractor.TableColumns.col2, prix);
        values.put(DbContractor.TableColumns.Quantity, Quanty);
        values.put(DbContractor.TableColumns.person, Sir);
        values.put(DbContractor.TableColumns.da, daat);
        db.update(DbContractor.TableColumns.TABLE_NAMEArch, values,  DbContractor.TableColumns._ID + " = ?" , new String[]{id});

        return true;
    }

    public boolean DeleteItemSelected(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DbContractor.TableColumns.TABLE_NAME,   DbContractor.TableColumns._ID + " = ?" , new String[]{id});
        return true;
    }

    public boolean DeleteItemSelectedArch(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DbContractor.TableColumns.TABLE_NAMEArch,   DbContractor.TableColumns._ID + " = ?" , new String[]{id});
        return true;
    }

    public Cursor GetDBdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery( "select " + DbContractor.TableColumns.col1 + ",count(" + DbContractor.TableColumns.col1 + ")as countIt from " +
                "" + DbContractor.TableColumns.TABLE_NAME + " group  by Item_Name order by countIt desc limit 8", null );
        return c1;

    }

    public Cursor GetdataByDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cu = db.rawQuery( "select " + DbContractor.TableColumns.da + ",sum(" + DbContractor.TableColumns.col2 + ")as totalbydate from "
                + DbContractor.TableColumns.TABLE_NAME + " group  by " + DbContractor.TableColumns.da + " order by totalbydate desc limit 15", null );
        return cu;

    }

    //delete table
    public boolean deleteall() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from  " + DbContractor.TableColumns.TABLE_NAME);
        return true;
    }


    public Cursor GetItemNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct " + DbContractor.TableColumns.col1 + " from " + DbContractor.TableColumns.TABLE_NAME, null);
        return cur;
    }

    public Cursor GetMolhanot() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct " + DbContractor.TableColumns.person + " from " + DbContractor.TableColumns.TABLE_NAME, null);
        return cur;
    }
    public Cursor GetSumall() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crall = db.rawQuery("SELECT SUM (Item_Price) FROM " + DbContractor.TableColumns.TABLE_NAME , null);
        return crall;
    }

    public Cursor getDates() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor datesCursor = db.rawQuery(" Select distinct " + DbContractor.TableColumns.da + " from " + DbContractor.TableColumns.TABLE_NAME + " order by " +
                "" +DbContractor.TableColumns.da + "", null);
        return datesCursor;
    }
}
