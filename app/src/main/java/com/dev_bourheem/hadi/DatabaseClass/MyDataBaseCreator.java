package com.dev_bourheem.hadi.DatabaseClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;


public class MyDataBaseCreator extends SQLiteOpenHelper {
    public static final String database_name = "Merchendise.db";
    public Context context;

    private static final String CreateMainTable= " CREATE TABLE " + DbContractor.TableColumns.MainTable + " ( "+ DbContractor.TableColumns._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + DbContractor.TableColumns.MItem_Name + " TEXT," + DbContractor.TableColumns.MQuantifier+
            " TEXT , " + DbContractor.TableColumns.MQuantity + " DOUBLE ," + DbContractor.TableColumns.MItem_Price + " DOUBLE," + DbContractor.TableColumns.MShopName +
            " TEXT,"+ DbContractor.TableColumns.MDate + " TEXT )";
    /*****************************************************************************/
    private static final String CreateArchiveTable= "CREATE TABLE " + DbContractor.TableColumns.ArchiveTable +
            " ( "+ DbContractor.TableColumns._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbContractor.TableColumns.ArItem_Name + " TEXT," + DbContractor.TableColumns.ArQuantifier+ " TEXT ," +
            " " + DbContractor.TableColumns.ArQuantity + " DOUBLE ," + DbContractor.TableColumns.ArItem_Price + " DOUBLE," +
            "" + DbContractor.TableColumns.ArShopName + " TEXT,"+ DbContractor.TableColumns.ArDate + " TEXT )";
    /*****************************************************************************/
    private static final String CreatePaymentTable= "Create Table "+DbContractor.TableColumns.PaymentTable +" ("+ DbContractor.TableColumns._ID +"" +
            " integer primary key autoincrement , "+DbContractor.TableColumns.shopName + " text ," +
            "" +DbContractor.TableColumns.paidAmount+" double , "+DbContractor.TableColumns.paymentDate+" date )";
    /*****************************************************************************/
    private static final String CreateSettingsTable= "Create Table "+DbContractor.TableColumns.PaymentTable +" ("+ DbContractor.TableColumns._ID +"" +
            " integer primary key autoincrement , "+DbContractor.TableColumns.userQuota + " double ," +
            "" + DbContractor.TableColumns.userGQuota +" double ," +DbContractor.TableColumns.paidAmount+" double , "+DbContractor.TableColumns.paymentDate+" date )";
    /*****************************************************************************/
    private static final String CreateInfoTable= "Create Table "+DbContractor.TableColumns.InfoTable +" ("+ DbContractor.TableColumns._ID +"" +
            " integer primary key autoincrement , "+DbContractor.TableColumns.SHOP_NAME + " text ," +
            "" + DbContractor.TableColumns.SHOP_PHONE +" text ," +DbContractor.TableColumns.SHOP_EMAIL+" text )";
    /*****************************************************************************/



    public MyDataBaseCreator(Context context) {
        super(context, database_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CreateMainTable);
        db.execSQL(CreateArchiveTable);
        db.execSQL(CreatePaymentTable);
        db.execSQL(CreateSettingsTable);
        db.execSQL(CreateInfoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DbContractor.TableColumns.MainTable);
        db.execSQL("drop table if exists " + DbContractor.TableColumns.ArchiveTable);
        onCreate(db);
    }

    public boolean InjectData( double Quanty,String Quantif,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.MItem_Name, name);
        values.put( DbContractor.TableColumns.MItem_Price, prix);
        values.put(DbContractor.TableColumns.MQuantifier, Quantif);
        values.put(DbContractor.TableColumns.MQuantity, Quanty);
        values.put(DbContractor.TableColumns.MShopName, Sir);
        values.put(DbContractor.TableColumns.MDate, daat);
        long insertStaus = db.insert(DbContractor.TableColumns.MainTable, null, values);

