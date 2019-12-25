package com.dev_bourheem.hadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
Button loginBtn;
EditText username,PasswordIn,register;
String userNm,PassIn;
String pass="123ana",usr="ana";
    TableCretorForlogin TCF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        PasswordIn=findViewById(R.id.PasswordIn);
        username=findViewById(R.id.loginNmIn);
        loginBtn =findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
        TCF=new TableCretorForlogin(this);
    }
    public void OpentAvtivity3() {
        final Intent intent2;
        intent2 = new Intent(this, MainActivity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent2);
    }
    public void logIn(){
        userNm = username.getText().toString();
        PassIn=PasswordIn.getText().toString();
        if (userNm.equals(usr) && PassIn.equals(pass) ) {
            OpentAvtivity3();
            username.setText("");
            PasswordIn.setText("");
        } else    MsgBox("pass not correct");

    }
    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void pushUserData(){
        boolean izdatainserted = TCF.Dirfihdata(userNm,PassIn);
        if (izdatainserted) MsgBox("Successful registration");
        else MsgBox("Sorry Couldn't register");
    }
    public void awidusrData(){


    }
}
