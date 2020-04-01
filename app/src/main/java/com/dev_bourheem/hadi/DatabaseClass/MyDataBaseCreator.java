package com.dev_bourheem.hadi.DatabaseClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyDataBaseCreator extends SQLiteOpenHelper {
    public static final String database_name = "Merchandise.db";
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
            " integer primary key autoincrement , "+DbContractor.TableColumns.PaidShopName + " text ," +
            "" +DbContractor.TableColumns.PaidAmount+" double , "+DbContractor.TableColumns.PaymentDate+" DATE )";
    /*****************************************************************************/
    private static final String CreateArchivePaymentTable= " Create Table " + DbContractor.TableColumns.ArchivePaymentTable +" ("+ DbContractor.TableColumns._ID +"" +
            " integer primary key autoincrement , "+DbContractor.TableColumns.ArPaidShopName + " text ," +
            "" +DbContractor.TableColumns.ArPaidAmount + " double , " + DbContractor.TableColumns.ArPaymentDate+" date )";
    /*****************************************************************************/
    private static final String CreateSettingsTable= "Create Table "+DbContractor.TableColumns.SettingsTable +" ("+ DbContractor.TableColumns._ID +"" +
            " integer primary key autoincrement , "+DbContractor.TableColumns.userQuota + " double ," +
            "" + DbContractor.TableColumns.userGQuota +" double )";
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
        db.execSQL(CreateArchivePaymentTable);
        db.execSQL(CreateSettingsTable);
        db.execSQL(CreateInfoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DbContractor.TableColumns.MainTable);
        db.execSQL("drop table if exists " + DbContractor.TableColumns.ArchiveTable);
        db.execSQL("drop table if exists " + DbContractor.TableColumns.PaymentTable);
        db.execSQL("drop table if exists " + DbContractor.TableColumns.ArchivePaymentTable);
        db.execSQL("drop table if exists " + DbContractor.TableColumns.InfoTable);
        db.execSQL("drop table if exists " + DbContractor.TableColumns.SettingsTable);

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
        values.put(DbContractor.TableColumns.ArItem_Name, name);
        values.put(DbContractor.TableColumns.ArItem_Price, prix);
        values.put(DbContractor.TableColumns.ArQuantity, Quanty);
        values.put(DbContractor.TableColumns.ArShopName, Sir);
        values.put(DbContractor.TableColumns.ArDate, daat);
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
// this is for Stats MainTable
    public Cursor Get8MostFrequentItemsBought(String table, String columnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery( "select " + columnName + ",count(" + columnName + ")as countIt from " +
                "" + table + " group  by "+columnName+" order by countIt desc limit 8", null );
        return c1;
    }


    public Cursor Get15HighDays(String table, String dateColumn,String itemPriceColumn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cu = db.rawQuery( "select " + dateColumn + ",sum(" + itemPriceColumn + ")as totalbydate from "
                +table+ " group  by " + dateColumn + " order by totalbydate desc limit 15", null );
        return cu;
    }

    //delete table
    public boolean deletAllTable(String table) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from  " + table);
        return true;
    }

    public ArrayList<String> GetDistinctFromTable(String table, String Column, String orderBy) {
        ArrayList<String> distinctStufList= new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct " + Column + " from " + table+"  order by "+orderBy+" asc ", null);
        cur.moveToFirst();
        while (!cur.isAfterLast()){
            distinctStufList.add(cur.getString(cur.getColumnIndex(Column)));
            cur.moveToNext();
        }
        cur.close();
        return distinctStufList;
    }


    public Cursor GetSumall() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crall = db.rawQuery("SELECT SUM (Item_Price) FROM " + DbContractor.TableColumns.MainTable , null);
        return crall;
    }

    public boolean InjectQuotaData(double userQ, double userGQ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.userQuota, userQ);
        values.put(DbContractor.TableColumns.userGQuota, userGQ);
        long insertStaus = db.insert(DbContractor.TableColumns.SettingsTable, null, values);
        return insertStaus != -1;
    }

    public Cursor JibData() {
        // M3Actvt= new LoginActivity();
        SQLiteDatabase db = getReadableDatabase();
        Cursor b1 = db.rawQuery("select * from " +DbContractor.TableColumns.SettingsTable + " ORDER BY _id DESC limit 1", null);
        return b1;
    }
    public void DeleteAllBy(Context context,String table, String ToDalete) {
        SQLiteDatabase db = getWritableDatabase();
        long deleted = db.delete(DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MShopName + " = ?", new String[]{ToDalete});
        if (deleted > 0) {
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "not deleted", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean PayShop(String shopToPay,double PaidAmount,String Paydate){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContractor.TableColumns.PaidShopName, shopToPay);
        values.put(DbContractor.TableColumns.PaidAmount, PaidAmount);
        values.put(DbContractor.TableColumns.PaymentDate, Paydate);
        long insertStaus = db.insert(DbContractor.TableColumns.PaymentTable, null, values);
        return insertStaus != -1;
    }
    public double GetPaidAmountForShop(String table,String shopName,String paidAmountColumn, String ShopNameLike){
        double paid ;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT SUM ("+ paidAmountColumn +") as sumOfShop from " + table + " " +
                "where "+shopName+" like  '%" + ShopNameLike + "%' ORDER BY " + shopName + " asc", null);
        if (c.getCount() == 0) {
            return paid = 0;
        } else {
            c.moveToFirst();
            paid = c.getDouble(c.getColumnIndex("sumOfShop"));
            //closing cursor so as not to bring anything else or ruin sth
            c.close();
            return paid;
        }
    }
    public double GetPaidAmountForAllShop(String table,String paidAmountColumn){
        double paid ;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT SUM ("+ paidAmountColumn +") from "+table+" ", null);
        if (c.getCount() == 0) {
            return paid = 0;
        } else {
            c.moveToFirst();
            paid = c.getDouble(0);
            //closing cursor so as not to bring anything else or ruin sth
            c.close();
            return paid;
        }
    }

}
