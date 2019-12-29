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
    public static final String TABLE_NAME = "User";
    public static final String col33 = "username";
    public static final String col44= "password";
    private Main3Activity M3Actvt;

    public LoginClass (Context context){
        super(context,database_name,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT," + col33+ " TEXT," + col44 + " TEXT )");
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
        values.put(col33, name);
        values.put(col44, pass);

        long insertStaus = db.insert(this.TABLE_NAME, null, values);
        if (insertStaus == -1)
            return false;
        else
            return true;
    }

    public Cursor JibLoginCredencials (){
        M3Actvt= new Main3Activity();
        SQLiteDatabase db =getReadableDatabase();
       Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+ " order by ID Desc limit 1 " ,null );
        return c;
    }
}
