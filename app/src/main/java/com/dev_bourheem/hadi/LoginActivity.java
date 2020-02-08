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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class LoginActivity extends AppCompatActivity {
    public String userNm, PassIn;
    public String usr = "dev", pass = "dev";
    Button loginBtn;
    EditText username, PasswordIn;
    LoginClass Lgin;
    AdView adlogin;



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
                    CheckIn();
                // calls the checking method
            }
        });

        Lgin = new LoginClass(getApplicationContext()); // initializing the LoginClass Object

        //AdsLogin();
    }

    public void AdsLogin() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adlogin = findViewById(R.id.adlogin);
        AdRequest adRequest = new AdRequest.Builder().build();
        adlogin.loadAd(adRequest);

    }
// this method opens the main Activity

    public void OpentAvtivityMain() {
        final Intent intent2;
        intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }

    // this method is  a backdoor for master code incase anthing happens
    public void MasterLogin(View view) {
        userNm = username.getText().toString().trim();
        PassIn = PasswordIn.getText().toString().trim();
        if (userNm.equals(usr) && PassIn.equals(pass)) {
            OpentAvtivityMain();
            MsgBox("Master");
        } else return;
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
            MsgBox("اسم المستعمل لم يتم إعداده");
            OpentAvtivityMain();
        }
        if (username.getText().toString().trim().length() == 0 || PasswordIn.getText().toString().trim().length()== 0) {

            MsgBox("عذرا. لم تقم بادخال معلومات المسخدم");

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



