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
import com.google.android.gms.ads.AdView;

public class Main3Activity extends AppCompatActivity {
    public String userNm, PassIn;
    public String usr = "dev", pass = "dev";
    Button loginBtn;
    EditText username, PasswordIn;
    LoginClass Lgin;
    AdView adView;

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
                // calls the checking method
                CheckIn();
            }
        });

        Lgin = new LoginClass(getApplicationContext()); // initializing the LoginClass Object
        adView=findViewById(R.id.adView);


    }
// this method opens the main Activity

    public void OpentAvtivityMain() {
        final Intent intent2;
        intent2 = new Intent(this, MainActivity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent2);
    }

    // this method is  a backdoor for master code incase anthing happens
    public void MasterLogin(View view) {
        userNm = username.getText().toString().trim();
        PassIn = PasswordIn.getText().toString().trim();
        if (userNm.equals(usr) && PassIn.equals(pass)) {
            OpentAvtivityMain();
            MsgBox("welcome master");
        } else MsgBox("really master");
    }

    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

// this method verefies the Login Credencials

    public void CheckIn() {
        userNm = username.getText().toString().trim();
        PassIn = PasswordIn.getText().toString().trim();
        Cursor loginCusor = Lgin.JibLoginCredencials();
        if (loginCusor.getCount() == 0) {
            MsgBox("اسم المستعمل لم يتم حفظه");
            OpentAvtivityMain();
        }
        if (username.length() == 0 || PassIn.length() == 0) {

            MsgBox("عذرا. لم تدخل المعلومات");

        } else {

//put the cursor data into strings ( this cursor gets only the limited fisrt column
                while (loginCusor.moveToNext()) {

                    String loginUsername = loginCusor.getString(1).toLowerCase();
                    String loginPassword = loginCusor.getString(2).toLowerCase();
//To check wether the feed username and password match or not
                    if (loginUsername.equals(userNm) && loginPassword.equals(PassIn)) {

                        OpentAvtivityMain();
                        MsgBox("مرحبا");
                    } else
                        MsgBox("اسم المستخدم او القن غير صحيح / صحيحان");

                }
        }
    }


}



