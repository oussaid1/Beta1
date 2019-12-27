package com.dev_bourheem.hadi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dev_bourheem.hadi.Login.LoginClass;

import static java.lang.Double.valueOf;

public class Settings extends AppCompatActivity {

    EditText setQuota, setGuestQta, setusername, setpassword;
    TextView saveBtnvar;
    double userQuota, userguestQuota;
    String Userusername, Userpassword , email ="email";
    TableCretorForlogin MyTCF;
    LoginClass Lgin;

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
                //SetGetTotalandQuota();
                if (setusername.length()==0 || setpassword.length()==0 || setQuota.length()==0 || setGuestQta.length()==0 )
                    MsgBox( " Please Insert Data");
                else
                LoadDatabase();
               // SetQuotaz();
            }
        });
        Lgin = new LoginClass(getApplicationContext());
        MyTCF = new TableCretorForlogin(getApplicationContext());
    }


    public void OpenActiviti() {
        Intent myintent = new Intent(this, MainActivity.class);
        startActivity(myintent);
    }

    public void SetQuotaz() {
        userQuota = valueOf(setQuota.getText().toString().trim());
        userguestQuota = valueOf(setGuestQta.getText().toString().trim());


        Toast.makeText(this, " Quota set successfully" + userQuota, Toast.LENGTH_SHORT).show();
    }

    public void LoadDatabase() {
// get !!!!!!!!!edditext input to vars.

        Userusername = setusername.getText().toString().trim();
        Userpassword = setpassword.getText().toString().trim();

// insert data to database's Table.
        boolean newRowAdded = Lgin.InjectData("dell", "dell");
        if (newRowAdded) {
            MsgBox(" registered");
        } else MsgBox("not registered");
    }
    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
