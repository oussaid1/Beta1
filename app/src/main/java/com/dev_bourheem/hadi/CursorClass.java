package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;

import java.util.ArrayList;
import java.util.List;

public class CursorClass {
    private List ShopNames;
    private List ItemNames;
    private List DateStrings;
    private Double SumAll;
    private Double SumFor;
    private SQLiteDatabase db;
    private ContentValues values;

    public boolean InjectDataToMainTable(MyDataBaseCreator myDataBaseCreator, ProductObject productObject) {

        db = myDataBaseCreator.getWritableDatabase();
        values = new ContentValues();
        values.put(MyDataBaseCreator.MQuantity, productObject.getQuantity());
        values.put(MyDataBaseCreator.MQuantifier, productObject.getQuantifier());
        values.put(MyDataBaseCreator.MItem_Name, productObject.getItemName());
        values.put(MyDataBaseCreator.MItem_Price, productObject.getItemPrice());
        values.put(MyDataBaseCreator.MShopName, productObject.getShopName());
        values.put(MyDataBaseCreator.MDate, productObject.getDateBought());
        long insertStaus = db.insert(MyDataBaseCreator.MainTable, null, values);

        return insertStaus >= 1;
    }

  /*  public boolean InjectDataToArchiveTable(int id, String Quantifier, double Quantity, String ItemName, double prix, String ShopName, String date) {
//        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, id);
        values.put(ArQuantifier, Quantifier);
        values.put(ArQuantity, Quantity);
        values.put(ArItem_Name, ItemName);
        values.put(ArItem_Price, prix);
        values.put(ArShopName, ShopName);
        values.put(ArDate, date);
        long insertStaus = db.insert(ArchiveTable, null, values);

        return insertStaus >= 1;
    }

    public boolean InjectToArchivePayment(int id, String paidShop, double paidAmount, String paymentDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, id);
        values.put(ArPaidShopName, paidShop);
        values.put(ArPaidAmount, paidAmount);
        values.put(ArPaymentDate, paymentDate);
        db.insert(ArchivePaymentTable, null, values);

        return true;
    }

    public boolean updateData(String id, double Quanty, String name, double prix, String Sir, String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MItem_Name, name);
        values.put(MItem_Price, prix);
        values.put(MQuantity, Quanty);
        values.put(MShopName, Sir);
        values.put(MDate, daat);
        db.update(MainTable, values, _ID + " = ?", new String[]{id});

        return true;
    }

    public boolean updateDataArch(String id, double Quanty, String name, double prix, String Sir, String daat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ArItem_Name, name);
        values.put(ArItem_Price, prix);
        values.put(ArQuantity, Quanty);
        values.put(ArShopName, Sir);
        values.put(ArDate, daat);
        db.update(ArchiveTable, values, _ID + " = ?", new String[]{id});

        return true;
    }

    public boolean DeleteItemSelected(String table, String ById) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table, _ID + " = ?", new String[]{ById});
        return true;
    }

    public Cursor Get8MostFrequentItemsBought(String table, String columnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c1 = db.rawQuery("select " + columnName + ",count(" + columnName + ")as countIt from " +
                "" + table + " group  by " + columnName + " order by countIt desc limit 8", null);

        return c1;
    }

    public Cursor Get15HighDays(String table, String dateColumn, String itemPriceColumn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cu = db.rawQuery("select " + dateColumn + ",sum(" + itemPriceColumn + ")as totalbydate from "
                + table + " group  by " + dateColumn + " order by totalbydate desc limit 15", null);

        return cu;
    }

    public boolean deletAllFromTable(String table) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(" delete from  " + table);
        return true;
    }

    public ArrayList<String> GetDistinctFromTable(String table, String Column, String orderBy) {
        ArrayList<String> distinctStufList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("select distinct " + Column + " from " + table + "  order by " + orderBy + " asc ", null);
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            distinctStufList.add(cur.getString(cur.getColumnIndex(Column)));
            cur.moveToNext();
        }
        cur.close();
        return distinctStufList;
    }

    public Cursor GetSumall() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crall = db.rawQuery("SELECT SUM (Item_Price) FROM " + MainTable, null);

        return crall;
    }

    public double GetSumAllForShop(String Pricescolumn, String table, String shopNameColumn, String shopName) {
        double sumAllByShop;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curall = db.rawQuery(" SELECT SUM (" + Pricescolumn + ") as sumAll FROM " + table + "  where " + shopNameColumn +
                " like '% " + shopName + " % ' ", null);
        curall.moveToFirst();
        sumAllByShop = curall.getDouble(curall.getColumnIndex("sumAll"));
        curall.close();
        return sumAllByShop;

    }

    public boolean InjectQuotaData(double userQ, double userGQ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userQuota, userQ);
        values.put(userGQuota, userGQ);
        long insertStaus = db.insert(SettingsTable, null, values);
        return insertStaus != -1;
    }

    public Cursor JibData() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor b1 = db.rawQuery("select * from " + SettingsTable + " ORDER BY _id DESC limit 1", null);
        return b1;
    }

    public boolean DeleteAllBy(String table, String columnName, String like) {
        SQLiteDatabase db = getWritableDatabase();
        long deleted = db.delete(" " + table + "", columnName + " = ? ", new String[]{like});
        if (deleted > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean TablegetCountIsFull(String table, String ColumnNameLike, String mohamed) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + table + " " +
                "where " + ColumnNameLike + " like  '%" + mohamed + "%' ", null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean PayShop(String shopToPay, double PaidAmounta, String Paydate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PaidShopName, shopToPay);
        values.put(PaidAmount, PaidAmounta);
        values.put(PaymentDate, Paydate);
        long insertStaus = db.insert(PaymentTable, null, values);
        return insertStaus != -1;
    }

    public boolean UpdatePayment(String id, String shopToPay, double PaidAmounta, String PayDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PaidShopName, shopToPay);
        values.put(PaidAmount, PaidAmounta);
        values.put(PaymentDate, PayDate);
        long insertStaus = db.update(PaymentTable, values, _ID + " = ? ", new String[]{id});
        return insertStaus != -1;
    }

    public boolean UpdateArPayment(String id, String shopToPay, double PaidAmount, String PayDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ArPaidShopName, shopToPay);
        values.put(ArPaidAmount, PaidAmount);
        values.put(ArPaymentDate, PayDate);
        long insertStaus = db.update(ArchivePaymentTable, values, _ID + " = ? ", new String[]{id});
        return insertStaus != -1;
    }

    public double GetSumOfPaidAmountForShop(String ShopNameLike) {
        double paid = 0;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT SUM (" + PaidAmount + ") as sumOfShops from " + PaymentTable + " " +
                "where " + PaidShopName + " like  '%" + ShopNameLike + "%' ", null);
        if (c.getCount() == 0) {
            return paid = 0;
        } else if (!c.isAfterLast()) {
            c.moveToFirst();
            paid = c.getDouble(c.getColumnIndex("sumOfShops"));
        }
        return paid;
    }

    public double GetPaidAmountForAllShop(String table, String paidAmountColumn) {
        double paid;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT SUM (" + paidAmountColumn + ") from " + table + " ", null);
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

    public double GetSumForShop(String mohamed) {
        double sum = 0;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT SUM (" + MItem_Price + ") as sumOfItems from " + MainTable + " " +
                "where " + MShopName + " like  '%" + mohamed + "%' ", null);

        c.moveToFirst();
        sum = c.getDouble(c.getColumnIndex("sumOfItems"));

        c.close();
        return sum;
    }

    public double OweHimNothing(String ShopNameLike) {
        double owe = GetSumForShop(ShopNameLike) - GetSumOfPaidAmountForShop(ShopNameLike);
        return owe;
    }

    public void ArchiveIt(String ShopName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(" insert into " + ArchiveTable + "" +
                " (" + ArItem_Name + "," +
                "" + ArQuantifier + "," +
                "" + ArQuantity + "," +
                "" + ArItem_Price + " ," +
                "" + ArShopName + " ," +
                " " + ArDate + ") select " + MItem_Name + "," +
                " " + MQuantifier + "," +
                "" + MQuantity + "," +
                "" + MItem_Price + " ," +
                "" + MShopName + " ," +
                " " + MDate + " from " + MainTable + "" +
                " where " + MShopName + " like  '%" + ShopName + "%' ");

        db.execSQL(" insert into " + ArchivePaymentTable + "" +
                " (" + ArPaidShopName + "," +
                "" + ArPaidAmount + "," +
                "" + ArPaymentDate + ") select " + PaidShopName + "," +
                " " + PaidAmount + "," +
                "" + PaymentDate + " from " + PaymentTable + "" +
                " where " + PaidShopName + " like '%" + ShopName + "%' ");

        DeleteAllBy(MainTable, MShopName, ShopName);
        DeleteAllBy(PaymentTable, PaidShopName, ShopName);
    }*/
}
