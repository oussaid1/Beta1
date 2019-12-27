package com.dev_bourheem.hadi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dev_bourheem.hadi.Login.LoginClass;

public class Main3Activity extends AppCompatActivity {
    Button loginBtn;
    EditText username, PasswordIn, register;
    public String userNm, PassIn, email;
    public String usr = "dev", pass = "dev";
    LoginClass Lgin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        PasswordIn = findViewById(R.id.PasswordIn);
        username = findViewById(R.id.loginNmIn);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Lgin = new LoginClass(getApplicationContext());
    }

    public void OpentAvtivity3() {
        final Intent intent2;
        intent2 = new Intent(this, MainActivity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent2);
    }
    public void MasterLogin(View v){

        userNm = username.getText().toString().trim();
        PassIn = PasswordIn.getText().toString().trim();
        if (userNm.equals(usr) && PassIn.equals(pass)) {
            OpentAvtivity3();
            MsgBox("welcome");
        }else MsgBox("try again");
    }
    /* public void logIn(){
         userNm = username.getText().toString().trim();
         PassIn=PasswordIn.getText().toString().trim();
         if (userNm.equals(usr) && PassIn.equals(pass) ) {
             OpentAvtivity3();
             username.setText("");
             PasswordIn.setText("");
        } else    MsgBox("pass not correct");

     }*/
    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

  /*  public void pushUserData(){
        boolean izdatainserted = TCF.Dirfihdata(userNm,PassIn,email);
        if (izdatainserted) MsgBox("Successful registration");
        else MsgBox("Sorry Couldn't register");
    }*/


   /* public void CheckIn() {
        userNm = username.getText().toString().trim();
        PassIn = PasswordIn.getText().toString().trim();
        ///SQLiteDatabase db = TCF.getReadableDatabase();
        Cursor c = Lgin.JibData();
        // To read or show singel data
        if (PasswordIn.getText().toString().trim().isEmpty() ||
                username.getText().toString().trim().isEmpty()) {

            MsgBox("Oops Make Sure You entered both Username and Password");

        } else {

            //To check wether the feed username and password match or not


            if (c.moveToNext()) {

                String loginUsername = c.getString(1);
                String loginPassword = c.getString(2);
                // String loginEmail = c.getString(3);
                DoIt(loginUsername, loginPassword);
                // showMessage(loginUsername, loginPassword
                        MsgBox("welcome");
            } else {

                MsgBox("Oops Username and Password does not match");

            }
        }
    }

    public void DoIt(String usr, String pss) {
        userNm = username.getText().toString().trim();
        PassIn = PasswordIn.getText().toString().trim();
        if (userNm.equals(usr)) MsgBox("welcome");
        else MsgBox("try again");
    }*/


}
