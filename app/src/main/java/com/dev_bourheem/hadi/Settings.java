package com.dev_bourheem.hadi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dev_bourheem.hadi.Login.ForQuotas;
import com.dev_bourheem.hadi.Login.LoginClass;

public class Settings extends AppCompatActivity {

    EditText setQuota, setGuestQta, setusername, setpassword;
    Button saveBtnforuserQuotas, getSaveBtnforuserdata;
    TextView saveBtnvar;
    double user_defined_Quota, user_defined_guestQuota;
    String Userusername, Userpassword, email = "email", text;
    String filedata = "data";
    LoginClass Lgin;
    ForQuotas forQutaOC;
    MainActivity MainObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setQuota = findViewById(R.id.limitQuotaDef);
        setGuestQta = findViewById(R.id.limitGuestDef);
        setusername = findViewById(R.id.UserNameDef);
        setpassword = findViewById(R.id.PasswordDef);
        getSaveBtnforuserdata=findViewById(R.id.saveBtnforuserdata);
        saveBtnforuserQuotas = findViewById(R.id.saveBtnforquotas);
        getSaveBtnforuserdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        saveBtnforuserQuotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadQuotatoDatabase(); // injects user defined quota to database ;
                //GetQuotaFromDataBZ();

            }
        });

    }

    // opens main activity where add and total
    public void OpenActiviti() {
        Intent myintent = new Intent(this, MainActivity.class);
        startActivity(myintent);

    }

    public void LoadQuotatoDatabase() {
        forQutaOC = new ForQuotas(getApplicationContext());
        user_defined_Quota = Double.parseDouble(setQuota.getText().toString().trim());
        user_defined_guestQuota = Double.parseDouble(setGuestQta.getText().toString().trim());
        boolean newRowAdded = forQutaOC.InjectData(user_defined_Quota, user_defined_guestQuota);
        if (newRowAdded) {
            MsgBox("data saved");
        } else MsgBox("data not saved");
    }


    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