        return insertStaus >= 1;
    }

    public boolean updateData(String id ,double Quanty,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.MItem_Name, name);
        values.put(DbContractor.TableColumns.MItem_Price, prix);
        values.put(DbContractor.TableColumns.MQuantity, Quanty);
        values.put(DbContractor.TableColumns.MShopName, Sir);
        values.put(DbContractor.TableColumns.MDate, daat);
         db.update(DbContractor.TableColumns.MainTable, values,  DbContractor.TableColumns._ID + " = ?" , new String[]{id});

         return true;
    }
    public boolean updateDataArch(String id ,double Quanty,String name, double prix, String Sir,String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.MItem_Name, name);
        values.put(DbContractor.TableColumns.MItem_Price, prix);
        values.put(DbContractor.TableColumns.MQuantity, Quanty);
        values.put(DbContractor.TableColumns.MShopName, Sir);
        values.put(DbContractor.TableColumns.MDate, daat);
        db.update(DbContractor.TableColumns.ArchiveTable, values,  DbContractor.TableColumns._ID + " = ?" , new String[]{id});

        return true;
    }

    public boolean DeleteItemSelected(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DbContractor.TableColumns.MainTable,   DbContractor.TableColumns._ID + " = ?" , new String[]{id});
        return true;
    }

    public boolean DeleteItemSelectedArch(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DbContractor.TableColumns.ArchiveTable,   DbContractor.TableColumns._ID + " = ?" , new String[]{id});
        return true;
    }

    public Cursor GetDBdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery( "select " + DbContractor.TableColumns.MItem_Name + ",count(" + DbContractor.TableColumns.MItem_Name + ")as countIt from " +
                "" + DbContractor.TableColumns.MainTable + " group  by Item_Name order by countIt desc limit 8", null );
        return c1;

    }
    public Cursor GetDBdataArch() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c2 = db.rawQuery( "select " + DbContractor.TableColumns.MItem_Name + ",count(" + DbContractor.TableColumns.MItem_Name + ")as countIt from " +
                "" + DbContractor.TableColumns.MainTable + " group  by Item_Name order by countIt desc limit 8", null );
        return c2;

    }

    public Cursor GetdataByDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cu = db.rawQuery( "select " + DbContractor.TableColumns.MDate + ",sum(" + DbContractor.TableColumns.MItem_Price + ")as totalbydate from "
                + DbContractor.TableColumns.MainTable + " group  by " + DbContractor.TableColumns.MDate + " order by totalbydate desc limit 15", null );
        return cu;

    }
    public Cursor GetdataByDateArch() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cu = db.rawQuery( "select " + DbContractor.TableColumns.ArDate + ",sum(" + DbContractor.TableColumns.ArItem_Price + ")as totalbydateArch from "
                + DbContractor.TableColumns.ArchiveTable + " group  by " + DbContractor.TableColumns.ArDate + " order by totalbydateArch desc limit 15", null );
        return cu;

    }

    //delete table
    public boolean deleteall() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from  " + DbContractor.TableColumns.MainTable);
        return true;
    }


    public Cursor GetItemNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct " + DbContractor.TableColumns.MItem_Name + " from " + DbContractor.TableColumns.MainTable, null);
        return cur;
    }

    public Cursor GetMolhanot() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct " + DbContractor.TableColumns.MShopName + " from " + DbContractor.TableColumns.MainTable, null);
        return cur;
    }
    public Cursor GetSumall() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crall = db.rawQuery("SELECT SUM (Item_Price) FROM " + DbContractor.TableColumns.MainTable , null);
        return crall;
    }

    public Cursor getDates() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor datesCursor = db.rawQuery(" Select distinct " + DbContractor.TableColumns.MDate + " from " + DbContractor.TableColumns.MainTable + " order by " +
                "" +DbContractor.TableColumns.MDate + "", null);
        return datesCursor;
    }
    public boolean InjectQuotaData(double Q1, double Q2) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.MItem_Name, Q1);
        values.put(DbContractor.TableColumns.MItem_Price, Q2);
        long insertStaus = db.insert(DbContractor.TableColumns.MainTable, null, values);
        return insertStaus != -1;
    }

    public Cursor JibData() {
        // M3Actvt= new LoginActivity();
        SQLiteDatabase db = getReadableDatabase();
        Cursor b1 = db.rawQuery("select * from " +DbContractor.TableColumns.SettingsTable + " ORDER BY id DESC limit 1", null);
        return b1;
    }

}
