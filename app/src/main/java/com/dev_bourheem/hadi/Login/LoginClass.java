package com.dev_bourheem.hadi.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dev_bourheem.hadi.Main3Activity;


public class LoginClass extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public static final int ID = 1;
    public static final String database_name= "Teachers.db";
    public static final String TABLE_NAME = "Hugs";
    public static final String col1 = "Item_Name";
    public static final String col2 = "Item_Price";
    private Main3Activity M3Actvt;

    public LoginClass (Context context){
        super(context,database_name,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT," + col1 + " TEXT," + col2 + " TEXT )");
        // db.execSQL("CREATE TABLE "+ SHOP_TABLE + "(_ID INTEGER Primary key autoincrement,"+SHOP_NAME +" TEXT, " +SHOP_PHONE +" TEXT, "+SHOP_EMAIL+ " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        // db.execSQL("drop table if exists molhanotinfo");
        onCreate(db);
    }
    public boolean InjectData(String name, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, name);
        values.put(col2, pass);

        long insertStaus = db.insert(this.TABLE_NAME, null, values);
        if (insertStaus == -1)
            return false;
        else
            return true;
    }

    public Cursor JibData (){
        M3Actvt= new Main3Activity();
        SQLiteDatabase db =getReadableDatabase();
        //Cursor crs = db.rawQuery("select * from "+ usrdataTable , null);
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+col1+" ='" + M3Actvt.userNm+ "'" + "AND "+col2+" ='"+M3Actvt.PassIn+"'", null);
        return c;
    }
}
