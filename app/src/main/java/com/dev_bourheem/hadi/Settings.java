package com.dev_bourheem.hadi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Double.valueOf;

public class Settings extends AppCompatActivity {

    EditText setQuota, setGuestQta, setusername, setpassword;
    TextView saveBtnvar;
    double userQuota, userguestQuota;
    String Userusername, Userpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setQuota = findViewById(R.id.limitQuotaDef);
        setGuestQta = findViewById(R.id.limitGuestDef);
        setusername = findViewById(R.id.UserNameDef);
        setpassword = findViewById(R.id.PasswordDef);
        saveBtnvar = findViewById(R.id.saveBtn);
        saveBtnvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetGetTotalandQuota();
            }
        });

    }


    public void OpenActiviti() {
        Intent myintent = new Intent(this, MainActivity.class);
        startActivity(myintent);
    }

    public void SetGetTotalandQuota() {
        userQuota =valueOf(setQuota.getText().toString().trim());
        userguestQuota= valueOf(setGuestQta.getText().toString().trim());
        Toast.makeText(this,""+userQuota,Toast.LENGTH_SHORT).show();
    }

}
