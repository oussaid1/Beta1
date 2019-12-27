package com.dev_bourheem.hadi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class TableCretorForlogin extends SQLiteOpenHelper {
    public static final String DatabaseName = "userdata.db";
    public static final String userTable ="userdata";
    public static String userColomn="user";
    public static String passColomn="password";
    public static String emailColomn="emailColomn";
    Main3Activity M3Actvt;
    public TableCretorForlogin(Context context) {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //    db.execSQL("CREATE TABLE "+usrdataTable+" (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + userColomn +" TEXT,"+ passColomn+ " TEXT )");
        db.execSQL("CREATE TABLE " + userTable + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," + userColomn + " TEXT," + passColomn + " TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + userTable );
        onCreate(db);
    }
    public  boolean Dirfihdata(String usrname , String userpass ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(userColomn ,usrname);
        contentValues.put(passColomn , userpass);
        //contentValues.put(passColomn , email);
       long boo= db.insert(userTable ,null,contentValues );
       if (boo==-1) return false;
       else return true;
    }
    public Cursor JibData (){
        M3Actvt= new Main3Activity();
        SQLiteDatabase db =getReadableDatabase();
        //Cursor crs = db.rawQuery("select * from "+ usrdataTable , null);
        Cursor c = db.rawQuery("SELECT * FROM "+userTable+" WHERE "+userColomn+" ='" + M3Actvt.userNm+ "'" + "AND "+passColomn+" ='"+M3Actvt.PassIn+"'", null);
        return c;
    }



}
